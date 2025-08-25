package com.joosua.villagermarket.commands;

import com.joosua.villagermarket.VillagerMarketMain;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

        plugin.getVillagerManager().createVillager(player.getLocation());

        return true;
    }
}
