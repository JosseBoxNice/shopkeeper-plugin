package com.joosua.villagermarket;

import com.joosua.villagermarket.commands.VillagerTabCompleter;
import com.joosua.villagermarket.listeners.Editor.EditorListener;
import com.joosua.villagermarket.listeners.Editor.TradeAdderListener;
import com.joosua.villagermarket.listeners.VillagerListener;
import com.joosua.villagermarket.managers.editor.AddTradeManager;
import com.joosua.villagermarket.managers.editor.EditorManager;
import com.joosua.villagermarket.managers.MainManager;
import com.joosua.villagermarket.managers.VillagerManager;
import com.joosua.villagermarket.commands.VillagerCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class VillagerMarketMain extends JavaPlugin {
    private VillagerManager villagerManager;
    private EditorManager editorManager;
    private MainManager mainManager;
    private AddTradeManager addTradeManager;

    public void onEnable() {
        if (getCommand("villager") != null) {
            getCommand("villager").setExecutor(new VillagerCommand(this));
            getCommand("villager").setTabCompleter(new VillagerTabCompleter());
        }

        // Enable managers
        villagerManager = new VillagerManager(this);
        editorManager = new EditorManager();
        mainManager = new MainManager();
        addTradeManager = new AddTradeManager(this);
        // Enable listeners
        getServer().getPluginManager().registerEvents(new VillagerListener(this), this);
        getServer().getPluginManager().registerEvents(new EditorListener(this), this);
        getServer().getPluginManager().registerEvents(new TradeAdderListener(this), this);
    }

    public VillagerManager getVillagerManager() { return villagerManager; }
    public EditorManager getEditorManager() { return editorManager; }
    public MainManager getMainManager() { return mainManager; }
    public AddTradeManager getAddTradeManager() { return addTradeManager; }
}