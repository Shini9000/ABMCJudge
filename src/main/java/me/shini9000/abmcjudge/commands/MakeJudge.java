package me.shini9000.abmcjudge.commands;

import me.shini9000.abmcjudge.ABMCJudge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MakeJudge implements CommandExecutor {
    private ABMCJudge plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;
        if (sender instanceof Player){
            Player tp = Bukkit.getPlayerExact(args[0]);
            if(!(tp.isOnline())) {
                p.sendMessage(ChatColor.GOLD + "The player " + tp.getName() + " is offline but will be promoted.");
                //Promote target player
            } else {
                p.sendMessage(ChatColor.GOLD + "Player " + tp.getName() + " was made a judge!");
                tp.sendMessage(ChatColor.GOLD + "You were promoted to Judge by " + p.getName());
                // promote target player
            }
        }
        return true;
    }
}

