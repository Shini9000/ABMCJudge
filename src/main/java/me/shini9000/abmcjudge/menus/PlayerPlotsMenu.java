package me.shini9000.abmcjudge.menus;

import me.shini9000.abmcjudge.utils.PlayerMenuUtil;
import me.shini9000.abmcjudge.utils.SQLUtils;
import me.shini9000.abmcjudge.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerPlotsMenu extends Menu {
    private final SQLUtils sqlUtils = new SQLUtils();

    Player p = this.playerMenuUtils.getOwner();

    public PlayerPlotsMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    public String getMenuName() {
        return "" + ChatColor.DARK_BLUE + "Submitted plots";
    }

    public int getSlots() {
        return 9;
    }

    public void handleMenu(InventoryClickEvent e) {
        this.p = (Player)e.getWhoClicked();
        if (e.getCurrentItem() == null)
            return;
        if (e.getCurrentItem().getType().equals(Material.SPRUCE_SIGN))
            this.p.performCommand("p v " + e.getCurrentItem().getItemMeta().getDisplayName());
        e.setCancelled(true);
    }

    public void setMenuItems() {
        for (int i = 0; i < this.sqlUtils.getSubmittedPlotID(this.p).size(); i++) {
            this.inventory.addItem(new ItemStack[] { Utils.createGuiItem(Material.SPRUCE_SIGN, this.sqlUtils.getSubmittedPlotID(this.p).get(i), 1, new String[0]) });
        }
    }
}
