package club.spfmc.simplewarps.commands;

import club.spfmc.simplewarps.SimpleWarps;
import club.spfmc.simplewarps.util.command.SimpleCommand;
import club.spfmc.simplewarps.util.yaml.Yaml;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CreateWarpCommand extends SimpleCommand {

    private final SimpleWarps simpleWarps;

    public CreateWarpCommand(SimpleWarps simpleWarps) {
        super(simpleWarps, "SimpleWarps");
        this.simpleWarps = simpleWarps;
    }

    @Override
    public boolean onPlayerExecute(Player player, Command command, String[] arguments) {
        Yaml messages = simpleWarps.getMessages();
        if (player.hasPermission("SimpleWarps.CreateWarp")) {

        } else {
            messages.sendMessage(player, "CreateWarp.noPermission");
        }
        return true;
    }
}