package uk.co.quartzcraft.core;

import java.sql.Connection;
import java.util.Arrays;
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
	public static String release = "VERSION";
	
	public Plugin plugin = this.plugin;
	public Server server = getServer();
	public static final Logger log = Logger.getLogger("Minecraft");
	
	public static Connection DBCore = null;
	public static Connection DBWeb = null;
    
	public static MySQL MySQLcore = null;
	public static MySQL MySQLweb = null;
	
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
		
		//Config files
		log.info("[QC]Running plugin configuration");
		this.saveDefaultConfig();
		
		//Core Database
		String SQLCoreHost = this.getConfig().getString("database.core.host");
		String SQLCoreDatabase = this.getConfig().getString("database.core.database");
		String SQLCoreUser = this.getConfig().getString("database.core.username");
		String SQLCorePassword = this.getConfig().getString("database.core.password");
		MySQLcore = new MySQL(plugin, SQLCoreHost, "3306", SQLCoreDatabase, SQLCoreUser, SQLCorePassword);
		
		//Website Database
		String SQLWebHost = this.getConfig().getString("database.web.host");
		String SQLWebDatabase = this.getConfig().getString("database.web.database");
		String SQLWebUser = this.getConfig().getString("database.web.username");
		String SQLWebPassword = this.getConfig().getString("database.web.password");
		MySQLweb = new MySQL(plugin, SQLWebHost, "3306", SQLWebDatabase, SQLWebUser, SQLWebPassword);
		
		//Phrases
		log.info("[QC][STARTUP]Creating Phrases");
		ChatPhrase.addPhrase("test_phrase", "&3This is a test of the phrases system.");
		
		ChatPhrase.addPhrase("could_not_create_player", "&cYou're playerdata could not be added to the database!");
		ChatPhrase.addPhrase("these_are_the_fields_required_for_website_registration", "&aThese are the values for the required website registration fields:");
		ChatPhrase.addPhrase("your_minecraft_username_is", "&aYou're Minecraft username is: &r");
		ChatPhrase.addPhrase("your_validation_code_is", "&aYou're validation code is: &r");
		ChatPhrase.addPhrase("your_quartzcore_id_is", "&aYou're QuartzCore id is: &r");
		
		ChatPhrase.addPhrase("no_permission", "&cYou do not have permission to perform this action!");
		ChatPhrase.addPhrase("Unknown_SubCommand", "&cCould not find the specified SubCommand! &aVisit the QuartzCraft Wiki for help.");
		ChatPhrase.addPhrase("Unknown_Command", "&cUnknown Command! &aPlease see the QuartzCraft Wiki for help.");
		ChatPhrase.addPhrase("Specify_Subcommand", "&cPlease specify a SubCommand. &aVisit the QuartzCraft Wiki for help.");
		ChatPhrase.addPhrase("feature_unavalible", "&cThis feature is currently unavalible &aVisit the QuartzCraft Website for information on new features.");
		ChatPhrase.addPhrase("database_error", "&cA database error occured! ");
		ChatPhrase.addPhrase("database_error_contact", "&cA database error occured! &aPlease contact an administrator on the QuartzCraft website immediately.");
		ChatPhrase.addPhrase("player_use_only", "&4This command can only be used ingame by a player.");
		ChatPhrase.addPhrase("specify_username", "&cPlease specify a user!");
		
		ChatPhrase.addPhrase("Server_Full", "&cServer Full!\n &aIt appears that the server is full. Please try again later, or purchase a supporter rank at http://quartzcraft.co.uk/index.php?upgrade");
		ChatPhrase.addPhrase("Kick_Whitelist", "&cYou are not whitelisted");
		
		ChatPhrase.addPhrase("promoted_player_yes", "&aThe player was successfully moved groups!");
		ChatPhrase.addPhrase("promoted_player_no", "&cThe player failed to move groups!");
		
		//Listeners
		log.info("[QC][STARTUP]Registering listeners...");
		//getServer().getPluginManager().registerEvents(new ConnectionListener(this), this);
		new ConnectionListener(this);
		new ChatListener(this);
		
	    //Commands
		log.info("[QC][STARTUP]Registering commands...");
	   	getCommand("quartz").setExecutor(new CommandQuartz());
	   	getCommand("test").setExecutor(new CommandTest());
	   	getCommand("m").setExecutor(new CommandM());
	   	getCommand("report").setExecutor(new CommandReport());
	   	getCommand("promo").setExecutor(new CommandPromo());
	   	getCommand("register").setExecutor(new CommandRegister());
	   	getCommand("qplayer").setExecutor(new CommandQPlayer());
	   	CommandQPlayer.addCommand(Arrays.asList("role"), new QPlayerGroupSubCommand());
	   	
	   	//ChatChannels
	   	//logger.info("[STARTUP]Registering chat channels...");
	   	
	   	//Database
	  	log.info("[QC][STARTUP]Connecting to Database");
	  	DBCore = MySQLcore.openConnection();
	  	DBWeb = MySQLweb.openConnection();
	  		
	   	//Startup notice
	  	log.info("[QC]The QuartzCore Plugin has been enabled!");
	  	log.info("[QC]QuartzCore Version " + release + " " + version);
	}
	
	public static String displayReleaseVersion() {
		String ReleaseVersion = release + version;
		return ReleaseVersion;
	}
}
