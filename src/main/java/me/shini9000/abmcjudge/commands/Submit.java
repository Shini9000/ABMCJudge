package me.shini9000.abmcjudge.commands;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.PlotId;
import me.shini9000.abmcjudge.ABMCJudge;
import me.shini9000.abmcjudge.gui.PlotOverviewMenu;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Submit implements CommandExecutor {

    private final SQLUtils sqlUtils = new SQLUtils();
    private ABMCJudge plugin;

    public Submit(ABMCJudge plugin) {
        this.plugin = plugin;
        plugin.getCommand("submit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if (!(sender instanceof Player)) { this.plugin.getConfig().getString("Console.error"); return true;}

        Player player = (Player) sender;
        if(!player.hasPermission("abmcjudge.command.submit")) {
            player.sendMessage(ChatColor.RED + "Lacking permission: abmcjudge.command.submit");
            return true;
        }

        PlotId id = PlotUtils.getId(player);

        if(id == null) {
            player.sendMessage(ChatColor.RED + "You must stand on your plot");
            return true;
        }

        PlotPlayer p = BukkitUtil.adapt(player);

        boolean isOwner = Bukkit.getOfflinePlayer(p.getCurrentPlot().getOwner()).getName() == player.getName();

        if (!isOwner) {
            player.sendMessage(ChatColor.RED + "You must stand on your plot");
            return true;
        }

        sqlUtils.setPlotTable(player);
        sqlUtils.addPlotID(id.toString());

        new PlotOverviewMenu(ABMCJudge.getPlayerMenuUtils(player)).open();
        return true;
    }

}