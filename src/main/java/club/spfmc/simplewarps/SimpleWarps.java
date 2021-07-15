package club.spfmc.simplewarps;

import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.WarpsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleWarps extends JavaPlugin {

    private final Yaml settings = new Yaml(this, "settings");
    private final Yaml messages = new Yaml(this, "messages");

    private WarpsManager warpsManager;

    @Override
    public void onEnable() {
        warpsManager = new WarpsManager(this);
        warpsManager.loadWarps();
    }

    @Override
    public void onDisable() {
        warpsManager.unloadWarps();
    }

    public Yaml getSettings() {
        return settings;
    }
    public Yaml getMessages() {
        return messages;
    }

    public WarpsManager getWarpsManager() {
        return warpsManager;
    }
}
