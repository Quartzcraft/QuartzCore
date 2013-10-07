package uk.co.quartzcraft;

import java.sql.Connection;
import java.util.logging.Logger;

import uk.co.quartzcraft.*;
import uk.co.quartzcraft.database.*;
import uk.co.quartzcraft.chat.*;
import uk.co.quartzcraft.command.*;
import uk.co.quartzcraft.listeners.*; 

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class QuartzCore extends JavaPlugin implements Defaults {
	
	public Plugin plugin = this.plugin;
	 
	MySQL MySQL = new MySQL(plugin, "localhost", "3306", "Quartz", "root", "database1");
	Connection c = null;
	
	@Override
	public void onEnable() {
		
		Logger logger = getLogger();
		logger.info("[STARTUP LOGGER]Console logger discovered");
		
		//Database
		logger.info("[STARTUP]Connection to Database");
		c = MySQL.openConnection();
		
		//Listeners
		logger.info("[STARTUP]Registering listeners...");
		getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
		
	    //Commands
		logger.info("[STARTUP]Registering commands...");
	   	getCommand("quartz").setExecutor(new CommandQuartz());
	   	getCommand("test").setExecutor(new CommandTest());
	   	getCommand("m").setExecutor(new CommandM());
	   	getCommand("list").setExecutor(new CommandList());
	   	getCommand("donate").setExecutor(new CommandDonate());
	   	getCommand("chat").setExecutor(new CommandChat());
	   	getCommand("group").setExecutor(new CommandGroup());
	   	
	   	//ChatChannels
	   	//logger.info("[STARTUP]Registering chat channels...");
	   	
	   	//Startup notice
	  	logger.info("The QuartzCore Plugin has been enabled!");
	  	logger.info("QuartzCore Version " + release + " " + version);
	}
	 
	@Override
	public void onDisable() {
		
		Logger logger = getLogger();
		
    	//Shutdown notice
		logger.info("The QuartzCore Plugin has been disabled!");
	}
}
