package com.joosua.shopkeepers.itemcreator.listeners.style;

import com.joosua.shopkeepers.itemcreator.utils.ItemCreatorConstants;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import com.joosua.shopkeepers.itemcreator.ui.UIManager;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import com.joosua.shopkeepers.ShopkeepersPlugin;

public class NameUIListener implements Listener {
    private final ShopkeepersPlugin plugin;
    private final UIManager uiManager;

    public NameUIListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.uiManager = new UIManager(plugin);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(ItemCreatorConstants.UI_TITLE_STYLE_NAME)) return;
        event.setCancelled(true);
    
        HumanEntity clicker = event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        Player player = (clicker instanceof Player) ? (Player) clicker : null;
        Material type = clicked.getType();
        String name = ItemUtils.getDisplayName(clicked);

        if (type == Material.BARRIER && name.equals(ItemCreatorConstants.BTN_CLOSE)) {
            clicker.closeInventory(); if (player != null) return;
        }
        if (type == Material.ARROW) { if (player != null) player.openInventory(uiManager.getStyleUIBuilder().buildStyleUI(player)); return; }
        var s = plugin.getPlayerStateManager().get(player.getUniqueId());
        if (s.getPreview() == null) { player.sendMessage(ChatColor.RED + "Pick a weapon first."); return; }
        if (type == Material.NAME_TAG && name.equals(ItemCreatorConstants.BTN_NAME_SETNAME)) {
            s.setAwaitingNameInput(true); player.closeInventory(); player.sendMessage(ChatColor.GOLD + "Type the new item name in chat."); return;
        }
        if (type == Material.GRAY_DYE && name.equals(ItemCreatorConstants.BTN_NAME_SETCOLOR)) {
            // player.openInventory(uiManager.getNameColorUIBuilder().buildNameColorUI(player)); return;
        }
        if (type == Material.FEATHER && name.equals(ItemCreatorConstants.BTN_NAME_FORMAT)) {
            // player.openInventory(uiManager.getNameFormatUIBuilder().buildNameFormatUI(player)); return;
        }
        return;
    }
    
}
