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
import club.spfmc.simplewarps.inventories.WarpsInventory;
import club.spfmc.simplewarps.util.command.SimpleCommand;
import club.spfmc.simplewarps.util.yaml.Yaml;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class WarpsCommand extends SimpleCommand {

    private final SimpleWarps simpleWarps;

    public WarpsCommand(SimpleWarps simpleWarps) {
        super(simpleWarps, "Warps");
        this.simpleWarps = simpleWarps;
    }

    @Override
    public void onPlayerExecute(Player player, String[] arguments) {
        simpleWarps.getMenuInventoryManager().openInventory(player, new WarpsInventory(simpleWarps));
    }

    @Override
    public void onConsoleExecute(ConsoleCommandSender console, Command command, String[] arguments) {
        Yaml messages = simpleWarps.getMessages();
        messages.sendMessage(console, "warps.isConsole");
    }
}