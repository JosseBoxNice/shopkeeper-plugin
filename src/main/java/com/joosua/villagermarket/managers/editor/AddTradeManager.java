package com.joosua.villagermarket.managers.editor;

import com.joosua.villagermarket.VillagerMarketMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

public class AddTradeManager {
    private final VillagerMarketMain plugin;
    public AddTradeManager(VillagerMarketMain plugin) {
        this.plugin = plugin;
    }
    public Inventory createAddTrade() {
        Inventory inv = Bukkit.createInventory(null, 27, "Trade Adder");
        // Cost sign
        ItemStack costSign = new ItemStack(Material.OAK_SIGN);
        plugin.getMainManager().renameItem(costSign, ChatColor.YELLOW + "Cost");
        inv.setItem(20, costSign);
        // Result sign
        ItemStack resultSign = new ItemStack(Material.OAK_SIGN);
        plugin.getMainManager().renameItem(resultSign, ChatColor.YELLOW + "Result");
        inv.setItem(24, resultSign);
        // Filler panels
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        plugin.getMainManager().renameItem(filler, " ");
        int[] emptySlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 13, 14, 17, 19,
                21, 22, 23, 25};
        for (int i : emptySlots) {
            inv.setItem(i, filler);
        }
        // Enchant
        ItemStack enchant = new ItemStack(Material.ENCHANTED_BOOK);
        plugin.getMainManager().renameItem(enchant, ChatColor.LIGHT_PURPLE + "Enchant");
        inv.setItem(12, enchant);
        inv.setItem(16, enchant);
        // Confirm
        ItemStack confirm = new ItemStack(Material.EMERALD);
        plugin.getMainManager().renameItem(confirm, ChatColor.GREEN + "Confirm");
        inv.setItem(26, confirm);
        // Go back
        ItemStack goBack = new ItemStack(Material.BARRIER);
        plugin.getMainManager().renameItem(goBack, ChatColor.RED + "Go back");
        inv.setItem(18, goBack);
        return inv;
    }
    public void confirmTradeAdd(Player player, ItemStack cost, ItemStack result) {
        MerchantRecipe recipe = new MerchantRecipe(result, 999);
        recipe.addIngredient(cost);
        plugin.getVillagerManager().addTrade(recipe);
        player.closeInventory();
    }
}
