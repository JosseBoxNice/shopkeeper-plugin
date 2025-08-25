package com.joosua.villagermarket.managers;

import com.joosua.villagermarket.VillagerMarketMain;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Merchant;
import org.bukkit.persistence.PersistentDataType;

public class VillagerManager {
    private final VillagerMarketMain plugin;

    public VillagerManager(VillagerMarketMain plugin) { this.plugin = plugin; }

    public void createVillager(Location location) {
        World world = location.getWorld();
        if (world == null) return;

        // Spawn a villager at the given location
        Villager villager = (Villager) world.spawnEntity(location, EntityType.VILLAGER);
        villager.setAI(false);
        NamespacedKey key = new NamespacedKey(plugin, "shopkeeper");
        villager.getPersistentDataContainer().set(
                key,
                PersistentDataType.STRING,
                "market");
    }

    public Merchant getVillagerGUI() {
        Merchant gui = Bukkit.createMerchant("Market");
        return gui;
    }
}
