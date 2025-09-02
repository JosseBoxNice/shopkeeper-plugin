package com.joosua.shopkeepers.itemmaker.listeners;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemmaker.state.PlayerState;
import com.joosua.shopkeepers.commands.ItemCreatorCommand;
import com.joosua.shopkeepers.itemmaker.utils.MaterialUtils;
import com.joosua.shopkeepers.itemmaker.utils.ItemUtils;
import com.joosua.shopkeepers.itemmaker.utils.StyleUtils;
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
    private final ItemCreatorCommand command;

    public WeaponsUIListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.command = new ItemCreatorCommand(plugin);
    }

    @EventHandler
    private void OnInventoryClick(InventoryClickEvent event) {
        HumanEntity clicker = event.getWhoClicked();
        Player player = (clicker instanceof Player) ? (Player) clicker : null;
        event.setCancelled(true);
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) return;

        Material type = clicked.getType();
        String name = ItemUtils.getDisplayName(clicked);

        if (type == Material.BARRIER && name.equals(com.joosua.shopkeepers.itemmaker.utils.ItemMakerConstants.BTN_CLOSE)) {
            clicker.closeInventory();
            return;
        }

        if (type == Material.ARROW) {
            if (player != null) {
                PlayerState state = plugin.getItemMakerStateManager().get(player.getUniqueId());
                player.openInventory(command.getMainUIBuilder().buildMainUI(player, state));
            }
            return;
        }

        if (MaterialUtils.isWeapon(type) && player != null) {
            PlayerState state = plugin.getItemMakerStateManager().get(player.getUniqueId());
            state.setPreview(clicked.clone());
            StyleUtils.resetStateStyles(state);
            StyleUtils.applyStyleToPreview(state);
            player.openInventory(command.getMainUIBuilder().buildMainUI(player, state));
            player.sendMessage(ChatColor.GREEN + "Preview set to " + ItemUtils.getItemDisplayName(clicked, type) + ChatColor.GREEN + ".");
        }
    }

} 