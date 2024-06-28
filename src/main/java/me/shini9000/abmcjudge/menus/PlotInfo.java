package me.shini9000.abmcjudge.menus;

import me.shini9000.abmcjudge.utils.PlayerMenuUtil;
import me.shini9000.abmcjudge.utils.PlotUtils;
import me.shini9000.abmcjudge.utils.SQLUtils;
import me.shini9000.abmcjudge.utils.Utils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlotInfo extends Menu {
    private final SQLUtils sqlUtils = new SQLUtils();

    private final Utils utils = new Utils();

    Player p = this.playerMenuUtils.getOwner();

    public PlotInfo(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    public String getMenuName() {
        return "" + ChatColor.BLUE + "Plot Info";
    }

    public int getSlots() {
        return 9;
    }

    public void handleMenu(InventoryClickEvent e) {
        TextComponent titleEdit;
        this.p = (Player)e.getWhoClicked();
        if (e.getCurrentItem() == null)
            return;
        switch (e.getCurrentItem().getType()) {
            case OAK_SIGN:
                this.p.closeInventory();
                titleEdit = new TextComponent("" + ChatColor.GREEN + "[Edit plot title]");
                titleEdit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ptitle "));
                titleEdit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("" + ChatColor.GRAY + "Click to edit title"))
                        .create()));
                this.p.spigot().sendMessage((BaseComponent)titleEdit);
                break;
            case WRITABLE_BOOK:
                this.p.closeInventory();
                this.p.performCommand("plore");
                break;
        }
        e.setCancelled(true);
    }

    public void setMenuItems() {
        this.inventory.setItem(1, this.utils.getHead(this.p, new String[0]));
        this.inventory.setItem(3, Utils.createGuiItem(Material.OAK_SIGN, "" + ChatColor.GOLD + "Edit Title:", 1, new String[] { this.sqlUtils

                .getPlotTitle(PlotUtils.getId((OfflinePlayer)this.p).toString()) }));
        this.inventory.setItem(5, Utils.createGuiItem(Material.WRITABLE_BOOK, "" + ChatColor.GOLD + "Edit Lore:", 1, new String[] { this.sqlUtils

                .getPlotLore(PlotUtils.getId((OfflinePlayer)this.p).toString()) }));
        this.inventory.setItem(7, Utils.createGuiItem(Material.PAPER, "" + ChatColor.GRAY + "Comments:", 1, new String[] { this.sqlUtils

                .getPlotComment(PlotUtils.getId((OfflinePlayer)this.p).toString()) }));
    }
}
