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
import club.spfmc.simplewarps.util.inventory.menu.SimpleMenuAction;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WarpsCommand extends SimpleCommand {

    private final SimpleWarps simpleWarps;

    public WarpsCommand(SimpleWarps simpleWarps) {
        super(simpleWarps, "Warps");
        this.simpleWarps = simpleWarps;
    }

    @Override
    public void onPlayerExecute(Player player, Command command, String[] arguments) {
        List<SimpleMenuAction> actions = new ArrayList<>();
        // Load Warps
        for (Warp warp:simpleWarps.getWarpsManager().getWarps()) {
            if (warp.isInGui()) {
                actions.add(new SimpleMenuAction(warp.getSlot()) {
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
                        World world = simpleWarps.getServer().getWorld(warp.getWorld());
                        Location location = new Location(world, warp.getX(), warp.getY(), warp.getZ(), warp.getYaw(), warp.getPitch());
                        player.teleport(location);
                    }
                });
            }
        }
        simpleWarps.getSimpleMenuInventory().openInventory(player, simpleWarps.getSettings().getString("inventory.warps.title"), 54, actions);
    }

}