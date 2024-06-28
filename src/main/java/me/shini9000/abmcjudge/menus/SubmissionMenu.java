package me.shini9000.abmcjudge.menus;

import java.util.ArrayList;
import java.util.List;
import me.shini9000.abmcjudge.utils.PlayerMenuUtil;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import me.shini9000.abmcjudge.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SubmissionMenu extends Menu {
    private final SQLUtils sqlUtils = new SQLUtils();

    private final Utils utils = new Utils();

    public SubmissionMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    public String getMenuName() {
        return "" + ChatColor.DARK_GREEN + "Submission";
    }

    public int getSlots() {
        return 9;
    }

    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        List<String> status = new ArrayList<>();
        status.add(this.sqlUtils.getPlotStatus(PlotUtils.getId((OfflinePlayer)p).toString()));
        if (e.getCurrentItem() == null)
            return;
        switch (e.getCurrentItem().getType()) {
            case GREEN_CONCRETE:
                if (!status.contains("ACCEPTED") && !status.contains("PENDING")) {
                    p.sendMessage("" + ChatColor.GOLD + "Plot submitted!");
                    this.sqlUtils.setPlotStatus(PlotUtils.getId((OfflinePlayer)p).toString(), "PENDING");
                    p.closeInventory();
                    break;
                }
                if (status.contains("ACCEPTED")) {
                    p.sendMessage("" + ChatColor.RED + "This plot has already been accepted");
                    break;
                }
                if (status.contains("PENDING"))
                    p.sendMessage("" + ChatColor.RED + "This plot has already been submitted");
                break;
            case RED_CONCRETE:
                (new PlotOverviewMenu(this.playerMenuUtils)).open();
                break;
        }
        e.setCancelled(true);
    }

    public void setMenuItems() {
        this.inventory.setItem(3, Utils.createGuiItem(Material.GREEN_CONCRETE, "" + ChatColor.DARK_GREEN + "Submit Plot", 1, new String[] { "" + ChatColor.GOLD + "Are you sure you want to submit this plot?" }));
        this.inventory.setItem(5, Utils.createGuiItem(Material.RED_CONCRETE, "" + ChatColor.RED + "Cancel", 1, new String[] { "" }));
    }
}
