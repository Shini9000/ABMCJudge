package me.shini9000.abmcjudge.commands;

import me.shini9000.abmcjudge.ABMCJudge;
import me.shini9000.abmcjudge.SQL.MySQL;
import me.shini9000.abmcjudge.menus.JudgeMenus.JudgeMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JudgeCommand implements CommandExecutor {
    private ABMCJudge plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "This command can only be ran by a player!");
            return true;
        }

        Player p = (Player) sender;
        String perm = "abmcjudge.command.judge";
        if (!(p.hasPermission("abmcjudge.command.judge"))) {
            p.sendMessage(ChatColor.RED + "You dont have the permission " + perm);

        } else{
            (new JudgeMenu(ABMCJudge.getPlayerMenuUtil(p))).open();
            p.sendMessage("Judge Test success");
        }

        return true;
    }
}
