package com.rpg.hud;

import com.rpg.util.ItemFactoryUtil;
import com.rpg.util.LastInventory;
import com.rpg.util.TranslateUtil;
import com.rpg.util.colorutil.ChatColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ClassMenu implements Listener {

    public static Inventory classMenu = Bukkit.createInventory(null, 9, ChatColorUtil.textColor(TranslateUtil.getKey("CHOOSE_CLASS"), ChatColor.RED));

    static {
        classMenu.setItem(0, ItemFactoryUtil.squireClass());
        classMenu.setItem(1, ItemFactoryUtil.menuDivisor());
        classMenu.setItem(2, ItemFactoryUtil.menuDivisor());
        classMenu.setItem(3, ItemFactoryUtil.menuDivisor());
        classMenu.setItem(4, ItemFactoryUtil.menuDivisor());
        classMenu.setItem(5, ItemFactoryUtil.menuDivisor());
        classMenu.setItem(6, ItemFactoryUtil.menuDivisor());
        classMenu.setItem(7, ItemFactoryUtil.menuDivisor());
        classMenu.setItem(8, ItemFactoryUtil.menuDivisor());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if( classMenu == null || event.getClickedInventory() == null || !event.getClickedInventory().equals(classMenu))
            return;

        if(event.getInventory().equals(classMenu))
            event.setCancelled(true);


        if(event.getCurrentItem().equals( ItemFactoryUtil.squireClass())){
            LastInventory.setLastInv(classMenu);
            event.getWhoClicked().openInventory(SquireMenu.squireMenu);
        }
    }

}
