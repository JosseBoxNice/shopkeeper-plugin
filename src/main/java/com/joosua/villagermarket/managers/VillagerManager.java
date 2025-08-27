package com.joosua.villagermarket.managers;

import com.joosua.villagermarket.VillagerMarketMain;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class VillagerManager {
    private final VillagerMarketMain plugin;
    private final List<MerchantRecipe> trades = new ArrayList<>();


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
        Merchant gui = Bukkit.createMerchant("Shopkeeper");
        gui.setRecipes(trades);
        return gui;
    }

    public void addTrade(MerchantRecipe recipe) {
        trades.add(recipe);
    }
}
