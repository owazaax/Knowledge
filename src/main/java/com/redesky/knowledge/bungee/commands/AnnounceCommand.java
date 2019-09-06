package com.redesky.knowledge.bungee.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.redesky.knowledge.bungee.Knowledge;
import com.redesky.knowledge.bungee.utilities.PluginMessage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AnnounceCommand extends Command {

    public AnnounceCommand() {
        super("announce", null, "announcement", "anunciar", "anuncio");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("redesky.anunciar")) {
            sender.sendMessage("§cVocê não tem permissão para executar esse comando.");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUse \"/anunciar <servidor> <mensagem>\".");
            return;
        }

        String message = "";

        for (String word : args) {
            if (word.equals(args[0])) {
                continue;
            }

            message = message.concat(word + " ");
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Announcement");
        out.writeUTF(message.replaceAll("&", "§"));

        new PluginMessage(Knowledge.getInstance(), args[0], "Knowledge", out.toByteArray());
    }

}