package club.spfmc.simplewarps.warp;

import club.spfmc.simplewarps.SimpleWarps;
import club.spfmc.simplewarps.util.yaml.Yaml;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;

public class WarpsManager {

    private final SimpleWarps simpleWarps;

    public WarpsManager(SimpleWarps simpleWarps) {
        this.simpleWarps = simpleWarps;
    }

    private final HashMap<String, Warp> warps = new HashMap<>();

    public void loadWarps() {
        File dir = new File(simpleWarps.getDataFolder() + File.separator + "warps");
        if (dir.exists()) {
            if (dir.isDirectory()) {
                File[] files = dir.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.getName().endsWith(".yml");
                    }
                });
                for (File file:files) {
                    loadWarp(file.getName().replaceAll(".yml", ""));
                }
            }
        }
    }

    public void loadWarp(String name) {
        Yaml yaml = new Yaml(simpleWarps, "warps", name);
        yaml.registerFileConfiguration();
        int countdown = yaml.getInt("countdown");
        Location location = yaml.getLocation("location");
        Warp warp = new Warp(name, countdown, location);
        warps.put(name, warp);
    }

    public void unloadWarps() {
        for (String name:warps.keySet()) {
            unloadWarp(name);
        }
    }

    public void unloadWarp(String name) {
        saveWarp(name);
        warps.remove(name);
    }

    public void saveWarp(String name) {
        Warp warp = warps.get(name);
        Yaml yaml = new Yaml(simpleWarps, "warps", name);
        yaml.registerFileConfiguration();
        yaml.set("countdown", warp.getCountdown());
        yaml.setLocation("location", warp.getLocation());
        yaml.saveFileConfiguration();
    }

    public void addWarp(Warp warp) {
        warps.put(warp.getName(), warp);
        saveWarp(warp.getName());
    }

    public Warp getWarp(String warp) {
        return warps.get(warp);
    }

}