package uk.co.quartzcraft;

import java.sql.Connection;
import java.util.logging.Logger;
import uk.co.quartzcraft.*;
import uk.co.quartzcraft.database.*;
import uk.co.quartzcraft.chat.*;
import uk.co.quartzcraft.command.*;
import uk.co.quartzcraft.listeners.*;
import uk.co.quartzcraft.services.*;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class QuartzCore extends JavaPlugin implements Defaults {
	
	public Plugin plugin = this.plugin;
	 
	MySQL MySQL = new MySQL(plugin, "localhost", "3306", "Quartz", "root", "database1");
	Connection c = null;
	
	@Override
	public void onEnable(){
		//Database
		logger.info("[STARTUP]Connection to Database");
		c = MySQL.openConnection();
		
		//Listeners
		getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
		
	    //Commands
	   	getCommand("quartz").setExecutor(new CommandQuartz());
	   	getCommand("test").setExecutor(new CommandTest());
	   	getCommand("m").setExecutor(new CommandM());
	   	getCommand("list").setExecutor(new CommandList());
	   	getCommand("donate").setExecutor(new CommandDonate());
	   	
	   	//Startup notice
	  	getLogger().info("The QuartzCore Plugin has been enabled!");
	}
	 
	@Override
	public void onDisable() {
    	//Shutdown notice
		getLogger().info("The QuartzCore Plugin has been disabled!");
	}
}
