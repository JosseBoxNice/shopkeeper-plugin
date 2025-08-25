package com.joosua.villagermarket;

import com.joosua.villagermarket.listeners.VillagerListener;
import com.joosua.villagermarket.managers.VillagerManager;
import com.joosua.villagermarket.commands.VillagerCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class VillagerMarketMain extends JavaPlugin {
    private VillagerManager villagerManager;

    public void onEnable() {
        if (getCommand("villager") != null) {
            getCommand("villager").setExecutor(new VillagerCommand(this));
            getCommand("villager").setTabCompleter(new VillagerTabCompleter());
        }

        villagerManager = new VillagerManager(this);
        getServer().getPluginManager().registerEvents(new VillagerListener(this), this);
    }

    public VillagerManager getVillagerManager() { return villagerManager; }
}