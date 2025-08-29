package com.joosua.shopkeepers.managers.editor;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.holders.IdInventoryHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AddTradeManager {
    private final ShopkeepersPlugin plugin;
    public AddTradeManager(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
    }
    public Inventory createAddTrade(UUID id) {
        Inventory inv = new IdInventoryHolder(id, 27, "Trade Adder").getInventory();
        List<String> signLore = Arrays.asList(
                ChatColor.YELLOW + "Put an item in the slot",
                ChatColor.YELLOW + "or use the item creator."
        );
        // Cost sign
        ItemStack costSign = plugin.getMainManager().quickItem(Material.OAK_SIGN, ChatColor.YELLOW + "Cost", signLore);
        inv.setItem(20, costSign);
        // Result sign
        ItemStack resultSign = plugin.getMainManager().quickItem(Material.OAK_SIGN, ChatColor.YELLOW + "Result", signLore);
        inv.setItem(24, resultSign);
        // Filler panels
        ItemStack filler = plugin.getMainManager().createFiller();
        int[] emptySlots = {0, 1, 2, 3, 4, 5, 6, 7, 8,
                            9, 10,   12, 13, 14,   16, 17,
                               19,    21, 22, 23,     25   };
        for (int i : emptySlots) {
            inv.setItem(i, filler);
        }
    // ItemCreator buttons above empty cost/result slots (2 -> returns to cost 11, 6 -> returns to result 15)
    ItemStack itemCreatorBtnLeft = plugin.getMainManager().quickItem(Material.CRAFTING_TABLE, ChatColor.GOLD + "Item Creator", List.of(ChatColor.GRAY + "Open Item Creator for cost slot"));
    ItemStack itemCreatorBtnRight = plugin.getMainManager().quickItem(Material.CRAFTING_TABLE, ChatColor.GOLD + "Item Creator", List.of(ChatColor.GRAY + "Open Item Creator for result slot"));
    inv.setItem(2, itemCreatorBtnLeft);
    inv.setItem(6, itemCreatorBtnRight);
        // Confirm
        ItemStack confirm = plugin.getMainManager().quickItem(Material.EMERALD, ChatColor.GREEN + "Confirm", null);
        inv.setItem(26, confirm);
        // Go back
        ItemStack goBack = plugin.getMainManager().quickItem(Material.BARRIER, ChatColor.RED + "Go back", null);
        inv.setItem(18, goBack);
        return inv;
    }
    public void confirmTradeAdd(Player player, ItemStack cost, ItemStack result, UUID id) {
        MerchantRecipe recipe = new MerchantRecipe(result, 999);
        recipe.addIngredient(cost);
        plugin.getTradeManager().addTrade(id, recipe);
        player.closeInventory();
    }
}
