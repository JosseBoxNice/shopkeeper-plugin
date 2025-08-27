package com.joosua.villagermarket.listeners;

import com.joosua.villagermarket.VillagerMarketMain;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class VillagerListener implements Listener {
    private final VillagerMarketMain plugin;

    public VillagerListener(VillagerMarketMain plugin) { this.plugin = plugin; }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        // Check if right-clicked entity is a shopkeeper
        Entity entity = event.getRightClicked();
        PersistentDataContainer data = entity.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "shopkeeper");
        if (data.has(key, PersistentDataType.STRING)) {
            String type = data.get(key, PersistentDataType.STRING);
            if (type.equals("market")) {
                // Check if the player is holding a debug emerald
                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                if (itemInHand.hasItemMeta()) {
                    NamespacedKey debugEmeraldKey = new NamespacedKey(plugin, "debugEmerald");
                    PersistentDataContainer debugEmeraldData = itemInHand.getItemMeta().getPersistentDataContainer();
                    if (debugEmeraldData.has(debugEmeraldKey, PersistentDataType.STRING)) {
                        String emeraldDataType = debugEmeraldData.get(debugEmeraldKey, PersistentDataType.STRING);
                        if (emeraldDataType.equals("debugEmerald")) {
                            Inventory edit = plugin.getEditorManager().getVillagerEdit();
                            player.openInventory(edit);
                        }
                    }
                } else { // If not, open the gui
                    Merchant villagerGUI = plugin.getVillagerManager().getVillagerGUI();
                    player.openMerchant(villagerGUI, true);
                }
            }
        }
    }
}
