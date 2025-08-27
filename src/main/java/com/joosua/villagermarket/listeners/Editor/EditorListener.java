package com.joosua.villagermarket.listeners.Editor;

import com.joosua.villagermarket.VillagerMarketMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class EditorListener implements Listener {
    private final VillagerMarketMain plugin;
    public EditorListener(VillagerMarketMain plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onEditorClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().equals("Editor")) return;
        int slot = event.getRawSlot();
        Player player = (Player) event.getWhoClicked();
        if (slot == 13) {
            Inventory addTrade = plugin.getAddTradeManager().createAddTrade();
            player.openInventory(addTrade);
        }
        event.setCancelled(true);
    }
}
