package com.joosua.shopkeepers.itemcreator.listeners;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.handlers.EnchantChatHandler;
import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import com.joosua.shopkeepers.itemcreator.utils.StyleUtils;
import com.joosua.shopkeepers.itemcreator.ui.UIManager;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ChatInputListener implements Listener {
    private final ShopkeepersPlugin plugin;
    private final UIManager uiManager;

    public ChatInputListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.uiManager = new UIManager(plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerState state = plugin.getPlayerStateManager().get(player.getUniqueId());
        // Enchant level prompt
        if (state.isAwaitingEnchantLevel() && state.getPendingEnchant() != null) {
            EnchantChatHandler handler = new EnchantChatHandler(plugin);
            handler.handleEnchantChat(event, player, state); return;
        }
        if (state.isAwaitingNameInput()) {
            event.setCancelled(true);
            String name = event.getMessage().trim();
            Bukkit.getScheduler().runTask(plugin, () -> {
                state.setNameText(name);
                StyleUtils.applyStyleToPreview(state);
                player.openInventory(uiManager.getNameUIBuilder().buildNameUI(player));
                player.sendMessage(ChatColor.GOLD + "Name set.");
                state.setAwaitingNameInput(false);
            });
            return;
        }
        // Name hex
        /*
        if (s.isAwaitingNameHexInput()) {
            event.setCancelled(true);
            String msg = event.getMessage().trim();
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (msg.equalsIgnoreCase("clear") || msg.equalsIgnoreCase("none")) s.setNameHex(null);
                else {
                    String hex = msg.startsWith("#") ? msg : "#" + msg;
                    if (hex.matches("^#(?i)[0-9a-f]{6}$")) s.setNameHex(hex);
                    else player.sendMessage(ChatColor.RED + "Invalid hex. Use #RRGGBB.");
                }
                builder.applyStyleToPreview(s);
                player.openInventory(builder.buildNameUI(player));
                s.setAwaitingNameHexInput(false);
            });
            return;
        }
        */
        /*
        // Lore line
        if (s.isAwaitingLoreInput()) {
            event.setCancelled(true);
            String line = event.getMessage();
            Bukkit.getScheduler().runTask(plugin, () -> {
                s.getLoreLines().add(line);
                builder.applyStyleToPreview(s);
                player.openInventory(builder.buildLoreUI(player));
                s.setAwaitingLoreInput(false);
            });
            return;
        }
        */
        /*
        // Lore hex
        if (s.isAwaitingLoreHexInput()) {
            event.setCancelled(true);
            String msg = event.getMessage().trim();
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (msg.equalsIgnoreCase("clear") || msg.equalsIgnoreCase("none")) s.setLoreHex(null);
                else {
                    String hex = msg.startsWith("#") ? msg : "#" + msg;
                    if (hex.matches("^#(?i)[0-9a-f]{6}$")) s.setLoreHex(hex);
                    else player.sendMessage(ChatColor.RED + "Invalid hex. Use #RRGGBB.");
                }
                builder.applyStyleToPreview(s);
                player.openInventory(builder.buildLoreUI(player));
                s.setAwaitingLoreHexInput(false);
            });
        }
        */
    }
}