package club.spfmc.simplewarps;

import club.spfmc.simplewarps.commands.CreateWarpCommand;
import club.spfmc.simplewarps.commands.ManageWarpsCommand;
import club.spfmc.simplewarps.commands.SimpleWarpsCommand;
import club.spfmc.simplewarps.commands.WarpsCommand;
import club.spfmc.simplewarps.util.bStats.Metrics;
import club.spfmc.simplewarps.util.inventory.menu.SimpleMenuInventory;
import club.spfmc.simplewarps.util.inventory.pages.PagesInventoryManager;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.WarpsManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleWarps extends JavaPlugin {

    private final Yaml settings = new Yaml(this, "settings");
    private final Yaml messages = new Yaml(this, "messages");

    private final WarpsManager warpsManager = new WarpsManager(this);

    private SimpleMenuInventory simpleMenuInventory = new SimpleMenuInventory();
    public SimpleMenuInventory getSimpleMenuInventory() {
        return simpleMenuInventory;
    }


    private final PagesInventoryManager pagesInventoryManager = new PagesInventoryManager();
    public PagesInventoryManager getPagesInventoryManager() {
        return pagesInventoryManager;
    }

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
        // Load Warps
        warpsManager.loadWarps();
        // Registers
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(pagesInventoryManager, this);
        pluginManager.registerEvents(simpleMenuInventory, this);
    }

    private void registerCommands() {
        new SimpleWarpsCommand(this);
        new ManageWarpsCommand(this);
        new CreateWarpCommand(this);
        new WarpsCommand(this);
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
