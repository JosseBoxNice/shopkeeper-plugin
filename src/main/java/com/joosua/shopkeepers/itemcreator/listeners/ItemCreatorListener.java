package com.joosua.shopkeepers.itemcreator.listeners;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.ui.UIManager;
import com.joosua.shopkeepers.itemcreator.utils.ItemCreatorConstants;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemCreatorListener implements Listener {
    private final ShopkeepersPlugin plugin;
    private final UIManager uiManager;

    public ItemCreatorListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.uiManager = new UIManager(plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(com.joosua.shopkeepers.itemcreator.utils.ItemCreatorConstants.UI_TITLE_MAIN)) return;

        Inventory inv = event.getInventory();
        if (inv == null) return;
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) return;

        HumanEntity clicker = event.getWhoClicked();
        Player player = (clicker instanceof Player) ? (Player) clicker : null;

        Material type = clicked.getType();
        String name = ItemUtils.getDisplayName(clicked);

        if (type == Material.BARRIER && name.equals(ItemCreatorConstants.BTN_CLOSE)) {
            clicker.closeInventory();
            return;
        }

        if (type == Material.EMERALD_BLOCK && name.equals(ItemCreatorConstants.BTN_CREATE)) {
            PlayerState state = plugin.getItemMakerStateManager().get(player.getUniqueId());
                if (state.getPreview() == null) { player.sendMessage(ChatColor.RED + "Pick a weapon first."); return; }
                ItemStack toGive = state.getPreview().clone();
                var leftover = player.getInventory().addItem(toGive);
                if (!leftover.isEmpty()) leftover.values().forEach(item -> player.getWorld().dropItemNaturally(player.getLocation(), item));
                player.sendMessage(ChatColor.GREEN + "Created and given: " +
                        (toGive.hasItemMeta() && toGive.getItemMeta().hasDisplayName()
                                ? toGive.getItemMeta().getDisplayName() : toGive.getType().name()) + ChatColor.GREEN + ".");
            player.closeInventory();
            return;
        }

        if (type == Material.DIAMOND_SWORD && name.equals(ItemCreatorConstants.BTN_WEAPONS)) {
            if (player != null) {
                player.openInventory(uiManager.getWeaponsUIBuilder().buildWeaponsUI(player));
            }
            return;
        }

        if (type == Material.NETHERITE_PICKAXE && name.equals(ItemCreatorConstants.BTN_TOOLS)) {
            if (player != null) {
                player.openInventory(uiManager.getToolsUIBuilder().buildToolsUI(player));
            }
            return;
        }

        if (type == Material.DIAMOND_CHESTPLATE && name.equals(ItemCreatorConstants.BTN_ARMOR)) {
            if (player != null) {
                player.openInventory(uiManager.getArmorUIBuilder().buildArmorUI(player));
            }
            return;
        }

        if (type == Material.STICK && name.equals(ItemCreatorConstants.BTN_MISC)) {
            if (player != null) {
                player.openInventory(uiManager.getMiscUIBuilder().buildMiscUI(player));
            }
            return;
        }

        event.setCancelled(true);
    }
}
