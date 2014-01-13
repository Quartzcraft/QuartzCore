package uk.co.quartzcraft.core.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import uk.co.quartzcraft.core.database.*;
import uk.co.quartzcraft.core.entity.QPlayer;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.Announce;
import uk.co.quartzcraft.core.chat.ChatPhrase;

public class CommandTest implements CommandExecutor {
	
	public Plugin plugin = this.plugin;

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("test")){ 
			
			Announce.announce("&4This is a test announcement!");
			Announce.announceWithPrefix("QuartzCraft", "&a", "&6The test command has been run!");
			QPlayer.sendMessage(sender, ChatPhrase.getPhrase("test_phrase"));
			Player player = (Player)sender;
			
			UUID UUID = player.getUniqueId();
			
			//run database query
			
			try {
		        Statement s = QuartzCore.MySQLcore.openConnection().createStatement();
		        ResultSet res = s.executeQuery("SELECT * FROM PlayerData WHERE DisplayName ='mba2012';");
		        res.next();
		        //sender.sendMessage("Hello, " + res.getString("DisplayName"));
		        if (res.getString("DisplayName") == null) {
		        	sender.sendMessage(":/");
		        } else {
		        	sender.sendMessage("Hello, " + res.getString("DisplayName"));
		        	return true;
		        }
		    } catch (SQLException ex) {
		        Logger.getLogger("Minecraft").log(Level.SEVERE, null, ex);
		    }
		    
		    
			
			//String String = "player";
			
		}
		return true;
	}
}
