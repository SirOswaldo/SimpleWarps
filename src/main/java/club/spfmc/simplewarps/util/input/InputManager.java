/*
 * Copyright (C) 2021  SirOswaldo
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package club.spfmc.simplewarps.util.input;

import club.spfmc.simplewarps.util.input.inputs.ChatInput;
import club.spfmc.simplewarps.util.input.inputs.DropInput;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;

public class InputManager implements Listener {

    private final HashMap<String, ChatInput> chats = new HashMap<>();
    private final HashMap<String, DropInput> drops = new HashMap<>();

    public void addChatInput(Player player, ChatInput chatInput) {
        chats.put(player.getName(), chatInput);
    }
    public void addDropInput(Player player, DropInput dropInput) {
        drops.put(player.getName(), dropInput);
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (chats.containsKey(player.getName())) {
            event.setCancelled(true);
            ChatInput chatInput = chats.get(player.getName());
            if (chatInput.onChatInput(player, event.getMessage())) {
                chats.remove(player.getName());
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (drops.containsKey(player.getName())) {
            event.setCancelled(true);
            DropInput chatInput = drops.get(player.getName());
            chatInput.onPLayerDrop(player, event.getItemDrop().getItemStack());
            drops.remove(player.getName());
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (chats.containsKey(player.getName())) {
            ChatInput chatInput = chats.get(player.getName());
            chatInput.onPlayerSneak(player);
            chats.remove(player.getName());
        }
        if (drops.containsKey(player.getName())) {
            DropInput chatInput = drops.get(player.getName());
            chatInput.onPlayerSneak(player);
            drops.remove(player.getName());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        chats.remove(event.getPlayer().getName());
        drops.remove(event.getPlayer().getName());
    }

}