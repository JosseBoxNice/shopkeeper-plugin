# Shopkeepers Plugin Refactoring Notes

## Overview
The Shopkeepers plugin has been refactored to improve code organization, maintainability, and readability. The long, monolithic files have been split into smaller, focused classes with clear responsibilities.

## New Project Structure

### ItemMaker Package (`com.joosua.shopkeepers.itemmaker`)

#### State Management (`state/`)
- **PlayerState.java** - Contains all player state data (preview items, pending inputs, styling preferences, etc.)
- **PlayerStateManager.java** - Manages PlayerState instances for all players

#### Attributes (`attributes/`)
- **AttributeModifierData.java** - Data class for storing attribute modifier information

#### UI Builders (`ui/`)
- **BaseUIBuilder.java** - Abstract base class providing common UI functionality
- **MainUIBuilder.java** - Builds the main ItemMaker interface
- **WeaponsUIBuilder.java** - Builds the weapons selection interface
- **ToolsUIBuilder.java** - Builds the tools selection interface
- *More UI builders can be added here as needed*

#### Utilities (`utils/`)
- **ItemMakerConstants.java** - All UI titles, button text, and constants
- **ItemUtils.java** - Utility methods for item creation and manipulation
- **MaterialUtils.java** - Material type checking utilities
- **StyleManager.java** - Handles applying styles to preview items

#### Core Classes
- **CreateItemCommand.java** - Main command executor (simplified)
- **ItemMakerListener.java** - Event listener (simplified)

## What Was Refactored

### Before (Monolithic Structure)
- **PlayerStateManager.java** - 210 lines, contained both PlayerState and AttributeModifierData as inner classes
- **CreateItemCommand.java** - 813 lines, contained all UI building logic
- **ItemMakerListener.java** - 951 lines, contained all event handling logic

### After (Modular Structure)
- **PlayerState.java** - 125 lines, focused only on state data
- **AttributeModifierData.java** - 30 lines, focused only on attribute data
- **CreateItemCommand.java** - 45 lines, focused only on command execution
- **ItemMakerListener.java** - 176 lines, focused only on event handling
- **UI Builders** - Each handles a specific UI interface
- **Utility Classes** - Each handles a specific type of functionality

## Benefits of Refactoring

1. **Maintainability** - Smaller files are easier to understand and modify
2. **Readability** - Clear separation of concerns makes code easier to follow
3. **Reusability** - Utility classes can be used across different parts of the code
4. **Testability** - Smaller, focused classes are easier to unit test
5. **Extensibility** - New UI builders can be added without modifying existing code
6. **Organization** - Logical grouping makes it easier to find specific functionality

## How to Add New Features

### Adding a New UI Interface
1. Create a new class extending `BaseUIBuilder`
2. Implement the `buildXXXUI()` method
3. Add the UI builder to `CreateItemCommand`
4. Add event handling in `ItemMakerListener`

### Adding New Utility Functions
1. Identify the appropriate utility class or create a new one
2. Add static methods following the existing pattern
3. Update imports in classes that need the new functionality

### Adding New State Properties
1. Add properties to `PlayerState`
2. Add getter/setter methods
3. Update `StyleManager` if the properties affect item styling

## Migration Notes

- All existing functionality has been preserved
- The plugin maintains the same external API
- No breaking changes for users
- Internal structure is now much cleaner and more maintainable

## Future Improvements

- Add more UI builders for remaining interfaces (armor, misc, enchants, style, etc.)
- Implement proper dependency injection
- Add unit tests for the new modular structure
- Consider using a UI framework for more complex interfaces
