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

import java.util.ArrayList;
import java.util.List;

public class JudgePlotInfoMenu extends Menu {

    private final SQLUtils sqlUtils = new SQLUtils();
    private final Utils utils = new Utils();

    Player p = playerMenuUtils.getOwner();

    public JudgePlotInfoMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.DARK_BLUE + "Plot info";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        p = (Player) e.getWhoClicked();
        List<String> status = new ArrayList<>();
        status.add(sqlUtils.getPlotStatus(PlotUtils.getId(p).toString()));

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()) {
            case PAPER -> {
                p.closeInventory();
                p.performCommand("pcomment");
            }
            case REDSTONE_TORCH -> {
                if(status.contains("PENDING")) {
                    new RankMenu(playerMenuUtils).open();
                }
                else if(status.contains("NOT SUBMITTED")) p.sendMessage(ChatColor.RED + "This plot has not been submitted");
                else if(status.contains("ACCEPTED")) p.sendMessage(ChatColor.RED + "This plot has already been accepted");
                else if(status.contains("DENIED")) p.sendMessage(ChatColor.RED + "This plot has already been denied");
            }
            case RED_CONCRETE -> {
                if(status.contains("PENDING")) {
                    new DenyMenu(playerMenuUtils).open();
                }
                else if(status.contains("NOT SUBMITTED")) p.sendMessage(ChatColor.RED + "This plot has not been submitted");
                else if(status.contains("ACCEPTED")) p.sendMessage(ChatColor.RED + "This plot has already been accepted");
                else if(status.contains("DENIED")) p.sendMessage(ChatColor.RED + "This plot has already been denied");
            }
        }

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        OfflinePlayer player = sqlUtils.getPlayerByID(PlotUtils.getId(p).toString());
        inventory.setItem(0, utils.getHead(player, PlotUtils.getId(p).toString()));
        inventory.setItem(1, Utils.createGuiItem(Material.REDSTONE_TORCH,
                ChatColor.GREEN + "Set rank", 1,
                ChatColor.YELLOW + "Accept Plot"));
        inventory.setItem(3, Utils.createGuiItem(Material.OAK_SIGN,
                ChatColor.GOLD + "Title:", 1,
                sqlUtils.getPlotTitle(PlotUtils.getId(p).toString())));
        inventory.setItem(5, Utils.createGuiItem(Material.WRITABLE_BOOK,
                ChatColor.GOLD + "Lore:", 1,
                sqlUtils.getPlotLore(PlotUtils.getId(p).toString())));
        inventory.setItem(7, Utils.createGuiItem(Material.PAPER,
                ChatColor.GRAY + "Comments:", 1,
                sqlUtils.getPlotComment(PlotUtils.getId(p).toString())));
        inventory.setItem(8, Utils.createGuiItem(Material.RED_CONCRETE,
                ChatColor.RED + "Deny plot", 1));
    }
}