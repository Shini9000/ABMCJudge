package me.shini9000.abmcjudge.gui;

import me.shini9000.abmcjudge.utils.PlayerMenuUtils;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import me.shini9000.abmcjudge.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class SubmissionMenu extends Menu {

    private final SQLUtils sqlUtils = new SQLUtils();
    private final Utils utils = new Utils();

    public SubmissionMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.DARK_GREEN + "Submission";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        List<String> status = new ArrayList<>();
        status.add(sqlUtils.getPlotStatus(PlotUtils.getId(p).toString()));

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()) {
            case GREEN_CONCRETE -> {
                if(!(status.contains("ACCEPTED") || status.contains("PENDING"))) {
                    p.sendMessage(ChatColor.GOLD + "Plot submitted!");
                    sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "PENDING");
                    p.closeInventory();
                } else if(status.contains("ACCEPTED")) p.sendMessage(ChatColor.RED + "This plot has already been accepted");
                else if (status.contains("PENDING")) p.sendMessage(ChatColor.RED + "This plot has already been submitted");

            }
            case RED_CONCRETE -> new PlotOverviewMenu(playerMenuUtils).open();
        }

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        //Possible change to show status color rather than block / lore status
        inventory.setItem(3, utils.createGuiItem(Material.GREEN_CONCRETE,
                ChatColor.DARK_GREEN + "Submit Plot", 1,
                ChatColor.GOLD + "Are you sure you want to submit this plot?"));
        inventory.setItem(5, utils.createGuiItem(Material.RED_CONCRETE,
                ChatColor.RED + "Cancel", 1, ""));
    }
}