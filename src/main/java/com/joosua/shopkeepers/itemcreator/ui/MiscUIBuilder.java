package com.joosua.shopkeepers.itemcreator.ui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.joosua.shopkeepers.itemcreator.utils.ItemCreatorConstants;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class MiscUIBuilder extends BaseUIBuilder {
    
    public Inventory buildMiscUI(Player player) {
        Inventory ui = createInventory(player, 54, ItemCreatorConstants.UI_TITLE_MISC);
        fillInventory(ui);

        // Navigation buttons
        addBackButton(ui, 0);
        ui.setItem(4, ItemUtils.quickItem(Material.NAME_TAG, ChatColor.AQUA + "Misc", 
            List.of(ChatColor.GRAY + "Choose a item to preview")));
        addCloseButton(ui, 8);

        // Armor items
        List<ItemStack> misc = createMiscList();
        final int[] slots = {10,11,12,13,14,15,16, 19,20,21,22,23,24,25, 28,29,30,31,32,33,34, 37,38,39,40,41,42,43};
        
        int i = 0;
        for (ItemStack item : misc) {
            if (i >= slots.length) break;
            ui.setItem(slots[i++], item);
        }
        
        return ui;
    }
    
    private List<ItemStack> createMiscList() {
        List<ItemStack> misc = new ArrayList<>();
            misc.add(ItemUtils.quickItem(Material.STICK, null, null));
            misc.add(ItemUtils.quickItem(Material.SNOWBALL, null, null));
            misc.add(ItemUtils.quickItem(Material.EGG, null, null));
            misc.add(ItemUtils.quickItem(Material.WIND_CHARGE, null, null));
            misc.add(ItemUtils.quickItem(Material.TOTEM_OF_UNDYING, null, null));
            misc.add(ItemUtils.quickItem(Material.BUCKET, null, null));
            misc.add(ItemUtils.quickItem(Material.FISHING_ROD, null, null));
            misc.add(ItemUtils.quickItem(Material.FLINT_AND_STEEL, null, null));
            misc.add(ItemUtils.quickItem(Material.FIRE_CHARGE, null, null));
            misc.add(ItemUtils.quickItem(Material.SHEARS, null, null));
            misc.add(ItemUtils.quickItem(Material.BRUSH, null, null));
            misc.add(ItemUtils.quickItem(Material.SPYGLASS, null, null));
            misc.add(ItemUtils.quickItem(Material.ELYTRA, null, null));
            misc.add(ItemUtils.quickItem(Material.ENDER_PEARL, null, null));
            misc.add(ItemUtils.quickItem(Material.SHIELD, null, null));
            misc.add(ItemUtils.quickItem(Material.GLASS_BOTTLE, null, null));
            misc.add(ItemUtils.quickItem(Material.PLAYER_HEAD, null, null));
            misc.add(ItemUtils.quickItem(Material.GOAT_HORN, null, null));
            misc.add(ItemUtils.quickItem(Material.APPLE, null, null));

        return misc;
    }
}
