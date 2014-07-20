package uk.co.quartzcraft.core.util;

import org.bukkit.Bukkit;
import uk.co.quartzcraft.core.QuartzCore;

public class BukkitUtil {

    public static void runTaskNextTick(Runnable r) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(QuartzCore.plugin, new Runnable() { //TODO Fix actual instance
            public void run() {
                // Your code goes here
            }
        }, 1L); //This is the delay, in ticks, until the thread is executed, since the main threads ticks 20 times per second, 60 ticks is 3 seconds.
    }
}
