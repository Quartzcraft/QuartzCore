package uk.co.quartzcraft.core.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.*;

public abstract class QPlayer {

	public static void sendMessage(CommandSender sender, String message) {
		ChatFormatParser.parseChat(message);
		
		sender.sendMessage(message);
	}
	
	public static ResultSet getData(QPlayer player) {
		
		String playername = player.toString();
		try {
			Statement s = QuartzCore.MySQLcore.openConnection().createStatement();
	        ResultSet res = s.executeQuery("SELECT * FROM PlayerData WHERE DisplayName ='" + playername + "';");
	        res.next();
	        
	        if(res.getString("DisplayName") == playername) {
	        	return res;
	        }
	        
		} catch(SQLException e) {
			Logger.getLogger("Minecraft").log(Level.SEVERE, null, e);
		}
		return null;
		
	}
	
	public abstract HashMap getDataSpecServer();
}
