package com.joosua.shopkeepers.itemmaker;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.LinkedHashMap;

public class CreateItemCommand implements org.bukkit.command.CommandExecutor {

    // Titles
    static final String UI_TITLE_MAIN       = ChatColor.DARK_GREEN + "ItemMaker";
    static final String UI_TITLE_WEAPONS    = ChatColor.DARK_AQUA + "ItemMaker - Weapons";
    static final String UI_TITLE_ARMOR      = ChatColor.DARK_AQUA + "ItemMaker - Armor";
    static final String UI_TITLE_ENCHANTS   = ChatColor.DARK_PURPLE + "ItemMaker - Enchants";
    static final String UI_TITLE_TOOLS      = ChatColor.DARK_AQUA + "ItemMaker - Tools";
    static final String UI_TITLE_MISC      = ChatColor.DARK_BLUE + "ItemMaker - Misc";
    static final String UI_TITLE_STYLE      = ChatColor.GOLD + "ItemMaker - Style";
    static final String UI_TITLE_STYLE_NAME = ChatColor.GOLD + "ItemMaker - Name";
    static final String UI_TITLE_STYLE_NAME_FMT = ChatColor.GOLD + "ItemMaker - Name Formatting";
    static final String UI_TITLE_STYLE_LORE = ChatColor.GOLD + "ItemMaker - Lore";
    static final String UI_TITLE_STYLE_LORE_FMT = ChatColor.GOLD + "ItemMaker - Lore Formatting";
    static final String UI_TITLE_MODIFIERS  = ChatColor.BLUE + "ItemMaker - Modifiers";

    // Main buttons
    static final String BTN_CLOSE   = ChatColor.RED + "Close";
    static final String BTN_ENCH    = ChatColor.LIGHT_PURPLE + "Enchants";
    static final String BTN_CREATE  = ChatColor.GREEN + "Create Item";
    static final String BTN_WEAPONS = ChatColor.AQUA + "Weapons";
    static final String BTN_TOOLS = ChatColor.AQUA + "Tools";
    static final String BTN_ARMOR = ChatColor.AQUA + "Armor";
    static final String BTN_MISC = ChatColor.AQUA + "Misc";
    static final String BTN_STYLE   = ChatColor.GOLD + "Style";
    static final String BTN_MODIFIERS = ChatColor.AQUA + "Modifiers";
    static final String BTN_UNBREAK = ChatColor.GOLD + "Unbreakable";

    // Style main
    static final String BTN_STYLE_SET_NAME = ChatColor.YELLOW + "Set Name";
    static final String BTN_STYLE_LORE     = ChatColor.AQUA + "Set Lore";

    // Name UI
    static final String BTN_NAME_SETNAME = ChatColor.YELLOW + "Set Name";
    static final String BTN_NAME_SETCOLOR = ChatColor.LIGHT_PURPLE + "Set Color";
    static final String BTN_NAME_FORMAT   = ChatColor.GOLD + "Formatting";
    static final String UI_TITLE_STYLE_NAME_COLOR = ChatColor.GOLD + "ItemMaker - Name Color";
    static final String UI_TITLE_STYLE_LORE_COLOR = ChatColor.GOLD + "ItemMaker - Lore Color";
    static final String BTN_COLOR_HEX = ChatColor.LIGHT_PURPLE + "Custom Hex";

    // Lore UI
    static final String BTN_LORE_ADDLINE  = ChatColor.AQUA + "Add Lore Line";
    static final String BTN_LORE_SETCOLOR = ChatColor.LIGHT_PURPLE + "Set Color";
    static final String BTN_LORE_FORMAT   = ChatColor.GOLD + "Formatting";
    static final String BTN_LORE_REMLAST  = ChatColor.RED + "Remove Last Lore Line";
    static final String BTN_LORE_CLEAR    = ChatColor.DARK_RED + "Clear Lore";

    // Formatting toggles
    static final String BTN_T_BOLD   = ChatColor.GREEN + "Bold";
    static final String BTN_T_ITALIC = ChatColor.GREEN + "Italic";
    static final String BTN_T_UNDER  = ChatColor.GREEN + "Underlined";
    static final String BTN_T_STRIKE = ChatColor.GREEN + "Strikethrough";
    static final String BTN_T_OBFUS  = ChatColor.GREEN + "Obfuscated";

    private final ShopkeepersPlugin plugin;
    private final NamespacedKey ENCH_KEY;

    public CreateItemCommand(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.ENCH_KEY = new NamespacedKey(plugin, "enchant_key");
    }

    // ===== MAIN (36) =====
    public Inventory buildMainUI(Player player) {
        var state = plugin.getItemMakerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 36, UI_TITLE_MAIN);

        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);

        ui.setItem(12, quickItem(Material.DIAMOND_SWORD, BTN_WEAPONS, List.of(ChatColor.GRAY + "Browse all weapon types")));
        ui.setItem(13, quickItem(Material.NETHERITE_PICKAXE, BTN_TOOLS, List.of(ChatColor.GRAY + "Pickaxes, axes, shovels")));
        ui.setItem(14, quickItem(Material.DIAMOND_CHESTPLATE, BTN_ARMOR, List.of(ChatColor.GRAY + "Helmets, chestplates, etc.")));
        ui.setItem(15, quickItem(Material.STICK, BTN_MISC, List.of(ChatColor.GRAY + "Other items")));

        if (state.getPreview() != null) ui.setItem(4, state.getPreview().clone());
        else ui.setItem(4, quickItem(Material.BARRIER, ChatColor.YELLOW + "Preview", List.of(ChatColor.GRAY + "Pick a category to preview items here")));

        ui.setItem(35, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close the ItemMaker")));

        if (state.getPreview() != null) {
            ui.setItem(27, quickItem(Material.EMERALD_BLOCK, BTN_CREATE, List.of(ChatColor.GRAY + "Give yourself the item")));
            ui.setItem(30, quickItem(Material.ENCHANTED_BOOK, BTN_ENCH, List.of(ChatColor.GRAY + "Pick enchantments")));
            ui.setItem(31, quickItem(Material.NAME_TAG, BTN_STYLE, List.of(ChatColor.GRAY + "Name/Lore styling")));
            ui.setItem(32, quickItem(Material.NETHER_STAR, BTN_MODIFIERS, List.of(ChatColor.GRAY + "Edit item modifiers")));
        }
        return ui;
    }

    // ===== WEAPONS (54) =====
    public Inventory buildWeaponsUI(Player player) {
        Inventory ui = Bukkit.createInventory(player, 54, UI_TITLE_WEAPONS);
        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);

        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Return to ItemMaker")));
        ui.setItem(4, quickItem(Material.NAME_TAG, ChatColor.AQUA + "Weapons", List.of(ChatColor.GRAY + "Choose a weapon to preview")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));

        List<ItemStack> weapons = new ArrayList<>();
        weapons.add(quickItem(Material.WOODEN_SWORD, ChatColor.YELLOW + "Wooden Sword", null));
        weapons.add(quickItem(Material.STONE_SWORD, ChatColor.YELLOW + "Stone Sword", null));
        weapons.add(quickItem(Material.IRON_SWORD, ChatColor.YELLOW + "Iron Sword", null));
        weapons.add(quickItem(Material.GOLDEN_SWORD, ChatColor.YELLOW + "Golden Sword", null));
        weapons.add(quickItem(Material.DIAMOND_SWORD, ChatColor.YELLOW + "Diamond Sword", null));
        weapons.add(quickItem(Material.NETHERITE_SWORD, ChatColor.YELLOW + "Netherite Sword", null));
        weapons.add(quickItem(Material.MACE, ChatColor.YELLOW + "Mace", null));
        weapons.add(quickItem(Material.WOODEN_AXE, ChatColor.YELLOW + "Wooden Axe", null));
        weapons.add(quickItem(Material.STONE_AXE, ChatColor.YELLOW + "Stone Axe", null));
        weapons.add(quickItem(Material.IRON_AXE, ChatColor.YELLOW + "Iron Axe", null));
        weapons.add(quickItem(Material.GOLDEN_AXE, ChatColor.YELLOW + "Golden Axe", null));
        weapons.add(quickItem(Material.DIAMOND_AXE, ChatColor.YELLOW + "Diamond Axe", null));
        weapons.add(quickItem(Material.NETHERITE_AXE, ChatColor.YELLOW + "Netherite Axe", null));
        weapons.add(quickItem(Material.TRIDENT, ChatColor.YELLOW + "Trident", null));
        weapons.add(quickItem(Material.BOW, ChatColor.YELLOW + "Bow", null));
        weapons.add(quickItem(Material.CROSSBOW, ChatColor.YELLOW + "Crossbow", null));

        final int[] slots = {19,20,21,22,23,24,25, 28,29,30,31,32,33,34, 37,38,39,40,41,42,43, 44,45,46,47,48,49,50};
        int i = 0;
        for (ItemStack w : weapons) {
            if (i >= slots.length) break;
            ui.setItem(slots[i++], w);
        }
        return ui;
    }

    /* TOOLS 54-slot menu listing common tools */
    public Inventory buildToolsUI(Player player) {
        Inventory ui = Bukkit.createInventory(player, 54, UI_TITLE_TOOLS);

        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);

        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back",
                List.of(ChatColor.GRAY + "Return to ItemMaker")));
        ui.setItem(4, quickItem(Material.NAME_TAG, ChatColor.AQUA + "Tools",
                List.of(ChatColor.GRAY + "Choose a tool to preview")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE,
                List.of(ChatColor.GRAY + "Close ItemMaker")));

        List < ItemStack > tools = new ArrayList<>();
        // Pickaxes
        tools.add(quickItem(Material.WOODEN_PICKAXE, ChatColor.YELLOW + "Wooden Pickaxe", null));
        tools.add(quickItem(Material.STONE_PICKAXE, ChatColor.YELLOW + "Stone Pickaxe", null));
        tools.add(quickItem(Material.IRON_PICKAXE, ChatColor.YELLOW + "Iron Pickaxe", null));
        tools.add(quickItem(Material.GOLDEN_PICKAXE, ChatColor.YELLOW + "Golden Pickaxe", null));
        tools.add(quickItem(Material.DIAMOND_PICKAXE, ChatColor.YELLOW + "Diamond Pickaxe", null));
        tools.add(quickItem(Material.NETHERITE_PICKAXE, ChatColor.YELLOW + "Netherite Pickaxe", null));
        // Axes
        tools.add(quickItem(Material.WOODEN_AXE, ChatColor.YELLOW + "Wooden Axe", null));
        tools.add(quickItem(Material.STONE_AXE, ChatColor.YELLOW + "Stone Axe", null));
        tools.add(quickItem(Material.IRON_AXE, ChatColor.YELLOW + "Iron Axe", null));
        tools.add(quickItem(Material.GOLDEN_AXE, ChatColor.YELLOW + "Golden Axe", null));
        tools.add(quickItem(Material.DIAMOND_AXE, ChatColor.YELLOW + "Diamond Axe", null));
        tools.add(quickItem(Material.NETHERITE_AXE, ChatColor.YELLOW + "Netherite Axe", null));
        // Shovels
        tools.add(quickItem(Material.WOODEN_SHOVEL, ChatColor.YELLOW + "Wooden Shovel", null));
        tools.add(quickItem(Material.STONE_SHOVEL, ChatColor.YELLOW + "Stone Shovel", null));
        tools.add(quickItem(Material.IRON_SHOVEL, ChatColor.YELLOW + "Iron Shovel", null));
        tools.add(quickItem(Material.GOLDEN_SHOVEL, ChatColor.YELLOW + "Golden Shovel", null));
        tools.add(quickItem(Material.DIAMOND_SHOVEL, ChatColor.YELLOW + "Diamond Shovel", null));
        tools.add(quickItem(Material.NETHERITE_SHOVEL, ChatColor.YELLOW + "Netherite Shovel", null));
        // Hoes
        tools.add(quickItem(Material.WOODEN_HOE, ChatColor.YELLOW + "Wooden Hoe", null));
        tools.add(quickItem(Material.STONE_HOE, ChatColor.YELLOW + "Stone Hoe", null));
        tools.add(quickItem(Material.IRON_HOE, ChatColor.YELLOW + "Iron Hoe", null));
        tools.add(quickItem(Material.GOLDEN_HOE, ChatColor.YELLOW + "Golden Hoe", null));
        tools.add(quickItem(Material.DIAMOND_HOE, ChatColor.YELLOW + "Diamond Hoe", null));
        tools.add(quickItem(Material.NETHERITE_HOE, ChatColor.YELLOW + "Netherite Hoe", null));

        final int[] slots = {
                10,11,12,13,14,15,
                19,20,21,22,23,24,
                28,29,30,31,32,33,
                37,38,39,40,41,42
        };
        int i = 0;
        for (ItemStack t : tools) {
            if (i >= slots.length) break;
            ui.setItem(slots[i++], t);
        }
        return ui;
    }

    // ===== ARMOR 45-slot menu listing armor pieces =====
    public Inventory buildArmorUI(Player player) {
        Inventory ui = Bukkit.createInventory(player, 45, UI_TITLE_ARMOR);
        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);

        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Return to ItemMaker")));
        ui.setItem(4, quickItem(Material.NAME_TAG, ChatColor.AQUA + "Armor", List.of(ChatColor.GRAY + "Choose armor to preview")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));

        List<ItemStack> items = new ArrayList<>();
        items.add(quickItem(Material.LEATHER_HELMET,     ChatColor.YELLOW + "Leather Helmet", null));
        items.add(quickItem(Material.CHAINMAIL_HELMET,     ChatColor.YELLOW + "Chainmail Helmet", null));
        items.add(quickItem(Material.IRON_HELMET,     ChatColor.YELLOW + "Iron Helmet", null));
        items.add(quickItem(Material.GOLDEN_HELMET,     ChatColor.YELLOW + "Golden Helmet", null));
        items.add(quickItem(Material.DIAMOND_HELMET,     ChatColor.YELLOW + "Diamond Helmet", null));
        items.add(quickItem(Material.NETHERITE_HELMET,     ChatColor.YELLOW + "Netherite Helmet", null));
        items.add(quickItem(Material.TURTLE_HELMET, ChatColor.YELLOW + "Turtle Helmet", null));

        items.add(quickItem(Material.LEATHER_CHESTPLATE, ChatColor.YELLOW + "Leather Chestplate", null));
        items.add(quickItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.YELLOW + "Chainmail Chestplate", null));
        items.add(quickItem(Material.IRON_CHESTPLATE, ChatColor.YELLOW + "Iron Chestplate", null));
        items.add(quickItem(Material.GOLDEN_CHESTPLATE, ChatColor.YELLOW + "Golden Chestplate", null));
        items.add(quickItem(Material.DIAMOND_CHESTPLATE, ChatColor.YELLOW + "Diamond Chestplate", null));
        items.add(quickItem(Material.NETHERITE_CHESTPLATE, ChatColor.YELLOW + "Netherite Chestplate", null));

        items.add(quickItem(Material.LEATHER_LEGGINGS,   ChatColor.YELLOW + "Leather Leggings", null));
        items.add(quickItem(Material.CHAINMAIL_LEGGINGS,   ChatColor.YELLOW + "Chainmail Leggings", null));
        items.add(quickItem(Material.IRON_LEGGINGS,   ChatColor.YELLOW + "Iron Leggings", null));
        items.add(quickItem(Material.GOLDEN_LEGGINGS,   ChatColor.YELLOW + "Golden Leggings", null));
        items.add(quickItem(Material.DIAMOND_LEGGINGS,   ChatColor.YELLOW + "Diamond Leggings", null));
        items.add(quickItem(Material.NETHERITE_LEGGINGS,   ChatColor.YELLOW + "Netherite Leggings", null));

        items.add(quickItem(Material.LEATHER_BOOTS,      ChatColor.YELLOW + "Leather Boots", null));
        items.add(quickItem(Material.CHAINMAIL_BOOTS,      ChatColor.YELLOW + "Chainmail Boots", null));
        items.add(quickItem(Material.IRON_BOOTS,      ChatColor.YELLOW + "Iron Boots", null));
        items.add(quickItem(Material.GOLDEN_BOOTS,      ChatColor.YELLOW + "Golden Boots", null));
        items.add(quickItem(Material.DIAMOND_BOOTS,      ChatColor.YELLOW + "Diamond Boots", null));
        items.add(quickItem(Material.NETHERITE_BOOTS,      ChatColor.YELLOW + "Netherite Boots", null));


        final int[] slots = {
                10, 11, 12, 13, 14, 15, 16,
                19, 20, 21, 22, 23, 24,
                28, 29, 30, 31, 32, 33,
                37, 38, 39, 40, 41, 42
        };
        int i = 0;
        for (ItemStack w : items) {
            if (i >= slots.length) break;
            ui.setItem(slots[i++], w);
        }
        return ui;
    }

    /* MISC 45-slot menu listing common tools */
    public Inventory buildMiscUI(Player player) {
        Inventory ui = Bukkit.createInventory(player, 45, UI_TITLE_MISC);

        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);

        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back",
                List.of(ChatColor.GRAY + "Return to ItemMaker")));
        ui.setItem(4, quickItem(Material.NAME_TAG, ChatColor.AQUA + "Misc",
                List.of(ChatColor.GRAY + "Choose an item to preview")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE,
                List.of(ChatColor.GRAY + "Close ItemMaker")));

        List < ItemStack > misc = new ArrayList<>();
        // Misc
        misc.add(quickItem(Material.STICK, ChatColor.YELLOW + "Stick", null));
        misc.add(quickItem(Material.SNOWBALL, ChatColor.YELLOW + "Snowball", null));
        misc.add(quickItem(Material.EGG, ChatColor.YELLOW + "Egg", null));
        misc.add(quickItem(Material.WIND_CHARGE, ChatColor.YELLOW + "Wind Charge", null));
        misc.add(quickItem(Material.TOTEM_OF_UNDYING, ChatColor.YELLOW + "Totem of Undying", null));
        misc.add(quickItem(Material.BUCKET, ChatColor.YELLOW + "Bucket", null));
        misc.add(quickItem(Material.FISHING_ROD, ChatColor.YELLOW + "Fishing Rod", null));
        misc.add(quickItem(Material.FLINT_AND_STEEL, ChatColor.YELLOW + "Flint and Steel", null));
        misc.add(quickItem(Material.FIRE_CHARGE, ChatColor.YELLOW + "Fire Charge", null));
        misc.add(quickItem(Material.SHEARS, ChatColor.YELLOW + "Shears", null));
        misc.add(quickItem(Material.BRUSH, ChatColor.YELLOW + "Brush", null));
        misc.add(quickItem(Material.SPYGLASS, ChatColor.YELLOW + "Spyglass", null));
        misc.add(quickItem(Material.ELYTRA, ChatColor.YELLOW + "Elytra", null));
        misc.add(quickItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Ender Pearl", null));
        misc.add(quickItem(Material.SHIELD, ChatColor.YELLOW + "Shield", null));
        misc.add(quickItem(Material.GLASS_BOTTLE, ChatColor.YELLOW + "Glass Bottle", null));
        misc.add(quickItem(Material.PLAYER_HEAD, ChatColor.YELLOW + "Player Head", null));
        misc.add(quickItem(Material.GOAT_HORN, ChatColor.YELLOW + "Goat Horn", null));

        final int[] slots = {
                10,11,12,13,14,15,
                19,20,21,22,23,24,
                28,29,30,31,32,33,
        };
        int i = 0;
        for (ItemStack t : misc) {
            if (i >= slots.length) break;
            ui.setItem(slots[i++], t);
        }
        return ui;
    }

    // ===== STYLE MAIN (36) =====
    public Inventory buildStyleUI(Player player) {
        var state = plugin.getItemMakerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 36, UI_TITLE_STYLE);

        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);

        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Return to ItemMaker")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));
        if (state.getPreview() != null) ui.setItem(4, state.getPreview().clone());
        else ui.setItem(4, quickItem(Material.BARRIER, ChatColor.YELLOW + "No Preview", List.of(ChatColor.GRAY + "Pick a weapon first")));

        ui.setItem(21, quickItem(Material.NAME_TAG, BTN_STYLE_SET_NAME, List.of(ChatColor.GRAY + "Open name editor")));
        ui.setItem(23, quickItem(Material.WRITABLE_BOOK, BTN_STYLE_LORE, List.of(ChatColor.GRAY + "Open lore editor")));
        return ui;
    }

    // ===== NAME UI (45) =====
    public Inventory buildNameUI(Player player) {
        var state = plugin.getItemMakerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 45, UI_TITLE_STYLE_NAME);

        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);

        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Back to Style")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));
        if (state.getPreview() != null) ui.setItem(4, state.getPreview().clone());

        ui.setItem(20, quickItem(Material.NAME_TAG, BTN_NAME_SETNAME, List.of(ChatColor.GRAY + "Type new display name")));
        ui.setItem(22, quickItem(Material.GRAY_DYE, BTN_NAME_SETCOLOR, List.of(ChatColor.GRAY + "Open name colors")));
        ui.setItem(24, quickItem(Material.FEATHER, BTN_NAME_FORMAT, List.of(ChatColor.GRAY + "Open formatting toggles")));
        return ui;
    }
    public Inventory buildNameColorUI(Player player) {
        var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 45, UI_TITLE_STYLE_NAME_COLOR);
        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);
        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Back to Name")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));
        if (s.getPreview() != null) ui.setItem(4, s.getPreview().clone());

        int slot = 10;
        for (var e : dyeHexMap().entrySet()) {
            Material dye = e.getKey(); String hex = e.getValue();
            String nice = dye.name().replace("_DYE","" ).toLowerCase().replace('_',' ');
            String dn = legacyHex(hex) + nice.substring(0,1).toUpperCase() + nice.substring(1);
            ui.setItem(slot++, quickItem(dye, dn, List.of(ChatColor.GRAY + "Apply to name")));
            if (slot == 17 || slot == 26 || slot == 35 || slot == 44) slot += 2;
            if (slot >= 53) break;
        }
        ui.setItem(40, quickItem(Material.PAPER, BTN_COLOR_HEX, List.of(ChatColor.GRAY + "Enter a custom #RRGGBB")));
        return ui;
    }

    public Inventory buildNameFormatUI(Player player) {
        var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 45, UI_TITLE_STYLE_NAME_FMT);

        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);

        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Back to Name")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));
        if (s.getPreview() != null) ui.setItem(4, s.getPreview().clone());

        ui.setItem(20, toggleItem(Material.ANVIL,     BTN_T_BOLD,   s.isNameBold()));
        ui.setItem(21, toggleItem(Material.FEATHER,   BTN_T_ITALIC, s.isNameItalic()));
        ui.setItem(22, toggleItem(Material.STRING,    BTN_T_UNDER,  s.isNameUnder()));
        ui.setItem(23, toggleItem(Material.SHEARS,    BTN_T_STRIKE, s.isNameStrike()));
        ui.setItem(24, toggleItem(Material.ENDER_EYE, BTN_T_OBFUS,  s.isNameObfus()));
        return ui;
    }

    // ===== LORE UI (54) =====
    public Inventory buildLoreUI(Player player) {
        var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 54, UI_TITLE_STYLE_LORE);

        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);

        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Back to Style")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));
        if (s.getPreview() != null) ui.setItem(4, s.getPreview().clone());
        int slot = 10;
        ui.setItem(20, quickItem(Material.WRITABLE_BOOK, BTN_LORE_ADDLINE, List.of(ChatColor.GRAY + "Type a lore line")));
        ui.setItem(22, quickItem(Material.GRAY_DYE, BTN_LORE_SETCOLOR, List.of(ChatColor.GRAY + "Open lore colors")));
        ui.setItem(24, quickItem(Material.FEATHER,        BTN_LORE_FORMAT, List.of(ChatColor.GRAY + "Open formatting toggles")));
        ui.setItem(29, quickItem(Material.SPECTRAL_ARROW, BTN_LORE_REMLAST, List.of(ChatColor.GRAY + "Remove last lore line")));
        ui.setItem(38, quickItem(Material.TNT,   BTN_LORE_CLEAR,   List.of(ChatColor.GRAY + "Clear all lore")));
        return ui;
    }

    public Inventory buildLoreFormatUI(Player player) {
        var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 45, UI_TITLE_STYLE_LORE_FMT);
        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);
        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Back to Lore")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));
        if (s.getPreview() != null) ui.setItem(4, s.getPreview().clone());

        ui.setItem(20, toggleItem(Material.ANVIL,     BTN_T_BOLD,   s.isLoreBold()));
        ui.setItem(21, toggleItem(Material.FEATHER,   BTN_T_ITALIC, s.isLoreItalic()));
        ui.setItem(22, toggleItem(Material.STRING,    BTN_T_UNDER,  s.isLoreUnder()));
        ui.setItem(23, toggleItem(Material.SHEARS,    BTN_T_STRIKE, s.isLoreStrike()));
        ui.setItem(24, toggleItem(Material.ENDER_EYE, BTN_T_OBFUS,  s.isLoreObfus()));
        return ui;
    }

    public Inventory buildLoreColorUI(Player player) {
        var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 45, UI_TITLE_STYLE_LORE_COLOR);
        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);
        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Back to Lore")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));
        if (s.getPreview() != null) ui.setItem(4, s.getPreview().clone());

        int slot = 10;
        for (var e : dyeHexMap().entrySet()) {
            Material dye = e.getKey(); String hex = e.getValue();
            String nice = dye.name().replace("_DYE","" ).toLowerCase().replace('_',' ');
            String dn = legacyHex(hex) + nice.substring(0,1).toUpperCase() + nice.substring(1);
            ui.setItem(slot++, quickItem(dye, dn, List.of(ChatColor.GRAY + "Apply to lore")));
            if (slot == 17 || slot == 26 || slot == 35 || slot == 44) slot += 2;
            if (slot >= 53) break;
        }
        ui.setItem(40, quickItem(Material.PAPER, BTN_COLOR_HEX, List.of(ChatColor.GRAY + "Enter a custom #RRGGBB")));
        return ui;
    }

    // ===== ENCHANTS (54) with Unbreakable toggle =====
    public Inventory buildEnchantsUI(Player player) {
        var state = plugin.getItemMakerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 54, UI_TITLE_ENCHANTS);

        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);

        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Return to ItemMaker")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));
        if (state.getPreview() != null) ui.setItem(4, state.getPreview().clone());
        else ui.setItem(4, quickItem(Material.BARRIER, ChatColor.YELLOW + "No Preview", List.of(ChatColor.GRAY + "Pick a weapon first")));

        int slot = 9;
        for (Enchantment ench : Enchantment.values()) {
            ItemStack book = quickItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + niceName(ench),
                    List.of(ChatColor.GRAY + "Click to set level 0–255"));
            ItemMeta meta = book.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            pdc.set(ENCH_KEY, PersistentDataType.STRING, ench.getKey().toString());
            book.setItemMeta(meta);

            if (slot >= 53) break;
            ui.setItem(slot++, book);
        }

        boolean unb = state.getPreview() != null && state.getPreview().getItemMeta() != null
                && state.getPreview().getItemMeta().isUnbreakable();
        ItemStack unbreakItem = quickItem(unb ? Material.ENCHANTED_BOOK : Material.BOOK,
                BTN_UNBREAK, List.of(ChatColor.GRAY + "Toggle unbreakable", ChatColor.GRAY + "Current: " + (unb ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF")));
        ui.setItem(53, unbreakItem);
        return ui;
    }

    /** Modifiers placeholder */
    public Inventory buildModifiersUI(Player player) {
        var state = plugin.getItemMakerStateManager().get(player.getUniqueId());
        Inventory ui = Bukkit.createInventory(player, 54, UI_TITLE_MODIFIERS);
        ItemStack filler = quickItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", null);
        for (int i = 0; i < ui.getSize(); i++) ui.setItem(i, filler);
        ui.setItem(0, quickItem(Material.ARROW, ChatColor.GREEN + "Back", List.of(ChatColor.GRAY + "Return to ItemMaker")));
        ui.setItem(8, quickItem(Material.BARRIER, BTN_CLOSE, List.of(ChatColor.GRAY + "Close ItemMaker")));
        if (state.getPreview() != null) ui.setItem(4, state.getPreview().clone());
    // --- Modifier options ---
    // 1) Food
    ui.setItem(20, quickItem(Material.APPLE, ChatColor.WHITE + "Food", null));
    // 2) Misc
    ui.setItem(21, quickItem(Material.CONDUIT, ChatColor.AQUA + "Misc", null));
    // 3) Attributes
    ui.setItem(22, quickItem(Material.NETHER_STAR, ChatColor.YELLOW + "Attributes", null));
    // 4) Tool
    ui.setItem(23, quickItem(Material.NETHERITE_HOE, ChatColor.WHITE + "Tool", null));
    // 5) Tooltip (use FIREWORK_STAR)
    ui.setItem(24, quickItem(Material.FIREWORK_STAR, ChatColor.WHITE + "Tooltip", null));
        return ui;
    }

    // ===== Apply styles to preview =====
    public void applyStyleToPreview(PlayerStateManager.PlayerState s) {
        if (s.getPreview() == null) return;
        ItemMeta meta = s.getPreview().getItemMeta();
        if (meta == null) return;

        // Name (use legacy string-based API so this compiles across Bukkit versions)
        String baseName;
        if (s.getNameText() != null) {
            baseName = s.getNameText();
        } else {
            String cur = meta.hasDisplayName() ? meta.getDisplayName() : null;
            if (cur != null && !cur.isBlank()) {
                // strip any existing color codes from the item's original display name so our default white applies
                baseName = org.bukkit.ChatColor.stripColor(cur);
            } else {
                baseName = s.getPreview().getType().name().toLowerCase(Locale.ROOT).replace('_', ' ');
            }
        }

        // Only set a custom display name if the player has explicitly changed name/hex/formatting.
        boolean nameChanged = s.getNameText() != null || (s.getNameHex() != null && !s.getNameHex().isBlank())
                || s.isNameBold() || s.isNameItalic() || s.isNameUnder() || s.isNameStrike() || s.isNameObfus();
        if (nameChanged) {
            StringBuilder nameSb = new StringBuilder();
            if (s.getNameHex() != null && !s.getNameHex().isBlank()) nameSb.append(legacyHex(s.getNameHex()));
            else nameSb.append(ChatColor.WHITE);
            if (s.isNameBold()) nameSb.append(ChatColor.BOLD);
            if (s.isNameItalic()) nameSb.append(ChatColor.ITALIC);
            if (s.isNameUnder()) nameSb.append(ChatColor.UNDERLINE);
            if (s.isNameStrike()) nameSb.append(ChatColor.STRIKETHROUGH);
            if (s.isNameObfus()) nameSb.append(ChatColor.MAGIC);
            nameSb.append(baseName);
            meta.setDisplayName(nameSb.toString());
        } else {
            // clear any display name so the item remains a plain vanilla item
            meta.setDisplayName(null);
        }

        // Lore (string-based list with formatting)
        boolean loreChanged = !s.getLoreLines().isEmpty() || (s.getLoreHex() != null && !s.getLoreHex().isBlank())
                || s.isLoreBold() || s.isLoreItalic() || s.isLoreUnder() || s.isLoreStrike() || s.isLoreObfus();
        if (loreChanged) {
            List<String> lore = new ArrayList<>();
            for (String line : s.getLoreLines()) {
                StringBuilder lb = new StringBuilder();
                if (s.getLoreHex() != null && !s.getLoreHex().isBlank()) lb.append(legacyHex(s.getLoreHex()));
                else lb.append(ChatColor.WHITE);
                if (s.isLoreBold()) lb.append(ChatColor.BOLD);
                if (s.isLoreItalic()) lb.append(ChatColor.ITALIC);
                if (s.isLoreUnder()) lb.append(ChatColor.UNDERLINE);
                if (s.isLoreStrike()) lb.append(ChatColor.STRIKETHROUGH);
                if (s.isLoreObfus()) lb.append(ChatColor.MAGIC);
                lb.append(line);
                lore.add(lb.toString());
            }
            meta.setLore(lore);
        } else {
            meta.setLore(null);
        }

        s.getPreview().setItemMeta(meta);
    }

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        player.openInventory(buildMainUI(player));
        return true;
    }

    // Helpers
    static ItemStack quickItem(Material mat, String name, List<String> lore) {
        ItemStack it = new ItemStack(mat);
        ItemMeta meta = it.getItemMeta();
        meta.setDisplayName(name);
        if (lore != null && !lore.isEmpty()) meta.setLore(new ArrayList<>(lore));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        it.setItemMeta(meta);
        return it;
    }

    static ItemStack toggleItem(Material mat, String name, boolean on) {
        ItemStack it = quickItem(mat, name + ChatColor.GRAY + " [" + (on ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF") + ChatColor.GRAY + "]",
                List.of(ChatColor.GRAY + "Click to toggle"));
        ItemMeta m = it.getItemMeta();
        // Avoid Paper-specific API call; use enchantment hide flag for visual hint
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        it.setItemMeta(m);
        return it;
    }

    static String legacyHex(String hex) {
        String h = hex.startsWith("#") ? hex.substring(1) : hex;
        StringBuilder sb = new StringBuilder("§x");
        for (char c : h.toCharArray()) sb.append('§').append(c);
        return sb.toString();
    }

    private static Map<Material, String> dyeHexMap() {
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

    public static String niceName(Enchantment ench) {
        String key = ench.getKey().getKey().replace('_', ' ');
        StringBuilder sb = new StringBuilder();
        for (String p : key.split(" ")) {
            if (p.isEmpty()) continue;
            sb.append(p.substring(0,1).toUpperCase(Locale.ROOT))
                    .append(p.substring(1).toLowerCase(Locale.ROOT)).append(" ");
        }
        return sb.toString().trim();
    }
}
