package com.redesky.knowledge.bukkit.utilities;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PluginMessage extends BukkitRunnable {

    /*
     * Não usei no pl, mas depois é só correr em async
     */

    private static Plugin plugin;
    private static Player player;
    private static String channel;
    private static byte[] message;

    public PluginMessage(Plugin plugin, Player player, String channel, byte[] message) {
        this.plugin = plugin;
        this.player = player;
        this.channel = channel;
        this.message = message;
    }

    @Override
    public void run() {
        player.sendPluginMessage(plugin, channel, message);
    }

}