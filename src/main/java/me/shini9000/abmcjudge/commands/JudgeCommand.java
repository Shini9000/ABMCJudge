package me.shini9000.abmcjudge.commands;

import me.shini9000.abmcjudge.ABMCJudge;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JudgeCommand implements CommandExecutor {
    private ABMCJudge plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        if (!(sender instanceof Player)) {
            this.plugin.getConfig().getString("Console.error");
            return true;
        }

        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage("Judge Test success");


        }
        return true;
    }
}
