package me.shini9000.abmcjudge.menus.JudgeMenus;


import java.util.ArrayList;
import java.util.List;
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

public class JudgePlotInfoMenu extends Menu {
    private final SQLUtils sqlUtils = new SQLUtils();

    private final Utils utils = new Utils();

    Player p = this.playerMenuUtils.getOwner();

    public JudgePlotInfoMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    public String getMenuName() {
        return "" + ChatColor.DARK_BLUE + "Plot info";
    }

    public int getSlots() {
        return 9;
    }

    public void handleMenu(InventoryClickEvent e) {
        this.p = (Player)e.getWhoClicked();
        List<String> status = new ArrayList<>();
        status.add(this.sqlUtils.getPlotStatus(PlotUtils.getId((OfflinePlayer)this.p).toString()));
        if (e.getCurrentItem() == null)
            return;
        switch (e.getCurrentItem().getType()) {
            case PAPER:
                this.p.closeInventory();
                this.p.performCommand("pcomment");
                break;
            case REDSTONE_TORCH:
                if (status.contains("PENDING")) {
                    (new RankMenu(this.playerMenuUtils)).open();
                    break;
                }
                if (status.contains("NOT SUBMITTED")) {
                    this.p.sendMessage("" + ChatColor.RED + "This plot has not been submitted");
                    break;
                }
                if (status.contains("ACCEPTED")) {
                    this.p.sendMessage("" + ChatColor.RED + "This plot has already been accepted");
                    break;
                }
                if (status.contains("DENIED"))
                    this.p.sendMessage("" + ChatColor.RED + "This plot has already been denied");
                break;
            case RED_CONCRETE:
                if (status.contains("PENDING")) {
                    (new DenyMenu(this.playerMenuUtils)).open();
                    break;
                }
                if (status.contains("NOT SUBMITTED")) {
                    this.p.sendMessage("" + ChatColor.RED + "This plot has not been submitted");
                    break;
                }
                if (status.contains("ACCEPTED")) {
                    this.p.sendMessage("" + ChatColor.RED + "This plot has already been accepted");
                    break;
                }
                if (status.contains("DENIED"))
                    this.p.sendMessage("" + ChatColor.RED + "This plot has already been denied");
                break;
        }
        e.setCancelled(true);
    }

    public void setMenuItems() {
        OfflinePlayer player = this.sqlUtils.getPlayerByID(PlotUtils.getId((OfflinePlayer)this.p).toString());
        this.inventory.setItem(0, this.utils.getHead(player, PlotUtils.getId((OfflinePlayer)this.p).toString()));
        this.inventory.setItem(1, Utils.createGuiItem(Material.REDSTONE_TORCH, "" + ChatColor.GREEN + "Set rank", 1, new String[] { "" + ChatColor.YELLOW + "Accept Plot" }));
        this.inventory.setItem(3, Utils.createGuiItem(Material.OAK_SIGN, "" + ChatColor.GOLD + "Title:", 1, new String[] { this.sqlUtils

                .getPlotTitle(PlotUtils.getId((OfflinePlayer)this.p).toString()) }));
        this.inventory.setItem(5, Utils.createGuiItem(Material.WRITABLE_BOOK, "" + ChatColor.GOLD + "Lore:", 1, new String[] { this.sqlUtils

                .getPlotLore(PlotUtils.getId((OfflinePlayer)this.p).toString()) }));
        this.inventory.setItem(7, Utils.createGuiItem(Material.PAPER, "" + ChatColor.GRAY + "Comments:", 1, new String[] { this.sqlUtils

                .getPlotComment(PlotUtils.getId((OfflinePlayer)this.p).toString()) }));
        this.inventory.setItem(8, Utils.createGuiItem(Material.RED_CONCRETE, "" + ChatColor.RED + "Deny plot", 1, new String[0]));
    }
}
