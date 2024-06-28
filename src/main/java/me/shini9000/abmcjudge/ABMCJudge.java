package me.shini9000.abmcjudge;

import java.sql.SQLException;
import java.util.HashMap;

import me.shini9000.abmcjudge.Listener.MenuListener;
import me.shini9000.abmcjudge.SQL.MySQL;
import me.shini9000.abmcjudge.SQL.SQLCreate;
import me.shini9000.abmcjudge.commands.*;
import me.shini9000.abmcjudge.utils.LPUtils;
import me.shini9000.abmcjudge.utils.PlayerMenuUtil;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ABMCJudge extends JavaPlugin implements Listener {
    public SQLCreate data;
    public MySQL SQL;
    public LuckPerms luckPerms;

    private static final HashMap<Player, PlayerMenuUtil> playerMenuUtilMap = new HashMap<>();

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("Enabling ABMCJudge!");
        Bukkit.getConsoleSender().sendMessage("Version: " + getPluginMeta().getVersion());
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        new SubmitCommand(this);
        new JudgeCommand(this);
        new SetPlotTitle(this);
        new SetPlotLore(this);
        new SetPlotComment(this);
        new MenuListener(this);
        this.luckPerms = (LuckPerms)getServer().getServicesManager().load(LuckPerms.class);
        new LPUtils(this.luckPerms);
        this.SQL = new MySQL();
        this.data = new SQLCreate(this);
        try {
            this.SQL.openConnection();
        } catch (ClassNotFoundException|SQLException e) {
            Bukkit.getLogger().info("Database not connected.");
        }
        try {
            if (this.SQL.checkConnection()) {
                Bukkit.getLogger().info("Database is connected.");
                this.data.createTable();
                getServer().getPluginManager().registerEvents(this, (Plugin)this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("Disabling abmcjudge!");
        try {
            this.SQL.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ABMCJudge getInstance() {
        return (ABMCJudge) getPlugin(ABMCJudge.class);
    }

    public static PlayerMenuUtil getPlayerMenuUtil(Player p) {
        if (!playerMenuUtilMap.containsKey(p)) {
            PlayerMenuUtil playerMenuUtil = new PlayerMenuUtil(p);
            playerMenuUtilMap.put(p, playerMenuUtil);
            return playerMenuUtil;
        }
        return playerMenuUtilMap.get(p);
    }
}
