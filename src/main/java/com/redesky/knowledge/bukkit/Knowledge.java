package com.redesky.knowledge.bukkit;

import com.redesky.knowledge.bukkit.commands.HelloCommand;
import com.redesky.knowledge.bukkit.config.Config;
import com.redesky.knowledge.bukkit.events.KnowledgeListener;
import com.redesky.knowledge.shared.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Knowledge extends JavaPlugin {

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
            getServer().shutdown();
        }

        /*
         * Também sei salvar dados por .db
         */

        try {
            PreparedStatement statement = Database.prepareStatement("CREATE TABLE IF NOT EXISTS knowledge (ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, NICK VARCHAR(16), ENTRADAS TINYINT NOT NULL, SAIDAS TINYINT NOT NULL)");
            statement.executeUpdate();
            statement.close();
            debug("Tabela 'shared_login' carregada com sucesso.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            debug("Ocorreu um erro ao carregar a tabela 'shared_login', desligando servidor...");
            getServer().shutdown();
        }

        getServer().getMessenger().registerIncomingPluginChannel(this, "Knowledge", new KnowledgeListener());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "Knowledge");

        getServer().getPluginManager().registerEvents(new KnowledgeListener(), instance);

        addCommand(new HelloCommand(this));
    }

    @Override
    public void onDisable() {
        try {
            Database.closeConnection();
            debug("Conecxão com a base de dados terminada com sucesso.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            debug("Ocorreu um erro ao terminar a conecxão com a base de dados, desligando servidor...");
            getServer().shutdown();
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

    public final void addCommand(Command command) {
        CommandMap map;
        Field field;

        try {
            field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            map = (CommandMap) field.get(getServer());
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
            return;
        }

        if (map.getCommand(command.getName()) == null) {
            map.register("knowledge", command);
        }
    }

}