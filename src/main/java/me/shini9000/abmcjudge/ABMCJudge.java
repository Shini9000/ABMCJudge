package me.shini9000.abmcjudge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ABMCJudge extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("Plugin " + getPluginMeta().getName() + " loading");
        Bukkit.getConsoleSender().sendMessage("Plugin version: " + getPluginMeta().getVersion());
        saveDefaultConfig();


        Bukkit.getConsoleSender().sendMessage("Plugin " + getPluginMeta().getName() + " loaded");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("Plugin " + getPluginMeta().getName() + " shutting down");

        Bukkit.getConsoleSender().sendMessage("Plugin " + getPluginMeta().getName() + " shutdown");
    }
}
