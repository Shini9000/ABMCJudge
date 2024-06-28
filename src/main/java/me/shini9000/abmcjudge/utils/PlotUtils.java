package me.shini9000.abmcjudge.utils;

import com.plotsquared.bukkit.player.BukkitPlayer;
import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotId;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlotUtils {
    public static PlotId getId(OfflinePlayer player) {
        BukkitPlayer bukkitPlayer = BukkitUtil.adapt((Player)player);
        Plot plot = bukkitPlayer.getCurrentPlot();
        if (plot == null)
            return null;
        if (plot.hasOwner())
            return plot.getId();
        return null;
    }

    public static String printId(Player player, PlotId id) {
        BukkitPlayer bukkitPlayer = BukkitUtil.adapt(player);
        Plot plot = bukkitPlayer.getCurrentPlot();
        if (id == null)
            return "You must be at your plot";
        if (plot.isOwner(bukkitPlayer.getUUID()))
            return "Plot ID: " + id.toString();
        return "You must be at your plot";
    }
}
