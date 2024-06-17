package me.shini9000.abmcjudge;

import me.shini9000.abmcjudge.commands.JudgeCommand;
import me.shini9000.abmcjudge.commands.MakeJudge;
import me.shini9000.abmcjudge.commands.SubmitCommand;
import me.shini9000.abmcjudge.utils.PlayerMenuUtil;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ABMCJudge extends JavaPlugin implements Listener {

    public LuckPerms luckPerms;
    private static final HashMap<Player, PlayerMenuUtil> playerMenuUtilMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("Plugin " + getPluginMeta().getName() + " loading");
        Bukkit.getConsoleSender().sendMessage("Plugin version: " + getPluginMeta().getVersion());
        //saveDefaultConfig();
        getCommand("judge").setExecutor(new JudgeCommand());
        getCommand("submit").setExecutor(new SubmitCommand());
        getCommand("mkjudge").setExecutor(new MakeJudge());

        Bukkit.getConsoleSender().sendMessage("Plugin " + getPluginMeta().getName() + " loaded");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("Plugin " + getPluginMeta().getName() + " shutting down");

        Bukkit.getConsoleSender().sendMessage("Plugin " + getPluginMeta().getName() + " shutdown");
    }

    public static PlayerMenuUtil getPlayerMenuUtil(Player p) {
        PlayerMenuUtil playerMenuUtil;
        if (playerMenuUtilMap.containsKey(p)) {
            return playerMenuUtilMap.get(p);
        } else {
            playerMenuUtil = new PlayerMenuUtil(p);
            playerMenuUtilMap.put(p, playerMenuUtil);
            return playerMenuUtil;
        }
    }
}
