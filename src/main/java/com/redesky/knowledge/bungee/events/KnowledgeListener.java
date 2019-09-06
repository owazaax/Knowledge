package com.redesky.knowledge.bungee.events;

import com.redesky.knowledge.bungee.managers.KnowledgeManager;
import com.redesky.knowledge.shared.api.KnowledgeAPI;
import com.redesky.knowledge.shared.objects.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class KnowledgeListener implements Listener {

    @EventHandler
    public void onPlayerPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (KnowledgeManager.users.containsKey(player.getName().toLowerCase())) {
            KnowledgeManager.users.get(player.getName().toLowerCase()).addJoined(1);
        } else {
            User user = KnowledgeAPI.loadPlayer(player.getName());
            user.addJoined(1);
            KnowledgeManager.users.put(player.getName().toLowerCase(), user);
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        KnowledgeManager.users.get(player.getName().toLowerCase()).addLeft(1);
    }

}