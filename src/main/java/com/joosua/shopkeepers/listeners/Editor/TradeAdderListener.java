package com.joosua.shopkeepers.listeners.Editor;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.holders.IdInventoryHolder;
import com.joosua.shopkeepers.itemcreator.ui.UIManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;
import org.bukkit.metadata.FixedMetadataValue;

public class TradeAdderListener implements Listener {
    private final ShopkeepersPlugin plugin;
    private final UIManager uiManager;
    public TradeAdderListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.uiManager = new UIManager(plugin);
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
            // ItemCreator buttons (2 -> cost slot 11, 6 -> result slot 15)
            if (slot == 2 || slot == 6) {
                // prepare player state to return to adder
                var s = plugin.getPlayerStateManager().get(player.getUniqueId());
                // Reset the ItemMaker preview and styling so AddTrade opens a fresh creator
                s.setPreview(null);
                s.setNameText(null); s.setNameHex(null);
                s.setNameBold(false); s.setNameItalic(false); s.setNameUnder(false); s.setNameStrike(false); s.setNameObfus(false);
                s.getLoreLines().clear(); s.setLoreHex(null);
                s.setLoreBold(false); s.setLoreItalic(false); s.setLoreUnder(false); s.setLoreStrike(false); s.setLoreObfus(false);
                // save current adder items so the other slot isn't lost
                ItemStack currentCost = event.getInventory().getItem(11);
                ItemStack currentResult = event.getInventory().getItem(15);
                s.setSavedAdderCost(currentCost == null ? null : currentCost.clone());
                s.setSavedAdderResult(currentResult == null ? null : currentResult.clone());
                s.setAwaitingReturnToAdder(true);
                s.setReturnShopkeeperId(id);
                s.setReturnSlot(slot == 2 ? 11 : 15);
                // also write metadata on player as a fallback for the return mapping
                player.setMetadata("shopkeeper_return", new FixedMetadataValue(plugin, id.toString() + ":" + (slot == 2 ? 11 : 15)));
                // open item maker main UI
                player.openInventory(uiManager.getMainUIBuilder().buildMainUI(player, s));
            }
        }
    }
}
