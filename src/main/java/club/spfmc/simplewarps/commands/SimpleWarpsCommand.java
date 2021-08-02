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
import club.spfmc.simplewarps.util.yaml.Yaml;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SimpleWarpsCommand extends SimpleCommand {

    private final SimpleWarps simpleWarps;

    public SimpleWarpsCommand(SimpleWarps simpleWarps) {
        super(simpleWarps, "SimpleWarps");
        this.simpleWarps = simpleWarps;
    }

    @Override
    public void onPlayerExecute(Player player, String[] arguments) {
        Yaml messages = simpleWarps.getMessages();
        if (player.hasPermission("simple.admin.warps")) {
            if (arguments.length > 0) {
                switch (arguments[0].toLowerCase()) {
                    case "reload":
                        messages.sendMessage(player, "simpleWarps.reload");
                        break;
                    case "version":
                        messages.sendMessage(player, "simpleWarps.version", new String[][] {
                                {"%version%", simpleWarps.getDescription().getVersion()}
                        });
                        break;
                    case "help":
                        messages.sendMessage(player, "simpleWarps.help");
                        break;
                    default:
                        messages.sendMessage(player, "simpleWarps.invalidOption", new String[][] {
                                {"%option%", arguments[0]}
                        });
                }
            } else {
                messages.sendMessage(player, "simpleWarps.emptyOption");
            }
        } else {
            messages.sendMessage(player, "simpleWarps.noPermission");
        }
    }

    @Override
    public List<String> onPlayerTabComplete(Player player, Command command, String[] arguments) {
        List<String> result = new ArrayList<>();
        if (player.hasPermission("simple.admin.warps")) {
            if (arguments.length == 1) {
                result.add("reload");
                result.add("version");
                result.add("reload");
            }
        }
        return result;
    }

    @Override
    public void onConsoleExecute(ConsoleCommandSender console, Command command, String[] arguments) {
        Yaml messages = simpleWarps.getMessages();
        if (arguments.length > 0) {
            switch (arguments[0].toLowerCase()) {
                case "reload":
                    messages.sendMessage(console, "simpleWarps.reload");
                    break;
                case "version":
                    messages.sendMessage(console, "simpleWarps.version", new String[][] {
                            {"%version%", simpleWarps.getDescription().getVersion()}
                    });
                    break;
                case "help":
                    messages.sendMessage(console, "simpleWarps.help");
                    break;
                default:
                    messages.sendMessage(console, "simpleWarps.invalidOption", new String[][] {
                            {"%option%", arguments[0]}
                    });
            }
        } else {
            messages.sendMessage(console, "simpleWarps.emptyOption");
        }
    }

    @Override
    public List<String> onConsoleTabComplete(ConsoleCommandSender console, Command command, String[] arguments) {
        List<String> result = new ArrayList<>();
        if (arguments.length == 1) {
            result.add("reload");
            result.add("version");
            result.add("reload");
        }
        return result;
    }
}