# ItemMaker Attribute Modifier System

This plugin now includes a comprehensive attribute modifier system similar to mcstacker, allowing you to add custom attributes to items through both the GUI and command line.

## Features

- **GUI-based attribute creation**: Use the ItemMaker interface to visually create items with custom attributes
- **Command-line support**: Create items with attributes using mcstacker-style commands
- **Full attribute support**: All Minecraft attributes are supported (movement speed, attack damage, armor, etc.)
- **Flexible operations**: Add value, add scaled, or multiply operations
- **Slot-specific modifiers**: Apply attributes to specific equipment slots or any slot
- **Hidden attributes**: Option to hide attributes from the tooltip

## Usage

### GUI Method

1. Use the `/itemmaker` command to open the ItemMaker
2. Select a weapon/tool/armor item
3. Click on "Modifiers" → "Attributes"
4. Click "Add Attribute" to start creating an attribute
5. Follow the prompts:
   - Enter attribute type (e.g., `movement_speed`, `attack_damage`)
   - Enter amount (e.g., `1.0`, `-0.5`)
   - Select operation (Add Value, Add Scaled, Multiply)
   - Select slot (Mainhand, Offhand, Head, Chest, Legs, Feet, or Any Slot)
   - Choose display (Show or Hidden)
6. The attribute will be applied to your preview item

### Command Method

Use mcstacker-style commands to create items with attributes:

```
/itemmaker wooden_sword[attribute_modifiers=[{id:"movement_speed",type:"movement_speed",amount:1,operation:"add_value",slot:"mainhand",display:{type:"hidden"}}]] 1
```

**Command Format:**
```
/itemmaker <material>[attribute_modifiers=[{id:"<attribute>",type:"<attribute>",amount:<value>,operation:"<operation>",slot:"<slot>",display:{type:"<display>"}}]] [amount]
```

**Parameters:**
- `material`: The item material (e.g., wooden_sword, diamond_pickaxe)
- `attribute`: The attribute type (e.g., movement_speed, attack_damage, max_health)
- `value`: The attribute value (positive or negative number)
- `operation`: 
  - `add_value`: Adds the amount to the base value
  - `add_scaled`: Adds amount × base value
  - `multiply`: Multiplies base value by (1 + amount)
- `slot`: Equipment slot (mainhand, offhand, head, chest, legs, feet, or any)
- `display`: 
  - `show`: Attribute visible in tooltip
  - `hidden`: Attribute hidden from tooltip
- `amount`: Number of items to create (optional, defaults to 1)

## Supported Attributes

- `GENERIC_MAX_HEALTH` - Maximum health
- `GENERIC_MOVEMENT_SPEED` - Movement speed
- `GENERIC_ATTACK_DAMAGE` - Attack damage
- `GENERIC_ATTACK_SPEED` - Attack speed
- `GENERIC_ARMOR` - Armor points
- `GENERIC_ARMOR_TOUGHNESS` - Armor toughness
- `GENERIC_KNOCKBACK_RESISTANCE` - Knockback resistance
- `GENERIC_LUCK` - Luck
- `GENERIC_FLYING_SPEED` - Flying speed
- `GENERIC_FOLLOW_RANGE` - Follow range
- `GENERIC_ATTACK_KNOCKBACK` - Attack knockback
- `GENERIC_MAX_ABSORPTION` - Maximum absorption

## Examples

### Speed Sword
```
/itemmaker wooden_sword[attribute_modifiers=[{id:"movement_speed",type:"movement_speed",amount:1,operation:"add_value",slot:"mainhand",display:{type:"hidden"}}]] 1
```

### Damage Axe
```
/itemmaker diamond_axe[attribute_modifiers=[{id:"attack_damage",type:"attack_damage",amount:5,operation:"add_value",slot:"mainhand",display:{type:"show"}}]] 1
```

### Health Armor
```
/itemmaker diamond_chestplate[attribute_modifiers=[{id:"max_health",type:"max_health",amount:10,operation:"add_value",slot:"chest",display:{type:"show"}}]] 1
```

## Notes

- Attributes are applied immediately to the preview item
- You can add multiple attributes to the same item
- Hidden attributes still function but won't show in the tooltip
- The system supports both positive and negative values
- All attributes are properly saved and will persist when the item is given to players

## Technical Details

The attribute system uses Bukkit's native `AttributeModifier` API, ensuring compatibility with all Minecraft versions and other plugins. Attributes are stored in the item's metadata and will work correctly when the item is used, equipped, or traded.
