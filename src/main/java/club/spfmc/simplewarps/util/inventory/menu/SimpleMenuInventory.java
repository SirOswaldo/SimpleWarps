/*
 *  Copyright (C) 2021 SirOswaldo
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package club.spfmc.simplewarps.util.inventory.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;

public class SimpleMenuInventory implements Listener {

    private final static HashMap<String, List<SimpleMenuAction>> actions = new HashMap<>();

    public void openInventory(Player player, String title, int slots, List<SimpleMenuAction> actions) {
        SimpleMenuInventory.actions.put(player.getName(), actions);
        Inventory inventory = Bukkit.createInventory(null, slots, ChatColor.translateAlternateColorCodes('&', title));
        for (SimpleMenuAction action:actions) {
            inventory.setItem(action.getSlot(), action.getItem());
        }
        // Open Inventory
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (SimpleMenuInventory.actions.containsKey(player.getName())) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            List<SimpleMenuAction> actions = SimpleMenuInventory.actions.get(player.getName());
            for (SimpleMenuAction action:actions) {
                if (slot == action.getSlot()) {
                    switch (event.getClick()) {
                        case LEFT:
                            action.onLeftClick(player);
                            break;
                        case RIGHT:
                            action.onRightClick(player);
                            break;
                        case MIDDLE:
                            action.onMiddleClick(player);
                            break;
                        case SHIFT_LEFT:
                            action.onShiftLeftClick(player);
                            break;
                        case SHIFT_RIGHT:
                            action.onShiftRightClick(player);
                            break;
                    }
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (SimpleMenuInventory.actions.containsKey(player.getName())) {
            SimpleMenuInventory.actions.remove(player.getName());
        }
    }
}
