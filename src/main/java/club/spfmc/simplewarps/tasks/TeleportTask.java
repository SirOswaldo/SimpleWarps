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

package club.spfmc.simplewarps.tasks;

import club.spfmc.simplewarps.SimpleWarps;
import club.spfmc.simplewarps.util.task.Task;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportTask extends Task {

    private final static List<String> teleporting = new ArrayList<>();
    public static List<String> getTeleporting() {
        return teleporting;
    }

    private final SimpleWarps simpleWarps;
    private int countdown;
    private final Player player;
    private final Warp warp;

    public TeleportTask(SimpleWarps simpleWarps, Player player, Warp warp) {
        super(simpleWarps, 20L);
        this.simpleWarps = simpleWarps;
        countdown = simpleWarps.getSettings().getInt("teleport.countdown");
        this.player = player;
        this.warp = warp;
        getTeleporting().add(player.getName());
    }

    public Player getPlayer() {
        return player;
    }
    public Warp getWarp() {
        return warp;
    }

    @Override
    public void actions() {
        if (player.isOnline() && TeleportTask.getTeleporting().contains(player.getName())) {
            if (player.hasPermission("simple.bypass.home.countdown")) {
                teleport();
            } else {
                Yaml settings = simpleWarps.getSettings();
                if (countdown == 0) {
                    teleport();
                    if (settings.contains("teleport.messages." + countdown)) {
                        settings.sendMessage(player, "teleport.messages." + countdown, new String[][] {{"%seconds%", countdown + ""}, {"%warp%", warp.getName()}});
                    }
                    if (settings.contains("teleport.sounds." + countdown)) {
                        String sound = settings.getString("teleport.sounds." + countdown);
                        try {
                            player.playSound(player.getLocation(), Sound.valueOf(sound), 1, 1);
                        } catch (IllegalArgumentException e) {
                            simpleWarps.getLogger().info("The sound: " + sound + " no found in your server version.");
                        }
                    }
                } else {
                    if (settings.contains("teleport.messages." + countdown)) {
                        settings.sendMessage(player, "teleport.messages." + countdown, new String[][] {{"%seconds%", countdown + ""}});
                    }
                    if (settings.contains("teleport.sounds." + countdown)) {
                        String sound = settings.getString("teleport.sounds." + countdown);
                        try {
                            player.playSound(player.getLocation(), Sound.valueOf(sound), 1, 1);
                        } catch (IllegalArgumentException e) {
                            simpleWarps.getLogger().info("The sound: " + sound + " no found in your server version.");
                        }
                    }
                    countdown--;
                }
            }
        } else {
            TeleportTask.teleporting.remove(player.getName());
            stopScheduler();
        }
    }

    private void teleport() {
        Yaml messages = simpleWarps.getMessages();
        World world = simpleWarps.getServer().getWorld(warp.getWorld());
        if (world != null) {
            double x = warp.getX();
            double y = warp.getY();
            double z = warp.getZ();
            float yaw = warp.getYaw();
            float pitch = warp.getPitch();
            Location location = new Location(world, x, y, z, yaw, pitch);
            player.teleport(location);
            messages.sendMessage(player, "warp.teleported", new String[][] {{"%warp%", warp.getName()}});
        } else {
            messages.sendMessage(player, "warp.invalidWorld", new String[][] {{"%world%", warp.getWorld()}});
        }
        TeleportTask.teleporting.remove(player.getName());
        stopScheduler();
    }

}