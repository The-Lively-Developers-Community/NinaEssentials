package io.github.realguystuff.ninaessentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetFire implements CommandExecutor {
    private final NinaEssentials plugin;
    public SetFire(NinaEssentials plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	FileConfiguration config = this.plugin.getConfig();
        if (cmd.getName().equalsIgnoreCase("setfire")) {
        	if (config.getBoolean("command-settings.setfire.enable")) {
	        	if (sender.hasPermission("ninaessentials.setfire") || sender.getName() == "realguystuff") {
	        		if (args.length != 1) {
	                    sender.sendMessage("§cUsage: /setfire <player>");
	                    return false;
	                }
	
	                Player target = Bukkit.getPlayer(args[0]);
	                if (target == null || !target.isOnline()) {
	                    sender.sendMessage("§c"+args[0] + " is not online.");
	                    return false;
	                }
	
	                target.setFireTicks(config.getInt("command-settings.setfire.fire-ticks"));
	
	                sender.sendMessage("Set " + target.getName() + " on fire for "+(config.getInt("command-settings.setfire.fireticks")/20)+"seconds.");
	                return true;
	        	} else {
	        		sender.sendMessage("§cYou do not have permission to access this command (ninaessentials.setfire)");
	        		return false;
	        	}
        	} else {
        		sender.sendMessage("§cSetFire is disabled. Contact staff for details.");
        		return false;
        	}
        }

        return false;
    }
}
