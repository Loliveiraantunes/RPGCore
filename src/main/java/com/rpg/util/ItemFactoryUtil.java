package com.rpg.util;

import com.rpg.util.colorutil.ChatColorUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemFactoryUtil {

    public static ItemStack menuDivisor(){
        ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColorUtil.textColor("X", ChatColor.GRAY));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    public static ItemStack squireClass(){
        ItemStack itemStack = new ItemStack(Material.STONE_SWORD, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColorUtil.textColor(TranslateUtil.getKey("SQUIRE"), ChatColor.YELLOW));
        List<String> lore = new ArrayList<>();

        String loreMessage = TranslateUtil.getKey("SQUIRE_LORE");

        Arrays.stream(loreMessage.split("\n")).forEach( str->{
            lore.add(str);
        });

        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    public static ItemStack aprove(){
        ItemStack itemStack = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColorUtil.textColor(TranslateUtil.getKey("APPROVE"), ChatColor.GREEN));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack cancel(){
        ItemStack itemStack = new ItemStack(Material.BARRIER, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColorUtil.textColor(TranslateUtil.getKey("CANCEL"), ChatColor.RED));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    public static ItemStack edit(){
        ItemStack itemStack = new ItemStack(Material.WRITABLE_BOOK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColorUtil.textColor(TranslateUtil.getKey("EDIT"), ChatColor.GREEN));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack strength(int attributeValue){

        ItemStack itemStack = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColorUtil.textColor(TranslateUtil.getKey("STRENGTH"), ChatColor.GREEN));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ATTRIBUTE")+": ",ChatColor.YELLOW)+ChatColor.GRAY+attributeValue);
        lore.add("\n");

        Arrays.stream(TranslateUtil.getKey("STRENGTH_INFO").replace("{$break}","\n").split("\n")).forEach( str->{
            lore.add(str);
        });
        lore.add("\n");
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ADD_CORE"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [+] "+TranslateUtil.getKey("LEFT_CLICK"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [-] "+TranslateUtil.getKey("RIGHT_CLICK"),ChatColor.GRAY));

        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(1001);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack dexterity( int attributeValue){

        ItemStack itemStack = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColorUtil.textColor(TranslateUtil.getKey("DEXTERITY"), ChatColor.GREEN));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ATTRIBUTE")+": ",ChatColor.YELLOW)+ChatColor.GRAY+attributeValue);
        lore.add("\n");
        Arrays.stream(TranslateUtil.getKey("DEXTERITY_INFO").replace("{$break}","\n").split("\n")).forEach( str->{
            lore.add(str);
        });
        lore.add("\n");
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ADD_CORE"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [+] "+TranslateUtil.getKey("LEFT_CLICK"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [-] "+TranslateUtil.getKey("RIGHT_CLICK"),ChatColor.GRAY));

        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(1002);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public  ItemStack constitution(  int attributeValue){

        ItemStack itemStack = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColorUtil.textColor(TranslateUtil.getKey("CONSTITUTION"), ChatColor.GREEN));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ATTRIBUTE")+": ",ChatColor.YELLOW)+ChatColor.GRAY+attributeValue);
        lore.add("\n");
        Arrays.stream(TranslateUtil.getKey("CONSTITUTION_INFO").replace("{$break}","\n").split("\n")).forEach( str->{
            lore.add(str);
        });
        lore.add("\n");
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ADD_CORE"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [+] "+TranslateUtil.getKey("LEFT_CLICK"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [-] "+TranslateUtil.getKey("RIGHT_CLICK"),ChatColor.GRAY));

        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(1003);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack intelligence( int attributeValue){

        ItemStack itemStack = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColorUtil.textColor(TranslateUtil.getKey("INTELLIGENCE"), ChatColor.GREEN));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ATTRIBUTE")+": ",ChatColor.YELLOW)+ChatColor.GRAY+attributeValue);
        lore.add("\n");
        Arrays.stream(TranslateUtil.getKey("INTELLIGENCE_INFO").replace("{$break}","\n").split("\n")).forEach( str->{
            lore.add(str);
        });
        lore.add("\n");
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ADD_CORE"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [+] "+TranslateUtil.getKey("LEFT_CLICK"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [-] "+TranslateUtil.getKey("RIGHT_CLICK"),ChatColor.GRAY));

        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(1004);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }



    public ItemStack wisdom( int attributeValue){

        ItemStack itemStack = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColorUtil.textColor(TranslateUtil.getKey("WISDOM"), ChatColor.GREEN));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ATTRIBUTE")+": ",ChatColor.YELLOW)+ChatColor.GRAY+attributeValue);
        lore.add("\n");
        Arrays.stream(TranslateUtil.getKey("WISDOM_INFO").replace("{$break}","\n").split("\n")).forEach( str->{
            lore.add(str);
        });
        lore.add("\n");
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ADD_CORE"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [+] "+TranslateUtil.getKey("LEFT_CLICK"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [-] "+TranslateUtil.getKey("RIGHT_CLICK"),ChatColor.GRAY));
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(1005);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    public ItemStack charisma( int attributeValue){

        ItemStack itemStack = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(ChatColorUtil.textColor(TranslateUtil.getKey("CHARISMA"), ChatColor.GREEN));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ATTRIBUTE")+": ",ChatColor.YELLOW)+ChatColor.GRAY+attributeValue);
        lore.add("\n");
        Arrays.stream(TranslateUtil.getKey("CHARISMA_INFO").replace("{$break}","\n").split("\n")).forEach( str->{
            lore.add(str);
        });
        lore.add("\n");
        lore.add(ChatColorUtil.textColor(TranslateUtil.getKey("ADD_CORE"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [+] "+TranslateUtil.getKey("LEFT_CLICK"),ChatColor.GRAY));
        lore.add(ChatColorUtil.textColor("   [-] "+TranslateUtil.getKey("RIGHT_CLICK"),ChatColor.GRAY));
        itemMeta.setLore(lore);

        itemMeta.setCustomModelData(1006);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }



}
