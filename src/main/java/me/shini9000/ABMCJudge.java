package me.shini9000;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ABMCJudge extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("Enabling plugin: "
                + getDescription().getName()
                + " Version: "
                + getDescription().getVersion()
        );
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}