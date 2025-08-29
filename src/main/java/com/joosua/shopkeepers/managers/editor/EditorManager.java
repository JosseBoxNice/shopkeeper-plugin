package com.joosua.shopkeepers.managers.editor;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.holders.IdInventoryHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EditorManager {
    private final ShopkeepersPlugin plugin;
    public EditorManager(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
    }
    public Inventory getVillagerEdit(UUID id) {
        Inventory edit = new IdInventoryHolder(id, 27, "Editor").getInventory();
        // Add a trade button
        ItemStack addTrade = plugin.getMainManager().quickItem(Material.EMERALD, ChatColor.GREEN + "Add a trade", null);
        edit.setItem(14, addTrade);
        // Remove a trade button
        ItemStack removeTrade = plugin.getMainManager().quickItem(Material.REDSTONE, ChatColor.RED + "Remove a trade", null);
        edit.setItem(12, removeTrade);
        // Filler panels
        ItemStack filler = plugin.getMainManager().createFiller();
        int[] emptySlots = {0, 1, 2, 3, 4, 5, 6, 7, 8,
                            9, 10, 11,     13,     15, 16, 17,
                            18, 19, 20, 21, 22, 23, 24, 25, 26};
        for (int i : emptySlots) {
            edit.setItem(i, filler);
        }

        return edit;
    }
}
