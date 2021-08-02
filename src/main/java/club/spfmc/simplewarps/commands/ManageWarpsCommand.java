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

package club.spfmc.simplewarps.commands;

import club.spfmc.simplewarps.SimpleWarps;
import club.spfmc.simplewarps.util.command.SimpleCommand;
import club.spfmc.simplewarps.util.inventory.pages.PagesInventory;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ManageWarpsCommand extends SimpleCommand {

    private final SimpleWarps simpleWarps;

    public ManageWarpsCommand(SimpleWarps simpleWarps) {
        super(simpleWarps, "ManageWarps");
        this.simpleWarps = simpleWarps;
    }

    @Override
    public void onPlayerExecute(Player player, String[] arguments) {
        Yaml messages = simpleWarps.getMessages();
        if (player.hasPermission("simple.manage.warps")) {
            Yaml settings = simpleWarps.getSettings();
            String title = settings.getString("inventory.manageWarps.title");
            int rows = settings.getInt("inventory.manageWarps.rows");
            List<Object> warps = new ArrayList<>(simpleWarps.getWarpsManager().getWarps());
            simpleWarps.getPagesInventoryManager().openInventory(player, new PagesInventory(title, rows, warps) {

                @Override
                public void onLeftClick(Player player, Object object) {
                    if (object != null) {
                        Warp warp = (Warp) object;
                        World world = simpleWarps.getServer().getWorld(warp.getWorld());
                        Location location = new Location(world, warp.getX(), warp.getY(), warp.getZ(), warp.getYaw(), warp.getPitch());
                        player.teleport(location);
                    }
                }

                @Override
                public void onRightClick(Player player, Object object) {
                    if (object != null) {
                        player.closeInventory();
                        Warp warp = (Warp) object;
                        simpleWarps.getServer().dispatchCommand(player, "EditWarp " + warp.getName());
                    }
                }

                @Override
                public ItemStack getListedItem(Object object) {
                    if (object != null) {
                        Warp warp = (Warp) object;
                        String enabled;
                        if (warp.isInGui()) {
                            enabled = messages.getString("enabled");
                        } else {
                            enabled = messages.getString("disabled");
                        }
                        return Yaml.replace(settings.getItemStack("inventory.manageWarps.items.warp"), new String[][] {
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
                    return settings.getItemStack("inventory.manageWarps.items.panel");
                }

                @Override
                public ItemStack getInformation() {
                    return Yaml.replace(settings.getItemStack("inventory.manageWarps.items.information"), new String[][] {
                            {"%warps_amount%", getObjects().size() + ""}
                    });
                }

                @Override
                public ItemStack getPrevious() {
                    return settings.getItemStack("inventory.manageWarps.items.previous");

                }

                @Override
                public ItemStack getNext() {
                    return settings.getItemStack("inventory.manageWarps.items.next");
                }

                @Override
                public ItemStack getClose() {
                    return settings.getItemStack("inventory.manageWarps.items.close");
                }
            });
        } else {
            messages.sendMessage(player, "manageWarps.noPermission");
        }
    }


}