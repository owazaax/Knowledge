package com.redesky.knowledge.bungee.utilities;

import net.md_5.bungee.api.plugin.Plugin;

public class PluginMessage {

    public PluginMessage(Plugin plugin, String server, String channel, byte[] message) {
        plugin.getProxy().getServerInfo(server).sendData(channel, message);
    }

}