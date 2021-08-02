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

import org.bukkit.inventory.ItemStack;

public class Warp {

    private final String name;

    // GUI
    private boolean inGui;
    private int slot;
    private ItemStack previewItem;

    // Location
    private String world;
    private double x, y, z;
    private float yaw, pitch;

    // Permission
    private String permission;
    // Cost
    private double cost;

    public Warp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // GUI
    public void setInGui(boolean inGui) {
        this.inGui = inGui;
    }
    public boolean isInGui() {
        return inGui;
    }
    public void setSlot(int slot) {
        this.slot = slot;
    }
    public int getSlot() {
        return slot;
    }
    public void setPreviewItem(ItemStack previewItem) {
        this.previewItem = previewItem;
    }
    public ItemStack getPreviewItem() {
        return previewItem;
    }

    // Location
    public void setWorld(String world) {
        this.world = world;
    }
    public String getWorld() {
        return world;
    }

    public void setX(double x) {
        this.x = x;
    }
    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getY() {
        return y;
    }

    public void setZ(double z) {
        this.z = z;
    }
    public double getZ() {
        return z;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    public float getYaw() {
        return yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    public float getPitch() {
        return pitch;
    }

    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }

    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

}