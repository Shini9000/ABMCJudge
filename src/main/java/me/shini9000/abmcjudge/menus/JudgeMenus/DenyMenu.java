package me.shini9000.abmcjudge.menus.JudgeMenus;

import me.shini9000.abmcjudge.menus.Menu;
import me.shini9000.abmcjudge.utils.PlayerMenuUtil;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import me.shini9000.abmcjudge.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DenyMenu extends Menu {
    private final Utils utils = new Utils();

    private final SQLUtils sqlUtils = new SQLUtils();

    public DenyMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    public String getMenuName() {
        return "" + ChatColor.DARK_RED + "Deny plot";
    }

    public int getSlots() {
        return 9;
    }

    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (e.getCurrentItem() == null)
            return;
        switch (e.getCurrentItem().getType()) {
            case GREEN_CONCRETE:
                p.closeInventory();
                p.sendMessage("" + ChatColor.GOLD + "Plot Denied!");
                this.sqlUtils.setPlotStatus(PlotUtils.getId((OfflinePlayer)p).toString(), "DENIED");
                break;
            case RED_CONCRETE:
                (new JudgePlotInfoMenu(this.playerMenuUtils)).open();
                break;
        }
        e.setCancelled(true);
    }

    public void setMenuItems() {
        this.inventory.setItem(3, Utils.createGuiItem(Material.GREEN_CONCRETE, "" + ChatColor.GREEN + "Confirm", 1, new String[0]));
        this.inventory.setItem(5, Utils.createGuiItem(Material.RED_CONCRETE, "" + ChatColor.RED + "Cancel", 1, new String[0]));
    }
}
