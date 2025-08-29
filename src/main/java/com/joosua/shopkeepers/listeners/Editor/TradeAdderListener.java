package com.joosua.shopkeepers.listeners.Editor;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.holders.IdInventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public class TradeAdderListener implements Listener {
    private final ShopkeepersPlugin plugin;
    public TradeAdderListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onTradeAdderClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Trade Adder")) return;
        Player player = (Player) event.getWhoClicked();
        Set<Integer> emptySlots = Set.of(11, 15);
        int slot = event.getRawSlot();
        if (event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getTopInventory())) {
            if (!emptySlots.contains(slot)) {
                event.setCancelled(true);
            }
        }
        // Checks if Trade adder has a holder
        if (event.getInventory().getHolder() instanceof IdInventoryHolder holder) {
            UUID id = holder.getId(); // gets the shopkeepers id
            // Confirm button
            if (slot == 26) {
                ItemStack cost = event.getInventory().getItem(11);
                ItemStack result = event.getInventory().getItem(15);
                plugin.getAddTradeManager().confirmTradeAdd(player, cost, result, id);
            }
            // Go back button
            if (slot == 18) {
                Inventory editor = plugin.getEditorManager().getVillagerEdit(id);
                player.openInventory(editor);
            }
        }
    }
}
