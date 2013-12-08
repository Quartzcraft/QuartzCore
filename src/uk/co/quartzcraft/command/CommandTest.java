package uk.co.quartzcraft.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.quartzcraft.database.*;
import uk.co.quartzcraft.QuartzCore;
import uk.co.quartzcraft.chat.Announce;

public class CommandTest implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("test")){ 
			
			Announce.announce("&4This is a test announcement!");
			Announce.announceWithPrefix("QuartzCraft", "&a", "&6The test command has been run!");
			
			//run database query
			
			//String String = "player";
			
			try {
				//Statement s = MySQL.open().createStatement();
				Statement statement = QuartzCore.DBXen.createStatement();
				
				ResultSet res = statement.executeQuery("SELECT * FROM xf_user_field WHERE Minecraft_Username = '" + sender + "';");
				res.next();
				Announce.announceWithPrefix("Database", "&5", "&bThe database retrived data! The release version is:" + QuartzCore.release);
				
				return true;
				
			} catch(SQLException err) {
				Announce.announceWithPrefix("Database", "&5", "&bSome sort of error occured... The release version is:" + QuartzCore.release);
				return false;
			}
			
		}
		return false;
	}
}
