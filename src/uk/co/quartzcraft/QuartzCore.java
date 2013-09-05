package uk.co.quartzcraft;

import uk.co.quartzcraft.*;
import uk.co.quartzcraft.chat.*;
import uk.co.quartzcraft.command.*;
import uk.co.quartzcraft.listeners.*;
import uk.co.quartzcraft.services.*;

import org.bukkit.plugin.java.JavaPlugin;

public class QuartzCore extends JavaPlugin {
	
    String releaseType = "DEV ";
	int releaseVersion = 1;
	 
	@Override
	public void onEnable(){
		
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
