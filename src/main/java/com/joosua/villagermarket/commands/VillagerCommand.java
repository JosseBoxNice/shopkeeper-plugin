package com.joosua.villagermarket.commands;

import com.joosua.villagermarket.VillagerMarketMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class VillagerCommand implements CommandExecutor {
    private final VillagerMarketMain plugin;

    public VillagerCommand(VillagerMarketMain plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command cmd,
            @NotNull String label,
            @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
        }
        Player player = (Player) sender;
        // /villager
        if (args.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "Usage: /villager create");
            return true;
        }
        // /villager [create, edit]
        switch(args[0].toLowerCase()) {
            case "create":
                plugin.getVillagerManager().createVillager(player.getLocation());
                return true;
            case "edit":
                ItemStack debugEmerald = new ItemStack(Material.EMERALD);
                ItemMeta meta = debugEmerald.getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW + "DebugEmerald");
                NamespacedKey debugEmeraldKey = new NamespacedKey(plugin, "debugEmerald");
                meta.getPersistentDataContainer().set(
                        debugEmeraldKey,
                        PersistentDataType.STRING,
                        "debugEmerald");
                debugEmerald.setItemMeta(meta);
                player.getInventory().addItem(debugEmerald);
                return true;
        }

        // /villager <something>
        player.sendMessage(ChatColor.YELLOW + "Usage: /villager create");
        return true;
    }
}
