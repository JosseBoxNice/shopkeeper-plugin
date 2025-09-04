package com.joosua.shopkeepers.itemcreator.ui.style;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.ui.BaseUIBuilder;
import com.joosua.shopkeepers.itemcreator.utils.ItemCreatorConstants;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.ChatColor;

import java.util.List;

public class NameUIBuilder extends BaseUIBuilder {
    private final ShopkeepersPlugin plugin;

    public NameUIBuilder(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
    }

    public Inventory buildNameUI(Player player) {
        var state = plugin.getPlayerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 36, ItemCreatorConstants.UI_TITLE_STYLE_NAME);
        fillInventory(ui);

        // Navigation buttons
        addBackButton(ui, 0);
        ui.setItem(4, ItemUtils.quickItem(Material.NAME_TAG, ChatColor.AQUA + "Armor", 
            List.of(ChatColor.GRAY + "Choose an armor piece to preview")));
        addCloseButton(ui, 8);

        if (state.getPreview() != null) ui.setItem(4, state.getPreview().clone());

        ui.setItem(20, ItemUtils.quickItem(Material.NAME_TAG, ItemCreatorConstants.BTN_NAME_SETNAME, List.of(ChatColor.GRAY + "Type new display name")));
        ui.setItem(22, ItemUtils.quickItem(Material.GRAY_DYE, ItemCreatorConstants.BTN_NAME_SETCOLOR, List.of(ChatColor.GRAY + "Open name colors")));
        ui.setItem(24, ItemUtils.quickItem(Material.FEATHER, ItemCreatorConstants.BTN_NAME_FORMAT, List.of(ChatColor.GRAY + "Open formatting toggles")));
        return ui;
    }
}
