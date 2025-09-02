package com.joosua.shopkeepers.itemmaker.state;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import com.joosua.shopkeepers.itemmaker.attributes.AttributeModifierData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerState {
    // Preview
    private ItemStack preview;

    // Enchant prompt
    private boolean awaitingEnchantLevel;
    private Enchantment pendingEnchant;

    // Name prompts
    private boolean awaitingNameInput;
    private boolean awaitingNameHexInput;

    // Lore prompts
    private boolean awaitingLoreInput;
    private boolean awaitingLoreHexInput;

    // Name style (independent)
    private String nameText;     // null = keep current base
    private String nameHex;      // "#RRGGBB" or null
    private boolean nameBold, nameItalic, nameUnder, nameStrike, nameObfus;

    // Lore style (independent)
    private final List<String> loreLines = new ArrayList<>();
    private String loreHex;      // "#RRGGBB" or null
    private boolean loreBold, loreItalic, loreUnder, loreStrike, loreObfus;

    // Attribute modifiers
    private final List<AttributeModifierData> attributeModifiers = new ArrayList<>();
    private boolean awaitingAttributeType;
    private boolean awaitingAttributeAmount;
    private boolean awaitingAttributeOperation;
    private boolean awaitingAttributeSlot;
    private boolean awaitingAttributeDisplay;
    private Attribute pendingAttribute;
    private double pendingAmount;
    private AttributeModifier.Operation pendingOperation;
    private EquipmentSlot pendingSlot;
    private boolean pendingHidden;

    // Return-to-adder (integration with Shopkeepers AddTrade)
    private boolean awaitingReturnToAdder;
    private int returnSlot = -1; // slot index in AddTrade to return preview to (11 or 15)
    private UUID returnShopkeeperId;

    // Saved AddTrade items so we can restore the other slot when returning
    private ItemStack savedAdderCost;
    private ItemStack savedAdderResult;

    // --- getters / setters ---
    public ItemStack getPreview() { return preview; }
    public void setPreview(ItemStack preview) { this.preview = preview; }

    public boolean isAwaitingEnchantLevel() { return awaitingEnchantLevel; }
    public void setAwaitingEnchantLevel(boolean awaitingEnchantLevel) { this.awaitingEnchantLevel = awaitingEnchantLevel; }

    public Enchantment getPendingEnchant() { return pendingEnchant; }
    public void setPendingEnchant(Enchantment pendingEnchant) { this.pendingEnchant = pendingEnchant; }

    public boolean isAwaitingNameInput() { return awaitingNameInput; }
    public void setAwaitingNameInput(boolean awaitingNameInput) { this.awaitingNameInput = awaitingNameInput; }

    public boolean isAwaitingNameHexInput() { return awaitingNameHexInput; }
    public void setAwaitingNameHexInput(boolean awaitingNameHexInput) { this.awaitingNameHexInput = awaitingNameHexInput; }

    public boolean isAwaitingLoreInput() { return awaitingLoreInput; }
    public void setAwaitingLoreInput(boolean awaitingLoreInput) { this.awaitingLoreInput = awaitingLoreInput; }

    public boolean isAwaitingLoreHexInput() { return awaitingLoreHexInput; }
    public void setAwaitingLoreHexInput(boolean awaitingLoreHexInput) { this.awaitingLoreHexInput = awaitingLoreHexInput; }

    public String getNameText() { return nameText; }
    public void setNameText(String nameText) { this.nameText = nameText; }

    public String getNameHex() { return nameHex; }
    public void setNameHex(String nameHex) { this.nameHex = nameHex; }

    public boolean isNameBold() { return nameBold; }
    public void setNameBold(boolean nameBold) { this.nameBold = nameBold; }

    public boolean isNameItalic() { return nameItalic; }
    public void setNameItalic(boolean nameItalic) { this.nameItalic = nameItalic; }

    public boolean isNameUnder() { return nameUnder; }
    public void setNameUnder(boolean nameUnder) { this.nameUnder = nameUnder; }

    public boolean isNameStrike() { return nameStrike; }
    public void setNameStrike(boolean nameStrike) { this.nameStrike = nameStrike; }

    public boolean isNameObfus() { return nameObfus; }
    public void setNameObfus(boolean nameObfus) { this.nameObfus = nameObfus; }

    public List<String> getLoreLines() { return loreLines; }

    public String getLoreHex() { return loreHex; }
    public void setLoreHex(String loreHex) { this.loreHex = loreHex; }

    public boolean isLoreBold() { return loreBold; }
    public void setLoreBold(boolean loreBold) { this.loreBold = loreBold; }

    public boolean isLoreItalic() { return loreItalic; }
    public void setLoreItalic(boolean loreItalic) { this.loreItalic = loreItalic; }

    public boolean isLoreUnder() { return loreUnder; }
    public void setLoreUnder(boolean loreUnder) { this.loreUnder = loreUnder; }

    public boolean isLoreStrike() { return loreStrike; }
    public void setLoreStrike(boolean loreStrike) { this.loreStrike = loreStrike; }

    public boolean isLoreObfus() { return loreObfus; }
    public void setLoreObfus(boolean loreObfus) { this.loreObfus = loreObfus; }

    // Attribute modifier getters/setters
    public List<AttributeModifierData> getAttributeModifiers() { return attributeModifiers; }

    public boolean isAwaitingAttributeType() { return awaitingAttributeType; }
    public void setAwaitingAttributeType(boolean awaitingAttributeType) { this.awaitingAttributeType = awaitingAttributeType; }

    public boolean isAwaitingAttributeAmount() { return awaitingAttributeAmount; }
    public void setAwaitingAttributeAmount(boolean awaitingAttributeAmount) { this.awaitingAttributeAmount = awaitingAttributeAmount; }

    public boolean isAwaitingAttributeOperation() { return awaitingAttributeOperation; }
    public void setAwaitingAttributeOperation(boolean awaitingAttributeOperation) { this.awaitingAttributeOperation = awaitingAttributeOperation; }

    public boolean isAwaitingAttributeSlot() { return awaitingAttributeSlot; }
    public void setAwaitingAttributeSlot(boolean awaitingAttributeSlot) { this.awaitingAttributeSlot = awaitingAttributeSlot; }

    public boolean isAwaitingAttributeDisplay() { return awaitingAttributeDisplay; }
    public void setAwaitingAttributeDisplay(boolean awaitingAttributeDisplay) { this.awaitingAttributeDisplay = awaitingAttributeDisplay; }

    public Attribute getPendingAttribute() { return pendingAttribute; }
    public void setPendingAttribute(Attribute pendingAttribute) { this.pendingAttribute = pendingAttribute; }

    public double getPendingAmount() { return pendingAmount; }
    public void setPendingAmount(double pendingAmount) { this.pendingAmount = pendingAmount; }

    public AttributeModifier.Operation getPendingOperation() { return pendingOperation; }
    public void setPendingOperation(AttributeModifier.Operation pendingOperation) { this.pendingOperation = pendingOperation; }

    public EquipmentSlot getPendingSlot() { return pendingSlot; }
    public void setPendingSlot(EquipmentSlot pendingSlot) { this.pendingSlot = pendingSlot; }

    public boolean isPendingHidden() { return pendingHidden; }
    public void setPendingHidden(boolean pendingHidden) { this.pendingHidden = pendingHidden; }

    public boolean isAwaitingReturnToAdder() { return awaitingReturnToAdder; }
    public void setAwaitingReturnToAdder(boolean awaitingReturnToAdder) { this.awaitingReturnToAdder = awaitingReturnToAdder; }

    public int getReturnSlot() { return returnSlot; }
    public void setReturnSlot(int returnSlot) { this.returnSlot = returnSlot; }

    public UUID getReturnShopkeeperId() { return returnShopkeeperId; }
    public void setReturnShopkeeperId(UUID returnShopkeeperId) { this.returnShopkeeperId = returnShopkeeperId; }

    public ItemStack getSavedAdderCost() { return savedAdderCost; }
    public void setSavedAdderCost(ItemStack savedAdderCost) { this.savedAdderCost = savedAdderCost; }

    public ItemStack getSavedAdderResult() { return savedAdderResult; }
    public void setSavedAdderResult(ItemStack savedAdderResult) { this.savedAdderResult = savedAdderResult; }
}
