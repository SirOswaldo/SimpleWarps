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
import club.spfmc.simplewarps.util.inventory.menu.inventories.ConfirmInventory;
import club.spfmc.simplewarps.warp.Warp;
import org.bukkit.inventory.ItemStack;

public class DeleteWarpConfirmInventory extends ConfirmInventory {

    private final SimpleWarps simpleWarps;
    private final Warp warp;

    public DeleteWarpConfirmInventory(SimpleWarps simpleWarps, Warp warp) {
        this.simpleWarps = simpleWarps;
        this.warp = warp;
    }

    @Override
    public String getTitle() {
        return simpleWarps.getSettings().getString("inventory.");
    }

    @Override
    public ItemStack getPanel() {
        return null;
    }

    @Override
    public ItemStack getInformation() {
        return null;
    }

    @Override
    public ItemStack getAccept() {
        return null;
    }

    @Override
    public ItemStack getCancel() {
        return null;
    }

    //todo Crear metodo de eliminar warp
    @Override
    public void onAccept() {
        //simpleWarps.getWarpsManager().
    }

    //todo Terminar este menu
    @Override
    public void onCancel() {

    }

}