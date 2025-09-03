package com.joosua.shopkeepers.itemcreator.ui;

import com.joosua.shopkeepers.ShopkeepersPlugin;

public class UIManager {
    private final MainUIBuilder mainUIBuilder;
    private final WeaponsUIBuilder weaponsUIBuilder;
    private final ToolsUIBuilder toolsUIBuilder;
    private final ArmorUIBuilder armorUIBuilder;
    private final MiscUIBuilder miscUIBuilder;
    private final EnchUIBuilder enchUIBuilder;

    public UIManager(ShopkeepersPlugin plugin) {
        this.mainUIBuilder = new MainUIBuilder();
        this.weaponsUIBuilder = new WeaponsUIBuilder();
        this.toolsUIBuilder = new ToolsUIBuilder();
        this.armorUIBuilder = new ArmorUIBuilder();
        this.miscUIBuilder = new MiscUIBuilder();
        this.enchUIBuilder = new EnchUIBuilder(plugin);
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

    public MiscUIBuilder getMiscUIBuilder() {
        return miscUIBuilder;
    }

    public EnchUIBuilder getEnchUIBuilder() {
        return enchUIBuilder;
    }
}