package uk.co.quartzcraft.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import uk.co.quartzcraft.core.command.*;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.database.MySQL;
import uk.co.quartzcraft.core.features.items.FinalItems;
import uk.co.quartzcraft.core.features.items.SoulboundItems;
import uk.co.quartzcraft.core.features.items.UnbreakableItems;
import uk.co.quartzcraft.core.listeners.*;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.systems.websync.UpdateGroups;
import uk.co.quartzcraft.core.systems.websync.Websync;

/**
 * Core file for QuartzCore plugin.
 */
public class QuartzCore extends JavaPlugin {
	
	public static String version;


    public static Plugin plugin;
    public static int server;
	public static final Logger log = Logger.getLogger("Minecraft");
	
	public static Connection DBCore = null;
    public static Connection DBLog = null;
    
	public static MySQL MySQLcore = null;
    public static MySQL MySQLlog = null;

    public QCommandFramework commandFramework;
	
	@Override
	public void onDisable() {

        //Close database
        log.info("[QC]Closing database connections");
        MySQLcore.closeConnection();
        MySQLlog.closeConnection();
        try {
            DBCore.close();
            DBLog.close();
            log.info("[QC]Successfully closed database connections");
        } catch(SQLException e) {
            log.log(Level.SEVERE,"[QC]Failed to close database connections!");
        }

    	//Shutdown notice
		log.info("[QC]The QuartzCore Plugin has been disabled!");
	}
	@Override
	public void onEnable() {
		
		log.info("[QC][STARTUP LOGGER]Console logger discovered");

        server = this.getConfig().getInt("settings.server-id");
        plugin = this;
        version = plugin.getDescription().getVersion();
		
		//Config files
		log.info("[QC]Running plugin configuration");
		this.saveDefaultConfig();
		boolean DBConnect = true;
		
		if(DBConnect) {
			//Core Database
			String SQLCoreHost = this.getConfig().getString("database.core.host");
			String SQLCoreDatabase = this.getConfig().getString("database.core.database");
			String SQLCoreUser = this.getConfig().getString("database.core.username");
			String SQLCorePassword = this.getConfig().getString("database.core.password");
			MySQLcore = new MySQL(plugin, SQLCoreHost, "3306", SQLCoreDatabase, SQLCoreUser, SQLCorePassword);

            //Logging Database
            String SQLLogHost = this.getConfig().getString("database.log.host");
            String SQLLogDatabase = this.getConfig().getString("database.log.database");
            String SQLLogUser = this.getConfig().getString("database.log.username");
            String SQLLogPassword = this.getConfig().getString("database.log.password");
            MySQLlog = new MySQL(plugin, SQLLogHost, "3306", SQLLogDatabase, SQLLogUser, SQLLogPassword);
		}

        //Websync
        log.info("[QC][STARTUP]Initializing Websync");
        if(this.getConfig().getBoolean("settings.websync.active")) {
            Websync.init();

            log.info("[QC][STARTUP]Performing websync operations");
            UpdateGroups.update();
        }

        //Database
        if(DBConnect) {
            log.info("[QC][STARTUP]Connecting to Database");
            DBCore = MySQLcore.openConnection();
            DBLog = MySQLlog.openConnection();
        }

		//Phrases
		log.info("[QC][STARTUP]Creating Phrases");
		QCChat.addPhrase("test_phrase", "&3This is a test of the phrases system.");
		
		QCChat.addPhrase("could_not_create_player", "&cYou're player data could not be added to the database!");
		QCChat.addPhrase("to_register_on_the_website_please_visit_web", "&aTo register on the QuartzCraft website, please visit &rhttp://quartzcraft.co.uk");
		QCChat.addPhrase("these_are_the_fields_required_for_website_registration", "&aThese are the values for the required website registration fields:");
		QCChat.addPhrase("your_minecraft_username_is", "&aYour Minecraft username is:&r ");
		QCChat.addPhrase("your_validation_code_is", "&aYour validation code is:&r ");
		QCChat.addPhrase("your_quartzcore_id_is", "&aYour QuartzCore id is:&r ");

        QCChat.addPhrase("teleported_you_to_player_X", "&aYou have been teleported to &r ");
        QCChat.addPhrase("X_has_been_teleported_to_you", " &ahas been teleported to you");
		
		QCChat.addPhrase("no_permission", "&cYou do not have permission to perform this action!");
		QCChat.addPhrase("Unknown_SubCommand", "&cCould not find the specified SubCommand! &aVisit the QuartzCraft Wiki for help.");
		QCChat.addPhrase("Unknown_Command", "&cUnknown Command! &aPlease see the QuartzCraft Wiki for help.");
		QCChat.addPhrase("Specify_Subcommand", "&cPlease specify a sub command. &aVisit the QuartzCraft Wiki for help.");
		QCChat.addPhrase("feature_unavalible", "&cThis feature is currently unavailable &aVisit the QuartzCraft Website for information on new features.");
		QCChat.addPhrase("database_error", "&cA database error occurred! ");
		QCChat.addPhrase("database_error_contact", "&cA database error occurred! &aPlease contact an administrator on the QuartzCraft website immediately.");
		QCChat.addPhrase("player_use_only", "&4This command can only be used in game by a player.");
        QCChat.addPhrase("specify_username", "&cPlease specify a player!");
        QCChat.addPhrase("specify_online_username", "&cPlease specify an online player!");
        QCChat.addPhrase("specify_arguments", "&cPlease specify some arguments!");

        QCChat.addPhrase("information_on_player_X", "&aInformation on player: &r");
        QCChat.addPhrase("group", "&aGroup: &r");
        QCChat.addPhrase("first_join", "&aFirst joined: &r");
        QCChat.addPhrase("last_seen", "&aLast online: &r");

        QCChat.addPhrase("please_specify_player_to_report", "&cYou must specify a player to report!");
        QCChat.addPhrase("thank_you_for_reporting_user", "Thank you for submitting a report. It is unlikely that we will need contact you.");
        QCChat.addPhrase("error_submitting_report", "&cAn error occurred while submitting your report! Please try again or contact a QuartzCraft administrator.");

        QCChat.addPhrase("you_are_currently_in_world", "&aYou are currently in world ");
		
		QCChat.addPhrase("Server_Full", "&cServer Full!\n &aIt appears that the server is full. Please try again later, or purchase a supporter rank at http://quartzcraft.co.uk/index.php?upgrade");
		QCChat.addPhrase("Kick_Whitelist", "&cYou are not whitelisted");
		
		QCChat.addPhrase("promoted_player_yes", "&aThe player was successfully moved groups!");
		QCChat.addPhrase("promoted_player_no", "&cThe player failed to move groups!");

        QCChat.addPhrase("could_not_fit_item_dropped", "&cThe item could not fit in your inventory and was dropped on the ground!");

        QCChat.addPhrase("chat_contained_bad_words_blocked", "&cThe chat message you attempted to send contained inappropriate words and was blocked!");
		
		//Listeners
		log.info("[QC][STARTUP]Registering listeners...");
		new ConnectionListener(this);
		new ChatListener(this);
        new UnbreakableItems(this);
        new SoulboundItems(this);
        new FinalItems(this);

	    //Commands
		log.info("[QC][STARTUP]Registering commands...");
        commandFramework = new QCommandFramework(this);
        commandFramework.registerCommands(new CommandTest(this));
        commandFramework.registerCommands(new CommandWorld(this));
        commandFramework.registerCommands(new CommandQCReload(this));
        commandFramework.registerCommands(new CommandAnnounce(this));
        commandFramework.registerCommands(new CommandPromo(this));
        commandFramework.registerCommands(new CommandReport(this));
        commandFramework.registerCommands(new CommandPinfo(this));
        commandFramework.registerCommands(new CommandTP(this));
	   	getCommand("quartz").setExecutor(new CommandQuartz());
	   	getCommand("m").setExecutor(new CommandM());
	   	getCommand("register").setExecutor(new CommandRegister());
	  		
	   	//Startup notice
	  	log.info("[QC]The QuartzCore Plugin has been enabled!");
	  	log.info("[QC]QuartzCore Version " + version);
	}

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return commandFramework.handleCommand(sender, label, command, args);
    }

	public static String displayReleaseVersion() {
		String ReleaseVersion = version;
		return ReleaseVersion;
	}
}
