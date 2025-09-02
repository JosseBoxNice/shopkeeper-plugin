package com.joosua.shopkeepers.itemmaker.utils;

import com.joosua.shopkeepers.itemmaker.state.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class StyleUtils {
    private StyleUtils() {} // Prevent instantiation

    public static void applyStyleToPreview(PlayerState state) {
        if (state.getPreview() == null) return;
        ItemMeta meta = state.getPreview().getItemMeta();
        if (meta == null) return;

        // Name (use legacy string-based API so this compiles across Bukkit versions)
        String baseName;
        if (state.getNameText() != null) {
            baseName = state.getNameText();
        } else {
            String cur = meta.hasDisplayName() ? meta.getDisplayName() : null;
            if (cur != null && !cur.isBlank()) {
                // strip any existing color codes from the item's original display name so our default white applies
                baseName = ChatColor.stripColor(cur);
            } else {
                baseName = state.getPreview().getType().name().toLowerCase(Locale.ROOT).replace('_', ' ');
            }
        }

        // Only set a custom display name if the player has explicitly changed name/hex/formatting.
        boolean nameChanged = state.getNameText() != null || (state.getNameHex() != null && !state.getNameHex().isBlank())
                || state.isNameBold() || state.isNameItalic() || state.isNameUnder() || state.isNameStrike() || state.isNameObfus();
        if (nameChanged) {
            StringBuilder nameSb = new StringBuilder();
            if (state.getNameHex() != null && !state.getNameHex().isBlank()) nameSb.append(ItemUtils.legacyHex(state.getNameHex()));
            else nameSb.append(ChatColor.WHITE);
            if (state.isNameBold()) nameSb.append(ChatColor.BOLD);
            if (state.isNameItalic()) nameSb.append(ChatColor.ITALIC);
            if (state.isNameUnder()) nameSb.append(ChatColor.UNDERLINE);
            if (state.isNameStrike()) nameSb.append(ChatColor.STRIKETHROUGH);
            if (state.isNameObfus()) nameSb.append(ChatColor.MAGIC);
            nameSb.append(baseName);
            meta.setDisplayName(nameSb.toString());
        } else {
            // clear any display name so the item remains a plain vanilla item
            meta.setDisplayName(null);
        }

        // Lore (string-based list with formatting)
        boolean loreChanged = !state.getLoreLines().isEmpty() || (state.getLoreHex() != null && !state.getLoreHex().isBlank())
                || state.isLoreBold() || state.isLoreItalic() || state.isLoreUnder() || state.isLoreStrike() || state.isLoreObfus();
        if (loreChanged) {
            List<String> lore = new ArrayList<>();
            for (String line : state.getLoreLines()) {
                StringBuilder lb = new StringBuilder();
                if (state.getLoreHex() != null && !state.getLoreHex().isBlank()) lb.append(ItemUtils.legacyHex(state.getLoreHex()));
                else lb.append(ChatColor.WHITE);
                if (state.isLoreBold()) lb.append(ChatColor.BOLD);
                if (state.isLoreItalic()) lb.append(ChatColor.ITALIC);
                if (state.isLoreUnder()) lb.append(ChatColor.UNDERLINE);
                if (state.isLoreStrike()) lb.append(ChatColor.STRIKETHROUGH);
                if (state.isLoreObfus()) lb.append(ChatColor.MAGIC);
                lb.append(line);
                lore.add(lb.toString());
            }
            meta.setLore(lore);
        } else {
            meta.setLore(null);
        }

        // Apply attribute modifiers
        if (!state.getAttributeModifiers().isEmpty()) {
            // Clear any existing attribute modifiers first
            for (org.bukkit.attribute.Attribute attr : org.bukkit.attribute.Attribute.values()) {
                meta.removeAttributeModifier(attr);
            }
            
            // Check if we have any hidden attributes
            boolean hasHiddenAttributes = state.getAttributeModifiers().stream().anyMatch(attr -> attr.isHidden());
            
            // Add our custom attribute modifiers
            for (var attrData : state.getAttributeModifiers()) {
                meta.addAttributeModifier(attrData.getAttribute(), attrData.toAttributeModifier());
            }
            
            // Only hide ALL attributes if we have at least one hidden attribute
            // This is because Bukkit doesn't support hiding individual attributes
            if (hasHiddenAttributes) {
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            } else {
                // Remove the flag if no attributes are hidden
                meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
        } else {
            // No attributes, so remove the hide flag
            meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        state.getPreview().setItemMeta(meta);
    }
    
    public static void resetStateStyles(PlayerState state) {
        // Reset name styles
        state.setNameText(null);
        state.setNameHex(null);
        state.setNameBold(false);
        state.setNameItalic(false);
        state.setNameUnder(false);
        state.setNameStrike(false);
        state.setNameObfus(false);

        // Reset lore styles
        state.getLoreLines().clear();
        state.setLoreHex(null);
        state.setLoreBold(false);
        state.setLoreItalic(false);
        state.setLoreUnder(false);
        state.setLoreStrike(false);
        state.setLoreObfus(false);

        // Reset attributes
        state.getAttributeModifiers().clear();
    }
}
