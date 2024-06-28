package me.shini9000.abmcjudge.utils;

import me.shini9000.abmcjudge.ABMCJudge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    private static ABMCJudge plugin = ABMCJudge.getInstance();

    private final LPUtils lpUtils = new LPUtils(plugin.luckPerms);

    private final SQLUtils sqlUtils = new SQLUtils();

    public static ItemStack createGuiItem(Material material, String name, int amount, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        String l = "" + ChatColor.BLUE + ChatColor.BLUE;
        List<String> metalore = Arrays.asList(ChatPaginator.wordWrap(l, 50));
        meta.setDisplayName(name);
        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getHead(Player p, String... lore) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta)skull.getItemMeta();
        meta.setOwningPlayer((OfflinePlayer)p);
        meta.setDisplayName(p.getName());
        String plotStatus = this.sqlUtils.getPlotStatus(this.sqlUtils.getPlotID(p));
        String playerGroup = this.lpUtils.getPlayerGroup((OfflinePlayer)p);
        List<String> loreList = new ArrayList<>();
        loreList.add(PlotUtils.printId(p, PlotUtils.getId((OfflinePlayer)p)));
        loreList.add("" + ChatColor.GRAY + "Status: " + ChatColor.GRAY + ChatColor.GOLD);
        loreList.add("" + ChatColor.GRAY + "Rank: " + ChatColor.GRAY + ChatColor.GOLD);
        meta.setLore(loreList);
        skull.setItemMeta((ItemMeta)meta);
        return skull;
    }

    public ItemStack getHead(Player p, String id) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta)skull.getItemMeta();
        meta.setOwningPlayer((OfflinePlayer)p);
        meta.setDisplayName(p.getName());
        List<String> l = new ArrayList<>();
        l.add(id);
        if (PlotUtils.getId((OfflinePlayer)p) != null) {
            l.add("" + ChatColor.GRAY + "Status: " + ChatColor.GRAY + ChatColor.GOLD);
            l.add("" + ChatColor.GRAY + "Rank: " + ChatColor.GRAY + ChatColor.GOLD);
        }
        meta.setLore(l);
        skull.setItemMeta((ItemMeta)meta);
        return skull;
    }

    public ItemStack getHead(OfflinePlayer player, String id) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta)skull.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName(player.getName());
        List<String> l = new ArrayList<>();
        l.add(id);
        l.add("" + ChatColor.GRAY + "Status: " + ChatColor.GRAY + ChatColor.GOLD);
        l.add("" + ChatColor.GRAY + "Rank: " + ChatColor.GRAY + ChatColor.GOLD);
        meta.setLore(l);
        skull.setItemMeta((ItemMeta)meta);
        return skull;
    }
}
