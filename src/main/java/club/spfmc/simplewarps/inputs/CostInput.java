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
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.entity.Player;

public class CostInput extends ChatInput {

    private final SimpleWarps simpleWarps;
    private final Warp warp;

    public CostInput(SimpleWarps simpleWarps, Warp warp) {
        super(null);
        this.simpleWarps = simpleWarps;
        this.warp = warp;
    }

    @Override
    public boolean onChatInput(Player player, String input) {
        Yaml messages = simpleWarps.getMessages();
        try {
            int cost = Integer.parseInt(input);
            warp.setCost(cost);
            simpleWarps.getWarpsManager().saveWarp(warp.getName());
            simpleWarps.getServer().dispatchCommand(player, "EditWarp " + warp.getName());
        } catch (NumberFormatException e) {
            messages.sendMessage(player, "editWarp.cost.invalidCost");
        }
        return false;
    }

    @Override
    public void onPlayerSneak(Player player) {
        simpleWarps.getServer().dispatchCommand(player, "EditWarp " + warp.getName());
    }

}