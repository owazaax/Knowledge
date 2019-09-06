package com.redesky.knowledge.bukkit.commands;

import com.redesky.knowledge.bukkit.Knowledge;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HelloCommand extends Command implements PluginIdentifiableCommand {

    private final Plugin plugin;

    public HelloCommand(Plugin plugin) {
        super("hello");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(" §cVocê não pode executar esse comando pela consola.");
            return true;
        }

        Player player = (Player) sender;

        player.sendMessage("§eOlá.");
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
        return true;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

}