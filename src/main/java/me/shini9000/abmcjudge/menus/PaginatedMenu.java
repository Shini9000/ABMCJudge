package me.shini9000.abmcjudge.menus;

import me.shini9000.abmcjudge.utils.PlayerMenuUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public abstract class PaginatedMenu extends Menu {
    protected int page = 0;

    protected int maxItemsPerPage = 28;

    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    public void addMenuBorder() {
        this.inventory.setItem(48, makeItem(Material.DARK_OAK_BUTTON, "" + ChatColor.GREEN + "Left", new String[0]));
        this.inventory.setItem(49, makeItem(Material.BARRIER, "" + ChatColor.DARK_RED + "Close", new String[0]));
        this.inventory.setItem(50, makeItem(Material.DARK_OAK_BUTTON, "" + ChatColor.GREEN + "Right", new String[0]));
        int i;
        for (i = 0; i < 10; i++) {
            if (this.inventory.getItem(i) == null)
                this.inventory.setItem(i, this.FILLER_GLASS);
        }
        this.inventory.setItem(17, this.FILLER_GLASS);
        this.inventory.setItem(18, this.FILLER_GLASS);
        this.inventory.setItem(26, this.FILLER_GLASS);
        this.inventory.setItem(27, this.FILLER_GLASS);
        this.inventory.setItem(35, this.FILLER_GLASS);
        this.inventory.setItem(36, this.FILLER_GLASS);
        for (i = 44; i < 54; i++) {
            if (this.inventory.getItem(i) == null)
                this.inventory.setItem(i, this.FILLER_GLASS);
        }
    }

    public int getMaxItemsPerPage() {
        return this.maxItemsPerPage;
    }
}
