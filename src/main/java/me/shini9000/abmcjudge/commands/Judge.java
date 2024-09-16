package me.shini9000.abmcjudge.commands;

import me.shini9000.abmcjudge.ABMCJudge;
import me.shini9000.abmcjudge.gui.judges.JudgeMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Judge implements CommandExecutor {
    private ABMCJudge plugin;

    public Judge(ABMCJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("judge").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            this.plugin.getConfig().getString("Console.error");
            return true;
        }

        Player player = (Player) sender;
        if(!(player.hasPermission("abmcjudge.command.judge"))) {
            player.sendMessage(ChatColor.RED + "Lacking permission: abmcjudge.command.judge");
            return true;
        }

        new JudgeMenu(plugin.getPlayerMenuUtils(player)).open();

        return true;
    }
}