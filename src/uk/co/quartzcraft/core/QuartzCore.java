package uk.co.quartzcraft.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import uk.co.quartzcraft.core.command.*;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.data.QServer;
import uk.co.quartzcraft.core.database.MySQL;
import uk.co.quartzcraft.core.features.QCAlertTypes;
import uk.co.quartzcraft.core.features.chat.ChatPhrases;
import uk.co.quartzcraft.core.features.items.FinalItems;
import uk.co.quartzcraft.core.features.items.SoulboundItems;
import uk.co.quartzcraft.core.features.items.UnbreakableItems;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.quartzcraft.core.systems.notifications.AlertTypeHandler;
import uk.co.quartzcraft.core.systems.websync.UpdateGroups;
import uk.co.quartzcraft.core.systems.websync.Websync;
import uk.co.quartzcraft.core.listeners.ChatListener;
import uk.co.quartzcraft.core.listeners.ConnectionListener;
import uk.co.quartzcraft.core.util.Util;

/**
 * Core file for QuartzCore plugin.
 */
public class QuartzCore extends JavaPlugin {
	
	public static String version;

    public static Plugin plugin;
    public static QServer server;
	public static final Logger logger = Logger.getLogger("Minecraft");
    public static FileConfiguration config;

    private FileConfiguration pluginConfig;
	
	public static Connection DBCore = null;
    
	public static MySQL MySQLcore = null;

    public QCommandFramework commandFramework;

    @Override
	public void onDisable() {

        //Close database
        Util.log("Closing database connections");
        MySQLcore.closeConnection();
        try {
            DBCore.close();
            Util.log("Successfully closed database connections");
        } catch(SQLException e) {
            Util.log(Level.SEVERE, "Failed to close database connections!");
        }

    	//Shutdown notice
		Util.log("The QuartzCore Plugin has been disabled!");
	}

	@Override
	public void onEnable() {
		
		Util.log("[STARTUP LOGGER]Console logger discovered");

        plugin = this;
        version = this.getDescription().getVersion();

        //Config files
        Util.log("[STARTUP]Running plugin configuration");
       	this.saveDefaultConfig();
        config = plugin.getConfig();
        this.pluginConfig = plugin.getConfig();

        //Starting database
        Util.log("[STARTUP]Running database configuration");
        boolean DBConnect = this.pluginConfig.getBoolean("settings.database-connect");
        //boolean DBConnect = false;

        Util.log("DBConnect value is: " + DBConnect);
		
		if(DBConnect) {
			//Core Database
            String SQLCoreHost = this.pluginConfig.getString("database.core.host");
			String SQLCoreDatabase = this.pluginConfig.getString("database.core.database");
			String SQLCoreUser = this.pluginConfig.getString("database.core.username");
			String SQLCorePassword = this.pluginConfig.getString("database.core.password");
			MySQLcore = new MySQL(this, SQLCoreHost, "3306", SQLCoreDatabase, SQLCoreUser, SQLCorePassword);

            Util.log("[STARTUP]Connecting to Database");
            DBCore = MySQLcore.openConnection();
		} else {
            Util.log(Level.SEVERE, "Database connection set to false! Please fix this in the config.yml file!");
            Util.log(Level.SEVERE, "The server is now shutting down!");
            this.getServer().shutdown();
        }

        Util.log("[STARTUP]Creating QServer instance");
        server = new QServer(this.pluginConfig.getInt("settings.server-id"));
        //server = new QServer(1);

        //Websync
        Util.log("[STARTUP]Preparing Websync");
        if(this.getConfig().getBoolean("settings.websync.active")) {
            Util.log("[STARTUP]Initializing Websync");
            Websync.init();

            Util.log("[STARTUP]Performing websync operations");
            UpdateGroups.update();
        }

		//Phrases
		Util.log("[STARTUP]Creating Phrases");
        ChatPhrases.phrases();

        //Alert Types
        Util.log("[STARTUP] Registering alert types");
        AlertTypeHandler.registerAlertTypes(new QCAlertTypes());

		//Listeners
		Util.log("[STARTUP]Registering listeners...");
		new ConnectionListener(this);
		new ChatListener(this);
        new UnbreakableItems(this);
        new SoulboundItems(this);
        new FinalItems(this);

	    //Commands
		Util.log("[STARTUP]Registering commands...");
        commandFramework = new QCommandFramework(this);
        commandFramework.registerCommands(new CommandTest(this));
        commandFramework.registerCommands(new CommandWorld(this));
        commandFramework.registerCommands(new CommandQCReload(this));
        commandFramework.registerCommands(new CommandAnnounce(this));
        commandFramework.registerCommands(new CommandPromo(this));
        commandFramework.registerCommands(new CommandReport(this));
        commandFramework.registerCommands(new CommandPinfo(this));
        commandFramework.registerCommands(new CommandTP(this));
        commandFramework.registerCommands(new CommandAlerts(this));
        commandFramework.registerCommands(new CommandM(this));
	   	getCommand("quartz").setExecutor(new CommandQuartz());
	   	getCommand("register").setExecutor(new CommandRegister());
	  		
	   	//Startup notice
	  	Util.log("The QuartzCore Plugin has been enabled!");
	  	Util.log("QuartzCore Version " + version);
	}

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return commandFramework.handleCommand(sender, label, command, args);
    }

    public static QServer getQServer() {
        return server;
    }

}
