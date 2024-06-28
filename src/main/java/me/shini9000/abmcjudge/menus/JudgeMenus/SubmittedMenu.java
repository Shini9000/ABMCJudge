package me.shini9000.abmcjudge.menus.JudgeMenus;

import java.util.List;
import me.shini9000.abmcjudge.menus.PaginatedMenu;
import me.shini9000.abmcjudge.utils.PlayerMenuUtil;
import me.shini9000.abmcjudge.utils.SQLUtils;
import me.shini9000.abmcjudge.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SubmittedMenu extends PaginatedMenu {
    private final SQLUtils sqlUtils = new SQLUtils();

    private final Utils utils = new Utils();

    public SubmittedMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    public String getMenuName() {
        return "Submitted Plots";
    }

    public int getSlots() {
        return 54;
    }

    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        List<String> plots = this.sqlUtils.getSubmittedPlotID();
        if (e.getCurrentItem() == null)
            return;
        switch (e.getCurrentItem().getType()) {
            case PLAYER_HEAD:
                p.performCommand("p v " + String.join("", new CharSequence[] { e.getCurrentItem().getItemMeta().getLore().get(0) }));
                break;
            case BARRIER:
                p.closeInventory();
                break;
            case DARK_OAK_BUTTON:
                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")) {
                    if (this.page == 0) {
                        p.sendMessage("" + ChatColor.GRAY + "You are already on the first page.");
                        break;
                    }
                    this.page--;
                    open();
                    break;
                }
                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")) {
                    if (this.index + 1 < plots.size()) {
                        this.page++;
                        open();
                        break;
                    }
                    p.sendMessage("" + ChatColor.GRAY + "You are on the last page.");
                }
                break;
        }
    }

    public void setMenuItems() {
        addMenuBorder();
        List<String> plots = this.sqlUtils.getSubmittedPlotID();
        if (plots == null && plots.isEmpty())
            return;
        for (int i = 0; i < this.maxItemsPerPage; i++) {
            this.index = this.maxItemsPerPage * this.page + i;
            if (this.index >= plots.size())
                break;
            if (plots.get(this.index) != null) {
                OfflinePlayer p = this.sqlUtils.getPlayerByID(plots.get(this.index));
                this.inventory.addItem(new ItemStack[] { this.utils.getHead(p, plots.get(this.index)) });
            }
        }
    }
}
