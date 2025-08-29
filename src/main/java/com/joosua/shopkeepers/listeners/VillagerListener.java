package com.joosua.shopkeepers.listeners;

import com.joosua.shopkeepers.ShopkeepersPlugin;
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

import java.util.UUID;

public class VillagerListener implements Listener {
    private final ShopkeepersPlugin plugin;

    public VillagerListener(ShopkeepersPlugin plugin) { this.plugin = plugin; }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        // Check if right-clicked entity is a shopkeeper
        Entity entity = event.getRightClicked();
        UUID id = entity.getUniqueId();
        PersistentDataContainer data = entity.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "shopkeeper");
        if (data.has(key, PersistentDataType.STRING)) {
            String type = data.get(key, PersistentDataType.STRING);
            if (type.equals("market")) {
                // Check if the player is holding a debug emerald; if so open editor.
                // Otherwise always open the villager merchant GUI (even if the held item has item meta).
                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                boolean opened = false;
                if (itemInHand != null && itemInHand.hasItemMeta()) {
                    NamespacedKey debugEmeraldKey = new NamespacedKey(plugin, "debugEmerald");
                    var meta = itemInHand.getItemMeta();
                    if (meta != null) {
                        PersistentDataContainer debugEmeraldData = meta.getPersistentDataContainer();
                        if (debugEmeraldData.has(debugEmeraldKey, PersistentDataType.STRING)) {
                            String emeraldDataType = debugEmeraldData.get(debugEmeraldKey, PersistentDataType.STRING);
                            if (emeraldDataType != null && emeraldDataType.equals("debugEmerald")) {
                                Inventory edit = plugin.getEditorManager().getVillagerEdit(id);
                                player.openInventory(edit);
                                opened = true;
                            }
                        }
                    }
                }
                if (!opened) {
                    Merchant villagerGUI = plugin.getVillagerManager().getVillagerGUI(id);
                    player.openMerchant(villagerGUI, true);
                }
            }
        }
    }
}
