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

public class NameColorUIBuilder extends BaseUIBuilder {
    private final ShopkeepersPlugin plugin;

    public NameColorUIBuilder(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
    }

    public Inventory buildNameColorUI(Player player) {
        var state = plugin.getPlayerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 54, ItemCreatorConstants.UI_TITLE_STYLE_NAME);
        fillInventory(ui);

        // Navigation buttons
        addBackButton(ui, 0);
        ui.setItem(4, ItemUtils.quickItem(Material.NAME_TAG, ChatColor.AQUA + "Armor", 
            List.of(ChatColor.GRAY + "Choose an armor piece to preview")));
        addCloseButton(ui, 8);

        if (state.getPreview() != null) ui.setItem(4, state.getPreview().clone());

        int slot = 10;
        for (var e : ItemUtils.dyeHexMap().entrySet()) {
            Material dye = e.getKey(); String hex = e.getValue();
            String nice = dye.name().replace("_DYE","").toLowerCase().replace('_',' ');
            String dn = ItemUtils.legacyHex(hex) + nice.substring(0,1).toUpperCase() + nice.substring(1);
            ui.setItem(slot++, ItemUtils.quickItem(dye, dn, List.of(ChatColor.GRAY + "Apply to name")));
            if (slot == 17 || slot == 26 || slot == 35 || slot == 44) slot += 2;
            if (slot >= 53) break;
        }
        ui.setItem(40, ItemUtils.quickItem(Material.PAPER, ItemCreatorConstants.BTN_COLOR_HEX, List.of(ChatColor.GRAY + "Enter a custom #RRGGBB")));
        return ui;
    }
}
