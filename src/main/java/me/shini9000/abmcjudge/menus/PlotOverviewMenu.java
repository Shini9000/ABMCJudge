package me.shini9000.abmcjudge.menus;

import me.shini9000.abmcjudge.utils.PlayerMenuUtil;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import me.shini9000.abmcjudge.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlotOverviewMenu extends Menu {
    private final SQLUtils sqlUtils = new SQLUtils();

    private final Utils utils = new Utils();

    public PlotOverviewMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    public String getMenuName() {
        return "" + ChatColor.AQUA + "Plot Overview";
    }

    public int getSlots() {
        return 9;
    }

    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (e.getCurrentItem() == null)
            return;
        switch (e.getCurrentItem().getType()) {
            case WRITABLE_BOOK:
                (new PlayerPlotsMenu(this.playerMenuUtils)).open();
                break;
            case PAPER:
                if (PlotUtils.getId((OfflinePlayer)p) != null) {
                    (new PlotInfo(this.playerMenuUtils)).open();
                    break;
                }
                p.sendMessage("" + ChatColor.RED + "This is not your plot");
                break;
            case GREEN_CONCRETE:
                if (PlotUtils.getId((OfflinePlayer)p) != null) {
                    (new SubmissionMenu(this.playerMenuUtils)).open();
                    break;
                }
                p.sendMessage("" + ChatColor.RED + "This is not your plot");
                break;
        }
        e.setCancelled(true);
    }

    public void setMenuItems() {
        this.inventory.setItem(0, this.utils.getHead(this.playerMenuUtils.getOwner(), new String[0]));
        this.inventory.setItem(3, Utils.createGuiItem(Material.WRITABLE_BOOK, "" + ChatColor.YELLOW + "List of your submitted plots", 1, new String[0]));
        if (PlotUtils.getId((OfflinePlayer)this.playerMenuUtils.getOwner()) != null) {
            this.inventory.setItem(5, Utils.createGuiItem(Material.PAPER, "" + ChatColor.YELLOW + "Plot Submission Info", 1, new String[] { "Title: " + this.sqlUtils

                    .getPlotTitle(PlotUtils.getId((OfflinePlayer)this.playerMenuUtils.getOwner()).toString()) }));
        } else {
            this.inventory.setItem(5, Utils.createGuiItem(Material.PAPER, "" + ChatColor.YELLOW + "Plot Submission Info", 1, new String[] { "You must be on your plot" }));
        }
        this.inventory.setItem(8, Utils.createGuiItem(Material.GREEN_CONCRETE, "" + ChatColor.DARK_GREEN + "Submit Plot", 1, new String[] { "" }));
    }
}