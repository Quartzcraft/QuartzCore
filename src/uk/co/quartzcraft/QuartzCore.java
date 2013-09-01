package uk.co.quartzcraft;

import uk.co.quartzcraft.*;
import uk.co.quartzcraft.commands.*;
import uk.co.quartzcraft.listeners.*;
import uk.co.quartzcraft.services.*;

import org.bukkit.plugin.java.JavaPlugin;

public class QuartzCore extends JavaPlugin {
	
    String releaseType = "DEV ";
	int releaseVersion = 1;
	 
	@Override
	public void onEnable(){
		
        //Startup notice
		getLogger().info("The QuartzCore Plugin has been enabled!");
		
		//Listeners
		getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
		
	    //Commands
	   	getCommand("quartz").setExecutor(new QuartzCommand());
	   	getCommand("m").setExecutor(new AdminGamemodeCommand());
	   	getCommand("list").setExecutor(new PlayerListCommand());
	   	getCommand("test").setExecutor(new TestCommand());
	}
	 
	@Override
	public void onDisable() {
    	//Shutdown notice
		getLogger().info("The QuartzCore Plugin has been disabled!");
	}
}
