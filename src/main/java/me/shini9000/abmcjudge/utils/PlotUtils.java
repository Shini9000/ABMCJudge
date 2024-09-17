package me.shini9000.abmcjudge.utils;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotId;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlotUtils {

    public PlotUtils(){
    }

    public static PlotId getId(OfflinePlayer player){
        PlotPlayer p = BukkitUtil.adapt((Player) player);
        Plot plot = p.getCurrentPlot();

        if (plot == null) return null;
        if (plot.hasOwner()) return plot.getId();

        return null;
    }

    public static String printId(Player player, PlotId id) {
        PlotPlayer p = BukkitUtil.adapt(player);
        World w = player.getWorld();
        Plot plot = p.getCurrentPlot();

        if (id == null) return ChatColor.RED + "You must be in a plot!";
        if (plot.isOwner(p.getUUID())){
            return ChatColor.GRAY + "Plot ID: " + ChatColor.GOLD + w.toString() + ";" + id.toString();
        } else {
            return ChatColor.RED + "You must be in your plot!";
        }
    }
}
