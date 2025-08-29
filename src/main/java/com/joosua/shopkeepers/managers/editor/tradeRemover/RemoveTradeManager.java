package com.joosua.shopkeepers.managers.editor.tradeRemover;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.holders.IdInventoryHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RemoveTradeManager {
    private final ShopkeepersPlugin plugin;
    public RemoveTradeManager(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
    }
    public Inventory createTradeRemover(UUID id, int page) {
    Inventory inv = new IdInventoryHolder(id, 27, "Trade remover (page " + (page+1) + ")").getInventory();
        // Filler panels
        ItemStack filler = plugin.getMainManager().createFiller();
        int[] emptySlots = {0, 1, 2, 3, 4, 5, 6, 7, 8,
                            9,                          17,
                                19, 20,     22,     24, 25, 26};
        for (int i : emptySlots) {
            inv.setItem(i, filler);
        }
        // Go back
        ItemStack goBack = plugin.getMainManager().quickItem(Material.BARRIER, ChatColor.RED + "Go back", null);
        inv.setItem(18, goBack);
        // Remove trade(s)
        List<ItemStack> removeTrades = new ArrayList<>();
        int tradeAmount = plugin.getTradeManager().getTradeAmount(id);
        for (int i = 0; i < tradeAmount; i++) {
            String name = "Remove trade " + (i + 1);
            ItemStack removeTrade = plugin.getMainManager().quickItem(Material.RED_CONCRETE, name, null);
            removeTrades.add(removeTrade);
        }
        // handle pages
        List<ItemStack> pageItems = plugin.getPageManager().getPage(removeTrades, page, 7);
        // Place up to 7 trade buttons starting at slot 10
        for (int i = 0; i < pageItems.size(); i++) {
            inv.setItem(10 + i, pageItems.get(i));
        }
        // Add navigation buttons
        int maxPageIndex = Math.max(0, (removeTrades.size() - 1) / 7);
        if (page > 0) {
            inv.setItem(21, plugin.getMainManager().quickItem(Material.ARROW, ChatColor.YELLOW + "Previous Page", null));
        }
        if (page < maxPageIndex) {
            inv.setItem(23, plugin.getMainManager().quickItem(Material.ARROW, ChatColor.YELLOW + "Next Page", null));
        }

        return inv;
    }
}
