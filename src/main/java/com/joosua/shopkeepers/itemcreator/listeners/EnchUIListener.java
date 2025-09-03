package com.joosua.shopkeepers.itemcreator.listeners;

import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import com.joosua.shopkeepers.itemcreator.utils.ItemCreatorConstants;
import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import com.joosua.shopkeepers.itemcreator.ui.UIManager;
import org.bukkit.event.Listener;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.NamespacedKey;

public class EnchUIListener implements Listener {
    private final ShopkeepersPlugin plugin;
    private final UIManager uiManager;
    public EnchUIListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.uiManager = new UIManager(plugin);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(ItemCreatorConstants.UI_TITLE_ENCHANTS)) return;

        HumanEntity clicker = event.getWhoClicked();
        Player player = (clicker instanceof Player) ? (Player) clicker : null;
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) return;
        PlayerState state = plugin.getPlayerStateManager().get(player.getUniqueId());

        Material type = clicked.getType();
        String name = ItemUtils.getDisplayName(clicked);
        if (type == Material.BARRIER && name.equals(ItemCreatorConstants.BTN_CLOSE)) {
            clicker.closeInventory(); if (player != null)
            return;
        }

        if (type == Material.ARROW) {
            if (player != null) player.openInventory(uiManager.getMainUIBuilder().buildMainUI(player, state));
            return;
        }
        // Unbreakable toggle
        if ((type == Material.BOOK || type == Material.ENCHANTED_BOOK) && name.startsWith(ItemCreatorConstants.BTN_UNBREAK) && player != null) {
            state = plugin.getPlayerStateManager().get(player.getUniqueId());
            if (state.getPreview() == null) { player.sendMessage(ChatColor.RED + "Pick a weapon first."); return; }
            ItemMeta meta = state.getPreview().getItemMeta(); if (meta == null) return;
            meta.setUnbreakable(!meta.isUnbreakable());
            state.getPreview().setItemMeta(meta);
            player.openInventory(uiManager.getEnchUIBuilder().buildEnchUI(player));
            player.sendMessage(ChatColor.YELLOW + "Unbreakable: " + (meta.isUnbreakable() ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF"));
            return;
        }
        // Enchant selection
        if (type == Material.ENCHANTED_BOOK && player != null) {
            ItemMeta meta = clicked.getItemMeta(); if (meta == null) return;
            String keyStr = meta.getPersistentDataContainer().get(ItemCreatorConstants.ENCH_KEY, PersistentDataType.STRING);
            if (keyStr == null) return;
            Enchantment ench = Enchantment.getByKey(NamespacedKey.fromString(keyStr));
            if (ench == null) { player.sendMessage(ChatColor.RED + "Unknown enchantment."); return; }
            var s = plugin.getPlayerStateManager().get(player.getUniqueId());
            if (s.getPreview() == null) { player.sendMessage(ChatColor.RED + "Pick a weapon first."); return; }
            s.setPendingEnchant(ench);
            s.setAwaitingEnchantLevel(true);
            player.closeInventory();
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Enter level (0–255) for " + ItemUtils.niceName(ench) + ChatColor.GRAY + " in chat. Type a number only.");
        }
        event.setCancelled(true);
    }
}