package com.joosua.shopkeepers.itemmaker.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.LinkedHashMap;

public final class ItemUtils {
    private ItemUtils() {} // Prevent instantiation

    public static ItemStack quickItem(Material mat, String name, List<String> lore) {
        ItemStack it = new ItemStack(mat);
        ItemMeta meta = it.getItemMeta();
        meta.setDisplayName(name);
        if (lore != null && !lore.isEmpty()) meta.setLore(new ArrayList<>(lore));
        // Note: We don't automatically hide attributes anymore - let the user control this
        it.setItemMeta(meta);
        return it;
    }

    public static ItemStack toggleItem(Material mat, String name, boolean on) {
        ItemStack it = quickItem(mat, name + ChatColor.GRAY + " [" + (on ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF") + ChatColor.GRAY + "]",
                List.of(ChatColor.GRAY + "Click to toggle"));
        ItemMeta m = it.getItemMeta();
        // Avoid Paper-specific API call; use enchantment hide flag for visual hint
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        it.setItemMeta(m);
        return it;
    }

    public static String legacyHex(String hex) {
        String h = hex.startsWith("#") ? hex.substring(1) : hex;
        StringBuilder sb = new StringBuilder("§x");
        for (char c : h.toCharArray()) sb.append('§').append(c);
        return sb.toString();
    }

    public static Map<Material, String> dyeHexMap() {
        Map<Material, String> m = new LinkedHashMap<>();
        m.put(Material.WHITE_DYE,       "#FFFFFF");
        m.put(Material.ORANGE_DYE,      "#F9801D");
        m.put(Material.MAGENTA_DYE,     "#C74EBD");
        m.put(Material.LIGHT_BLUE_DYE,  "#3AB3DA");
        m.put(Material.YELLOW_DYE,      "#FED83D");
        m.put(Material.LIME_DYE,        "#80C71F");
        m.put(Material.LIGHT_GRAY_DYE,  "#9D9D97");
        m.put(Material.PINK_DYE,        "#F38BAA");
        m.put(Material.GRAY_DYE,        "#474F52");
        m.put(Material.CYAN_DYE,        "#169C9C");
        m.put(Material.PURPLE_DYE,      "#8932B8");
        m.put(Material.BLUE_DYE,        "#3C44AA");
        m.put(Material.BROWN_DYE,       "#835432");
        m.put(Material.GREEN_DYE,       "#5E7C16");
        m.put(Material.RED_DYE,         "#B02E26");
        m.put(Material.BLACK_DYE,       "#1D1D21");
        return m;
    }

    public static String hexForDye(Material mat) {
        return dyeHexMap().get(mat);
    }

    public static String niceName(org.bukkit.enchantments.Enchantment ench) {
        String key = ench.getKey().getKey().replace('_', ' ');
        StringBuilder sb = new StringBuilder();
        for (String p : key.split(" ")) {
            if (p.isEmpty()) continue;
            sb.append(p.substring(0,1).toUpperCase(Locale.ROOT))
                    .append(p.substring(1).toLowerCase(Locale.ROOT)).append(" ");
        }
        return sb.toString().trim();
    }

    public static String getDisplayName(ItemStack item) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return "";
        return item.getItemMeta().getDisplayName();
    }

    public static String getItemDisplayName(ItemStack item, Material type) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        }
        return type.name();
    }
}
