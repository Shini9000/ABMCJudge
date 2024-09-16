package me.shini9000.abmcjudge.gui.judges;

import me.shini9000.abmcjudge.ABMCJudge;
import me.shini9000.abmcjudge.gui.Menu;
import me.shini9000.abmcjudge.utils.PlayerMenuUtils;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class JudgeMenu extends Menu {

    private final Utils utils = new Utils();

    Player p = playerMenuUtils.getOwner();

    public JudgeMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.DARK_PURPLE + "Judge Panel";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        boolean exists = false;

        if(PlotUtils.getId(p) != null){
            UUID uuid = UUID.nameUUIDFromBytes(PlotUtils.getId(p).toString().getBytes());
            exists =  ABMCJudge.getInstance().data.exists(uuid);
        }

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()) {
            case WRITABLE_BOOK -> { new SubmittedMenu(playerMenuUtils).open(); break;}
            case PAPER -> {
                if(PlotUtils.getId(p) != null && exists)
                    new JudgePlotInfoMenu(playerMenuUtils).open();
                else
                    p.sendMessage(ChatColor.RED + "You must stand on a submitted plot");
                break;
            }
        }

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        String idmsg;

        if(PlotUtils.getId(p) != null) idmsg = "Plot ID: " + PlotUtils.getId(p).toString();
        else idmsg = ChatColor.RED + "You must stand on a plot";

        inventory.setItem(3, utils.createGuiItem(Material.WRITABLE_BOOK,
                ChatColor.YELLOW + "List of submitted plots", 1, ""));
        inventory.setItem(5, Utils.createGuiItem(Material.PAPER,
                ChatColor.YELLOW + "Plot info", 1, idmsg));
    }
}