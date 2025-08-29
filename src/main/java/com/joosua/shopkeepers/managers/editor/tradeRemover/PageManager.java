package com.joosua.shopkeepers.managers.editor.tradeRemover;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.List;

public class PageManager {
    private final Map<UUID, Integer> currentPageMap = new HashMap<>();
    // count page for paged inventories
    public List<ItemStack> getPage(List<ItemStack> items, int page, int pageSize) {
        int start = page * pageSize;
        int end = Math.min(start + pageSize, items.size());
        if (start >= items.size()) return Collections.emptyList();
        return items.subList(start, end);
    }

    // Per-player page state
    public int getCurrentPage(UUID player) {
        return currentPageMap.getOrDefault(player, 0);
    }

    public void setCurrentPage(UUID player, int page) {
        if (page < 0) page = 0;
        currentPageMap.put(player, page);
    }

    public void nextPage(UUID player, int maxPageIndex) {
        int cur = getCurrentPage(player);
        if (cur < maxPageIndex) currentPageMap.put(player, cur + 1);
    }

    public void previousPage(UUID player) {
        int cur = getCurrentPage(player);
        if (cur > 0) currentPageMap.put(player, cur - 1);
    }
}
