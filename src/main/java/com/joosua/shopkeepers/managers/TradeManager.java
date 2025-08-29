package com.joosua.shopkeepers.managers;

import org.bukkit.inventory.MerchantRecipe;

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
        return true;
    }
}
