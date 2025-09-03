package com.joosua.shopkeepers.itemcreator.listeners;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.commands.ItemCreatorCommand;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemMakerListener implements Listener {
    private final ItemCreatorCommand command;

    public ItemMakerListener(ShopkeepersPlugin plugin) {
        this.command = new ItemCreatorCommand(plugin);
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

        if (type == Material.BARRIER && name.equals(com.joosua.shopkeepers.itemcreator.utils.ItemMakerConstants.BTN_CLOSE)) {
            clicker.closeInventory();
            return;
        }

        if (type == Material.DIAMOND_SWORD && name.equals(com.joosua.shopkeepers.itemcreator.utils.ItemMakerConstants.BTN_WEAPONS)) {
            if (player != null) {
                player.openInventory(command.getWeaponsUIBuilder().buildWeaponsUI(player));
            }
            return;
        }

        if (type == Material.NETHERITE_PICKAXE && name.equals(com.joosua.shopkeepers.itemcreator.utils.ItemMakerConstants.BTN_TOOLS)) {
            if (player != null) {
                player.openInventory(command.getToolsUIBuilder().buildToolsUI(player));
            }
            return;
        }

        event.setCancelled(true);
    }
}
