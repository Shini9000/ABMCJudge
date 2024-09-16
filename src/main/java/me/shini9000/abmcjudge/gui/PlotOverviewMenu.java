package me.shini9000.abmcjudge.gui;

import me.shini9000.abmcjudge.utils.PlayerMenuUtils;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import me.shini9000.abmcjudge.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlotOverviewMenu extends Menu {

    private final SQLUtils sqlUtils = new SQLUtils();
    private final Utils utils = new Utils();

    public PlotOverviewMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.AQUA + "Plot Overview";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()){
            case WRITABLE_BOOK -> {
                new PlayerPlotsMenu(playerMenuUtils).open();
                break;
            }
            case PAPER -> {
                if(PlotUtils.getId(p) != null){ new PlotInfo(playerMenuUtils).open();}
                else p.sendMessage( ChatColor.RED + "This is not your plot");
                break;
            }
            case GREEN_CONCRETE -> {
                if(PlotUtils.getId(p) != null){
                    new SubmissionMenu(playerMenuUtils).open();
                }
                else p.sendMessage( ChatColor.RED + "This is not your plot");
                break;
            }
        }

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(0, utils.getHead(playerMenuUtils.getOwner()));
        inventory.setItem(3, utils.createGuiItem(Material.WRITABLE_BOOK,
                ChatColor.YELLOW + "List of your submitted plots", 1));
        if(PlotUtils.getId(playerMenuUtils.getOwner()) != null){
            inventory.setItem(5, utils.createGuiItem(Material.PAPER,
                    ChatColor.YELLOW + "Plot Submission Info", 1, "Title: "
                            + sqlUtils.getPlotTitle(PlotUtils.getId(playerMenuUtils.getOwner()).toString())));
        } else {
            inventory.setItem(5, utils.createGuiItem(Material.PAPER,
                    ChatColor.YELLOW + "Plot Submission Info", 1, "You must be on your plot"));
        }
        inventory.setItem(8, utils.createGuiItem(Material.GREEN_CONCRETE,
                ChatColor.DARK_GREEN + "Submit Plot", 1, ""));
    }
}