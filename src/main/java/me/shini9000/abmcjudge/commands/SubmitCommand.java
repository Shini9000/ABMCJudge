package me.shini9000.abmcjudge.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubmitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "This command can only be ran by a player!");
            return true;
        }

        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage("Submit Test success");

        }
        return false;
    }
}
