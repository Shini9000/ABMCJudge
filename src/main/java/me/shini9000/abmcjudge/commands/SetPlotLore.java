package me.shini9000.abmcjudge.commands;

import me.shini9000.abmcjudge.ABMCJudge;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import com.plotsquared.bukkit.player.BukkitPlayer;
import com.plotsquared.bukkit.util.BukkitUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetPlotLore implements CommandExecutor {
    private final SQLUtils sqlUtils = new SQLUtils();

    private ABMCJudge plugin;

    public SetPlotLore(ABMCJudge plugin) {
        this.plugin = plugin;
        plugin.getCommand("plore").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> lore, loreadd, orlore, addedlore;
        if (!(sender instanceof Player)) {
            this.plugin.getConfig().getString("Console.error");
            return true;
        }
        Player player = (Player)sender;
        if (!player.hasPermission("abmcjudge.plore")) {
            player.sendMessage("" + ChatColor.GRAY + "Lacking permission: " + ChatColor.GRAY + "abmcjudge.plore");
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
        if (args.length == 0) {
            TextComponent loreEdit = new TextComponent("" + ChatColor.GREEN + "[Edit plot lore] ");
            TextComponent loreAdd = new TextComponent("" + ChatColor.BLUE + "[Add to lore]");
            loreEdit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plore edit "));
            loreEdit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("" + ChatColor.GRAY + "Click to edit lore"))
                    .create()));
            loreAdd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plore add "));
            loreAdd.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("" + ChatColor.GRAY + "Click to add to lore"))
                    .create()));
            player.spigot().sendMessage(new BaseComponent[] { (BaseComponent)loreEdit, (BaseComponent)loreAdd });
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "edit":
                lore = new ArrayList<>();
                for (String s : args)
                    lore.add(s);
                lore.remove(0);
                this.sqlUtils.addPlotLore(PlotUtils.getId((OfflinePlayer)player).toString(), String.join(" ", (Iterable)lore));
                player.sendMessage("" + ChatColor.GOLD + "Lore updated");
                break;
            case "add":
                loreadd = new ArrayList<>();
                orlore = new ArrayList<>();
                orlore.add(this.sqlUtils.getPlotLore(PlotUtils.getId((OfflinePlayer)player).toString()));
                for (String s : args)
                    loreadd.add(s);
                loreadd.remove(0);
                addedlore = (List<String>)Stream.<List>of(new List[] { orlore, loreadd }).flatMap(x -> x.stream()).collect(Collectors.toList());
                this.sqlUtils.addPlotLore(PlotUtils.getId((OfflinePlayer)player).toString(), String.join(" ", (Iterable)addedlore));
                player.sendMessage("" + ChatColor.GOLD + "Lore updated");
                break;
        }
        return true;
    }
}
