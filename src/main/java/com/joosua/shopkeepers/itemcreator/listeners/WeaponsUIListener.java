package com.joosua.shopkeepers.itemcreator.listeners;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.ui.UIManager;
import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import com.joosua.shopkeepers.itemcreator.utils.MaterialUtils;
import com.joosua.shopkeepers.itemcreator.utils.StyleUtils;
import org.bukkit.event.Listener;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class WeaponsUIListener implements Listener {
    private final ShopkeepersPlugin plugin;
    private final UIManager uiManager;

    public WeaponsUIListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.uiManager = new UIManager(plugin);
    }

    @EventHandler
    private void OnInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(com.joosua.shopkeepers.itemcreator.utils.ItemMakerConstants.UI_TITLE_WEAPONS)) return;

        HumanEntity clicker = event.getWhoClicked();
        Player player = (clicker instanceof Player) ? (Player) clicker : null;
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) return;

        Material type = clicked.getType();
        String name = ItemUtils.getDisplayName(clicked);
        PlayerState state = plugin.getItemMakerStateManager().get(player.getUniqueId());

        if (type == Material.BARRIER && name.equals(com.joosua.shopkeepers.itemcreator.utils.ItemMakerConstants.BTN_CLOSE)) {
            clicker.closeInventory();
            return;
        }

        if (type == Material.ARROW) {
            if (player != null) {
                player.openInventory(uiManager.getMainUIBuilder().buildMainUI(player, state));
            }
            return;
        }

        if (MaterialUtils.isWeapon(type) && player != null) {
            state.setPreview(clicked.clone());
            StyleUtils.resetStateStyles(state);
            StyleUtils.applyStyleToPreview(state);
            player.openInventory(uiManager.getMainUIBuilder().buildMainUI(player, state));
            player.sendMessage(ChatColor.GREEN + "Preview set to " + ItemUtils.getItemDisplayName(clicked, type) + ChatColor.GREEN + ".");
        }
        event.setCancelled(true);
    }
} 