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

package club.spfmc.simplewarps.inputs;

import club.spfmc.simplewarps.SimpleWarps;
import club.spfmc.simplewarps.util.chatimput.ChatInput;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PermissionInput extends ChatInput {

    private final SimpleWarps simpleWarps;
    private final Warp warp;

    public PermissionInput(SimpleWarps simpleWarps, Warp warp) {
        super(null);
        this.simpleWarps = simpleWarps;
        this.warp = warp;
    }

    @Override
    public boolean onChatInput(Player player, String input) {
        if (!input.contains(" ")) {
            warp.setPermission(input);
            simpleWarps.getWarpsManager().saveWarp(warp.getName());
            Bukkit.getScheduler().runTaskLater(simpleWarps, new Runnable() {
                @Override
                public void run() {
                    simpleWarps.getServer().dispatchCommand(player, "EditWarp " + warp.getName());
                }
            }, 1);
            return true;
        }
        simpleWarps.getMessages().sendMessage(player, "editWarp.permission.containSpace");
        return false;
    }

    @Override
    public void onPlayerSneak(Player player) {
        simpleWarps.getServer().dispatchCommand(player, "EditWarp " + warp.getName());
    }

}