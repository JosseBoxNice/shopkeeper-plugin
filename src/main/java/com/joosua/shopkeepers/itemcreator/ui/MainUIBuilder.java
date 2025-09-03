package com.joosua.shopkeepers.itemcreator.ui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import com.joosua.shopkeepers.itemcreator.utils.ItemCreatorConstants;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;

import java.util.List;

public class MainUIBuilder extends BaseUIBuilder {
    
    public Inventory buildMainUI(Player player, PlayerState state) {
        Inventory ui = createInventory(player, 36, ItemCreatorConstants.UI_TITLE_MAIN);
        fillInventory(ui);

        // Category buttons
        ui.setItem(12, ItemUtils.quickItem(Material.DIAMOND_SWORD, ItemCreatorConstants.BTN_WEAPONS, 
            List.of(ChatColor.GRAY + "Browse all weapon types")));
        ui.setItem(13, ItemUtils.quickItem(Material.NETHERITE_PICKAXE, ItemCreatorConstants.BTN_TOOLS, 
            List.of(ChatColor.GRAY + "Pickaxes, axes, shovels")));
        ui.setItem(14, ItemUtils.quickItem(Material.DIAMOND_CHESTPLATE, ItemCreatorConstants.BTN_ARMOR, 
            List.of(ChatColor.GRAY + "Helmets, chestplates, etc.")));
        ui.setItem(15, ItemUtils.quickItem(Material.STICK, ItemCreatorConstants.BTN_MISC, 
            List.of(ChatColor.GRAY + "Other items")));

        // Preview item
        addPreviewItem(ui, 4, state.getPreview());

        // Close button
        addCloseButton(ui, 35);

        // Action buttons (only show if preview exists)
        if (state.getPreview() != null) {
            ui.setItem(27, ItemUtils.quickItem(Material.EMERALD_BLOCK, ItemCreatorConstants.BTN_CREATE, 
                List.of(ChatColor.GRAY + "Give yourself the item")));
            ui.setItem(30, ItemUtils.quickItem(Material.ENCHANTED_BOOK, ItemCreatorConstants.BTN_ENCH, 
                List.of(ChatColor.GRAY + "Pick enchantments")));
            ui.setItem(31, ItemUtils.quickItem(Material.NAME_TAG, ItemCreatorConstants.BTN_STYLE, 
                List.of(ChatColor.GRAY + "Name/Lore styling")));
            ui.setItem(32, ItemUtils.quickItem(Material.NETHER_STAR, ItemCreatorConstants.BTN_MODIFIERS, 
                List.of(ChatColor.GRAY + "Edit item modifiers")));
        }
        
        return ui;
    }
}
