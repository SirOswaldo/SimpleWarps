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
import club.spfmc.simplewarps.util.inventory.Item;
import club.spfmc.simplewarps.util.inventory.MenuInventory;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WarpsInventory extends MenuInventory {

    private final SimpleWarps simpleWarps;

    public WarpsInventory(SimpleWarps simpleWarps) {
        this.simpleWarps = simpleWarps;
        for (Warp warp:simpleWarps.getWarpsManager().getWarps()) {
            if (warp.isInGui()) {
                if (getRows() * 9 > warp.getSlot()) {
                    addMenuAction(warp.getSlot(), new Item() {
                        @Override
                        public ItemStack getItem() {
                            return Yaml.replace(warp.getPreviewItem(), new String[][] {
                                    {"%name%", warp.getName()},
                                    {"%world%", warp.getWorld()},
                                    {"%x%", warp.getX() + ""},
                                    {"%y%", warp.getY() + ""},
                                    {"%z%", warp.getZ() + ""},
                                    {"%yaw%", warp.getYaw() + ""},
                                    {"%pitch%", warp.getPitch() + ""}
                            });
                        }

                        @Override
                        public void onLeftClick(Player player) {
                            if (player.hasPermission("simple.teleport.warp." + warp.getName())) {
                                World world = simpleWarps.getServer().getWorld(warp.getWorld());
                                Location location = new Location(world, warp.getX(), warp.getY(), warp.getZ(), warp.getYaw(), warp.getPitch());
                                player.teleport(location);
                            } else {
                                String soundString = simpleWarps.getSettings().getString("inventory.warps.noPermissionSound");
                                try {
                                    player.playSound(player.getLocation(), Sound.valueOf(soundString), 1, 1);
                                } catch (IllegalArgumentException e) {
                                    simpleWarps.getLogger().info("The sound: " + soundString + " no is valid.");
                                    simpleWarps.getLogger().info("Change this sound in: inventory.warps.noPermissionSound");
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public String getTitle() {
        return simpleWarps.getSettings().getString("inventory.warps.title");
    }

    @Override
    public int getRows() {
        return simpleWarps.getSettings().getInt("inventory.warps.rows");
    }
}
