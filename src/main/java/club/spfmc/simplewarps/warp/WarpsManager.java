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

package club.spfmc.simplewarps.warp;

import club.spfmc.simplewarps.SimpleWarps;
import club.spfmc.simplewarps.util.yaml.Yaml;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WarpsManager {

    private final SimpleWarps simpleWarps;
    private final HashMap<String , Warp> warps = new HashMap<>();

    public WarpsManager(SimpleWarps simpleWarps) {
        this.simpleWarps = simpleWarps;
    }

    public void loadWarps() {
        List<File> files = Yaml.getFolderFiles(simpleWarps.getDataFolder() + "/warps");
        for (File file:files) {
            loadWarp(file.getName().replaceAll(".yml", ""));
        }
    }

    public void loadWarp(String name) {
        Yaml yaml = new Yaml(simpleWarps, "warps", name);
        yaml.registerFileConfiguration();
        // GUI
        boolean inGui = false;
        int slot = 0;
        ItemStack previewItem = new ItemStack(Material.PAPER);
        // Location
        String world = "";
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        float yaw = 0;
        float pitch = 0;
        // Permission
        String permission = "simple.teleport.warp." + name;
        // Cost
        double cost = 0.0;

        // GUI
        if (yaml.contains("gui.enable") && yaml.isBoolean("gui.enable")) {
            inGui = yaml.getBoolean("gui.enable");
        } else {
            simpleWarps.getLogger().info("The warp " + name + "has no loaded because the 'gui.enable' no exist or is invalid");
            return;
        }
        if (yaml.contains("gui.slot") && yaml.isInt("gui.slot")) {
            slot = yaml.getInt("gui.slot");
        } else {
            simpleWarps.getLogger().info("The warp " + name + "has no loaded because the 'gui.slot' no exist or is invalid");
            return;
        }
        if (yaml.contains("gui.previewItem")) {
            previewItem = yaml.getItemStack("gui.previewItem");
        } else {
            simpleWarps.getLogger().info("The warp " + name + "has no loaded because the 'gui.previewItem' no exist or is invalid");
            return;
        }

        // Location World
        if (yaml.contains("location.world") && yaml.isString("location.world")) {
            world = yaml.getString("location.world");
        } else {
            simpleWarps.getLogger().info("The warp " + name + "has no loaded because the 'location.world' no exist or is invalid");
            return;
        }
        // Location X
        if (yaml.contains("location.x") && yaml.isDouble("location.x")) {
            x = yaml.getDouble("location.x");
        } else {
            simpleWarps.getLogger().info("The warp " + name + "has no loaded because the 'location.x' no exist or is invalid");
            return;
        }
        // Location Y
        if (yaml.contains("location.y") && yaml.isDouble("location.y")) {
            y = yaml.getDouble("location.y");
        } else {
            simpleWarps.getLogger().info("The warp " + name + "has no loaded because the 'location.y' no exist or is invalid");
            return;
        }
        // Location Z
        if (yaml.contains("location.z") && yaml.isDouble("location.z")) {
            z = yaml.getDouble("location.z");
        } else {
            simpleWarps.getLogger().info("The warp " + name + "has no loaded because the 'location.z' no exist or is invalid");
            return;
        }
        // Location Yaw
        if (yaml.contains("location.yaw") && yaml.isDouble("location.yaw")) {
            yaw = (float) yaml.getDouble("location.yaw");
        } else {
            simpleWarps.getLogger().info("The warp " + name + "has no loaded because the 'location.yaw' no exist or is invalid");
            return;
        }
        // Location Pitch
        if (yaml.contains("location.pitch") && yaml.isDouble("location.pitch")) {
            pitch = (float) yaml.getDouble("location.pitch");
        } else {
            simpleWarps.getLogger().info("The warp " + name + "has no loaded because the 'location.pitch' no exist or is invalid");
            return;
        }
        // Permission
        if (yaml.contains("permission") && yaml.isString("permission")) {
            permission = yaml.getString("permission");
        } else {
            simpleWarps.getLogger().info("The warp " + name + "has no loaded because the 'permission' no exist or is invalid");
            return;
        }
        // Cost
        if (yaml.contains("cost") && yaml.isDouble("cost")) {
            cost = yaml.getDouble("cost");
        } else {
            simpleWarps.getLogger().info("The warp " + name + "has no loaded because the 'cost' no exist or is invalid");
            return;
        }
        // Warp
        Warp warp = new Warp(name);
        // GUI
        warp.setInGui(inGui);
        warp.setSlot(slot);
        warp.setPreviewItem(previewItem);
        // Location
        warp.setWorld(world);
        warp.setX(x);
        warp.setY(y);
        warp.setZ(z);
        warp.setYaw(yaw);
        warp.setPitch(pitch);
        // Permission
        warp.setPermission(permission);
        // Cost
        warp.setCost(cost);
        warps.put(name, warp);
    }

    public void unloadWarps() {
        for (String name:warps.keySet()) {
            unloadWarp(name);
        }
    }

    public void unloadWarp(String name) {
        warps.remove(name);
    }

    public void saveWarp(String name) {
        Yaml yaml = new Yaml(simpleWarps, "warps", name);
        yaml.registerFileConfiguration();
        Warp warp = warps.get(name);
        yaml.set("gui.enable", warp.isInGui());
        yaml.set("gui.slot", warp.getSlot());
        yaml.setItemStack("gui.previewItem", warp.getPreviewItem());
        yaml.set("location.world", warp.getWorld());
        yaml.set("location.x", warp.getX());
        yaml.set("location.y", warp.getY());
        yaml.set("location.z", warp.getZ());
        yaml.set("location.yaw", warp.getYaw());
        yaml.set("location.pitch", warp.getPitch());
        yaml.set("permission", warp.getPermission());
        yaml.set("cost", warp.getCost());
        yaml.saveFileConfiguration();
    }

    public void addWarp(Warp warp) {
        warps.put(warp.getName(), warp);
        saveWarp(warp.getName());
    }

    public List<Warp> getWarps() {
        List<Warp> warpsList = new ArrayList<>();
        for (String warp:warps.keySet()) {
            warpsList.add(warps.get(warp));
        }
        return warpsList;
    }

    public List<String> getWarpsNames() {
        return new ArrayList<>(warps.keySet());
    }


    public Warp getWarp(String name) {
        return warps.get(name);
    }

    public boolean existWarp(String name) { return warps.containsKey(name); }

}