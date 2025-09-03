package com.joosua.shopkeepers.commands;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import com.joosua.shopkeepers.itemcreator.ui.UIManager;

import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ItemCreatorCommand implements CommandExecutor {
    private final ShopkeepersPlugin plugin;
    private final UIManager uiManager;

    public ItemCreatorCommand(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.uiManager = new UIManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can do this.");
            return true;
        }
        
        PlayerState state = plugin.getItemMakerStateManager().get(player.getUniqueId());
        player.openInventory(uiManager.getMainUIBuilder().buildMainUI(player, state));
        return true;
    }
}
