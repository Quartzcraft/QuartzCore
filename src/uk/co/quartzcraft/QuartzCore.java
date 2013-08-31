package uk.co.quartzcraft;

import uk.co.quartzcraft.*;
import uk.co.quartzcraft.commands.*;
import uk.co.quartzcraft.listeners.*;

import org.bukkit.plugin.java.JavaPlugin;

public class QuartzCore extends JavaPlugin {
	
    String releaseType = "DEV ";
	int releaseVersion = 1;
	 
	@Override
	public void onEnable(){
        // TODO Insert logic to be performed when the plugin is enabled
		getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
	    getLogger().info("The QuartzCore Plugin has been enabled!");
	   	getCommand("quartz").setExecutor(new quartzExecutor());
	   	getCommand("m").setExecutor(new AdminGamemodeExecutor());
	   	getCommand("list").setExecutor(new PlayerListExecutor());
	}
	 
	@Override
	public void onDisable() {
    	// TODO Insert logic to be performed when the plugin is disabled
		getLogger().info("The QuartzCore Plugin has been disabled!");
	}
}
