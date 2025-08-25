package com.joosua.villagermarket.listeners;

import com.joosua.villagermarket.VillagerMarketMain;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Merchant;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class VillagerListener implements Listener {
    private final VillagerMarketMain plugin;

    public VillagerListener(VillagerMarketMain plugin) { this.plugin = plugin; }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        PersistentDataContainer data = entity.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "shopkeeper");
        event.getPlayer().sendMessage("Event fired");
        if (data.has(key, PersistentDataType.STRING)) {
            String type = data.get(key, PersistentDataType.STRING);
            if (type.equals("market")) {
                event.getPlayer().sendMessage("Key was right");
                Merchant villagerGUI = plugin.getVillagerManager().getVillagerGUI();
                event.getPlayer().openMerchant(villagerGUI, true);
            }
        }
    }
}
