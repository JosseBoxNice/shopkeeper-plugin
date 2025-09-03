package com.joosua.shopkeepers.itemcreator.ui;

import com.joosua.shopkeepers.ShopkeepersPlugin;

public class UIManager {
    private final MainUIBuilder mainUIBuilder;
    private final WeaponsUIBuilder weaponsUIBuilder;
    private final ToolsUIBuilder toolsUIBuilder;
    private final ArmorUIBuilder armorUIBuilder;

    public UIManager(ShopkeepersPlugin plugin) {
        this.mainUIBuilder = new MainUIBuilder();
        this.weaponsUIBuilder = new WeaponsUIBuilder();
        this.toolsUIBuilder = new ToolsUIBuilder();
        this.armorUIBuilder = new ArmorUIBuilder();
    }

    public MainUIBuilder getMainUIBuilder() {
        return mainUIBuilder;
    }

    public WeaponsUIBuilder getWeaponsUIBuilder() {
        return weaponsUIBuilder;
    }

    public ToolsUIBuilder getToolsUIBuilder() {
        return toolsUIBuilder;
    }

    public ArmorUIBuilder getArmorUIBuilder() {
        return armorUIBuilder;
    }
}