package com.joosua.shopkeepers.managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MainManager {
    // create an item with material, name and lore
    public ItemStack quickItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if (lore != null && !lore.isEmpty()) meta.setLore(new ArrayList<>(lore));
        item.setItemMeta(meta);
        return item;
    }
    // create filler item
    public ItemStack createFiller() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        meta.setHideTooltip(true);
        item.setItemMeta(meta);
        return item;
    }
}
