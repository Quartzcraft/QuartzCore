package uk.co.quartzcraft;

import java.sql.Connection;
import java.util.logging.Logger;

import uk.co.quartzcraft.database.*;
import uk.co.quartzcraft.chat.*;
import uk.co.quartzcraft.command.*;
import uk.co.quartzcraft.listeners.*; 

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Core file for QuartzCore plugin.
 * 
 * @author mba2012
 * @author SoulPunisher
 */
public class QuartzCore extends JavaPlugin {
	
	public static double version = 1.0;
	public static String release = "BETA";
	
	public Plugin plugin = this.plugin;
	
	public static Connection DBCore = null;
	public static Connection DBXen = null;
	
	Server server = getServer();
	Logger logger = getLogger();
	 
	public MySQL MySQLcore = new MySQL(plugin, "localhost", "3306", "Quartz", "root", "database1");
	public MySQL MySQLxen = new MySQL(plugin, "localhost", "3306", "XenForo", "root", "database1");
	
	@Override
	public void onEnable() {
		
		logger.info("[STARTUP LOGGER]Console logger discovered");
		
		//Database
		logger.info("[STARTUP]Connecting to Database");
		DBCore = MySQLcore.openConnection();
		DBXen = MySQLxen.openConnection();

		
		//Listeners
		logger.info("[STARTUP]Registering listeners...");
		server.getPluginManager().registerEvents(new ConnectionListener(), this);
		
	    //Commands
		logger.info("[STARTUP]Registering commands...");
	   	getCommand("quartz").setExecutor(new CommandQuartz());
	   	getCommand("register").setExecutor(new CommandRegister());
	   	getCommand("test").setExecutor(new CommandTest());
	   	getCommand("m").setExecutor(new CommandM());
	   	getCommand("report").setExecutor(new CommandReport());
	   	
	   	//ChatChannels
	   	//logger.info("[STARTUP]Registering chat channels...");
	   	
	   	//Startup notice
	  	logger.info("The QuartzCore Plugin has been enabled!");
	  	logger.info("QuartzCore Version " + release + " " + version);
	}
	 
	@Override
	public void onDisable() {
		
		//Database
		logger.info("[SHUTDOWN]Terminating connection to database");
		
    	//Shutdown notice
		logger.info("The QuartzCore Plugin has been disabled!");
	}
}
