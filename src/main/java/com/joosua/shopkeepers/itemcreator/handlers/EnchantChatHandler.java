package com.joosua.shopkeepers.itemcreator.handlers;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import com.joosua.shopkeepers.itemcreator.ui.UIManager;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantChatHandler {
    private final ShopkeepersPlugin plugin;
    private final UIManager uiManager;

    public EnchantChatHandler(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.uiManager = new UIManager(plugin);
    }
    
    public void handleEnchantChat(AsyncPlayerChatEvent event, Player p, PlayerState s) {
        String msg = event.getMessage().trim();
        int level;
        try { level = Integer.parseInt(msg); }
        catch (NumberFormatException e) { p.sendMessage(ChatColor.RED + "Please type a whole number between 0 and 255."); event.setCancelled(true); return; }
        if (level < 0 || level > 255) { p.sendMessage(ChatColor.RED + "Level must be between 0 and 255."); event.setCancelled(true); return; }
        event.setCancelled(true);

        Enchantment ench = s.getPendingEnchant();
        Bukkit.getScheduler().runTask(plugin, () -> {
            ItemStack preview = s.getPreview();
            if (preview == null) { p.sendMessage(ChatColor.RED + "No preview item set."); s.setAwaitingEnchantLevel(false); s.setPendingEnchant(null); return; }
            ItemMeta meta = preview.getItemMeta(); if (meta == null) { p.sendMessage(ChatColor.RED + "Could not edit the item."); s.setAwaitingEnchantLevel(false); s.setPendingEnchant(null); return; }

            if (level == 0) {
                if (meta.hasEnchant(ench)) meta.removeEnchant(ench);
                p.sendMessage(ChatColor.YELLOW + "Removed " + ItemUtils.niceName(ench) + ".");
            } else {
                meta.addEnchant(ench, level, true);
                p.sendMessage(ChatColor.GREEN + "Applied " + ItemUtils.niceName(ench) + ChatColor.GREEN + " level " + level + ".");
            }
            preview.setItemMeta(meta);
            s.setPreview(preview);
            p.openInventory(uiManager.getEnchUIBuilder().buildEnchUI(p));
            s.setAwaitingEnchantLevel(false);
            s.setPendingEnchant(null);
        });
    }
}