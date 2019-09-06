package com.redesky.knowledge.bungee;

import com.redesky.knowledge.bungee.commands.AnnounceCommand;
import com.redesky.knowledge.bungee.config.Config;
import com.redesky.knowledge.bungee.events.KnowledgeListener;
import com.redesky.knowledge.bungee.managers.KnowledgeManager;
import com.redesky.knowledge.shared.api.KnowledgeAPI;
import com.redesky.knowledge.shared.database.Database;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Knowledge extends Plugin {

    private static Knowledge instance;
    private static Config settings;

    @Override
    public void onEnable() {
        instance = this;
        settings = new Config(this, "config.yml");

        if (!settings.existsConfig()) {
            settings.saveDefaultConfig();
        }

        try {
            Database.openConnection(settings.getString("mysql.hostname"), settings.getInt("mysql.port"), settings.getString("mysql.database"), settings.getString("mysql.username"), settings.getString("mysql.password"));
            debug("Conecxão com a base de dados estabelecida com sucesso.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            debug("Ocorreu um erro ao estabelecer uma conecxão com a base de dados, desligando servidor...");
            getProxy().stop();
        }

        /*
         * Também sei salvar dados por .db
         */

        try {
            PreparedStatement statement = Database.prepareStatement("CREATE TABLE IF NOT EXISTS knowledge (ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, NICK VARCHAR(16), ENTRADAS TINYINT NOT NULL, SAIDAS TINYINT NOT NULL)");
            statement.executeUpdate();
            statement.close();
            debug("Tabela 'knowledge' carregada com sucesso.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            debug("Ocorreu um erro ao carregar a tabela 'knowledge', desligando servidor...");
            getProxy().stop();
        }

        getProxy().registerChannel("Knowledge");

        getProxy().getPluginManager().registerListener(this, new KnowledgeListener());

        getProxy().getPluginManager().registerCommand(this, new AnnounceCommand());
    }

    @Override
    public void onDisable() {
        KnowledgeManager.users.forEach((name, user) -> KnowledgeAPI.savePlayer(user));

        try {
            Database.closeConnection();
            debug("Conecxão com a base de dados terminada com sucesso.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            debug("Ocorreu um erro ao terminar a conecxão com a base de dados, desligando servidor...");
            getProxy().stop();
        }
    }

    public static void debug(String message) {
        instance.getLogger().info(message);
    }

    public static Knowledge getInstance() {
        return instance;
    }

    public static Config getSettings() {
        return settings;
    }

}