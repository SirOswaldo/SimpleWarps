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

package club.spfmc.simplewarps.inventories;

import club.spfmc.simplewarps.SimpleWarps;
import club.spfmc.simplewarps.inputs.CostInput;
import club.spfmc.simplewarps.inputs.PermissionInput;
import club.spfmc.simplewarps.util.chatimput.ChatInput;
import club.spfmc.simplewarps.util.inventory.menu.Item;
import club.spfmc.simplewarps.util.inventory.menu.MenuInventory;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EditWarpInventory extends MenuInventory {

    private final SimpleWarps simpleWarps;
    private final Warp warp;

    public EditWarpInventory(SimpleWarps simpleWarps, Warp warp) {
        this.simpleWarps = simpleWarps;
        this.warp = warp;
        Yaml settings = simpleWarps.getSettings();
        Yaml messages = simpleWarps.getMessages();

        // Panel
        for (int i = 0; i < 45; i++) {
            addMenuAction(i, new Item() {
                @Override
                public ItemStack getItem() {
                    return settings.getItemStack("inventory.editWarp.items.panel");
                }
            });
        }
        // PreviewItem
        addMenuAction(10, new Item() {
            @Override
            public ItemStack getItem() {
                return warp.getPreviewItem();
            }
        });
        // Change Visible in GUI
        addMenuAction(12, new Item() {
            @Override
            public ItemStack getItem() {
                String status;
                if (warp.isInGui()) {
                    status = messages.getString("enabled");
                } else {
                    status = messages.getString("disabled");
                }
                return Yaml.replace(settings.getItemStack("inventory.editWarp.items.visible"), new String[][] {
                        {"%status%", status}
                });
            }

            @Override
            public void onLeftClick(Player player) {
                warp.setInGui(!warp.isInGui());
                simpleWarps.getWarpsManager().saveWarp(warp.getName());
                player.getOpenInventory().setItem(12 , getItem());
            }
        });
        // Change Item in GUI
        addMenuAction(14, new Item() {
            @Override
            public ItemStack getItem() {
                return settings.getItemStack("inventory.editWarp.items.preview");
            }
        });
        // Change Position in GUI
        addMenuAction(16, new Item() {
            @Override
            public ItemStack getItem() {
                return Yaml.replace(settings.getItemStack("inventory.editWarp.items.position"), new String[][] {
                        {"%warp_position%", warp.getSlot() + ""}
                });
            }

            @Override
            public void onLeftClick(Player player) {
                simpleWarps.getMenuInventoryManager().openInventory(player, new SelectPositionInventory(simpleWarps, warp));
            }
        });
        // Change Location
        addMenuAction(30, new Item() {
            @Override
            public ItemStack getItem() {
                return Yaml.replace(settings.getItemStack("inventory.editWarp.items.location"), new String[][] {
                        {"%warp_world%", warp.getWorld()},
                        {"%warp_x%", Math.round(warp.getX()) + ""},
                        {"%warp_y%", Math.round(warp.getY()) + ""},
                        {"%warp_z%", Math.round(warp.getZ()) + ""},
                        {"%warp_yaw%", Math.round(warp.getYaw()) + ""},
                        {"%warp_pitch%", Math.round(warp.getPitch()) + ""}
                });
            }

            @Override
            public void onLeftClick(Player player) {

            }
        });
        // Change Permission
        addMenuAction(32, new Item() {
            @Override
            public ItemStack getItem() {
                return Yaml.replace(settings.getItemStack("inventory.editWarp.items.permission"), new String[][] {
                        {"%warp_permission%", warp.getPermission()}
                });
            }

            @Override
            public void onLeftClick(Player player) {
                player.closeInventory();
                ChatInput input = new PermissionInput(simpleWarps, warp);
                simpleWarps.getChatInputManager().addChatInput(player, input);
                messages.sendMessage(player, "editWarp.permission.enterNewPermission");
            }

            @Override
            public void onRightClick(Player player) {
                warp.setPermission("simple.teleport.warp." + warp.getName());
                player.getOpenInventory().setItem(32, getItem());
            }
        });
        // Change Cost
        addMenuAction(34, new Item() {
            @Override
            public ItemStack getItem() {
                return Yaml.replace(settings.getItemStack("inventory.editWarp.items.cost"), new String[][] {
                        {"%warp_cost%", Math.round(warp.getCost()) + ""}
                });
            }

            @Override
            public void onLeftClick(Player player) {
                player.closeInventory();
                ChatInput input = new CostInput(simpleWarps, warp);
                simpleWarps.getChatInputManager().addChatInput(player, input);
                messages.sendMessage(player, "editWarp.cost.enterNewCost");
            }
        });
    }

    @Override
    public String getTitle() {
        String title = simpleWarps.getSettings().getString("inventory.editWarp.title", "Editing Warp: %warp_name%");
        return title.replaceAll("%warp_name%", warp.getName());
    }

    @Override
    public int getRows() {
        return 5;
    }
}