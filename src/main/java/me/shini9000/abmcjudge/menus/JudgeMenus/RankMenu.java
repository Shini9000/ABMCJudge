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

public class RankMenu extends Menu {
    private final Utils utils = new Utils();

    private final SQLUtils sqlUtils = new SQLUtils();

    public RankMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    public String getMenuName() {
        return "" + ChatColor.RED + "Ranks";
    }

    public int getSlots() {
        return 18;
    }

    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        OfflinePlayer ptarget = this.sqlUtils.getPlayerByID(PlotUtils.getId((OfflinePlayer)p).toString());
        String name = ptarget.getName();
        if (e.getCurrentItem() == null)
            return;
        switch (e.getCurrentItem().getType()) {
            case ORANGE_BANNER:
                p.closeInventory();
                p.performCommand("lp user " + name + " parent add novice");
                p.sendMessage("" + ChatColor.GOLD + "Plot Accepted! Rank up to Novice");
                this.sqlUtils.setPlotStatus(PlotUtils.getId((OfflinePlayer)p).toString(), "ACCEPTED");
                break;
            case BLUE_BANNER:
                p.closeInventory();
                p.performCommand("lp user " + name + " parent add disciple");
                p.sendMessage("" + ChatColor.GOLD + "Plot Accepted! Rank up to Disciple");
                this.sqlUtils.setPlotStatus(PlotUtils.getId((OfflinePlayer)p).toString(), "ACCEPTED");
                break;
            case GREEN_BANNER:
                p.closeInventory();
                p.performCommand("lp user " + name + " parent add mentor");
                p.sendMessage("" + ChatColor.GOLD + "Plot Accepted! Rank up to Mentor");
                this.sqlUtils.setPlotStatus(PlotUtils.getId((OfflinePlayer)p).toString(), "ACCEPTED");
                break;
            case CYAN_BANNER:
                p.closeInventory();
                p.performCommand("lp user " + name + " parent add guru");
                p.sendMessage("" + ChatColor.GOLD + "Plot Accepted! Rank up to Guru");
                this.sqlUtils.setPlotStatus(PlotUtils.getId((OfflinePlayer)p).toString(), "ACCEPTED");
                break;
            case PURPLE_BANNER:
                p.closeInventory();
                p.performCommand("lp user " + name + " parent add expert");
                p.sendMessage("" + ChatColor.GOLD + "Plot Accepted! Rank up to Expert");
                this.sqlUtils.setPlotStatus(PlotUtils.getId((OfflinePlayer)p).toString(), "ACCEPTED");
                break;
            case RED_BANNER:
                p.closeInventory();
                p.performCommand("lp user " + name + " parent add master");
                p.sendMessage("" + ChatColor.GOLD + "Plot Accepted! Rank up to Master");
                this.sqlUtils.setPlotStatus(PlotUtils.getId((OfflinePlayer)p).toString(), "ACCEPTED");
                break;
        }
        e.setCancelled(true);
    }

    public void setMenuItems() {
        this.inventory.setItem(2, Utils.createGuiItem(Material.ORANGE_BANNER, "" + ChatColor.GOLD + "Novice", 1, new String[0]));
        this.inventory.setItem(4, Utils.createGuiItem(Material.BLUE_BANNER, "" + ChatColor.BLUE + "Disciple", 1, new String[0]));
        this.inventory.setItem(6, Utils.createGuiItem(Material.GREEN_BANNER, "" + ChatColor.GREEN + "Mentor", 1, new String[0]));
        this.inventory.setItem(11, Utils.createGuiItem(Material.CYAN_BANNER, "" + ChatColor.AQUA + "Guru", 1, new String[0]));
        this.inventory.setItem(13, Utils.createGuiItem(Material.PURPLE_BANNER, "" + ChatColor.DARK_PURPLE + "Expert", 1, new String[0]));
        this.inventory.setItem(15, Utils.createGuiItem(Material.RED_BANNER, "" + ChatColor.RED + "Master", 1, new String[0]));
    }
}
