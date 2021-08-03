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
import club.spfmc.simplewarps.inventories.EditWarpInventory;
import club.spfmc.simplewarps.util.input.inputs.DropInput;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemInput implements DropInput {

    private final SimpleWarps simpleWarps;
    private final Warp warp;
    private final String from;

    public ItemInput(SimpleWarps simpleWarps, Warp warp, String from) {
        this.simpleWarps = simpleWarps;
        this.warp = warp;
        this.from = from;
    }

    @Override
    public void onPLayerDrop(Player player, ItemStack itemStack) {
        warp.setPreviewItem(itemStack);
        simpleWarps.getWarpsManager().saveWarp(warp.getName());
        simpleWarps.getMenuInventoryManager().openInventory(player, new EditWarpInventory(simpleWarps, warp, from));
    }

    @Override
    public void onPlayerSneak(Player player) {
        simpleWarps.getMenuInventoryManager().openInventory(player, new EditWarpInventory(simpleWarps, warp, from));
    }

}