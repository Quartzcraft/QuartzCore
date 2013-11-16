package uk.co.quartzcraft.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.quartzcraft.QuartzCore;
import uk.co.quartzcraft.QuartzPlayer;
import uk.co.quartzcraft.chat.Announce;

public class CommandRegister implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("register")){ 
			
			try {
				Statement statement = QuartzCore.DBXen.createStatement();
				
				ResultSet res = statement.executeQuery("SELECT * FROM xf_user_field WHERE Minecraft_Username = '" + sender + "';");
				res.next();
				
				
				return true;
				
			} catch(SQLException err) {
				
				return false;
			}
		}
		return false;
	}

}
