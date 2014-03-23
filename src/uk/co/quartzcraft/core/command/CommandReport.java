package uk.co.quartzcraft.core.command;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import uk.co.quartzcraft.core.entity.QPlayer;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.ChatPhrase;

import java.sql.SQLException;

public class CommandReport implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg0) {
		
		Player player = (Player) sender;
        OfflinePlayer player2 = Bukkit.getServer().getOfflinePlayer("name");
		
		if(command.getName().equalsIgnoreCase("report")) {
			if(!(player instanceof Player)){
	    		sender.sendMessage(ChatPhrase.getPhrase("player_use_only"));
	    	} else {
	    		if(arg0.length == 0) {
	    			player.sendMessage(ChatPhrase.getPhrase("please_specify_player_to_report"));
	    		} else {
                    try {
                        java.sql.Connection connection = QuartzCore.MySQLcore.openConnection();
                        java.sql.PreparedStatement s = connection.prepareStatement("INSERT INTO Reports (reported_user_id, reporting_user_id, report_content) VALUES (" + QPlayer.getUserID(player2) + ", " + QPlayer.getUserID(player) + ", '" + getReportContent(arg0) + "');");
                        if(s.executeUpdate() == 1) {
                            player.sendMessage(ChatPhrase.getPhrase("thank_you_for_reporting_user"));
                            return true;
                        } else {
                            player.sendMessage(ChatPhrase.getPhrase("error_submitting_report"));
                            return false;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        player.sendMessage(ChatPhrase.getPhrase("database_error_contact"));
                        player.sendMessage(ChatPhrase.getPhrase("error_submitting_report"));
                        return false;
                    }
	    		}
	    	}
		} 
		return true;
	}

    public String getReportContent(String[] args) {
        StringBuilder builder = new StringBuilder();
        for(String s : args) {
            builder.append(s + " ");
        }
        return builder.toString();
    }

}
