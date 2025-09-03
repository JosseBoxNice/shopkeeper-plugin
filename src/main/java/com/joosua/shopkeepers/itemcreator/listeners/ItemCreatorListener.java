package com.joosua.shopkeepers.itemcreator.listeners;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.ui.UIManager;
import com.joosua.shopkeepers.itemcreator.utils.ItemMakerConstants;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemCreatorListener implements Listener {
    private final UIManager uiManager;

    public ItemCreatorListener(ShopkeepersPlugin plugin) {
        this.uiManager = new UIManager(plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(com.joosua.shopkeepers.itemcreator.utils.ItemMakerConstants.UI_TITLE_MAIN)) return;

        Inventory inv = event.getInventory();
        if (inv == null) return;
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) return;

        HumanEntity clicker = event.getWhoClicked();
        Player player = (clicker instanceof Player) ? (Player) clicker : null;

        Material type = clicked.getType();
        String name = ItemUtils.getDisplayName(clicked);

        if (type == Material.BARRIER && name.equals(ItemMakerConstants.BTN_CLOSE)) {
            clicker.closeInventory();
            return;
        }

        if (type == Material.DIAMOND_SWORD && name.equals(ItemMakerConstants.BTN_WEAPONS)) {
            if (player != null) {
                player.openInventory(uiManager.getWeaponsUIBuilder().buildWeaponsUI(player));
            }
            return;
        }

        if (type == Material.NETHERITE_PICKAXE && name.equals(ItemMakerConstants.BTN_TOOLS)) {
            if (player != null) {
                player.openInventory(uiManager.getToolsUIBuilder().buildToolsUI(player));
            }
            return;
        }

        if (type == Material.DIAMOND_CHESTPLATE && name.equals(ItemMakerConstants.BTN_ARMOR)) {
            if (player != null) {
                player.openInventory(uiManager.getArmorUIBuilder().buildArmorUI(player));
            }
            return;
        }

        event.setCancelled(true);
    }
}
