package club.spfmc.simplewarps;

import club.spfmc.simplewarps.commands.*;
import club.spfmc.simplewarps.util.bStats.Metrics;
import club.spfmc.simplewarps.util.input.InputManager;
import club.spfmc.simplewarps.util.inventory.MenuInventoryManager;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.WarpsManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleWarps extends JavaPlugin {

    private final Yaml settings = new Yaml(this, "settings");
    private final Yaml messages = new Yaml(this, "messages");

    private final WarpsManager warpsManager = new WarpsManager(this);

    private final MenuInventoryManager menuInventoryManager = new MenuInventoryManager();
    public MenuInventoryManager getMenuInventoryManager() {
        return menuInventoryManager;
    }

    private final InputManager inputManager = new InputManager();
    public InputManager getInputManager() {
        return inputManager;
    }

    @Override
    public void onEnable() {
        // bStats
        int pluginId = 12264;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SingleLineChart("homes", () -> Yaml.getFolderFiles(getDataFolder() + "/warps").size()));
        // Yaml Files
        registerFiles();
        // Load Warps
        warpsManager.loadWarps();
        // Registers
        registerListeners();
        registerCommands();
        registerFiles();
    }

    @Override
    public void onDisable() {

    }

    private void registerFiles() {
        messages.reloadFileConfiguration();
        if (!messages.getString("version").equals(getDescription().getVersion())) {
            Yaml backup = new Yaml(this, "backup-settings");
            backup.registerFileConfiguration();
            backup.saveWithOtherFileConfiguration(messages.getFileConfiguration());
            messages.deleteFileConfiguration();
        }
        settings.reloadFileConfiguration();
        if (!settings.getString("version").equals(getDescription().getVersion())) {
            Yaml backup = new Yaml(this, "backup-settings");
            backup.registerFileConfiguration();
            backup.saveWithOtherFileConfiguration(settings.getFileConfiguration());
            settings.deleteFileConfiguration();
        }
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(menuInventoryManager, this);
        pluginManager.registerEvents(inputManager, this);
    }

    private void registerCommands() {
        new SimpleWarpsCommand(this);
        new ManageWarpsCommand(this);
        new CreateWarpCommand(this);
        new DeleteWarpCommand(this);
        new WarpsCommand(this);
        new EditWarpCommand(this);

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
