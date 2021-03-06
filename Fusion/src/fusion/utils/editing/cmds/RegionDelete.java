package fusion.utils.editing.cmds;

import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.protection.Region;
import fusion.utils.protection.RegionManager;
import mpermissions.utils.permissions.Rank;

/**
	 * 
	 * Copyright GummyPvP. Created on May 28, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class RegionDelete {
	
	@Command(name = "deleteregion", description = "Deletes a region.", usage = "/deleteregion (name)", rank = Rank.ADMIN)
	public void regionDelete(CommandArgs args) {
		
		if (args.length() < 1) {
			
			Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "Usage: /deleteregion (name)");
			
			return;
		}
		
		String regionName = args.getArgs(0);
		
		if (!RegionManager.getInstance().regionExists(regionName)) {
			
			Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "Region " + regionName + " does not exist!");
			
			return;
		}
		
		Region region = RegionManager.getInstance().getRegion(regionName);
		
		RegionManager.getInstance().removeRegion(region);
		
		Chat.getInstance().messagePlayer(args.getSender(), Chat.SECONDARY_BASE + "Region " + regionName + " removed!");
		
	}
	
}
