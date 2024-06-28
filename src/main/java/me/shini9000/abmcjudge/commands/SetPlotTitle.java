package me.shini9000.abmcjudge.commands;

import com.plotsquared.bukkit.player.BukkitPlayer;
import com.plotsquared.bukkit.util.BukkitUtil;
import me.shini9000.abmcjudge.ABMCJudge;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetPlotTitle implements CommandExecutor {
    private final SQLUtils sqlUtils = new SQLUtils();

    private ABMCJudge plugin;

    public SetPlotTitle(ABMCJudge plugin) {
        this.plugin = plugin;
        plugin.getCommand("ptitle").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            this.plugin.getConfig().getString("Console.error");
            return true;
        }
        Player player = (Player)sender;
        if (!player.hasPermission("abmcjudge.ptitle")) {
            player.sendMessage("" + ChatColor.GRAY + "Lacking permission: " + ChatColor.GRAY + "abmcjudge.ptitle");
            return true;
        }
        if (PlotUtils.getId((OfflinePlayer)player) == null) {
            player.sendMessage("" + ChatColor.RED + "You must be on your plot");
            return true;
        }
        BukkitPlayer bukkitPlayer = BukkitUtil.adapt(player);
        boolean isOwner = (Bukkit.getOfflinePlayer(bukkitPlayer.getCurrentPlot().getOwner()).getName() == player.getName());
        if (!isOwner) {
            player.sendMessage("" + ChatColor.RED + "You must stand on your plot");
            return true;
        }
        this.sqlUtils.setPlotTable(player);
        if (args.length == 0)
            player.sendMessage("" + ChatColor.GREEN + "Input a title for you plot: /ptitle [title]");
        List<String> title = new ArrayList<>();
        for (String s : args) {
            title.add(s);
            this.sqlUtils.addPlotTitle(PlotUtils.getId((OfflinePlayer)player).toString(), String.join(" ", (Iterable)title));
        }
        player.sendMessage("" + ChatColor.GOLD + "Title updated");
        return true;
    }
}
