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

public class ToolsUIBuilder extends BaseUIBuilder {
    
    public Inventory buildToolsUI(Player player) {
        Inventory ui = createInventory(player, 54, ItemMakerConstants.UI_TITLE_TOOLS);
        fillInventory(ui);

        // Navigation buttons
        addBackButton(ui, 0);
        ui.setItem(4, ItemUtils.quickItem(Material.NAME_TAG, ChatColor.AQUA + "Tools", 
            List.of(ChatColor.GRAY + "Choose a tool to preview")));
        addCloseButton(ui, 8);

        // Tool items
        List<ItemStack> tools = createToolList();
        final int[] slots = {
            10,11,12,13,14,15,
            19,20,21,22,23,24,
            28,29,30,31,32,33,
            37,38,39,40,41,42
        };
        
        int i = 0;
        for (ItemStack tool : tools) {
            if (i >= slots.length) break;
            ui.setItem(slots[i++], tool);
        }
        
        return ui;
    }
    
    private List<ItemStack> createToolList() {
        List<ItemStack> tools = new ArrayList<>();
        
        // Pickaxes
        tools.add(ItemUtils.quickItem(Material.WOODEN_PICKAXE, null, null));
        tools.add(ItemUtils.quickItem(Material.STONE_PICKAXE, null, null));
        tools.add(ItemUtils.quickItem(Material.IRON_PICKAXE, null, null));
        tools.add(ItemUtils.quickItem(Material.GOLDEN_PICKAXE, null, null));
        tools.add(ItemUtils.quickItem(Material.DIAMOND_PICKAXE, null, null));
        tools.add(ItemUtils.quickItem(Material.NETHERITE_PICKAXE, null, null));
        
        // Axes
        tools.add(ItemUtils.quickItem(Material.WOODEN_AXE, null, null));
        tools.add(ItemUtils.quickItem(Material.STONE_AXE, null, null));
        tools.add(ItemUtils.quickItem(Material.IRON_AXE, null, null));
        tools.add(ItemUtils.quickItem(Material.GOLDEN_AXE, null, null));
        tools.add(ItemUtils.quickItem(Material.DIAMOND_AXE, null, null));
        tools.add(ItemUtils.quickItem(Material.NETHERITE_AXE, null, null));
        
        // Shovels
        tools.add(ItemUtils.quickItem(Material.WOODEN_SHOVEL, null, null));
        tools.add(ItemUtils.quickItem(Material.STONE_SHOVEL, null, null));
        tools.add(ItemUtils.quickItem(Material.IRON_SHOVEL, null, null));
        tools.add(ItemUtils.quickItem(Material.GOLDEN_SHOVEL, null, null));
        tools.add(ItemUtils.quickItem(Material.DIAMOND_SHOVEL, null, null));
        tools.add(ItemUtils.quickItem(Material.NETHERITE_SHOVEL, null, null));
        
        // Hoes
        tools.add(ItemUtils.quickItem(Material.WOODEN_HOE, null, null));
        tools.add(ItemUtils.quickItem(Material.STONE_HOE, null, null));
        tools.add(ItemUtils.quickItem(Material.IRON_HOE, null, null));
        tools.add(ItemUtils.quickItem(Material.GOLDEN_HOE, null, null));
        tools.add(ItemUtils.quickItem(Material.DIAMOND_HOE, null, null));
        tools.add(ItemUtils.quickItem(Material.NETHERITE_HOE, null, null));
        
        return tools;
    }
}
