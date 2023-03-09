package com.rpg.hud;

import com.rpg.domain.PlayerStatus;
import com.rpg.repository.impl.PlayerStatusImpl;
import com.rpg.service.LoaderPlayerStatusService;
import com.rpg.util.ItemFactoryUtil;
import com.rpg.util.TranslateUtil;
import com.rpg.util.colorutil.ChatColorUtil;
import com.rpg.util.config.ConfigPlayers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class StatusMenu implements Listener {

    private static ConfigPlayers configPlayers = ConfigPlayers.getInstance();

    private static Map<UUID,Integer> playerStrengthAttribute = new HashMap<>();
    private static Map<UUID,Integer> playerDexterityAttribute = new HashMap<>();
    private static Map<UUID,Integer> playerConstitutionAttribute = new HashMap<>();
    private static Map<UUID,Integer> playerIntelligenceAttribute = new HashMap<>();
    private static Map<UUID,Integer> playerWisdomAttribute = new HashMap<>();
    private static Map<UUID,Integer> playerCharismaAttribute = new HashMap<>();
    private static Map<UUID,Integer> tempCoreStatus = new HashMap<>();

    public static Inventory getInventoryByPlayer(Player player){
        Inventory statusMenu = Bukkit.createInventory(player, 18, ChatColorUtil.textColor(TranslateUtil.getKey("STATUS"), ChatColor.BLACK));
        ItemFactoryUtil  itemFactoryUtil =  new ItemFactoryUtil();
        ConfigurationSection playerSection = configPlayers.getConfiguration(player);
        ConfigurationSection attributeSection = playerSection.getConfigurationSection("Attributes");

        int strength =  attributeSection.getInt("strength");
        int dexterity =  attributeSection.getInt("dexterity");
        int constitution =  attributeSection.getInt("constitution");
        int intelligence =  attributeSection.getInt("intelligence");
        int wisdom =  attributeSection.getInt("wisdom");
        int charisma =  attributeSection.getInt("charisma");

        statusMenu.setItem(1,itemFactoryUtil.strength(strength));
        statusMenu.setItem(2,itemFactoryUtil.dexterity(dexterity));
        statusMenu.setItem(3,itemFactoryUtil.constitution(constitution));
        statusMenu.setItem(5,itemFactoryUtil.wisdom(intelligence));
        statusMenu.setItem(6,itemFactoryUtil.intelligence(wisdom));
        statusMenu.setItem(7,itemFactoryUtil.charisma(charisma));

        statusMenu.setItem(13, ItemFactoryUtil.edit());


        playerStrengthAttribute.put(player.getUniqueId(),strength);
        playerDexterityAttribute.put(player.getUniqueId(),dexterity);
        playerConstitutionAttribute.put(player.getUniqueId(),constitution);
        playerIntelligenceAttribute.put(player.getUniqueId(),intelligence);
        playerWisdomAttribute.put(player.getUniqueId(),wisdom);
        playerCharismaAttribute.put(player.getUniqueId(),charisma);

        tempCoreStatus.put(player.getUniqueId(),playerSection.getInt("core"));

        return  statusMenu;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
         Inventory statusMenu = event.getInventory();

        if(!event.getView().getTitle().equalsIgnoreCase(ChatColorUtil.textColor(TranslateUtil.getKey("STATUS"), ChatColor.BLACK)))
            return;

        if(event.getInventory().equals(statusMenu))
            event.setCancelled(true);

        if(Objects.equals(event.getCurrentItem(), ItemFactoryUtil.cancel())){
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().openInventory(StatusMenu.getInventoryByPlayer((Player) event.getWhoClicked()));
        }

        if(Objects.equals(event.getCurrentItem(), ItemFactoryUtil.edit())){
            statusMenu.setItem(9,ItemFactoryUtil.aprove());
            statusMenu.setItem(17,ItemFactoryUtil.cancel());
            statusMenu.setItem(13, null);
        }


        if(statusMenu.contains(ItemFactoryUtil.edit()))
            return;

        Player player = (Player) event.getWhoClicked();

        ConfigurationSection playerSection = configPlayers.getConfiguration(player);
        ConfigurationSection attributeSection = playerSection.getConfigurationSection("Attributes");

        int tempCore = tempCoreStatus.get(player.getUniqueId());

        int temp_strength = playerStrengthAttribute.get(player.getUniqueId());
        int temp_dexterity = playerDexterityAttribute.get(player.getUniqueId());
        int temp_constitution =  playerConstitutionAttribute.get(player.getUniqueId());
        int temp_intelligence =  playerIntelligenceAttribute.get(player.getUniqueId());
        int temp_wisdom =  playerWisdomAttribute.get(player.getUniqueId());
        int temp_charisma = playerCharismaAttribute.get(player.getUniqueId());

        if(attributeSection == null)
            return;
        int strength =  attributeSection.getInt("strength");
        int dexterity =  attributeSection.getInt("dexterity");
        int constitution =  attributeSection.getInt("constitution");
        int intelligence =  attributeSection.getInt("intelligence");
        int wisdom =  attributeSection.getInt("wisdom");
        int charisma =  attributeSection.getInt("charisma");

        if(Objects.equals(event.getCurrentItem(), ItemFactoryUtil.aprove())){

            if(temp_constitution > constitution)
              playerSection.set("maxHealth", playerSection.getDouble("maxHealth")+ temp_constitution);

            playerSection.set("core", tempCore);

            attributeSection.set("strength",temp_strength);
            attributeSection.set("dexterity",temp_dexterity);
            attributeSection.set("constitution",temp_constitution);
            attributeSection.set("intelligence",temp_intelligence);
            attributeSection.set("wisdom",temp_wisdom);
            attributeSection.set("charisma",temp_charisma);
            configPlayers.saveChanges();

            PlayerStatusImpl.calculateCurrentHealth(player,(playerSection.getDouble("maxHealth")) );
            LoaderPlayerStatusService.createScoreBoardAutoUpdate(player);
            event.getWhoClicked().closeInventory();
        }

        if( event.getCurrentItem() == null)
            return;

        if( event.getCurrentItem().getItemMeta() == null)
            return;

        if( !event.getCurrentItem().getItemMeta().hasCustomModelData())
            return;

        if (event.getCurrentItem().getItemMeta().getCustomModelData() == 1001) {
            if (event.getClick().isLeftClick() && tempCore > 0){

                temp_strength = incrementValue(playerStrengthAttribute, temp_strength, player);
                buildItemAttribute(temp_strength , event);
            }

            if (event.getClick().isRightClick() && temp_strength > strength ){
                temp_strength = decrementValue(playerStrengthAttribute,temp_strength, player);
                buildItemAttribute(temp_strength , event);
            }
        }

        if ( event.getCurrentItem().getItemMeta().getCustomModelData() == 1002) {
            if (event.getClick().isLeftClick() && tempCore > 0){
                temp_dexterity = incrementValue(playerDexterityAttribute, temp_dexterity, player);
                buildItemAttribute(temp_dexterity , event);
            }

            if (event.getClick().isRightClick() && temp_dexterity > dexterity ){
                temp_dexterity = decrementValue(playerDexterityAttribute,temp_dexterity, player);
                buildItemAttribute(temp_dexterity , event);
            }
        }

        if ( event.getCurrentItem().getItemMeta().getCustomModelData() == 1003) {
            if (event.getClick().isLeftClick() && tempCore > 0){
                temp_constitution = incrementValue(playerConstitutionAttribute, temp_constitution, player);
                buildItemAttribute(temp_constitution , event);
            }

            if (event.getClick().isRightClick() && temp_constitution > constitution ){
                temp_constitution = decrementValue(playerConstitutionAttribute,temp_constitution, player);
                buildItemAttribute(temp_constitution , event);
            }
        }

        if ( event.getCurrentItem().getItemMeta().getCustomModelData() == 1004) {
            if (event.getClick().isLeftClick() && tempCore > 0){
                temp_intelligence = incrementValue(playerIntelligenceAttribute, temp_intelligence, player);
                buildItemAttribute(temp_intelligence , event);
            }

            if (event.getClick().isRightClick() && temp_intelligence > intelligence ){
                temp_intelligence = decrementValue(playerIntelligenceAttribute,temp_intelligence, player);
                buildItemAttribute(temp_intelligence , event);
            }
        }

        if ( event.getCurrentItem().getItemMeta().getCustomModelData() == 1005) {
            if (event.getClick().isLeftClick() && tempCore > 0){
                temp_wisdom = incrementValue(playerWisdomAttribute, temp_wisdom, player);
                buildItemAttribute(temp_wisdom , event);
            }

            if (event.getClick().isRightClick() && temp_wisdom > wisdom ){
                temp_wisdom = decrementValue(playerWisdomAttribute, temp_wisdom, player);
                buildItemAttribute(temp_wisdom , event);
            }
        }

        if ( event.getCurrentItem().getItemMeta().getCustomModelData() == 1006) {
            if (event.getClick().isLeftClick() && tempCore > 0){
                temp_charisma = incrementValue(playerCharismaAttribute, temp_charisma, player);
                buildItemAttribute(temp_charisma , event);
            }

            if (event.getClick().isRightClick() && temp_charisma > charisma ){
                temp_charisma = decrementValue(playerCharismaAttribute, temp_charisma, player);
                buildItemAttribute(temp_charisma , event);
            }
        }
    }


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){

        if(!event.getView().getTitle().equalsIgnoreCase(ChatColorUtil.textColor(TranslateUtil.getKey("STATUS"), ChatColor.BLACK)))
            return;

        Player player = (Player) event.getPlayer();

        playerStrengthAttribute.remove(player.getUniqueId());
        playerDexterityAttribute.remove(player.getUniqueId());
        playerConstitutionAttribute.remove(player.getUniqueId());
        playerIntelligenceAttribute.remove(player.getUniqueId());
        playerWisdomAttribute.remove(player.getUniqueId());
        playerCharismaAttribute.remove(player.getUniqueId());
    }



    private int incrementValue(Map<UUID,Integer> listAttribute, int attribute , Player player){
        tempCoreStatus.put(player.getUniqueId(), tempCoreStatus.get(player.getUniqueId()) -1);
        listAttribute.put(player.getUniqueId(), attribute +1 );
        return listAttribute.get(player.getUniqueId());
    }

    private int decrementValue(Map<UUID,Integer> listAttribute, int attribute, Player player ){
        tempCoreStatus.put(player.getUniqueId(), tempCoreStatus.get(player.getUniqueId()) +1);
        listAttribute.put(player.getUniqueId(), attribute -1 );
        return listAttribute.get(player.getUniqueId());
    }

    private void buildItemAttribute(int attribute , InventoryClickEvent event){

        ItemStack clickedItem = event.getCurrentItem();
        ItemMeta clickedItemMeta = clickedItem.getItemMeta();

        List<String> lore = new ArrayList<>();
        for(int index = 0; index < clickedItemMeta.getLore().size(); index++){
            if(index == 0)
                lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ATTRIBUTE")+": ", ChatColor.YELLOW)+ChatColor.GRAY+attribute);
            else
                lore.add(clickedItemMeta.getLore().get(index));
        }
        clickedItemMeta.setLore(lore);
        clickedItem.setItemMeta(clickedItemMeta);
    }


}
