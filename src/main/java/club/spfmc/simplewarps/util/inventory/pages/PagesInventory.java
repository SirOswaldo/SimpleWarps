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

package club.spfmc.simplewarps.util.inventory.pages;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class PagesInventory {

    private final String title;
    private final int rows;
    private int page;
    private List<Object> objects;

    public PagesInventory(String title, int rows, List<Object> objects) {
        this.title = title;
        this.rows = rows;
        page = 1;
        this.objects = objects;
    }

    public String getTitle() {
        return title;
    }
    public int getRows() {
        return rows;
    }

    public void setPage(int page) {
        this.page = page;
    }
    public int getPage() {
        return page;
    }

    public List<Object> getObjects() {
        return objects;
    }



    public abstract ItemStack getListedItem(Object object);
    public abstract ItemStack getPanel();
    public abstract ItemStack getInformation();
    public abstract ItemStack getPrevious();
    public abstract ItemStack getNext();
    public abstract ItemStack getClose();



    public void onLeftClick(Player player, Object object) {};
    public void onRightClick(Player player, Object object) {};
    public void onMiddleClick(Player player, Object object) {};
    public void onShiftRightClick(Player player, Object object) {};
    public void onShiftLeftClick(Player player, Object object) {};


}
