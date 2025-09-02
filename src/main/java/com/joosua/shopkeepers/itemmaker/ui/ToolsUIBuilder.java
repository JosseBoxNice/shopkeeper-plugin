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
        tools.add(ItemUtils.quickItem(Material.WOODEN_PICKAXE, ChatColor.YELLOW + "Wooden Pickaxe", null));
        tools.add(ItemUtils.quickItem(Material.STONE_PICKAXE, ChatColor.YELLOW + "Stone Pickaxe", null));
        tools.add(ItemUtils.quickItem(Material.IRON_PICKAXE, ChatColor.YELLOW + "Iron Pickaxe", null));
        tools.add(ItemUtils.quickItem(Material.GOLDEN_PICKAXE, ChatColor.YELLOW + "Golden Pickaxe", null));
        tools.add(ItemUtils.quickItem(Material.DIAMOND_PICKAXE, ChatColor.YELLOW + "Diamond Pickaxe", null));
        tools.add(ItemUtils.quickItem(Material.NETHERITE_PICKAXE, ChatColor.YELLOW + "Netherite Pickaxe", null));
        
        // Axes
        tools.add(ItemUtils.quickItem(Material.WOODEN_AXE, ChatColor.YELLOW + "Wooden Axe", null));
        tools.add(ItemUtils.quickItem(Material.STONE_AXE, ChatColor.YELLOW + "Stone Axe", null));
        tools.add(ItemUtils.quickItem(Material.IRON_AXE, ChatColor.YELLOW + "Iron Axe", null));
        tools.add(ItemUtils.quickItem(Material.GOLDEN_AXE, ChatColor.YELLOW + "Golden Axe", null));
        tools.add(ItemUtils.quickItem(Material.DIAMOND_AXE, ChatColor.YELLOW + "Diamond Axe", null));
        tools.add(ItemUtils.quickItem(Material.NETHERITE_AXE, ChatColor.YELLOW + "Netherite Axe", null));
        
        // Shovels
        tools.add(ItemUtils.quickItem(Material.WOODEN_SHOVEL, ChatColor.YELLOW + "Wooden Shovel", null));
        tools.add(ItemUtils.quickItem(Material.STONE_SHOVEL, ChatColor.YELLOW + "Stone Shovel", null));
        tools.add(ItemUtils.quickItem(Material.IRON_SHOVEL, ChatColor.YELLOW + "Iron Shovel", null));
        tools.add(ItemUtils.quickItem(Material.GOLDEN_SHOVEL, ChatColor.YELLOW + "Golden Shovel", null));
        tools.add(ItemUtils.quickItem(Material.DIAMOND_SHOVEL, ChatColor.YELLOW + "Diamond Shovel", null));
        tools.add(ItemUtils.quickItem(Material.NETHERITE_SHOVEL, ChatColor.YELLOW + "Netherite Shovel", null));
        
        // Hoes
        tools.add(ItemUtils.quickItem(Material.WOODEN_HOE, ChatColor.YELLOW + "Wooden Hoe", null));
        tools.add(ItemUtils.quickItem(Material.STONE_HOE, ChatColor.YELLOW + "Stone Hoe", null));
        tools.add(ItemUtils.quickItem(Material.IRON_HOE, ChatColor.YELLOW + "Iron Hoe", null));
        tools.add(ItemUtils.quickItem(Material.GOLDEN_HOE, ChatColor.YELLOW + "Golden Hoe", null));
        tools.add(ItemUtils.quickItem(Material.DIAMOND_HOE, ChatColor.YELLOW + "Diamond Hoe", null));
        tools.add(ItemUtils.quickItem(Material.NETHERITE_HOE, ChatColor.YELLOW + "Netherite Hoe", null));
        
        return tools;
    }
}
