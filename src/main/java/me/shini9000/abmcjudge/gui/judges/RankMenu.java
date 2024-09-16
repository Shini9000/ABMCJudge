package me.shini9000.abmcjudge.gui.judges;

import me.shini9000.abmcjudge.gui.Menu;
import me.shini9000.abmcjudge.utils.PlayerMenuUtils;
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

    public RankMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.RED + "Ranks";
    }

    @Override
    public int getSlots() {
        return 18;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        OfflinePlayer ptarget = sqlUtils.getPlayerByID(PlotUtils.getId(p).toString());
        String name = ptarget.getName();

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()) {

            case ORANGE_BANNER -> {
                p.closeInventory();
                p.performCommand("lp user "+name+" parent add novice");
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Novice");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");

            }
            case BLUE_BANNER -> {
                p.closeInventory();
                p.performCommand("lp user "+name+" parent add disciple");
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Disciple");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
            }
            case GREEN_BANNER -> {
                p.closeInventory();
                p.performCommand("lp user "+name+" parent add mentor");
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Mentor");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
            }
            case CYAN_BANNER -> {
                p.closeInventory();
                p.performCommand("lp user "+name+" parent add guru");
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Guru");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
            }
            case PURPLE_BANNER -> {
                p.closeInventory();
                p.performCommand("lp user "+name+" parent add expert");
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Expert");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
            }
            case RED_BANNER -> {
                p.closeInventory();
                p.performCommand("lp user "+name+" parent add master");
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Master");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
            }
        }

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(2, utils.createGuiItem(Material.ORANGE_BANNER,
                ChatColor.GOLD + "Novice", 1));
        inventory.setItem(4, Utils.createGuiItem(Material.BLUE_BANNER,
                ChatColor.BLUE + "Disciple", 1));
        inventory.setItem(6, Utils.createGuiItem(Material.GREEN_BANNER,
                ChatColor.GREEN + "Mentor", 1));
        inventory.setItem(11, Utils.createGuiItem(Material.CYAN_BANNER,
                ChatColor.AQUA + "Guru", 1));
        inventory.setItem(13, Utils.createGuiItem(Material.PURPLE_BANNER,
                ChatColor.DARK_PURPLE + "Expert", 1));
        inventory.setItem(15, Utils.createGuiItem(Material.RED_BANNER,
                ChatColor.RED + "Master", 1));
    }
}