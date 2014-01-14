package uk.co.quartzcraft.core;

import java.sql.Connection;
import java.util.logging.Logger;

import uk.co.quartzcraft.core.database.*;
import uk.co.quartzcraft.core.chat.*;
import uk.co.quartzcraft.core.command.*;
import uk.co.quartzcraft.core.database.MySQL;
import uk.co.quartzcraft.core.listeners.*; 

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Core file for QuartzCore plugin.
 * 
 * @author mba2012
 * @author SoulPunisher
 */
public class QuartzCore extends JavaPlugin {
	
	public static String version = "1.0.0";
	public static String release = "BETA";
	
	public Plugin plugin = this.plugin;
	public Server server = getServer();
	public static final Logger log = Logger.getLogger("Minecraft");
	//public final PluginManager manager = getServer().getPluginManager();
	
	public static Connection DBCore = null;
	public static Connection DBXen = null;
    
	public static MySQL MySQLcore = null;
	//public MySQL MySQLxen = null;
	
	@Override
	public void onDisable() {
		
		//Database
		log.info("[QC][SHUTDOWN]Terminating connection to database");
		
    	//Shutdown notice
		log.info("[QC]The QuartzCore Plugin has been disabled!");
	}
	@Override
	public void onEnable() {
		
		log.info("[QC][STARTUP LOGGER]Console logger discovered");
		
		log.info("[QC]Running plugin configuration");
		this.saveDefaultConfig();
		
		String SQLCoreHost = this.getConfig().getString("database.core.host");
		String SQLCoreDatabase = this.getConfig().getString("database.core.database");
		String SQLCoreUser = this.getConfig().getString("database.core.username");
		String SQLCorePassword = this.getConfig().getString("database.core.password");
		MySQLcore = new MySQL(plugin, SQLCoreHost, "3306", SQLCoreDatabase, SQLCoreUser, SQLCorePassword);
		//public MySQL MySQLxen = new MySQL(plugin, "ns1.gingetechnologies.com", "3306", "quartz_craft", "quartz", "226+nBEV[{RL");

		
		//Phrases
		log.info("[QC][STARTUP]Creating Phrases");
		ChatPhrase.addPhrase("Unknown_SubCommand", "&3Could not find the specified SubCommand! &aVisit the QuartzCraft Wiki for help.");
		ChatPhrase.addPhrase("Unknown_Command", "Unknown Command! Please see the QuartzCraft Wiki for help.");
		ChatPhrase.addPhrase("subcommand_not_found", "&3This is a test of the phrases system.");
		ChatPhrase.addPhrase("test_phrase", "&3This is a test of the phrases system.");
		ChatPhrase.addPhrase("database_error", "&3A database error occured!");
		ChatPhrase.addPhrase("database_error_contact", "&3A database error occured! &aPlease contact an administrator on the QuartzCraft website immediately.");
		
		
		//Listeners
		log.info("[QC][STARTUP]Registering listeners...");
		//getServer().getPluginManager().registerEvents(new ConnectionListener(this), this);
		new ConnectionListener(this);
		
	    //Commands
		log.info("[QC][STARTUP]Registering commands...");
	   	getCommand("quartz").setExecutor(new CommandQuartz());
	   	getCommand("test").setExecutor(new CommandTest());
	   	getCommand("m").setExecutor(new CommandM());
	   	getCommand("report").setExecutor(new CommandReport());
	   	getCommand("promo").setExecutor(new CommandPromo());
	   	getCommand("qplayer").setExecutor(new CommandQPlayer());
	   	
	   	//ChatChannels
	   	//logger.info("[STARTUP]Registering chat channels...");
	   	
	   	//Database
	  	log.info("[QC][STARTUP]Connecting to Database");
	  	DBCore = MySQLcore.openConnection();
	  	//DBXen = MySQLxen.openConnection();
	  		
	   	//Startup notice
	  	log.info("[QC]The QuartzCore Plugin has been enabled!");
	  	log.info("[QC]QuartzCore Version " + release + " " + version);
	}
	
	public static String displayReleaseVersion() {
		String ReleaseVersion = release + version;
		return ReleaseVersion;
	}
}
