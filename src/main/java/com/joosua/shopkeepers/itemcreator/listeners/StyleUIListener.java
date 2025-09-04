package com.joosua.shopkeepers.itemcreator.listeners;

import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.utils.ItemCreatorConstants;
import com.joosua.shopkeepers.itemcreator.ui.UIManager;
import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class StyleUIListener implements Listener {
    private final UIManager uiManager;
    private final ShopkeepersPlugin plugin;

    public StyleUIListener(ShopkeepersPlugin plugin) {
        this.uiManager = new UIManager(plugin);
        this.plugin = plugin;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(ItemCreatorConstants.UI_TITLE_STYLE)) return;
        event.setCancelled(true);

        HumanEntity clicker = event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        Player player = (clicker instanceof Player) ? (Player) clicker : null;
        Material type = clicked.getType();
        String name = ItemUtils.getDisplayName(clicked);
        PlayerState state = plugin.getPlayerStateManager().get(player.getUniqueId());

        if (type == Material.BARRIER && name.equals(ItemCreatorConstants.BTN_CLOSE)) {
            clicker.closeInventory(); if (player != null)
            return;
        }
        if (type == Material.ARROW) {
            if (player != null) player.openInventory(uiManager.getMainUIBuilder().buildMainUI(player, state));
            return;
        }
        if (type == Material.NAME_TAG && name.equals(ItemCreatorConstants.BTN_STYLE_SET_NAME)) { 
            player.openInventory(uiManager.getNameUIBuilder().buildNameUI(player)); 
            return; 
        }
        if (type == Material.WRITABLE_BOOK && name.equals(ItemCreatorConstants.BTN_STYLE_LORE)) { 
            //player.openInventory(uiManager.getLoreUIBuilder().buildLoreUI(player)); 
            return; 
        }
        return;
    }
}
