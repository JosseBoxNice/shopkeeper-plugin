package com.joosua.shopkeepers.itemcreator.ui;

import com.joosua.shopkeepers.itemcreator.utils.ItemCreatorConstants;
import com.joosua.shopkeepers.itemcreator.utils.ItemUtils;
import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.Material;
import org.bukkit.ChatColor;

import java.util.List;

public class EnchUIBuilder extends BaseUIBuilder {
    private final ShopkeepersPlugin plugin;

    public EnchUIBuilder(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
    } 

    public Inventory buildEnchUI(Player player) {
        Inventory ui = createInventory(player, 54, ItemCreatorConstants.UI_TITLE_ENCHANTS);
        fillInventory(ui);

        addBackButton(ui, 0);
        ui.setItem(4, ItemUtils.quickItem(Material.NAME_TAG, ChatColor.AQUA + "Armor", 
            List.of(ChatColor.GRAY + "Choose an armor piece to preview")));
        addCloseButton(ui, 8);

        PlayerState state = plugin.getPlayerStateManager().get(player.getUniqueId());

        if (state.getPreview() != null) ui.setItem(4, state.getPreview().clone());
        // else ui.setItem(4, quickItem(Material.BARRIER, ChatColor.YELLOW + "No Preview", List.of(ChatColor.GRAY + "Pick a weapon first")));

        int slot = 9;
        for (Enchantment ench : Enchantment.values()) {
            ItemStack book = ItemUtils.quickItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + ItemUtils.niceName(ench),
                    List.of(ChatColor.GRAY + "Click to set level 0–255"));
            ItemMeta meta = book.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            pdc.set(ItemCreatorConstants.ENCH_KEY, PersistentDataType.STRING, ench.getKey().toString());
            book.setItemMeta(meta);

            if (slot >= 53) break;
            ui.setItem(slot++, book);
        }

        boolean unb = state.getPreview() != null && state.getPreview().getItemMeta() != null
                && state.getPreview().getItemMeta().isUnbreakable();
        ItemStack unbreakItem = ItemUtils.quickItem(unb ? Material.ENCHANTED_BOOK : Material.BOOK,
                ItemCreatorConstants.BTN_UNBREAK, List.of(ChatColor.GRAY + "Toggle unbreakable", ChatColor.GRAY + "Current: " + (unb ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF")));
        ui.setItem(53, unbreakItem);

        return ui;
    }
}
