package uk.co.quartzcraft.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class QSubCommand {
	
        public abstract String getPermission();
        
        public boolean needsPlayer()
        {
                return true;
        }
        
        public abstract void onCommand(CommandSender sender, Command cmd, String label, String[] args);

        public boolean runCommand(CommandSender sender, Command cmd, String label, String[] args)
        {
                if(!sender.hasPermission(getPermission()))
                {
                        sender.sendMessage("Unknown Command. Visit the QuartzCraft Wiki for help.");
                }
                else
                {
                        if(needsPlayer())
                        {
                                if(sender instanceof Player)
                                {
                                        onCommand(sender, cmd, label, args);
                                }
                        }
                        else
                        {
                                onCommand(sender, cmd, label, args);
                        }
                }
                
                return true;
        }       
}
