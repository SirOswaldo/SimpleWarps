package club.spfmc.simplewarps;

import club.spfmc.simplewarps.util.bStats.Metrics;
import club.spfmc.simplewarps.util.yaml.Yaml;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleWarps extends JavaPlugin {

    private final Yaml settings = new Yaml(this, "settings");
    private final Yaml messages = new Yaml(this, "messages");


    @Override
    public void onEnable() {
        // bStats
        int pluginId = 12264;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SingleLineChart("homes", () -> {
            int warps = Yaml.getFolderFiles(getDataFolder() + "/warps").size();
            return warps;
        }));
        // Yaml Files
        settings.registerFileConfiguration();
        messages.registerFileConfiguration();
    }

    @Override
    public void onDisable() {

    }

    public Yaml getSettings() {
        return settings;
    }
    public Yaml getMessages() {
        return messages;
    }

}
