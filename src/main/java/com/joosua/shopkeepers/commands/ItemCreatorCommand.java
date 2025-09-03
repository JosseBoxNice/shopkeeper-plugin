package com.joosua.shopkeepers.commands;

import com.joosua.shopkeepers.ShopkeepersPlugin;
import com.joosua.shopkeepers.itemcreator.state.PlayerState;
import com.joosua.shopkeepers.itemcreator.ui.MainUIBuilder;
import com.joosua.shopkeepers.itemcreator.ui.ToolsUIBuilder;
import com.joosua.shopkeepers.itemcreator.ui.WeaponsUIBuilder;

import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ItemCreatorCommand implements CommandExecutor {

    private final ShopkeepersPlugin plugin;
    private final MainUIBuilder mainUIBuilder;
    private final WeaponsUIBuilder weaponsUIBuilder;
    private final ToolsUIBuilder toolsUIBuilder;

    public ItemCreatorCommand(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.mainUIBuilder = new MainUIBuilder();
        this.weaponsUIBuilder = new WeaponsUIBuilder();
        this.toolsUIBuilder = new ToolsUIBuilder();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can do this.");
            return true;
        }
        
        PlayerState state = plugin.getItemMakerStateManager().get(player.getUniqueId());
        player.openInventory(mainUIBuilder.buildMainUI(player, state));
        return true;
    }

    // Public methods for other classes to access UI builders
    public MainUIBuilder getMainUIBuilder() {
        return mainUIBuilder;
    }

    public WeaponsUIBuilder getWeaponsUIBuilder() {
        return weaponsUIBuilder;
    }

    public ToolsUIBuilder getToolsUIBuilder() {
        return toolsUIBuilder;
    }
}
