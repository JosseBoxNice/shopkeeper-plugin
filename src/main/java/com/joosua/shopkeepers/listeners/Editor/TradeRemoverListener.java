package com.joosua.shopkeepers.listeners.Editor;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.holders.IdInventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class TradeRemoverListener implements Listener {
    private final ShopkeepersPlugin plugin;
    public TradeRemoverListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onTradeRemoverCLick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (title == null || !title.toLowerCase().startsWith("trade remover") && !title.toLowerCase().startsWith("trade remover")) {
            // also accept titles with page suffix like "Trade remover (page 1)" or "Trade remover (page 1)"
        }
        if (title == null || !title.toLowerCase().startsWith("trade remover")) return;
        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();
        // Checks if Trade adder has a holder
        if (event.getInventory().getHolder() instanceof IdInventoryHolder holder) {
            UUID id = holder.getId(); // gets the shopkeepers id
            // Go back button
            if (slot == 18) {
                Inventory editor = plugin.getEditorManager().getVillagerEdit(id);
                player.openInventory(editor);
            }
            // Previous page
            if (slot == 21) {
                // update player's page and reopen
                plugin.getPageManager().previousPage(player.getUniqueId());
                int page = plugin.getPageManager().getCurrentPage(player.getUniqueId());
                Inventory inv = plugin.getRemoveTradeManager().createTradeRemover(id, page);
                player.openInventory(inv);
            }
            // Next page
            if (slot == 23) {
                // compute max page index based on trade amount
                int tradeAmount = plugin.getTradeManager().getTradeAmount(id);
                int maxPageIndex = Math.max(0, (Math.max(0, tradeAmount + 1) - 1) / 7);
                plugin.getPageManager().nextPage(player.getUniqueId(), maxPageIndex);
                int page = plugin.getPageManager().getCurrentPage(player.getUniqueId());
                Inventory inv = plugin.getRemoveTradeManager().createTradeRemover(id, page);
                player.openInventory(inv);
            }

            // Remove trade buttons (slots 10..16)
            if (slot >= 10 && slot <= 16) {
                int page = plugin.getPageManager().getCurrentPage(player.getUniqueId());
                int tradeIndex = page * 7 + (slot - 10);
                boolean removed = plugin.getTradeManager().removeTrade(id, tradeIndex);
                if (removed) {
                    // ensure player's current page is still valid
                    int tradeAmount = plugin.getTradeManager().getTradeAmount(id);
                    int maxPageIndex = Math.max(0, (Math.max(0, tradeAmount) - 1) / 7);
                    int cur = plugin.getPageManager().getCurrentPage(player.getUniqueId());
                    if (cur > maxPageIndex) plugin.getPageManager().setCurrentPage(player.getUniqueId(), maxPageIndex);
                }
                int newPage = plugin.getPageManager().getCurrentPage(player.getUniqueId());
                Inventory inv = plugin.getRemoveTradeManager().createTradeRemover(id, newPage);
                player.openInventory(inv);
            }

        }
        event.setCancelled(true);
    }
}
