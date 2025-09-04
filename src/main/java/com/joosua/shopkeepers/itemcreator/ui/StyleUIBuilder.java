package com.joosua.shopkeepers.itemcreator.ui;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.utils.ItemCreatorConstants;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.ChatColor;

import java.util.List;

public class StyleUIBuilder {
    private final ShopkeepersPlugin plugin;

    public StyleUIBuilder(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
    }
    public Inventory buildStyleUI(Player player) {
        var state = plugin.getPlayerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 36, ItemCreatorConstants.UI_TITLE_STYLE);

        ItemStack filler = ItemUtils.quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);

        ui.setItem(0, ItemUtils.quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Return to ItemMaker")));
        ui.setItem(8, ItemUtils.quickItem(Material.BARRIER, ItemCreatorConstants.BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));
        if (state.getPreview() != null) ui.setItem(4, state.getPreview().clone());
        else ui.setItem(4, ItemUtils.quickItem(Material.BARRIER, ChatColor.YELLOW + "No Preview", List.of(ChatColor.GRAY + "Pick a weapon first")));

        ui.setItem(21, ItemUtils.quickItem(Material.NAME_TAG, ItemCreatorConstants.BTN_STYLE_SET_NAME, List.of(ChatColor.GRAY + "Open name editor")));
        ui.setItem(23, ItemUtils.quickItem(Material.WRITABLE_BOOK, ItemCreatorConstants.BTN_STYLE_LORE, List.of(ChatColor.GRAY + "Open lore editor")));
        return ui;
    }
}