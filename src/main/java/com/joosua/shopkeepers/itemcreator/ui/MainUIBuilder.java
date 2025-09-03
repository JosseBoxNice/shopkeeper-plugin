package com.joosua.shopkeepers.itemcreator.ui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import com.joosua.shopkeepers.itemcreator.utils.ItemMakerConstants;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;

import java.util.List;

public class MainUIBuilder extends BaseUIBuilder {
    
    public Inventory buildMainUI(Player player, PlayerState state) {
        Inventory ui = createInventory(player, 36, ItemMakerConstants.UI_TITLE_MAIN);
        fillInventory(ui);

        // Category buttons
        ui.setItem(12, ItemUtils.quickItem(Material.DIAMOND_SWORD, ItemMakerConstants.BTN_WEAPONS, 
            List.of(ChatColor.GRAY + "Browse all weapon types")));
        ui.setItem(13, ItemUtils.quickItem(Material.NETHERITE_PICKAXE, ItemMakerConstants.BTN_TOOLS, 
            List.of(ChatColor.GRAY + "Pickaxes, axes, shovels")));
        ui.setItem(14, ItemUtils.quickItem(Material.DIAMOND_CHESTPLATE, ItemMakerConstants.BTN_ARMOR, 
            List.of(ChatColor.GRAY + "Helmets, chestplates, etc.")));
        ui.setItem(15, ItemUtils.quickItem(Material.STICK, ItemMakerConstants.BTN_MISC, 
            List.of(ChatColor.GRAY + "Other items")));

        // Preview item
        addPreviewItem(ui, 4, state.getPreview());

        // Close button
        addCloseButton(ui, 35);

        // Action buttons (only show if preview exists)
        if (state.getPreview() != null) {
            ui.setItem(27, ItemUtils.quickItem(Material.EMERALD_BLOCK, ItemMakerConstants.BTN_CREATE, 
                List.of(ChatColor.GRAY + "Give yourself the item")));
            ui.setItem(30, ItemUtils.quickItem(Material.ENCHANTED_BOOK, ItemMakerConstants.BTN_ENCH, 
                List.of(ChatColor.GRAY + "Pick enchantments")));
            ui.setItem(31, ItemUtils.quickItem(Material.NAME_TAG, ItemMakerConstants.BTN_STYLE, 
                List.of(ChatColor.GRAY + "Name/Lore styling")));
            ui.setItem(32, ItemUtils.quickItem(Material.NETHER_STAR, ItemMakerConstants.BTN_MODIFIERS, 
                List.of(ChatColor.GRAY + "Edit item modifiers")));
        }
        
        return ui;
    }
}
