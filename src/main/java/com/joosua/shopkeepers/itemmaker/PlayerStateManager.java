package com.joosua.shopkeepers.itemmaker;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStateManager {

	public static class PlayerState {
		// Preview
		private ItemStack preview;

		// Enchant prompt
		private boolean awaitingEnchantLevel;
		private Enchantment pendingEnchant;

		// Name prompts
		private boolean awaitingNameInput;
		private boolean awaitingNameHexInput;

		// Lore prompts
		private boolean awaitingLoreInput;
		private boolean awaitingLoreHexInput;

		// Name style (independent)
		private String nameText;     // null = keep current base
		private String nameHex;      // "#RRGGBB" or null
		private boolean nameBold, nameItalic, nameUnder, nameStrike, nameObfus;

		// Lore style (independent)
		private final List<String> loreLines = new ArrayList<>();
		private String loreHex;      // "#RRGGBB" or null
		private boolean loreBold, loreItalic, loreUnder, loreStrike, loreObfus;

		// --- getters / setters ---
		public ItemStack getPreview() { return preview; }
		public void setPreview(ItemStack preview) { this.preview = preview; }

		public boolean isAwaitingEnchantLevel() { return awaitingEnchantLevel; }
		public void setAwaitingEnchantLevel(boolean awaitingEnchantLevel) { this.awaitingEnchantLevel = awaitingEnchantLevel; }

		public Enchantment getPendingEnchant() { return pendingEnchant; }
		public void setPendingEnchant(Enchantment pendingEnchant) { this.pendingEnchant = pendingEnchant; }

		public boolean isAwaitingNameInput() { return awaitingNameInput; }
		public void setAwaitingNameInput(boolean awaitingNameInput) { this.awaitingNameInput = awaitingNameInput; }

		public boolean isAwaitingNameHexInput() { return awaitingNameHexInput; }
		public void setAwaitingNameHexInput(boolean awaitingNameHexInput) { this.awaitingNameHexInput = awaitingNameHexInput; }

		public boolean isAwaitingLoreInput() { return awaitingLoreInput; }
		public void setAwaitingLoreInput(boolean awaitingLoreInput) { this.awaitingLoreInput = awaitingLoreInput; }

		public boolean isAwaitingLoreHexInput() { return awaitingLoreHexInput; }
		public void setAwaitingLoreHexInput(boolean awaitingLoreHexInput) { this.awaitingLoreHexInput = awaitingLoreHexInput; }

		public String getNameText() { return nameText; }
		public void setNameText(String nameText) { this.nameText = nameText; }

		public String getNameHex() { return nameHex; }
		public void setNameHex(String nameHex) { this.nameHex = nameHex; }

		public boolean isNameBold() { return nameBold; }
		public void setNameBold(boolean nameBold) { this.nameBold = nameBold; }

		public boolean isNameItalic() { return nameItalic; }
		public void setNameItalic(boolean nameItalic) { this.nameItalic = nameItalic; }

		public boolean isNameUnder() { return nameUnder; }
		public void setNameUnder(boolean nameUnder) { this.nameUnder = nameUnder; }

		public boolean isNameStrike() { return nameStrike; }
		public void setNameStrike(boolean nameStrike) { this.nameStrike = nameStrike; }

		public boolean isNameObfus() { return nameObfus; }
		public void setNameObfus(boolean nameObfus) { this.nameObfus = nameObfus; }

		public List<String> getLoreLines() { return loreLines; }

		public String getLoreHex() { return loreHex; }
		public void setLoreHex(String loreHex) { this.loreHex = loreHex; }

		public boolean isLoreBold() { return loreBold; }
		public void setLoreBold(boolean loreBold) { this.loreBold = loreBold; }

		public boolean isLoreItalic() { return loreItalic; }
		public void setLoreItalic(boolean loreItalic) { this.loreItalic = loreItalic; }

		public boolean isLoreUnder() { return loreUnder; }
		public void setLoreUnder(boolean loreUnder) { this.loreUnder = loreUnder; }

		public boolean isLoreStrike() { return loreStrike; }
		public void setLoreStrike(boolean loreStrike) { this.loreStrike = loreStrike; }

		public boolean isLoreObfus() { return loreObfus; }
		public void setLoreObfus(boolean loreObfus) { this.loreObfus = loreObfus; }

	// Return-to-adder (integration with Shopkeepers AddTrade)
	private boolean awaitingReturnToAdder;
	private int returnSlot = -1; // slot index in AddTrade to return preview to (11 or 15)
	private java.util.UUID returnShopkeeperId;

	public boolean isAwaitingReturnToAdder() { return awaitingReturnToAdder; }
	public void setAwaitingReturnToAdder(boolean awaitingReturnToAdder) { this.awaitingReturnToAdder = awaitingReturnToAdder; }

	public int getReturnSlot() { return returnSlot; }
	public void setReturnSlot(int returnSlot) { this.returnSlot = returnSlot; }

	public java.util.UUID getReturnShopkeeperId() { return returnShopkeeperId; }
	public void setReturnShopkeeperId(java.util.UUID returnShopkeeperId) { this.returnShopkeeperId = returnShopkeeperId; }
	}

	private final ConcurrentHashMap<UUID, PlayerState> states = new ConcurrentHashMap<>();

	public PlayerState get(UUID id) { return states.computeIfAbsent(id, k -> new PlayerState()); }
}
