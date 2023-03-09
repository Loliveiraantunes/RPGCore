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

import java.util.Objects;

public class SquireMenu implements Listener {
    public static Inventory squireMenu = Bukkit.createInventory(null, 36, ChatColorUtil.boldText(TranslateUtil.getKey("SQUIRE"), ChatColor.BLACK));

    static {
        squireMenu.setItem(0, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(1, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(2, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(3, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(4, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(5, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(6, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(7, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(8, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(9, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(10, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(11, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(12, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(13, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(14, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(15, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(16, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(17, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(18, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(19, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(20, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(21, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(22, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(23, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(24, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(25, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(26, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(27, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(28, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(29, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(30, ItemFactoryUtil.aprove());
        squireMenu.setItem(31, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(32, ItemFactoryUtil.cancel());
        squireMenu.setItem(33, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(34, ItemFactoryUtil.menuDivisor());
        squireMenu.setItem(35, ItemFactoryUtil.menuDivisor());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if(squireMenu == null || event.getClickedInventory() == null || !event.getClickedInventory().equals(squireMenu))
            return;

        if(event.getInventory().equals(squireMenu))
            event.setCancelled(true);

        if(Objects.equals(event.getCurrentItem(), ItemFactoryUtil.cancel())){
            event.getWhoClicked().openInventory(LastInventory.getLastInv());
        }
    }
}
