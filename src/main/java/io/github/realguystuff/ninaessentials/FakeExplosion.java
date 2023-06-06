package io.github.realguystuff.ninaessentials;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

//import com.comphenix.protocol.ProtocolManager;

public class FakeExplosion implements CommandExecutor {
	//private ProtocolManager protocolManager;
	private final NinaEssentials plugin;
    public FakeExplosion(NinaEssentials plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	FileConfiguration config = this.plugin.getConfig();
        if (cmd.getName().equalsIgnoreCase("fakeexplosion")) {
        	if (config.getBoolean("command-settings.fakeexplosion.enable")) {
	        	if (sender.hasPermission("ninaessentials.fakeexplosion") || sender.getName() == "realguystuff") {
	        		if (args.length != 1) {
	                    sender.sendMessage("§cUsage: /fakeexplosion <player>");
	                    return false;
	                }
	
	                Player target = Bukkit.getPlayer(args[0]);
	                if (target == null || !target.isOnline()) {
	                    sender.sendMessage("§c"+args[0] + " is not online.");
	                    return false;
	                }
	                
	                target.getWorld().playSound(target.getLocation(), Sound.ENTITY_TNT_PRIMED, 1, 0);
	                
	                sender.sendMessage("Gave " + target.getName() + " a fake explosion.");
	                return true;
	        	} else {
	        		sender.sendMessage("§cYou do not have permission to access this command (ninaessentials.fakeexplosion)");
	        		return false;
	        	}
        	} else {
        		sender.sendMessage("§cFakeExplosion is disabled. Contact staff for details.");
        		return false;
        	}
        }

        return false;
    }
}
