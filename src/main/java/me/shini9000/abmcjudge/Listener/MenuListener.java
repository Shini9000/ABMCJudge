package me.shini9000.abmcjudge.Listener;

import me.shini9000.abmcjudge.ABMCJudge;
import me.shini9000.abmcjudge.menus.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

public class MenuListener implements Listener {
    private ABMCJudge plugin;

    public MenuListener(ABMCJudge plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, (Plugin)plugin);
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null)
                return;
            Menu menu = (Menu)holder;
            menu.handleMenu(e);
        }
    }
}
