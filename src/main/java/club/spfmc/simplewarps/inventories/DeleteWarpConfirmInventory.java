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
import club.spfmc.simplewarps.util.inventory.inventories.ConfirmInventory;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DeleteWarpConfirmInventory extends ConfirmInventory {

    private final SimpleWarps simpleWarps;
    private String from;

    public DeleteWarpConfirmInventory(SimpleWarps simpleWarps, Warp warp, String from) {
        super(warp);
        this.simpleWarps = simpleWarps;
        this.from = from;
    }

    @Override
    public String getTitle() {
        return simpleWarps.getSettings().getString("inventory.deleteWarp.title");
    }

    @Override
    public ItemStack getPanel() {
        return simpleWarps.getSettings().getItemStack("inventory.deleteWarp.items.panel");
    }

    @Override
    public ItemStack getInformation() {
        Warp warp = (Warp) getObject();
        ItemStack item = simpleWarps.getSettings().getItemStack("inventory.deleteWarp.items.information");
        return Yaml.replace(item, new String[][] {
                {"%warp_name%", warp.getName()},
                {"%warp_x%", Math.round(warp.getX()) + ""},
                {"%warp_y%", Math.round(warp.getY()) + ""},
                {"%warp_z%", Math.round(warp.getZ()) + ""},
                {"%warp_yaw%", Math.round(warp.getYaw()) + ""},
                {"%warp_pitch%", Math.round(warp.getPitch()) + ""}
        });
    }

    @Override
    public ItemStack getAccept() {
        return simpleWarps.getSettings().getItemStack("inventory.deleteWarp.items.accept");
    }

    @Override
    public ItemStack getCancel() {
        return simpleWarps.getSettings().getItemStack("inventory.deleteWarp.items.cancel");
    }

    @Override
    public void onAccept(Player player, Object object) {
        Warp warp = (Warp) object;
        String name = warp.getName();
        simpleWarps.getWarpsManager().deleteWarp(name);
        if (from.equals("cmd")) {
            simpleWarps.getMessages().sendMessage(player, "deleteWarp.deleted", new String[][] {
                    {"%warp%", name}
            });
        } else {
            player.closeInventory();
            List<Object> warps = new ArrayList<>(simpleWarps.getWarpsManager().getWarps());
            simpleWarps.getMenuInventoryManager().openInventory(player, new ManageWarpsInventory(simpleWarps, warps));
        }
    }

    @Override
    public void onCancel(Player player, Object object) {
        if (from.equals("gui")) {
            player.closeInventory();
            List<Object> warps = new ArrayList<>(simpleWarps.getWarpsManager().getWarps());
            simpleWarps.getMenuInventoryManager().openInventory(player, new ManageWarpsInventory(simpleWarps, warps));
        } else {
            player.closeInventory();
            simpleWarps.getMessages().sendMessage(player, "deleteWarp.deleted", new String[][] {
                    {"%warp%", ((Warp) object).getName()}
            });
        }
    }

}