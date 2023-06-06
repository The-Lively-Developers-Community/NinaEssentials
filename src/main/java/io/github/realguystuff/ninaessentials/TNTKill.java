package io.github.realguystuff.ninaessentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

//import com.comphenix.protocol.ProtocolManager;

public class TNTKill implements CommandExecutor {
	//private ProtocolManager protocolManager;
	private final NinaEssentials plugin;
    public TNTKill(NinaEssentials plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = this.plugin.getConfig();
        if (cmd.getName().equalsIgnoreCase("tntkill") && config.getBoolean("command-settings.tntkill.enable")) {
        	if (sender instanceof Player) {
        	    Player player = (Player) sender;
        	    Player target = player.getServer().getPlayer(args[0]);
        	    // Make sure the player is online.
        	    if (target == null) {
        	        player.sendMessage(args[0] + " is not currently online or in this world.");
        	        return false;
        	    }
        	    target.getWorld().createExplosion(target.getLocation(), (float) config.getDouble("command-settings.tntkill.explosion-power"));
        	    target.setHealth(0.0D); 
        	    return true;
        	} else {
        	    sender.sendMessage("You must be a player!");
        	    return false;
        	}
        }
        return false;
    }
}
