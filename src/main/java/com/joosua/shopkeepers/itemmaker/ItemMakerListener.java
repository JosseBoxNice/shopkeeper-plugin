package com.joosua.shopkeepers.itemmaker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.persistence.PersistentDataType;

import com.joosua.shopkeepers.ShopkeepersPlugin;

public class ItemMakerListener implements Listener {

    private final ShopkeepersPlugin plugin;
    private final CreateItemCommand builder;
    private final NamespacedKey ENCH_KEY;

    public ItemMakerListener(ShopkeepersPlugin plugin) {
        this.plugin = plugin;
        this.builder = new CreateItemCommand(plugin);
        this.ENCH_KEY = new NamespacedKey(plugin, "enchant_key");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        if (inv == null) return;
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) return;

        String title = event.getView().getTitle();
        HumanEntity clicker = event.getWhoClicked();
        Player player = (clicker instanceof Player) ? (Player) clicker : null; // single cast

        // ===== MAIN =====
        if (CreateItemCommand.UI_TITLE_MAIN.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String name = dn(clicked);

            if (type == Material.BARRIER && name.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (player != null)
                return;
            }
            if (type == Material.DIAMOND_SWORD && name.equals(CreateItemCommand.BTN_WEAPONS)) {
                if (player != null) player.openInventory(builder.buildWeaponsUI(player));
                return;
            }
            if (type == Material.NETHERITE_PICKAXE) {
                if (player != null) player.openInventory(builder.buildToolsUI(player));
                return;
            }
            if (type == Material.DIAMOND_CHESTPLATE) {
                if (player != null) player.openInventory(builder.buildArmorUI(player));
                return;
            }
            if (type == Material.STICK) {
                if (player != null) player.openInventory(builder.buildMiscUI(player));
                return;
            }
            if (type == Material.ENCHANTED_BOOK && name.equals(CreateItemCommand.BTN_ENCH)) {
                if (player != null) {
                    var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
                    if (s.getPreview() == null) { player.sendMessage(ChatColor.RED + "Pick a weapon first."); return; }
                    player.openInventory(builder.buildEnchantsUI(player));
                }
                return;
            }
            if (type == Material.NAME_TAG && name.equals(CreateItemCommand.BTN_STYLE)) {
                if (player != null) player.openInventory(builder.buildStyleUI(player));
                return;
            }
            if (type == Material.NETHER_STAR && name.equals(CreateItemCommand.BTN_MODIFIERS)) {
                if (player != null) player.openInventory(builder.buildModifiersUI(player));
                return;
            }
            if (type == Material.EMERALD_BLOCK && name.equals(CreateItemCommand.BTN_CREATE) && player != null) {
                var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
                if (s.getPreview() == null) { player.sendMessage(ChatColor.RED + "Pick a weapon first."); return; }
                ItemStack toGive = s.getPreview().clone();
                var leftover = player.getInventory().addItem(toGive);
                if (!leftover.isEmpty()) leftover.values().forEach(item -> player.getWorld().dropItemNaturally(player.getLocation(), item));
                player.sendMessage(ChatColor.GREEN + "Created and given: " +
                        (toGive.hasItemMeta() && toGive.getItemMeta().hasDisplayName()
                                ? toGive.getItemMeta().getDisplayName() : toGive.getType().name()) + ChatColor.GREEN + ".");
            }
            return;
        }

        // ===== WEAPONS =====
        if (CreateItemCommand.UI_TITLE_WEAPONS.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String name = dn(clicked);

            if (type == Material.BARRIER && name.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (player != null)
                return;
            }
            if (type == Material.ARROW) {
                if (player != null) player.openInventory(builder.buildMainUI(player));
                return;
            }
            if (isWeapon(type) && player != null) {
                var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
                s.setPreview(clicked.clone());
                // reset styles when base changes
                s.setNameText(null); s.setNameHex(null);
                s.setNameBold(false); s.setNameItalic(false); s.setNameUnder(false); s.setNameStrike(false); s.setNameObfus(false);
                s.getLoreLines().clear(); s.setLoreHex(null);
                s.setLoreBold(false); s.setLoreItalic(false); s.setLoreUnder(false); s.setLoreStrike(false); s.setLoreObfus(false);
                player.openInventory(builder.buildMainUI(player));
                player.sendMessage(ChatColor.GREEN + "Preview set to " +
                        (clicked.hasItemMeta() ? clicked.getItemMeta().getDisplayName() : type.name()) + ChatColor.GREEN + ".");
            }
            return;
        }
        // ===== TOOLS =====
        if (CreateItemCommand.UI_TITLE_TOOLS.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String name = dn(clicked);
            if (type == Material.BARRIER && name.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (player != null)
                return;
            }
            if (type == Material.ARROW) {
                if (player != null) player.openInventory(builder.buildMainUI(player));
                return;
            }
            if (player != null && isTool(type)) {
                var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
                s.setPreview(clicked.clone());
                player.openInventory(builder.buildMainUI(player));
                player.sendMessage(ChatColor.GREEN + "Preview set to " +
                        (clicked.hasItemMeta() ? clicked.getItemMeta().getDisplayName() : type.name()) + ChatColor.GREEN + ".");
            }
            return;
        }
        // ===== ARMOR =====
        if (CreateItemCommand.UI_TITLE_ARMOR.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String name = dn(clicked);
            if (type == Material.BARRIER && name.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (player != null)
                return;
            }
            if (type == Material.ARROW) {
                if (player != null) player.openInventory(builder.buildMainUI(player));
                return;
            }
            if (player != null && isArmor(type)) {
                var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
                s.setPreview(clicked.clone());
                player.openInventory(builder.buildMainUI(player));
                player.sendMessage(ChatColor.GREEN + "Preview set to " +
                        (clicked.hasItemMeta() ? clicked.getItemMeta().getDisplayName() : type.name()) + ChatColor.GREEN + ".");
            }
            return;
        }

        // ===== MISC =====
        if (CreateItemCommand.UI_TITLE_MISC.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String name = dn(clicked);
            if (type == Material.BARRIER && name.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (player != null)
                return;
            }
            if (type == Material.ARROW) {
                if (player != null) player.openInventory(builder.buildMainUI(player));
                return;
            }
            if (player != null && isMisc(type)) {
                var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
                s.setPreview(clicked.clone());
                player.openInventory(builder.buildMainUI(player));
                player.sendMessage(ChatColor.GREEN + "Preview set to " +
                        (clicked.hasItemMeta() ? clicked.getItemMeta().getDisplayName() : type.name()) + ChatColor.GREEN + ".");
            }
            return;
        }


        // ===== ENCHANTS =====
        if (CreateItemCommand.UI_TITLE_ENCHANTS.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String name = dn(clicked);

            if (type == Material.BARRIER && name.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (player != null)
                return;
            }
            if (type == Material.ARROW) {
                if (player != null) player.openInventory(builder.buildMainUI(player));
                return;
            }

            // Unbreakable toggle
            if ((type == Material.BOOK || type == Material.ENCHANTED_BOOK) && name.startsWith(CreateItemCommand.BTN_UNBREAK) && player != null) {
                var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
                if (s.getPreview() == null) { player.sendMessage(ChatColor.RED + "Pick a weapon first."); return; }
                ItemMeta meta = s.getPreview().getItemMeta(); if (meta == null) return;
                meta.setUnbreakable(!meta.isUnbreakable());
                s.getPreview().setItemMeta(meta);
                player.openInventory(builder.buildEnchantsUI(player));
                player.sendMessage(ChatColor.YELLOW + "Unbreakable: " + (meta.isUnbreakable() ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF"));
                return;
            }

            // Enchant selection
            if (type == Material.ENCHANTED_BOOK && player != null) {
                ItemMeta meta = clicked.getItemMeta(); if (meta == null) return;
                String keyStr = meta.getPersistentDataContainer().get(ENCH_KEY, PersistentDataType.STRING);
                if (keyStr == null) return;
                Enchantment ench = Enchantment.getByKey(NamespacedKey.fromString(keyStr));
                if (ench == null) { player.sendMessage(ChatColor.RED + "Unknown enchantment."); return; }
                var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
                if (s.getPreview() == null) { player.sendMessage(ChatColor.RED + "Pick a weapon first."); return; }
                s.setPendingEnchant(ench);
                s.setAwaitingEnchantLevel(true);
                player.closeInventory();
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Enter level (0–255) for " + CreateItemCommand.niceName(ench) + ChatColor.GRAY + " in chat. Type a number only.");
            }
            return;
        }

        // ===== STYLE MAIN =====
        if (CreateItemCommand.UI_TITLE_STYLE.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String name = dn(clicked);
            if (type == Material.BARRIER && name.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (player != null) return;
            }
            if (type == Material.ARROW) { if (player != null) player.openInventory(builder.buildMainUI(player)); return; }
            if (player == null) return;
            if (type == Material.NAME_TAG && name.equals(CreateItemCommand.BTN_STYLE_SET_NAME)) { player.openInventory(builder.buildNameUI(player)); return; }
            if (type == Material.WRITABLE_BOOK && name.equals(CreateItemCommand.BTN_STYLE_LORE)) { player.openInventory(builder.buildLoreUI(player)); return; }
            return;
        }

        // ===== NAME UI =====
        if (CreateItemCommand.UI_TITLE_STYLE_NAME.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String name = dn(clicked);
            if (type == Material.BARRIER && name.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (player != null) return;
            }
            if (type == Material.ARROW) { if (player != null) player.openInventory(builder.buildStyleUI(player)); return; }
            if (player == null) return;
            var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
            if (s.getPreview() == null) { player.sendMessage(ChatColor.RED + "Pick a weapon first."); return; }
            if (type == Material.NAME_TAG && name.equals(CreateItemCommand.BTN_NAME_SETNAME)) {
                s.setAwaitingNameInput(true); player.closeInventory(); player.sendMessage(ChatColor.GOLD + "Type the new item name in chat."); return;
            }
            if (type == Material.GRAY_DYE && name.equals(CreateItemCommand.BTN_NAME_SETCOLOR)) {
                player.openInventory(builder.buildNameColorUI(player)); return;
            }
            if (type == Material.FEATHER && name.equals(CreateItemCommand.BTN_NAME_FORMAT)) {
                player.openInventory(builder.buildNameFormatUI(player)); return;
            }
            return;
        }

        // ===== NAME FORMAT =====
        if (CreateItemCommand.UI_TITLE_STYLE_NAME_FMT.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String name = dn(clicked);
            if (type == Material.BARRIER && name.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (player != null) return;
            }
            if (type == Material.ARROW) { if (player != null) player.openInventory(builder.buildNameUI(player)); return; }
            if (player == null) return;
            var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
            if (name.startsWith(CreateItemCommand.BTN_T_BOLD)) s.setNameBold(!s.isNameBold());
            else if (name.startsWith(CreateItemCommand.BTN_T_ITALIC)) s.setNameItalic(!s.isNameItalic());
            else if (name.startsWith(CreateItemCommand.BTN_T_UNDER)) s.setNameUnder(!s.isNameUnder());
            else if (name.startsWith(CreateItemCommand.BTN_T_STRIKE)) s.setNameStrike(!s.isNameStrike());
            else if (name.startsWith(CreateItemCommand.BTN_T_OBFUS)) s.setNameObfus(!s.isNameObfus());
            else return;
            builder.applyStyleToPreview(s);
            player.openInventory(builder.buildNameFormatUI(player));
            return;
        }
        // ===== NAME COLOR UI =====
        if (CreateItemCommand.UI_TITLE_STYLE_NAME_COLOR.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String disp = dn(clicked);
            if (type == Material.BARRIER && disp.equals(CreateItemCommand.BTN_CLOSE)) {
                         clicker.closeInventory(); if (clicker instanceof Player p) return;
            }
            if (type == Material.ARROW) { if (clicker instanceof Player p) p.openInventory(builder.buildNameUI(p)); return; }
            if (!(clicker instanceof Player p)) return;
            var s = plugin.getItemMakerStateManager().get(p.getUniqueId());
            if (type == Material.PAPER && disp.equals(CreateItemCommand.BTN_COLOR_HEX)) {
                s.setAwaitingNameHexInput(true); p.closeInventory();
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Type a hex color like #FFAA00 (or 'clear' to remove).");
                return;
            }
            String hex = CreateItemCommand.hexForDye(type);
            if (hex != null) {
                s.setNameHex(hex);
                builder.applyStyleToPreview(s);
                p.openInventory(builder.buildNameUI(p));
            }
            return;
        }
        // ===== LORE UI =====
        if (CreateItemCommand.UI_TITLE_STYLE_LORE.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String name = dn(clicked);
            if (type == Material.BARRIER && name.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (player != null) return;
            }
            if (type == Material.ARROW) { if (player != null) player.openInventory(builder.buildStyleUI(player)); return; }
            if (player == null) return;
            var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
            if (s.getPreview() == null) { player.sendMessage(ChatColor.RED + "Pick a weapon first."); return; }
            if (type == Material.WRITABLE_BOOK && name.equals(CreateItemCommand.BTN_LORE_ADDLINE)) { // <-- WRITABLE_BOOK
                s.setAwaitingLoreInput(true); player.closeInventory(); player.sendMessage(ChatColor.AQUA + "Type a lore line in chat."); return;
            }
            if (type == Material.GRAY_DYE && name.equals(CreateItemCommand.BTN_LORE_SETCOLOR)) {
                player.openInventory(builder.buildLoreColorUI(player));
            }
            if (type == Material.FEATHER && name.equals(CreateItemCommand.BTN_LORE_FORMAT)) { player.openInventory(builder.buildLoreFormatUI(player)); return; }
            if (type == Material.SPECTRAL_ARROW && name.equals(CreateItemCommand.BTN_LORE_REMLAST)) {
                if (!s.getLoreLines().isEmpty()) s.getLoreLines().remove(s.getLoreLines().size() - 1);
                builder.applyStyleToPreview(s); player.openInventory(builder.buildLoreUI(player)); return;
            }
            if (type == Material.TNT && name.equals(CreateItemCommand.BTN_LORE_CLEAR)) {
                s.getLoreLines().clear(); builder.applyStyleToPreview(s); player.openInventory(builder.buildLoreUI(player)); return;
            }
            return;
        }
        // ===== LORE FORMAT =====
        if (CreateItemCommand.UI_TITLE_STYLE_LORE_FMT.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String lore = dn(clicked);
            if (type == Material.BARRIER && lore.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory();
                return;
            }
            if (type == Material.ARROW) {
                if (player != null) player.openInventory(builder.buildLoreUI(player));
                return;
            }
            if (player == null) return;
            var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
            if (lore.startsWith(CreateItemCommand.BTN_T_BOLD)) s.setLoreBold(!s.isLoreBold());
            else if (lore.startsWith(CreateItemCommand.BTN_T_ITALIC)) s.setLoreItalic(!s.isLoreItalic());
            else if (lore.startsWith(CreateItemCommand.BTN_T_UNDER)) s.setLoreUnder(!s.isLoreUnder());
            else if (lore.startsWith(CreateItemCommand.BTN_T_STRIKE)) s.setLoreStrike(!s.isLoreStrike());
            else if (lore.startsWith(CreateItemCommand.BTN_T_OBFUS)) s.setLoreObfus(!s.isLoreObfus());
            else return;
            builder.applyStyleToPreview(s);
            player.openInventory(builder.buildLoreFormatUI(player));
            return;
        }
        // ===== LORE COLOR =====
        if (CreateItemCommand.UI_TITLE_STYLE_LORE_COLOR.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String disp = dn(clicked);
            if (type == Material.BARRIER && disp.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (clicker instanceof Player p) return;
            }
            if (type == Material.ARROW) { if (clicker instanceof Player p) p.openInventory(builder.buildLoreUI(p)); return; }
            if (!(clicker instanceof Player p)) return;
            var s = plugin.getItemMakerStateManager().get(p.getUniqueId());
            if (type == Material.PAPER && disp.equals(CreateItemCommand.BTN_COLOR_HEX)) {
                s.setAwaitingLoreHexInput(true); p.closeInventory();
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Type a hex color like #FFAA00 (or 'clear' to remove).");
                return;
            }
            String hex = CreateItemCommand.hexForDye(type);
            if (hex != null) {
                s.setLoreHex(hex);
                builder.applyStyleToPreview(s);
                p.openInventory(builder.buildLoreUI(p));
            }
            return;
        }
        // ===== MODIFIERS (placeholder) =====
        if (CreateItemCommand.UI_TITLE_MODIFIERS.equals(title)) {
            event.setCancelled(true);
            Material type = clicked.getType();
            String name = dn(clicked);
            if (type == Material.BARRIER && name.equals(CreateItemCommand.BTN_CLOSE)) {
                clicker.closeInventory(); if (player != null) return;
            }
            if (type == Material.ARROW) { if (player != null) player.openInventory(builder.buildMainUI(player)); }
        }
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
        // Enchant level prompt
        if (s.isAwaitingEnchantLevel() && s.getPendingEnchant() != null) {
            handleEnchantChat(event, player, s); return;
        }
        // Name text
        if (s.isAwaitingNameInput()) {
            event.setCancelled(true);
            String name = event.getMessage().trim();
            Bukkit.getScheduler().runTask(plugin, () -> {
                s.setNameText(name);
                builder.applyStyleToPreview(s);
                player.openInventory(builder.buildNameUI(player));
                player.sendMessage(ChatColor.GOLD + "Name set.");
                s.setAwaitingNameInput(false);
            });
            return;
        }
        // Name hex
        if (s.isAwaitingNameHexInput()) {
            event.setCancelled(true);
            String msg = event.getMessage().trim();
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (msg.equalsIgnoreCase("clear") || msg.equalsIgnoreCase("none")) s.setNameHex(null);
                else {
                    String hex = msg.startsWith("#") ? msg : "#" + msg;
                    if (hex.matches("^#(?i)[0-9a-f]{6}$")) s.setNameHex(hex);
                    else player.sendMessage(ChatColor.RED + "Invalid hex. Use #RRGGBB.");
                }
                builder.applyStyleToPreview(s);
                player.openInventory(builder.buildNameUI(player));
                s.setAwaitingNameHexInput(false);
            });
            return;
        }
        // Lore line
        if (s.isAwaitingLoreInput()) {
            event.setCancelled(true);
            String line = event.getMessage();
            Bukkit.getScheduler().runTask(plugin, () -> {
                s.getLoreLines().add(line);
                builder.applyStyleToPreview(s);
                player.openInventory(builder.buildLoreUI(player));
                s.setAwaitingLoreInput(false);
            });
            return;
        }
        // Lore hex
        if (s.isAwaitingLoreHexInput()) {
            event.setCancelled(true);
            String msg = event.getMessage().trim();
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (msg.equalsIgnoreCase("clear") || msg.equalsIgnoreCase("none")) s.setLoreHex(null);
                else {
                    String hex = msg.startsWith("#") ? msg : "#" + msg;
                    if (hex.matches("^#(?i)[0-9a-f]{6}$")) s.setLoreHex(hex);
                    else player.sendMessage(ChatColor.RED + "Invalid hex. Use #RRGGBB.");
                }
                builder.applyStyleToPreview(s);
                player.openInventory(builder.buildLoreUI(player));
                s.setAwaitingLoreHexInput(false);
            });
        }
    }
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        String title = event.getView().getTitle();
        if (!(title.startsWith(ChatColor.DARK_GREEN + "ItemMaker")
                || title.startsWith(ChatColor.DARK_AQUA + "ItemMaker")
                || title.startsWith(ChatColor.DARK_PURPLE + "ItemMaker")
                || title.startsWith(ChatColor.GOLD + "ItemMaker")
                || title.startsWith(ChatColor.BLUE + "ItemMaker"))) {
            return;
        }
        Inventory inv = event.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack it = inv.getItem(i);
            if (it == null || it.getType() != Material.GRAY_STAINED_GLASS_PANE) continue;
            var meta = it.getItemMeta();
            if (meta == null) continue;
            meta.setDisplayName(null);
            meta.setLore(null);
            meta.setHideTooltip(true);
            meta.addItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS,
                    ItemFlag.HIDE_UNBREAKABLE,
                    ItemFlag.HIDE_DYE,
                    ItemFlag.HIDE_ARMOR_TRIM,
                    ItemFlag.HIDE_ADDITIONAL_TOOLTIP,
                    ItemFlag.HIDE_DESTROYS,
                    ItemFlag.HIDE_PLACED_ON
            );
            it.setItemMeta(meta);
            inv.setItem(i, it);
        }
    }

    private void handleEnchantChat(AsyncPlayerChatEvent event, Player p, PlayerStateManager.PlayerState s) {
        String msg = event.getMessage().trim();
        int level;
        try { level = Integer.parseInt(msg); }
        catch (NumberFormatException e) { p.sendMessage(ChatColor.RED + "Please type a whole number between 0 and 255."); event.setCancelled(true); return; }
        if (level < 0 || level > 255) { p.sendMessage(ChatColor.RED + "Level must be between 0 and 255."); event.setCancelled(true); return; }
        event.setCancelled(true);

        Enchantment ench = s.getPendingEnchant();
        Bukkit.getScheduler().runTask(plugin, () -> {
            ItemStack preview = s.getPreview();
            if (preview == null) { p.sendMessage(ChatColor.RED + "No preview item set."); s.setAwaitingEnchantLevel(false); s.setPendingEnchant(null); return; }
            ItemMeta meta = preview.getItemMeta(); if (meta == null) { p.sendMessage(ChatColor.RED + "Could not edit the item."); s.setAwaitingEnchantLevel(false); s.setPendingEnchant(null); return; }

            if (level == 0) {
                if (meta.hasEnchant(ench)) meta.removeEnchant(ench);
                p.sendMessage(ChatColor.YELLOW + "Removed " + CreateItemCommand.niceName(ench) + ".");
            } else {
                meta.addEnchant(ench, level, true);
                p.sendMessage(ChatColor.GREEN + "Applied " + CreateItemCommand.niceName(ench) + ChatColor.GREEN + " level " + level + ".");
            }
            preview.setItemMeta(meta);
            s.setPreview(preview);
            p.openInventory(builder.buildEnchantsUI(p));
            s.setAwaitingEnchantLevel(false);
            s.setPendingEnchant(null);
        });
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        HumanEntity he = event.getPlayer();
        if (!(he instanceof Player player)) return;
        String title = event.getView().getTitle();
        if (!(title.startsWith(ChatColor.DARK_GREEN + "ItemMaker")
                || title.startsWith(ChatColor.DARK_AQUA + "ItemMaker")
                || title.startsWith(ChatColor.DARK_PURPLE + "ItemMaker")
                || title.startsWith(ChatColor.GOLD + "ItemMaker")
                || title.startsWith(ChatColor.BLUE + "ItemMaker"))) {
            return;
        }
        var s = plugin.getItemMakerStateManager().get(player.getUniqueId());
        if (!s.isAwaitingReturnToAdder()) return;
        // reset flag early
        s.setAwaitingReturnToAdder(false);
        java.util.UUID shopId = s.getReturnShopkeeperId();
        int retSlot = s.getReturnSlot();
        ItemStack preview = s.getPreview();
        if (shopId == null || retSlot < 0 || preview == null) return;
        // Reopen AddTrade for this shop and place preview in slot
        Inventory adder = plugin.getAddTradeManager().createAddTrade(shopId);
        adder.setItem(retSlot, preview.clone());
        player.openInventory(adder);
        // clear return mapping
        s.setReturnShopkeeperId(null);
        s.setReturnSlot(-1);
    }

    private boolean isWeapon(Material type) {
        return switch (type) {
            case WOODEN_SWORD, STONE_SWORD, IRON_SWORD, GOLDEN_SWORD, DIAMOND_SWORD, NETHERITE_SWORD,
                 WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE, NETHERITE_AXE,
                 BOW, CROSSBOW, TRIDENT, MACE -> true;
            default -> false;
        };
    }
    private boolean isTool(Material type) {
        return switch (type) {
            case WOODEN_PICKAXE, STONE_PICKAXE, IRON_PICKAXE, GOLDEN_PICKAXE, DIAMOND_PICKAXE, NETHERITE_PICKAXE,
                 WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE, NETHERITE_AXE,
                 WOODEN_SHOVEL, STONE_SHOVEL, IRON_SHOVEL, GOLDEN_SHOVEL, DIAMOND_SHOVEL, NETHERITE_SHOVEL,
                 WOODEN_HOE, STONE_HOE, IRON_HOE, GOLDEN_HOE, DIAMOND_HOE, NETHERITE_HOE -> true;
            default -> false;
        };
    }
    private boolean isArmor(Material type) {
        return switch (type) {
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS,
                 CHAINMAIL_HELMET, CHAINMAIL_CHESTPLATE, CHAINMAIL_LEGGINGS, CHAINMAIL_BOOTS,
                 IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS,
                 GOLDEN_HELMET, GOLDEN_CHESTPLATE, GOLDEN_LEGGINGS, GOLDEN_BOOTS,
                 DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_LEGGINGS, DIAMOND_BOOTS,
                 NETHERITE_HELMET, NETHERITE_CHESTPLATE, NETHERITE_LEGGINGS, NETHERITE_BOOTS,
                 TURTLE_HELMET -> true;
            default -> false;
        };
    }
    private boolean isMisc(Material type) {
        return switch (type) {
            case STICK, SNOWBALL, EGG, WIND_CHARGE, TOTEM_OF_UNDYING,
                 BUCKET, FISHING_ROD, FIRE_CHARGE, ELYTRA, ENDER_PEARL,
                 SHIELD, GLASS_BOTTLE, PLAYER_HEAD, GOAT_HORN, SHEARS,
                 FLINT_AND_STEEL, BRUSH, SPYGLASS -> true;
            default -> false;
        };
    }
    private String dn(ItemStack it) {
        if (it == null || !it.hasItemMeta() || !it.getItemMeta().hasDisplayName()) return "";
        return it.getItemMeta().getDisplayName();
    }
}
