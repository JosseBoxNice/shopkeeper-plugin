package com.joosua.shopkeepers.itemcreator.listeners;

import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import com.joosua.shopkeepers.itemcreator.utils.ItemMakerConstants;
import com.joosua.shopkeepers.itemcreator.utils.MaterialUtils;
import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import com.joosua.shopkeepers.itemcreator.ui.UIManager;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.ChatColor;

public class ArmorUIListener implements Listener {
    private final ShopkeepersPlugin plugin;
    private final UIManager uiManager;
    public ArmorUIListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.uiManager = new UIManager(plugin);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(ItemMakerConstants.UI_TITLE_ARMOR)) return;

        HumanEntity clicker = event.getWhoClicked();
        Player player = (clicker instanceof Player) ? (Player) clicker : null;
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) return;
        PlayerState state = plugin.getItemMakerStateManager().get(player.getUniqueId());

        Material type = clicked.getType();
        String name = ItemUtils.getDisplayName(clicked);
        if (type == Material.BARRIER && name.equals(ItemMakerConstants.BTN_CLOSE)) {
            clicker.closeInventory(); if (player != null)
            return;
        }
        if (type == Material.ARROW) {
            if (player != null) player.openInventory(uiManager.getMainUIBuilder().buildMainUI(player, state));
            return;
        }
        if (MaterialUtils.isArmor(type)) {
            state.setPreview(clicked.clone());
            player.openInventory(uiManager.getMainUIBuilder().buildMainUI(player, state));
            player.sendMessage(ChatColor.GREEN + "Preview set to " +
            (clicked.hasItemMeta() ? clicked.getItemMeta().getDisplayName() : type.name()) + ChatColor.GREEN + ".");
        }
        event.setCancelled(true);
    }
}