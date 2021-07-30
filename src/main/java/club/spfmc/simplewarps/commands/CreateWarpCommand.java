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
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CreateWarpCommand extends SimpleCommand {

    private final SimpleWarps simpleWarps;

    public CreateWarpCommand(SimpleWarps simpleWarps) {
        super(simpleWarps, "CreateWarp");
        this.simpleWarps = simpleWarps;
    }

    @Override
    public void onPlayerExecute(Player player, Command command, String[] arguments) {
        if (arguments.length > 0) {
            String name = arguments[0];
            Location location = player.getLocation();
            Warp warp = new Warp(name);
            warp.setInGui(false);
            warp.setSlot(0);
            warp.setPreviewItem(new ItemStack(Material.PAPER));
            warp.setWorld(location.getWorld().getName());
            warp.setX(location.getX());
            warp.setY(location.getY());
            warp.setZ(location.getZ());
            warp.setYaw(location.getYaw());
            warp.setPitch(location.getPitch());
            simpleWarps.getWarpsManager().addWarp(warp);
            player.sendMessage("Warp creado");
        }
    }

}