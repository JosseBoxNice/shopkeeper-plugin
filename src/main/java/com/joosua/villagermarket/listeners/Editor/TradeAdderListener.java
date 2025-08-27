package com.joosua.villagermarket.listeners.Editor;

import com.joosua.villagermarket.VillagerMarketMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class TradeAdderListener implements Listener {
    private final VillagerMarketMain plugin;
    public TradeAdderListener(VillagerMarketMain plugin) {
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
        // Confirm button
        if (slot == 26) {
            ItemStack cost = event.getInventory().getItem(11);
            ItemStack result = event.getInventory().getItem(15);
            plugin.getAddTradeManager().confirmTradeAdd(player, cost, result);
        }
    }
}
