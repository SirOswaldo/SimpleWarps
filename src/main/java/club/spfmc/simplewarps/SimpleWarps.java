package club.spfmc.simplewarps;

import club.spfmc.simplewarps.commands.*;
import club.spfmc.simplewarps.util.bStats.Metrics;
import club.spfmc.simplewarps.util.chatimput.ChatInputManager;
import club.spfmc.simplewarps.util.inventory.menu.MenuInventoryManager;
import club.spfmc.simplewarps.util.inventory.pages.PagesInventoryManager;
import club.spfmc.simplewarps.util.yaml.Yaml;
import club.spfmc.simplewarps.warp.WarpsManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleWarps extends JavaPlugin {

    private final Yaml settings = new Yaml(this, "settings");
    private final Yaml messages = new Yaml(this, "messages");

    private final WarpsManager warpsManager = new WarpsManager(this);

    private MenuInventoryManager menuInventoryManager = new MenuInventoryManager();
    public MenuInventoryManager getMenuInventoryManager() {
        return menuInventoryManager;
    }

    private final PagesInventoryManager pagesInventoryManager = new PagesInventoryManager();
    public PagesInventoryManager getPagesInventoryManager() {
        return pagesInventoryManager;
    }

    private final ChatInputManager chatInputManager = new ChatInputManager();
    public ChatInputManager getChatInputManager() {
        return chatInputManager;
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
        pluginManager.registerEvents(pagesInventoryManager, this);
        pluginManager.registerEvents(menuInventoryManager, this);
        pluginManager.registerEvents(chatInputManager, this);
    }

    private void registerCommands() {
        new SimpleWarpsCommand(this);
        new ManageWarpsCommand(this);
        new CreateWarpCommand(this);
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
