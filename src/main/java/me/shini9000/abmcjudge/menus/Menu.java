package me.shini9000.abmcjudge.menus;

import me.shini9000.abmcjudge.utils.PlayerMenuUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class Menu implements InventoryHolder {
    protected PlayerMenuUtil playerMenuUtils;

    protected Inventory inventory;

    protected ItemStack FILLER_GLASS = makeItem(Material.GRAY_STAINED_GLASS_PANE, " ", new String[0]);

    public Menu(PlayerMenuUtil playerMenuUtils) {
        this.playerMenuUtils = playerMenuUtils;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent paramInventoryClickEvent);

    public abstract void setMenuItems();

    public void open() {
        this.inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        setMenuItems();
        this.playerMenuUtils.getOwner().openInventory(this.inventory);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setFillerGlass() {
        for (int i = 0; i < getSlots(); i++) {
            if (this.inventory.getItem(i) == null)
                this.inventory.setItem(i, this.FILLER_GLASS);
        }
    }

    public ItemStack makeItem(Material material, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }
}
