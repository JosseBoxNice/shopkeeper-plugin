package com.joosua.shopkeepers.itemcreator.ui.style;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.utils.ItemCreatorConstants;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import com.joosua.shopkeepers.itemcreator.ui.BaseUIBuilder;
import org.bukkit.inventory.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.ChatColor;

import java.util.List;

public class StyleUIBuilder extends BaseUIBuilder {
    private final ShopkeepersPlugin plugin;

    public StyleUIBuilder(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
    }
    public Inventory buildStyleUI(Player player) {
        var state = plugin.getPlayerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 36, ItemCreatorConstants.UI_TITLE_STYLE);
        fillInventory(ui);

        // Navigation buttons
        addBackButton(ui, 0);
        ui.setItem(4, ItemUtils.quickItem(Material.NAME_TAG, ChatColor.AQUA + "Armor", 
            List.of(ChatColor.GRAY + "Choose an armor piece to preview")));
        addCloseButton(ui, 8);


        if (state.getPreview() != null) ui.setItem(4, state.getPreview().clone());
        else ui.setItem(4, ItemUtils.quickItem(Material.BARRIER, ChatColor.YELLOW + "No Preview", List.of(ChatColor.GRAY + "Pick a weapon first")));

        ui.setItem(21, ItemUtils.quickItem(Material.NAME_TAG, ItemCreatorConstants.BTN_STYLE_SET_NAME, List.of(ChatColor.GRAY + "Open name editor")));
        ui.setItem(23, ItemUtils.quickItem(Material.WRITABLE_BOOK, ItemCreatorConstants.BTN_STYLE_LORE, List.of(ChatColor.GRAY + "Open lore editor")));
        return ui;
    }
}