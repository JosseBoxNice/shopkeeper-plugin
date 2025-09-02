package com.joosua.shopkeepers.itemmaker.attributes;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;

public class AttributeModifierData {
    private final Attribute attribute;
    private final double amount;
    private final AttributeModifier.Operation operation;
    private final EquipmentSlot slot;
    private final boolean hidden;
    private final String name;

    public AttributeModifierData(Attribute attribute, double amount, AttributeModifier.Operation operation, EquipmentSlot slot, boolean hidden) {
        this.attribute = attribute;
        this.amount = amount;
        this.operation = operation;
        this.slot = slot;
        this.hidden = hidden;
        this.name = attribute.name().toLowerCase().replace('_', ' ');
    }

    public Attribute getAttribute() { return attribute; }
    public double getAmount() { return amount; }
    public AttributeModifier.Operation getOperation() { return operation; }
    public EquipmentSlot getSlot() { return slot; }
    public boolean isHidden() { return hidden; }
    public String getName() { return name; }

    public AttributeModifier toAttributeModifier() {
        return new AttributeModifier(UUID.randomUUID(), name, amount, operation, slot);
    }
}
