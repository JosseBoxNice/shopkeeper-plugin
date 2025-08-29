package com.joosua.shopkeepers.listeners.Editor;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.holders.IdInventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class EditorListener implements Listener {
    private final ShopkeepersPlugin plugin;
    public EditorListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onEditorClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().equals("Editor")) return;
        int slot = event.getRawSlot();
        Player player = (Player) event.getWhoClicked();
        switch (slot) {
            // On "Add a trade"
            case 14:
                // check if editor has an inventory listener
                if (event.getInventory().getHolder() instanceof IdInventoryHolder holder) {
                    UUID id = holder.getId(); // gets the shopkeeper id from the holder
                    Inventory addTrade = plugin.getAddTradeManager().createAddTrade(id);
                    player.openInventory(addTrade);
                }
                break;
            // On "Remove a trade"
            case 12:
                // check if editor has an inventory listener
                if (event.getInventory().getHolder() instanceof IdInventoryHolder holder) {
                    UUID id = holder.getId(); // gets the shopkeeper id from the holder
                    // initialize per-player page to 0 for this player
                    plugin.getPageManager().setCurrentPage(player.getUniqueId(), 0);
                    int page = plugin.getPageManager().getCurrentPage(player.getUniqueId());
                    Inventory addTrade = plugin.getRemoveTradeManager().createTradeRemover(id, page);

                    player.openInventory(addTrade);
                }
                break;
        }
        event.setCancelled(true);
    }
}
