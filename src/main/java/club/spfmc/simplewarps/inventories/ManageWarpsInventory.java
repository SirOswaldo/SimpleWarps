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

package club.spfmc.simplewarps.inventories;

import club.spfmc.simplewarps.SimpleWarps;
import club.spfmc.simplewarps.tasks.TeleportTask;
import club.spfmc.simplewarps.util.inventory.inventories.PagesInventory;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ManageWarpsInventory extends PagesInventory {

    private final SimpleWarps simpleWarps;
    public ManageWarpsInventory(SimpleWarps simpleWarps, List<Object> list) {
        super(simpleWarps.getSettings().getString("inventory.manageWarps.title"),simpleWarps.getSettings().getInt("inventory.manageWarps.rows"), list);
        this.simpleWarps = simpleWarps;
    }

    @Override
    public ItemStack getListedItem(Object object) {
        if (object != null) {
            Warp warp = (Warp) object;
            String enabled;
            if (warp.isInGui()) {
                enabled = simpleWarps.getMessages().getString("enabled");
            } else {
                enabled = simpleWarps.getMessages().getString("disabled");
            }
            return Yaml.replace(simpleWarps.getSettings().getItemStack("inventory.manageWarps.items.warp"), new String[][] {
                    {"%warp_name%", warp.getName()},
                    {"%warp_gui_enabled%", enabled},
                    {"%warp_gui_position%", warp.getSlot() + ""},
                    {"%warp_world%", warp.getWorld()},
                    {"%warp_x%", Math.round(warp.getX()) + ""},
                    {"%warp_y%", Math.round(warp.getY()) + ""},
                    {"%warp_z%", Math.round(warp.getZ()) + ""},
                    {"%warp_yaw%", Math.round(warp.getYaw()) + ""},
                    {"%warp_pitch%", Math.round(warp.getPitch()) + ""}
            });
        } else {
            return new ItemStack(Material.AIR);
        }
    }

    @Override
    public ItemStack getPanel() {
        return simpleWarps.getSettings().getItemStack("inventory.manageWarps.items.panel");
    }

    @Override
    public ItemStack getInformation() {
        return Yaml.replace(simpleWarps.getSettings().getItemStack("inventory.manageWarps.items.information"), new String[][] {
                {"%warps_amount%", getList().size() + ""}
        });
    }

    @Override
    public ItemStack getPrevious() {
        return simpleWarps.getSettings().getItemStack("inventory.manageWarps.items.previous");
    }

    @Override
    public ItemStack getClose() {
        return simpleWarps.getSettings().getItemStack("inventory.manageWarps.items.close");
    }

    @Override
    public ItemStack getNext() {
        return simpleWarps.getSettings().getItemStack("inventory.manageWarps.items.next");
    }

    @Override
    public void onLeftClick(Player player, Object object) { // Teleport
        player.closeInventory();
        TeleportTask teleportTask = new TeleportTask(simpleWarps, player, (Warp) object);
        teleportTask.startScheduler();
    }

    @Override
    public void onRightClick(Player player, Object object) { // Edit
        player.closeInventory();
        Warp warp = (Warp) object;
        simpleWarps.getMenuInventoryManager().openInventory(player, new EditWarpInventory(simpleWarps, warp, "gui"));
    }

    @Override
    public void onShiftRightClick(Player player, Object object) { // Delete
        player.closeInventory();
        Warp warp = (Warp) object;
        player.sendMessage("Nombre: " + warp.getName());
        simpleWarps.getMenuInventoryManager().openInventory(player, new DeleteWarpConfirmInventory(simpleWarps, warp, "gui"));
    }

}