package com.joosua.shopkeepers.managers;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VillagerManager {
    private final ShopkeepersPlugin plugin;

    public VillagerManager(ShopkeepersPlugin plugin) { this.plugin = plugin; }

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

    public Merchant getVillagerGUI(UUID id) {
        Merchant gui = Bukkit.createMerchant("Shopkeeper");
        List<MerchantRecipe> trades = plugin.getTradeManager().getTrades(id);
        gui.setRecipes(trades);
        return gui;
    }
}
