package me.shini9000.abmcjudge.menus.JudgeMenus;

import me.shini9000.abmcjudge.ABMCJudge;
import me.shini9000.abmcjudge.menus.Menu;
import me.shini9000.abmcjudge.utils.PlayerMenuUtil;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class JudgeMenu extends Menu {
    private final Utils utils = new Utils();

    Player p = this.playerMenuUtils.getOwner();

    public JudgeMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    public String getMenuName() {
        return "" + ChatColor.DARK_PURPLE + "Judge Panel";
    }

    public int getSlots() {
        return 9;
    }

    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        boolean exists = false;
        if (PlotUtils.getId((OfflinePlayer)p) != null) {
            UUID uuid = UUID.nameUUIDFromBytes(PlotUtils.getId((OfflinePlayer)p).toString().getBytes());
            exists = (ABMCJudge.getInstance()).data.exists(uuid);
        }
        if (e.getCurrentItem() == null)
            return;
        switch (e.getCurrentItem().getType()) {
            case WRITABLE_BOOK:
                (new SubmittedMenu(this.playerMenuUtils)).open();
                break;
            case PAPER:
                if (PlotUtils.getId((OfflinePlayer)p) != null && exists) {
                    (new JudgePlotInfoMenu(this.playerMenuUtils)).open();
                    break;
                }
                p.sendMessage("" + ChatColor.RED + "You must stand on a submitted plot");
                break;
        }
        e.setCancelled(true);
    }

    public void setMenuItems() {
        String idmsg;
        if (PlotUtils.getId((OfflinePlayer)this.p) != null) {
            idmsg = "Plot ID: " + PlotUtils.getId((OfflinePlayer)this.p).toString();
        } else {
            idmsg = "" + ChatColor.RED + "You must stand on a plot";
        }
        this.inventory.setItem(3, Utils.createGuiItem(Material.WRITABLE_BOOK, "" + ChatColor.YELLOW + "List of submitted plots", 1, new String[] { "" }));
        this.inventory.setItem(5, Utils.createGuiItem(Material.PAPER, "" + ChatColor.YELLOW + "Plot info", 1, new String[] { idmsg }));
    }
}
