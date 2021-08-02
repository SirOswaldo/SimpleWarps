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
import club.spfmc.simplewarps.inventories.EditWarpInventory;
import club.spfmc.simplewarps.util.command.SimpleCommand;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class EditWarpCommand extends SimpleCommand {

    private final SimpleWarps simpleWarps;

    public EditWarpCommand(SimpleWarps simpleWarps) {
        super(simpleWarps, "EditWarp");
        this.simpleWarps = simpleWarps;
    }

    @Override
    public void onPlayerExecute(Player player, String[] arguments) {
        Yaml messages = simpleWarps.getMessages();
        if (player.hasPermission("simple.edit.warp")) {
            if (arguments.length > 0) {
                String name = arguments[0];
                if (simpleWarps.getWarpsManager().getWarpsNames().contains(name)) {
                    Warp warp = simpleWarps.getWarpsManager().getWarp(arguments[0]);
                    simpleWarps.getMenuInventoryManager().openInventory(player, new EditWarpInventory(simpleWarps, warp));
                } else {
                    messages.sendMessage(player, "editWarp.invalidName", new String[][] {{"%warp_name%", arguments[0]}});
                }
            } else {
                messages.sendMessage(player, "editWarp.emptyName");
            }
        } else {
            messages.sendMessage(player, "editWarp.noPermission");
        }
    }

    @Override
    public void onConsoleExecute(ConsoleCommandSender console, Command command, String[] arguments) {
        simpleWarps.getMessages().sendMessage(console, "editWarp.isConsole");
    }

}