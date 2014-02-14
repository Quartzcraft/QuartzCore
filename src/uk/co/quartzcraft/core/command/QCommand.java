package uk.co.quartzcraft.core.command;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import uk.co.quartzcraft.core.chat.ChatPhrase;

public abstract class QCommand {
	
	public static void runSubCommand(CommandSender sender, Command cmd, String label, String[] args, HashMap<List<String>, QSubCommand> commands) {
		if(args.length >= 1) {
            boolean match = false; 
            
            for(List<String> s : commands.keySet())
            {
                    if(s.contains(args[0]))
                    {
                            commands.get(s).runCommand(sender, cmd, label, args);
                            match = true;
                    }
            }
            
            if(!match)
            {
                    sender.sendMessage(ChatPhrase.getPhrase("Unknown_SubCommand"));
            }
		} else {
            sender.sendMessage(ChatColor.GREEN + "Please specify a SubCommand. Visit the QuartzCraft Wiki for help.");
		}
	}
}
