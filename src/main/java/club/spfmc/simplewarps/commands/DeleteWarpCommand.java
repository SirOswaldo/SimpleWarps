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
import club.spfmc.simplewarps.inventories.DeleteWarpConfirmInventory;
import club.spfmc.simplewarps.util.command.SimpleCommand;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.Warp;
import club.spfmc.simplewarps.warp.WarpsManager;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DeleteWarpCommand extends SimpleCommand {

    private final SimpleWarps simpleWarps;

    public DeleteWarpCommand(SimpleWarps simpleWarps) {
        super(simpleWarps, "DeleteWarp");
        this.simpleWarps = simpleWarps;
    }

    @Override
    public void onPlayerExecute(Player player, String[] arguments) {
        Yaml messages = simpleWarps.getMessages();
        if (player.hasPermission("simple.delete.warp")) {
            if (arguments.length > 0) {
                String name = arguments[0];
                WarpsManager warpsManager = simpleWarps.getWarpsManager();
                if (warpsManager.existWarp(name)) {
                    Warp warp = warpsManager.getWarp(name);
                    simpleWarps.getMenuInventoryManager().openInventory(player, new DeleteWarpConfirmInventory(simpleWarps, warp, "cmd"));
                } else {
                    messages.sendMessage(player, "deleteWarp.invalidName", new String[][] {
                            {"%name%", name}
                    });
                }
            } else {
                messages.sendMessage(player, "deleteWarp.emptyName");
            }
        } else {
            messages.sendMessage(player, "deleteWarp.noPermission");
        }
    }

    @Override
    public List<String> onPlayerTabComplete(Player player, Command command, String[] arguments) {
        List<String> result = new ArrayList<>();
        if (player.hasPermission("simple.delete.warp")) {
            if (arguments.length == 1) {
                result = simpleWarps.getWarpsManager().getWarpsNames();
            }
        }
        return result;
    }

    @Override
    public void onConsoleExecute(ConsoleCommandSender console, Command command, String[] arguments) {
        Yaml messages = simpleWarps.getMessages();
        if (arguments.length > 0) {
            String name = arguments[0];
            WarpsManager warpsManager = simpleWarps.getWarpsManager();
            if (warpsManager.existWarp(name)) {
                warpsManager.deleteWarp(name);
                messages.sendMessage(console, "deleteWarp.deleted", new String[][] {
                        {"%warp%", name}
                });
            } else {
                messages.sendMessage(console, "deleteWarp.invalidName", new String[][] {
                        {"%name%", name}
                });
            }
        } else {
            messages.sendMessage(console, "deleteWarp.emptyName");
        }
    }

    @Override
    public List<String> onConsoleTabComplete(ConsoleCommandSender console, Command command, String[] arguments) {
        List<String> result = new ArrayList<>();
        if (arguments.length == 1) {
            result = simpleWarps.getWarpsManager().getWarpsNames();
        }
        return result;
    }

}