package io.github.realguystuff.ninaessentials;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

//import com.comphenix.protocol.ProtocolLibrary;
//import com.comphenix.protocol.ProtocolManager;

public final class NinaEssentials extends JavaPlugin implements Listener {
	FileConfiguration config = this.getConfig();
	//private ProtocolManager protocolManager;
	
    @Override
    public void onEnable() {
    	getLogger().info("Thank you for using Nina's plugin. Loading...");
    	//if (config.getBoolean("verbose")) getLogger().info("Linking with ProtocolLib...");
    	//protocolManager = ProtocolLibrary.getProtocolManager();
    	if (config.getBoolean("verbose")) getLogger().info("Registering events...");
    	getServer().getPluginManager().registerEvents(this, this);
    	if (config.getBoolean("verbose")) getLogger().info("Loading your configuration...");
    	this.saveDefaultConfig();
    	getConfig().options().copyDefaults(true);
    	if (config.getBoolean("verbose")) getLogger().info("Loading commands...");
    	this.getCommand("setfire").setExecutor(new SetFire(this));
    	this.getCommand("staffchat").setExecutor(new StaffChat(this));
    	this.getCommand("randomfact").setExecutor(new RandomFact(this));
    	getLogger().info("Everything is loaded up and ready!");
    }
    
    @Override
    public void onDisable() {
    	getLogger().info("Bye, see you next time!");
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        String player = event.getPlayer().getName();
        Block block = event.getBlock();
        
        if (block.getType() == Material.DIAMOND_ORE) {
        	if (config.getBoolean("log-mining-ores.diamond-ore")) getLogger().info(player + " has mined diamond ore!");
        } else if (block.getType() == Material.DEEPSLATE_DIAMOND_ORE) {
        	if (config.getBoolean("log-mining-ores.deepslate-diamond-ore")) getLogger().info(player + " has mined deepslate diamond ore!");
        } else if (block.getType() == Material.ANCIENT_DEBRIS) {
        	if (config.getBoolean("log-mining-ores.ancient-debris")) getLogger().info(player + " has mined ancient debris!");
        } else if (block.getType() == Material.IRON_ORE) {
        	if (config.getBoolean("log-mining-ores.iron-ore")) getLogger().info(player + " has mined iron ore!");
        } else if (block.getType() == Material.DEEPSLATE_IRON_ORE) {
        	if (config.getBoolean("log-mining-ores.deepslate-iron-ore")) getLogger().info(player + " has mined deepslate iron ore!");
        }
        
        if (block.getType() == Material.BEDROCK && config.getBoolean("fixes.fix-bedrock-glitch")) {
            // Get the original location of the bedrock
            Location originalLocation = block.getLocation();
            World world = block.getWorld();

            // Restore bedrock after a 1 tick delay
            Bukkit.getScheduler().runTaskLater(NinaEssentials.this, () -> {
                // Check if the bedrock block is still broken
                if (block.getType() != Material.BEDROCK) {
                    // Restore the bedrock block at its original location
                    world.getBlockAt(originalLocation).setType(Material.BEDROCK);

                    // Log the event in the console
                    getLogger().info("Bedrock at " + originalLocation + " was restored.");
                }
            }, 1);
        }
    }
    
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
    	if (config.getBoolean("log-logging-in-players")) getLogger().info("\""+event.getPlayer().getName()+"\" attempted to join the server.");
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String servername;
        if (config.getBoolean("serverdetails.smp")) {
        	servername = config.getString("serverdetails.name") + " SMP";
        } else {
        	servername = config.getString("serverdetails.name") + " Server";
        }
        if (config.getBoolean("serverdetails.the")) {
        	servername = "the " + servername;
        }
        
    	config.getString("serverdetails.welcome-message").replace("{servername}", servername);
    	config.getString("serverdetails.welcome-message").replace("{ownername}", config.getString("serverdetails.owner-name"));
    	config.getString("serverdetails.welcome-message").replace("{helpers}", config.getString("serverdetails.helpers"));
    	config.getString("serverdetails.welcome-message").replace("{player}", player.getName());
        if (config.getBoolean("serverdetails.send-welcome-message")) player.sendMessage(config.getString("serverdetails.welcome-message"));
    }
}