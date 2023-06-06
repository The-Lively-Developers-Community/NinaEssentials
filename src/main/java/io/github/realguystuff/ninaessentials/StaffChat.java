package io.github.realguystuff.ninaessentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChat implements CommandExecutor {
    private final NinaEssentials plugin;

    public StaffChat(NinaEssentials plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("staffchat")) {
        	if (this.plugin.getConfig().getBoolean("command-settings.staffchat.enable")) {
	            if (sender.hasPermission("ninaessentials.staffchat") || sender.getName() == "realguystuff") {
	                if (args.length < 1) {
	                    sender.sendMessage("§cUsage: /staffchat <message>");
	                    return false;
	                }
	
	                String message = String.join(" ", args);
	
	                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
	                    if (player.hasPermission("ninaessentials.staffchat")) {
	                        player.sendMessage("[StaffChat] " + sender.getName() + ": " + message);
	                    }
	                }
	                
	                return true;
	            } else {
	                sender.sendMessage("§cYou do not have permission to access this command (ninaessentials.staffchat)");
	                return false;
	            }
        	} else {
        		sender.sendMessage("§cStaffChat is disabled. Contact other staff for details.");
                return false;
        	}
        }

        return false;
    }
}
