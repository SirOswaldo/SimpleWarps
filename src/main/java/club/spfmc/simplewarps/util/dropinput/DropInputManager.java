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

package club.spfmc.simplewarps.util.dropinput;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;

public class DropInputManager implements Listener {

    private final HashMap<String, DropInput> inputs = new HashMap<>();

    public void addInput(Player player, DropInput dropInput) {
        inputs.put(player.getName(), dropInput);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (inputs.containsKey(player.getName())) {
            event.setCancelled(true);
            DropInput chatInput = inputs.get(player.getName());
            chatInput.onPLayerDrop(player, event.getItemDrop().getItemStack());
            inputs.remove(player.getName());
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (inputs.containsKey(player.getName())) {
            DropInput chatInput = inputs.get(player.getName());
            chatInput.onPlayerSneak(player);
            inputs.remove(player.getName());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        inputs.remove(event.getPlayer().getName());
    }
}