package com.joosua.shopkeepers.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TradeManager {
    // A map where trades are stored by shopkeeper id
    private final Map<UUID, List<MerchantRecipe>> tradeMap = new HashMap<>();

    // Method for adding a trade to the map
    public void addTrade(UUID id, MerchantRecipe trade) {
        tradeMap.computeIfAbsent(id, k -> new ArrayList<>()).add(trade);
    }

    // get the trades from the map based on id
    public List<MerchantRecipe> getTrades(UUID id) {
        return tradeMap.getOrDefault(id, Collections.emptyList());
    }

    public int getTradeAmount(UUID id) {
        return tradeMap.getOrDefault(id, Collections.emptyList()).size();
    }

    // Remove a trade by index for a given shopkeeper id. Returns true if removed.
    public boolean removeTrade(UUID id, int index) {
        List<MerchantRecipe> list = tradeMap.get(id);
        if (list == null) return false;
        if (index < 0 || index >= list.size()) return false;
        list.remove(index);
        if (list.isEmpty()) tradeMap.remove(id);
        return true;
    }

    // Persist trades to a YAML file inside the plugin data folder
    public void saveTrades(File dataFolder) {
        if (!dataFolder.exists()) dataFolder.mkdirs();
        File file = new File(dataFolder, "trades.yml");
        YamlConfiguration cfg = new YamlConfiguration();
        for (Map.Entry<UUID, List<MerchantRecipe>> entry : tradeMap.entrySet()) {
            String id = entry.getKey().toString();
            List<MerchantRecipe> recipes = entry.getValue();
            for (int i = 0; i < recipes.size(); i++) {
                MerchantRecipe r = recipes.get(i);
                String base = "trades." + id + "." + i + ".";
                cfg.set(base + "result", r.getResult());
                cfg.set(base + "maxUses", r.getMaxUses());
                // store ingredients as a list
                List<ItemStack> ingredients = new ArrayList<>(r.getIngredients());
                cfg.set(base + "ingredients", ingredients);
            }
        }
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load trades from YAML file into memory
    public void loadTrades(File dataFolder) {
        File file = new File(dataFolder, "trades.yml");
        if (!file.exists()) return;
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (!cfg.isConfigurationSection("trades")) return;
        for (String id : cfg.getConfigurationSection("trades").getKeys(false)) {
            UUID uuid;
            try {
                uuid = UUID.fromString(id);
            } catch (IllegalArgumentException e) {
                continue;
            }
            for (String indexKey : cfg.getConfigurationSection("trades." + id).getKeys(false)) {
                String base = "trades." + id + "." + indexKey + ".";
                ItemStack result = cfg.getItemStack(base + "result");
                int maxUses = cfg.getInt(base + "maxUses", 999);
                if (result == null) continue;
                MerchantRecipe recipe = new MerchantRecipe(result, maxUses);
                List<?> ingrRaw = cfg.getList(base + "ingredients", Collections.emptyList());
                for (Object o : ingrRaw) {
                    if (o instanceof ItemStack) recipe.addIngredient((ItemStack) o);
                }
                addTrade(uuid, recipe);
            }
        }
    }
}
