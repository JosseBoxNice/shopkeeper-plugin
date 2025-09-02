package com.joosua.shopkeepers.itemmaker.ui;

import com.joosua.shopkeepers.itemmaker.utils.ItemMakerConstants;
import com.joosua.shopkeepers.itemmaker.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WeaponsUIBuilder extends BaseUIBuilder {
    
    public Inventory buildWeaponsUI(Player player) {
        Inventory ui = createInventory(player, 54, ItemMakerConstants.UI_TITLE_WEAPONS);
        fillInventory(ui);

        // Navigation buttons
        addBackButton(ui, 0);
        ui.setItem(4, ItemUtils.quickItem(Material.NAME_TAG, ChatColor.AQUA + "Weapons", 
            List.of(ChatColor.GRAY + "Choose a weapon to preview")));
        addCloseButton(ui, 8);

        // Weapon items
        List<ItemStack> weapons = createWeaponList();
        final int[] slots = {19,20,21,22,23,24,25, 28,29,30,31,32,33,34, 37,38,39,40,41,42,43, 44,45,46,47,48,49,50};
        
        int i = 0;
        for (ItemStack weapon : weapons) {
            if (i >= slots.length) break;
            ui.setItem(slots[i++], weapon);
        }
        
        return ui;
    }
    
    private List<ItemStack> createWeaponList() {
        List<ItemStack> weapons = new ArrayList<>();
        weapons.add(ItemUtils.quickItem(Material.WOODEN_SWORD, ChatColor.YELLOW + "Wooden Sword", null));
        weapons.add(ItemUtils.quickItem(Material.STONE_SWORD, ChatColor.YELLOW + "Stone Sword", null));
        weapons.add(ItemUtils.quickItem(Material.IRON_SWORD, ChatColor.YELLOW + "Iron Sword", null));
        weapons.add(ItemUtils.quickItem(Material.GOLDEN_SWORD, ChatColor.YELLOW + "Golden Sword", null));
        weapons.add(ItemUtils.quickItem(Material.DIAMOND_SWORD, ChatColor.YELLOW + "Diamond Sword", null));
        weapons.add(ItemUtils.quickItem(Material.NETHERITE_SWORD, ChatColor.YELLOW + "Netherite Sword", null));
        weapons.add(ItemUtils.quickItem(Material.MACE, ChatColor.YELLOW + "Mace", null));
        weapons.add(ItemUtils.quickItem(Material.WOODEN_AXE, ChatColor.YELLOW + "Wooden Axe", null));
        weapons.add(ItemUtils.quickItem(Material.STONE_AXE, ChatColor.YELLOW + "Stone Axe", null));
        weapons.add(ItemUtils.quickItem(Material.IRON_AXE, ChatColor.YELLOW + "Iron Axe", null));
        weapons.add(ItemUtils.quickItem(Material.GOLDEN_AXE, ChatColor.YELLOW + "Golden Axe", null));
        weapons.add(ItemUtils.quickItem(Material.DIAMOND_AXE, ChatColor.YELLOW + "Diamond Axe", null));
        weapons.add(ItemUtils.quickItem(Material.NETHERITE_AXE, ChatColor.YELLOW + "Netherite Axe", null));
        weapons.add(ItemUtils.quickItem(Material.TRIDENT, ChatColor.YELLOW + "Trident", null));
        weapons.add(ItemUtils.quickItem(Material.BOW, ChatColor.YELLOW + "Bow", null));
        weapons.add(ItemUtils.quickItem(Material.CROSSBOW, ChatColor.YELLOW + "Crossbow", null));
        
        return weapons;
    }
}
