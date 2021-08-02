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
import club.spfmc.simplewarps.util.inventory.menu.Item;
import club.spfmc.simplewarps.util.inventory.menu.MenuInventory;
import club.spfmc.simplewarps.warp.Warp;
import club.spfmc.simplewarps.warp.WarpsManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SelectPositionInventory extends MenuInventory {

    private final SimpleWarps simpleWarps;
    private final Warp warp;

    public SelectPositionInventory(SimpleWarps simpleWarps, Warp warp) {
        this.simpleWarps = simpleWarps;
        WarpsManager warpsManager = simpleWarps.getWarpsManager();
        this.warp = warp;
        for (Warp i:warpsManager.getWarps()) {
            if (getRows() * 9 > i.getSlot()) {
                addMenuAction(i.getSlot(), new Item() {
                    @Override
                    public ItemStack getItem() {
                        return simpleWarps.getSettings().getItemStack("inventory.selectPosition.items.blocked");
                    }
                });
            }
        }
        for (int i = 0; i < (getRows()*9); i++) {
            if (!getItems().containsKey(i)) {
                int slot = i;
                if (getRows() * 9 > i) {
                    addMenuAction(i, new Item() {
                        @Override
                        public ItemStack getItem() {
                            return simpleWarps.getSettings().getItemStack("inventory.selectPosition.items.free");
                        }

                        @Override
                        public void onLeftClick(Player player) {
                            player.closeInventory();
                            warp.setSlot(slot);
                            simpleWarps.getServer().dispatchCommand(player, "EditWarp " + warp.getName());
                        }
                    });
                }
            }
        }
    }

    @Override
    public String getTitle() {
        return simpleWarps.getSettings().getString("inventory.selectPosition.title");
    }

    @Override
    public int getRows() {
        return simpleWarps.getSettings().getInt("inventory.warps.rows");
    }

}