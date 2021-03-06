package fusion.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.gui.CandyGUI;
import fusion.utils.gui.KitGUI;
import fusion.utils.gui.ShopGUI;
import fusion.utils.gui.WarpGUI;
import fusion.utils.warps.Warp;
import fusion.utils.warps.WarpManager;
import klap.utils.mPlayer;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on May 17, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (e.getInventory().getName() == null) return;
		if (!e.getInventory().getName().contains("GummyPvP")) return;
		if (e.getCurrentItem() == null) return;
		if (!e.getCurrentItem().hasItemMeta()) return;
		if (e.getCurrentItem().getItemMeta() == null) return;
		
		Player player = (Player) e.getWhoClicked();
		
		e.setCancelled(true);
		
		if (e.getInventory().getName().contains(KitGUI.INVENTORY_NAME)) {
			
			if (KitManager.getInstance().valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) == null) {
				
				switch (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase())) {
				
				case "-->":
					
					new KitGUI(player, KitGUI.getPage(player) + 1);
					
					break;
					
				case "<--":
					
					new KitGUI(player, KitGUI.getPage(player) - 1);
					
					break;
					
				case "kit shop":
					
					new ShopGUI(player);
					
					break;
				}
				
				return;
			}
			
			KitManager.getInstance().valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())).apply(player);
			
			return;
		}
		
		/**
		 * 
		 * Shop
		 * 
		 */
		
		if (e.getInventory().getName().contains(ShopGUI.INVENTORY_NAME)) {
			
			if (KitManager.getInstance().valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) == null) return;
			
			Kit kit = KitManager.getInstance().valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
			
			if (!((mKitUser.getInstance(player).getCandies() - kit.getCost()) >= 0)) {
				
				Chat.getInstance().messagePlayer(player, Chat.SECONDARY_BASE + "You do not have enough candies to purchase " + Chat.IMPORTANT_COLOR + kit.getName());
				
				return;
			}
			
			mKitUser.getInstance(player).addOwnedKit(kit);
			mKitUser.getInstance(player).removeCandies(kit.getCost());
			
			Chat.getInstance().messagePlayer(player, Chat.SECONDARY_BASE + "You now own kit " + Chat.IMPORTANT_COLOR + kit.getName());
			
			player.closeInventory();
			
			return;
		}
		
		/**
		 * 
		 * Warps
		 * 
		 */
		
		if (e.getInventory().getName().contains(WarpGUI.INVENTORY_NAME)) {
			
			Warp warp = WarpManager.getInstance().getWarp(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
			
			if (warp == null) return;
			
			WarpManager.getInstance().sendPlayer(player, warp);
			
			return;
		}
		
		/**
		 * 
		 * Candyman
		 * 
		 */
		
		if (e.getInventory().getName().contains(CandyGUI.INVENTORY_NAME)) {
			
			ItemStack item = e.getCurrentItem();
			mPlayer user = mPlayer.getInstance(player);
			
			switch (item.getType()) {
			case IRON_INGOT:
				
				player.sendMessage("you got 250 candies from default prize");
				
				break;
				
			case GOLD_INGOT:
				
				if (user.getGroup().getRank().hasRequiredRank(Rank.SLIME)) {
					
					player.sendMessage("you got 250 candies from slime prize");
					
					break;
				}
				
				player.sendMessage("no perms");
				
				break;
				
			case DIAMOND:
				
				if (user.getGroup().getRank().hasRequiredRank(Rank.HARIBO)) {
					
					player.sendMessage("you got 250 candies from haribo prize");
					
					break;
				}
				
				player.sendMessage("no perms");
				
				break;
				
			case EMERALD:
				
				if (user.getGroup().getRank().hasRequiredRank(Rank.GUMMY)) {
					
					player.sendMessage("you got 250 candies from gummy prize");
					
					break;
				}
				
				player.sendMessage("no perms");
				
				break;
				
			case WATCH:
				
				player.closeInventory();
				new ShopGUI(player);
				
				break;

			default:
				
				player.sendMessage("random item");
				
				break;
			}
			
			return;
		}
	}

}
