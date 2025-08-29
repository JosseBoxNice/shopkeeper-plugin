package com.joosua.shopkeepers;

import com.joosua.shopkeepers.commands.VillagerTabCompleter;
import com.joosua.shopkeepers.listeners.Editor.EditorListener;
import com.joosua.shopkeepers.listeners.Editor.TradeAdderListener;
import com.joosua.shopkeepers.listeners.Editor.TradeRemoverListener;
import com.joosua.shopkeepers.listeners.VillagerListener;
import com.joosua.shopkeepers.managers.editor.tradeRemover.PageManager;
import com.joosua.shopkeepers.managers.TradeManager;
import com.joosua.shopkeepers.managers.editor.AddTradeManager;
import com.joosua.shopkeepers.managers.editor.EditorManager;
import com.joosua.shopkeepers.managers.MainManager;
import com.joosua.shopkeepers.managers.VillagerManager;
import com.joosua.shopkeepers.commands.VillagerCommand;
import com.joosua.shopkeepers.managers.editor.tradeRemover.RemoveTradeManager;
import com.joosua.shopkeepers.itemmaker.PlayerStateManager;
import com.joosua.shopkeepers.itemmaker.CreateItemCommand;
import com.joosua.shopkeepers.itemmaker.ItemMakerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopkeepersPlugin extends JavaPlugin {
    private VillagerManager villagerManager;
    private EditorManager editorManager;
    private MainManager mainManager;
    private AddTradeManager addTradeManager;
    private TradeManager tradeManager;
    private RemoveTradeManager removeTradeManager;
    private PageManager pageManager;
    private PlayerStateManager itemMakerStateManager;
    public void onEnable() {
        if (getCommand("villager") != null) {
            getCommand("villager").setExecutor(new VillagerCommand(this));
            getCommand("villager").setTabCompleter(new VillagerTabCompleter());
        }

        // Enable managers
        villagerManager = new VillagerManager(this);
        editorManager = new EditorManager(this);
        mainManager = new MainManager();
        addTradeManager = new AddTradeManager(this);
        tradeManager = new TradeManager();
        removeTradeManager = new RemoveTradeManager(this);
        pageManager = new PageManager();
    itemMakerStateManager = new PlayerStateManager();
    // register item maker command and listener
    if (getCommand("createitem") != null) getCommand("createitem").setExecutor(new CreateItemCommand(this));
    getServer().getPluginManager().registerEvents(new ItemMakerListener(this), this);
        // Enable listeners
        getServer().getPluginManager().registerEvents(new VillagerListener(this), this);
        getServer().getPluginManager().registerEvents(new EditorListener(this), this);
        getServer().getPluginManager().registerEvents(new TradeAdderListener(this), this);
        getServer().getPluginManager().registerEvents(new TradeRemoverListener(this), this);
    }

    public VillagerManager getVillagerManager() { return villagerManager; }
    public EditorManager getEditorManager() { return editorManager; }
    public MainManager getMainManager() { return mainManager; }
    public AddTradeManager getAddTradeManager() { return addTradeManager; }
    public TradeManager getTradeManager() { return tradeManager; }
    public RemoveTradeManager getRemoveTradeManager() { return removeTradeManager; }
    public PageManager getPageManager() { return pageManager; }
    public PlayerStateManager getItemMakerStateManager() { return itemMakerStateManager; }
}