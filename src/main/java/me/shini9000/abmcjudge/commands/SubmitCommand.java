package me.shini9000.abmcjudge.commands;

import com.plotsquared.bukkit.player.BukkitPlayer;
import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.plot.PlotId;
import me.shini9000.abmcjudge.ABMCJudge;
import me.shini9000.abmcjudge.menus.PlotOverviewMenu;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubmitCommand implements CommandExecutor {
    private final SQLUtils sqlUtils = new SQLUtils();
    private ABMCJudge plugin;

    public SubmitCommand(ABMCJudge plugin) {
        this.plugin = plugin;
        plugin.getCommand("submit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            this.plugin.getConfig().getString("Console.error");
            return true;
        }
        Player player = (Player)sender;
        if (!player.hasPermission("abmcjudge.command.submit")) {
            player.sendMessage("" + ChatColor.GRAY + "Lacking permission: " + ChatColor.GRAY + "abmcjudge.command.submit");
            return true;
        }
        PlotId id = PlotUtils.getId((OfflinePlayer)player);
        if (id == null) {
            player.sendMessage("" + ChatColor.RED + "You must stand on your plot");
            return true;
        }
        BukkitPlayer bukkitPlayer = BukkitUtil.adapt(player);
        boolean isOwner = (Bukkit.getOfflinePlayer(bukkitPlayer.getCurrentPlot().getOwner()).getName() == player.getName());
        if (!isOwner) {
            player.sendMessage("" + ChatColor.RED + "You must stand on your plot");
            return true;
        }
        this.sqlUtils.setPlotTable(player);
        this.sqlUtils.addPlotID(id.toString());
        (new PlotOverviewMenu(ABMCJudge.getPlayerMenuUtil(player))).open();
        return true;
    }
}