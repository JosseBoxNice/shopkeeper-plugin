package com.joosua.shopkeepers.itemmaker.ui;

import com.joosua.shopkeepers.itemmaker.utils.ItemMakerConstants;
import com.joosua.shopkeepers.itemmaker.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class BaseUIBuilder {
    
    protected ItemStack createFiller() {
        return ItemUtils.quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
    }
    
    protected void fillInventory(Inventory inventory) {
        ItemStack filler = createFiller();
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, filler);
        }
    }
    
    protected void addBackButton(Inventory inventory, int slot) {
        inventory.setItem(slot, ItemUtils.quickItem(Material.ARROW, ChatColor.GREEN + "Back", 
            List.of(ChatColor.GRAY + "Return to previous menu")));
    }
    
    protected void addCloseButton(Inventory inventory, int slot) {
        inventory.setItem(slot, ItemUtils.quickItem(Material.BARRIER, ItemMakerConstants.BTN_CLOSE, 
            List.of(ChatColor.GRAY + "Close the ItemMaker")));
    }
    
    protected void addPreviewItem(Inventory inventory, int slot, ItemStack preview) {
        if (preview != null) {
            inventory.setItem(slot, preview.clone());
        } else {
            inventory.setItem(slot, ItemUtils.quickItem(Material.BARRIER, ChatColor.YELLOW + "Preview", 
                List.of(ChatColor.GRAY + "Pick a category to preview items here")));
        }
    }
    
    protected Inventory createInventory(Player player, int size, String title) {
        return Bukkit.createInventory(player, size, title);
    }
}
