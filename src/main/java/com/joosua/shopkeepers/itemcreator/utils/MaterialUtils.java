package com.joosua.shopkeepers.itemcreator.utils;

import org.bukkit.Material;

public final class MaterialUtils {
    private MaterialUtils() {} // Prevent instantiation

    public static boolean isWeapon(Material type) {
        return switch (type) {
            case WOODEN_SWORD, STONE_SWORD, IRON_SWORD, GOLDEN_SWORD, DIAMOND_SWORD, NETHERITE_SWORD,
                 WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE, NETHERITE_AXE,
                 BOW, CROSSBOW, TRIDENT, MACE -> true;
            default -> false;
        };
    }

    public static boolean isTool(Material type) {
        return switch (type) {
            case WOODEN_PICKAXE, STONE_PICKAXE, IRON_PICKAXE, GOLDEN_PICKAXE, DIAMOND_PICKAXE, NETHERITE_PICKAXE,
                 WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE, NETHERITE_AXE,
                 WOODEN_SHOVEL, STONE_SHOVEL, IRON_SHOVEL, GOLDEN_SHOVEL, DIAMOND_SHOVEL, NETHERITE_SHOVEL,
                 WOODEN_HOE, STONE_HOE, IRON_HOE, GOLDEN_HOE, DIAMOND_HOE, NETHERITE_HOE -> true;
            default -> false;
        };
    }

    public static boolean isArmor(Material type) {
        return switch (type) {
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS,
                 CHAINMAIL_HELMET, CHAINMAIL_CHESTPLATE, CHAINMAIL_LEGGINGS, CHAINMAIL_BOOTS,
                 IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS,
                 GOLDEN_HELMET, GOLDEN_CHESTPLATE, GOLDEN_LEGGINGS, GOLDEN_BOOTS,
                 DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_LEGGINGS, DIAMOND_BOOTS,
                 NETHERITE_HELMET, NETHERITE_CHESTPLATE, NETHERITE_LEGGINGS, NETHERITE_BOOTS,
                 TURTLE_HELMET -> true;
            default -> false;
        };
    }

    public static boolean isMisc(Material type) {
        return switch (type) {
            case STICK, SNOWBALL, EGG, WIND_CHARGE, TOTEM_OF_UNDYING,
                 BUCKET, FISHING_ROD, FIRE_CHARGE, ELYTRA, ENDER_PEARL,
                 SHIELD, GLASS_BOTTLE, PLAYER_HEAD, GOAT_HORN, SHEARS,
                 FLINT_AND_STEEL, BRUSH, SPYGLASS, APPLE -> true;
            default -> false;
        };
    }
}
