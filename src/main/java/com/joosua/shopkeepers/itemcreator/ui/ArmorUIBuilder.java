package com.joosua.shopkeepers.itemcreator.ui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.joosua.shopkeepers.itemcreator.utils.ItemMakerConstants;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class ArmorUIBuilder extends BaseUIBuilder {
    
    public Inventory buildArmorUI(Player player) {
        Inventory ui = createInventory(player, 54, ItemMakerConstants.UI_TITLE_ARMOR);
        fillInventory(ui);

        // Navigation buttons
        addBackButton(ui, 0);
        ui.setItem(4, ItemUtils.quickItem(Material.NAME_TAG, ChatColor.AQUA + "Armor", 
            List.of(ChatColor.GRAY + "Choose an armor piece to preview")));
        addCloseButton(ui, 8);

        // Armor items
        List<ItemStack> armor = createArmorList();
        final int[] slots = {10,11,12,13,14,15, 19,20,21,22,23,24, 28,29,30,31,32,33, 37,38,39,40,41,42, 46};
        
        int i = 0;
        for (ItemStack armorPiece : armor) {
            if (i >= slots.length) break;
            ui.setItem(slots[i++], armorPiece);
        }
        
        return ui;
    }
    
    private List<ItemStack> createArmorList() {
        List<ItemStack> armor = new ArrayList<>();
            armor.add(ItemUtils.quickItem(Material.LEATHER_HELMET, null, null));
            armor.add(ItemUtils.quickItem(Material.CHAINMAIL_HELMET, null, null));
            armor.add(ItemUtils.quickItem(Material.IRON_HELMET, null, null));
            armor.add(ItemUtils.quickItem(Material.GOLDEN_HELMET, null, null));
            armor.add(ItemUtils.quickItem(Material.DIAMOND_HELMET, null, null));
            armor.add(ItemUtils.quickItem(Material.NETHERITE_HELMET, null, null));

            armor.add(ItemUtils.quickItem(Material.LEATHER_CHESTPLATE, null, null));
            armor.add(ItemUtils.quickItem(Material.CHAINMAIL_CHESTPLATE, null, null));
            armor.add(ItemUtils.quickItem(Material.IRON_CHESTPLATE, null, null));
            armor.add(ItemUtils.quickItem(Material.GOLDEN_CHESTPLATE, null, null));
            armor.add(ItemUtils.quickItem(Material.DIAMOND_CHESTPLATE, null, null));
            armor.add(ItemUtils.quickItem(Material.NETHERITE_CHESTPLATE, null, null));

            armor.add(ItemUtils.quickItem(Material.LEATHER_LEGGINGS, null, null));
            armor.add(ItemUtils.quickItem(Material.CHAINMAIL_LEGGINGS, null, null));
            armor.add(ItemUtils.quickItem(Material.IRON_LEGGINGS, null, null));
            armor.add(ItemUtils.quickItem(Material.GOLDEN_LEGGINGS, null, null));
            armor.add(ItemUtils.quickItem(Material.DIAMOND_LEGGINGS, null, null));
            armor.add(ItemUtils.quickItem(Material.NETHERITE_LEGGINGS, null, null));

            armor.add(ItemUtils.quickItem(Material.LEATHER_BOOTS, null, null));
            armor.add(ItemUtils.quickItem(Material.CHAINMAIL_BOOTS, null, null));
            armor.add(ItemUtils.quickItem(Material.IRON_BOOTS, null, null));
            armor.add(ItemUtils.quickItem(Material.GOLDEN_BOOTS, null, null));
            armor.add(ItemUtils.quickItem(Material.DIAMOND_BOOTS, null, null));
            armor.add(ItemUtils.quickItem(Material.NETHERITE_BOOTS, null, null));

            armor.add(ItemUtils.quickItem(Material.TURTLE_HELMET, null, null));

        return armor;
    }
}
