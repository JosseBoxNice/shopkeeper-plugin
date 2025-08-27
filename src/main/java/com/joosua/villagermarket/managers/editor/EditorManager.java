package com.joosua.villagermarket.managers.editor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditorManager {
    public Inventory getVillagerEdit() {
        Inventory edit = Bukkit.createInventory(null, 27, "Editor");
        // Add a trade button
        ItemStack addTrade = new ItemStack(Material.EMERALD);
        ItemMeta addTradeMeta = addTrade.getItemMeta();
        addTradeMeta.setDisplayName(ChatColor.GREEN + "Add a trade");
        addTrade.setItemMeta(addTradeMeta);
        edit.setItem(13, addTrade);

        return edit;
    }
}
