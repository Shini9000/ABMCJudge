package me.shini9000.abmcjudge.gui;

import me.shini9000.abmcjudge.utils.PlayerMenuUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import me.shini9000.abmcjudge.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerPlotsMenu extends Menu{

    private final SQLUtils sqlUtils = new SQLUtils();

    Player p = playerMenuUtils.getOwner();

    public PlayerPlotsMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.DARK_BLUE + "Submitted plots";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;

        if(e.getCurrentItem().getType().equals(Material.SPRUCE_SIGN))
            p.performCommand("p v " + e.getCurrentItem().getItemMeta().getDisplayName());

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        //Items for player's submitted plots
        for(int i = 0; i < sqlUtils.getSubmittedPlotID(p).size(); i++){
            inventory.addItem(Utils.createGuiItem(Material.SPRUCE_SIGN, sqlUtils.getSubmittedPlotID(p).get(i), 1));
        }
    }
}