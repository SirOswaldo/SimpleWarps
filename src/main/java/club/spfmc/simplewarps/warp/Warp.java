package club.spfmc.simplewarps.warp;

import org.bukkit.Location;

public class Warp {

    private final String name;
    private int countdown;
    private Location location;

    public Warp(String name, int countdown, Location location) {
        this.name = name;
        this.countdown = countdown;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public int getCountdown() {
        return countdown;
    }
    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

}