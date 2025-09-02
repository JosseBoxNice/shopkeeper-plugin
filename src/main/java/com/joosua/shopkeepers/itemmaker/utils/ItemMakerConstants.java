package com.joosua.shopkeepers.itemmaker.utils;

import org.bukkit.ChatColor;

public final class ItemMakerConstants {
    private ItemMakerConstants() {} // Prevent instantiation

    // Titles
    public static final String UI_TITLE_MAIN = ChatColor.DARK_GREEN + "ItemMaker";
    public static final String UI_TITLE_WEAPONS = ChatColor.DARK_AQUA + "ItemMaker - Weapons";
    public static final String UI_TITLE_ARMOR = ChatColor.DARK_AQUA + "ItemMaker - Armor";
    public static final String UI_TITLE_ENCHANTS = ChatColor.DARK_PURPLE + "ItemMaker - Enchants";
    public static final String UI_TITLE_TOOLS = ChatColor.DARK_AQUA + "ItemMaker - Tools";
    public static final String UI_TITLE_MISC = ChatColor.DARK_BLUE + "ItemMaker - Misc";
    public static final String UI_TITLE_STYLE = ChatColor.GOLD + "ItemMaker - Style";
    public static final String UI_TITLE_STYLE_NAME = ChatColor.GOLD + "ItemMaker - Name";
    public static final String UI_TITLE_STYLE_NAME_FMT = ChatColor.GOLD + "ItemMaker - Name Formatting";
    public static final String UI_TITLE_STYLE_LORE = ChatColor.GOLD + "ItemMaker - Lore";
    public static final String UI_TITLE_STYLE_LORE_FMT = ChatColor.GOLD + "ItemMaker - Lore Formatting";
    public static final String UI_TITLE_MODIFIERS = ChatColor.BLUE + "ItemMaker - Modifiers";
    public static final String UI_TITLE_ATTRIBUTES = ChatColor.YELLOW + "ItemMaker - Attributes";
    public static final String UI_TITLE_ATTRIBUTE_TYPE = ChatColor.YELLOW + "ItemMaker - Select Attribute";
    public static final String UI_TITLE_ATTRIBUTE_OPERATION = ChatColor.YELLOW + "ItemMaker - Select Operation";
    public static final String UI_TITLE_ATTRIBUTE_SLOT = ChatColor.YELLOW + "ItemMaker - Select Slot";
    public static final String UI_TITLE_ATTRIBUTE_DISPLAY = ChatColor.YELLOW + "ItemMaker - Display Options";
    public static final String UI_TITLE_STYLE_NAME_COLOR = ChatColor.GOLD + "ItemMaker - Name Color";
    public static final String UI_TITLE_STYLE_LORE_COLOR = ChatColor.GOLD + "ItemMaker - Lore Color";

    // Main buttons
    public static final String BTN_CLOSE = ChatColor.RED + "Close";
    public static final String BTN_ENCH = ChatColor.LIGHT_PURPLE + "Enchants";
    public static final String BTN_CREATE = ChatColor.GREEN + "Create Item";
    public static final String BTN_WEAPONS = ChatColor.AQUA + "Weapons";
    public static final String BTN_TOOLS = ChatColor.AQUA + "Tools";
    public static final String BTN_ARMOR = ChatColor.AQUA + "Armor";
    public static final String BTN_MISC = ChatColor.AQUA + "Misc";
    public static final String BTN_STYLE = ChatColor.GOLD + "Style";
    public static final String BTN_MODIFIERS = ChatColor.AQUA + "Modifiers";
    public static final String BTN_UNBREAK = ChatColor.GOLD + "Unbreakable";

    // Style main
    public static final String BTN_STYLE_SET_NAME = ChatColor.YELLOW + "Set Name";
    public static final String BTN_STYLE_LORE = ChatColor.AQUA + "Set Lore";

    // Name UI
    public static final String BTN_NAME_SETNAME = ChatColor.YELLOW + "Set Name";
    public static final String BTN_NAME_SETCOLOR = ChatColor.LIGHT_PURPLE + "Set Color";
    public static final String BTN_NAME_FORMAT = ChatColor.GOLD + "Formatting";
    public static final String BTN_COLOR_HEX = ChatColor.LIGHT_PURPLE + "Custom Hex";

    // Lore UI
    public static final String BTN_LORE_ADDLINE = ChatColor.AQUA + "Add Lore Line";
    public static final String BTN_LORE_SETCOLOR = ChatColor.LIGHT_PURPLE + "Set Color";
    public static final String BTN_LORE_FORMAT = ChatColor.GOLD + "Formatting";
    public static final String BTN_LORE_REMLAST = ChatColor.RED + "Remove Last Lore Line";
    public static final String BTN_LORE_CLEAR = ChatColor.DARK_RED + "Clear Lore";

    // Formatting toggles
    public static final String BTN_T_BOLD = ChatColor.GREEN + "Bold";
    public static final String BTN_T_ITALIC = ChatColor.GREEN + "Italic";
    public static final String BTN_T_UNDER = ChatColor.GREEN + "Underlined";
    public static final String BTN_T_STRIKE = ChatColor.GREEN + "Strikethrough";
    public static final String BTN_T_OBFUS = ChatColor.GREEN + "Obfuscated";

    // Attribute buttons
    public static final String BTN_ADD_ATTRIBUTE = ChatColor.GREEN + "Add Attribute";
    public static final String BTN_ATTRIBUTES = ChatColor.YELLOW + "Attributes";
    
    // Attribute operations
    public static final String BTN_OP_ADD_VALUE = ChatColor.GREEN + "Add Value";
    public static final String BTN_OP_ADD_SCALED = ChatColor.BLUE + "Add Scaled";
    public static final String BTN_OP_MULTIPLY = ChatColor.RED + "Multiply";
    
    // Attribute slots
    public static final String BTN_SLOT_MAINHAND = ChatColor.YELLOW + "Mainhand";
    public static final String BTN_SLOT_OFFHAND = ChatColor.YELLOW + "Offhand";
    public static final String BTN_SLOT_HEAD = ChatColor.YELLOW + "Head";
    public static final String BTN_SLOT_CHEST = ChatColor.YELLOW + "Chest";
    public static final String BTN_SLOT_LEGS = ChatColor.YELLOW + "Legs";
    public static final String BTN_SLOT_FEET = ChatColor.YELLOW + "Feet";
    public static final String BTN_SLOT_ANY = ChatColor.GRAY + "Any Slot";
    
    // Attribute display
    public static final String BTN_DISPLAY_SHOW = ChatColor.GREEN + "Show";
    public static final String BTN_DISPLAY_HIDDEN = ChatColor.RED + "Hidden";
}
