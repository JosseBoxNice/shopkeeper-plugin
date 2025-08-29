package com.joosua.shopkeepers.holders;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class IdInventoryHolder implements InventoryHolder {
    private final UUID id;
    private final Inventory inventory;

    public IdInventoryHolder(UUID id, int size, String title) {
        this.id = id;
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public UUID getId() {
        return id;
    }
}