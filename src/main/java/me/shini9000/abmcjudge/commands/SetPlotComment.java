package me.shini9000.abmcjudge.commands;

import com.plotsquared.bukkit.player.BukkitPlayer;
import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.plot.PlotId;
import me.shini9000.abmcjudge.ABMCJudge;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetPlotComment implements CommandExecutor {
    private final SQLUtils sqlUtils = new SQLUtils();

    private ABMCJudge plugin;

    public SetPlotComment(ABMCJudge plugin) {
        this.plugin = plugin;
        plugin.getCommand("pcomment").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> comment, comadd, orcom, addedcom;
        if (!(sender instanceof Player)) {
            this.plugin.getConfig().getString("Console.error");
            return true;
        }
        Player p = (Player)sender;
        if (!p.hasPermission("abmcjudge.pcomment")) {
            p.sendMessage("" + ChatColor.GRAY + "Lacking permission: " + ChatColor.GRAY + "abmcjudge.pcomment");
            return true;
        }
        if (PlotUtils.getId((OfflinePlayer)p) == null) {
            p.sendMessage("" + ChatColor.RED + "You must stand on a plot");
            return true;
        }
        if (!isSubmit(p).booleanValue()) {
            p.sendMessage("" + ChatColor.RED + "This plot is not submitted");
            return true;
        }
        if (args.length == 0) {
            TextComponent comEdit = new TextComponent("" + ChatColor.GREEN + "[Edit comment] ");
            TextComponent comAdd = new TextComponent("" + ChatColor.BLUE + "[Add to comment]");
            comEdit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/pcomment edit "));
            comEdit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("" + ChatColor.GRAY + "Click to edit comment"))
                    .create()));
            comAdd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/pcomment add "));
            comAdd.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("" + ChatColor.GRAY + "Click to add to comment"))
                    .create()));
            p.spigot().sendMessage(new BaseComponent[] { (BaseComponent)comEdit, (BaseComponent)comAdd });
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "edit":
                comment = new ArrayList<>();
                for (String s : args)
                    comment.add(s);
                comment.remove(0);
                this.sqlUtils.addPlotComment(PlotUtils.getId((OfflinePlayer)p).toString(), String.join(" ", (Iterable)comment));
                p.sendMessage("" + ChatColor.GOLD + "Comment updated");
                break;
            case "add":
                comadd = new ArrayList<>();
                orcom = new ArrayList<>();
                orcom.add(this.sqlUtils.getPlotComment(PlotUtils.getId((OfflinePlayer)p).toString()));
                for (String s : args)
                    comadd.add(s);
                comadd.remove(0);
                addedcom = (List<String>) Stream.<List>of(new List[] { orcom, comadd }).flatMap(x -> x.stream()).collect(Collectors.toList());
                this.sqlUtils.addPlotComment(PlotUtils.getId((OfflinePlayer)p).toString(), String.join(" ", (Iterable)addedcom));
                p.sendMessage("" + ChatColor.GOLD + "Comment updated");
                break;
        }
        return true;
    }

    public Boolean isSubmit(Player p) {
        BukkitPlayer bukkitPlayer = BukkitUtil.adapt(p);
        PlotId id = bukkitPlayer.getCurrentPlot().getId();
        if (this.sqlUtils.getSubmittedPlotID().contains(id.toString()))
            return Boolean.valueOf(true);
        return Boolean.valueOf(false);
    }
}
