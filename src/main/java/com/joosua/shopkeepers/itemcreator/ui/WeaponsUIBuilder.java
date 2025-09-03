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

public class WeaponsUIBuilder extends BaseUIBuilder {
    
    public Inventory buildWeaponsUI(Player player) {
        Inventory ui = createInventory(player, 54, ItemCreatorConstants.UI_TITLE_WEAPONS);
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
        weapons.add(ItemUtils.quickItem(Material.WOODEN_SWORD, null, null));
        weapons.add(ItemUtils.quickItem(Material.STONE_SWORD, null, null));
        weapons.add(ItemUtils.quickItem(Material.IRON_SWORD, null, null));
        weapons.add(ItemUtils.quickItem(Material.GOLDEN_SWORD, null, null));
        weapons.add(ItemUtils.quickItem(Material.DIAMOND_SWORD, null, null));
        weapons.add(ItemUtils.quickItem(Material.NETHERITE_SWORD, null, null));
        weapons.add(ItemUtils.quickItem(Material.MACE, null, null));
        weapons.add(ItemUtils.quickItem(Material.WOODEN_AXE, null, null));
        weapons.add(ItemUtils.quickItem(Material.STONE_AXE, null, null));
        weapons.add(ItemUtils.quickItem(Material.IRON_AXE, null, null));
        weapons.add(ItemUtils.quickItem(Material.GOLDEN_AXE, null, null));
        weapons.add(ItemUtils.quickItem(Material.DIAMOND_AXE, null, null));
        weapons.add(ItemUtils.quickItem(Material.NETHERITE_AXE, null, null));
        weapons.add(ItemUtils.quickItem(Material.TRIDENT, null, null));
        weapons.add(ItemUtils.quickItem(Material.BOW, null, null));
        weapons.add(ItemUtils.quickItem(Material.CROSSBOW, null, null));
        
        return weapons;
    }
}
