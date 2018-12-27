package com.ryuuta0217.sampleJDA.listeners;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static com.ryuuta0217.sampleJDA.Main.PINDOME_ENABLED;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //Bot自身のメッセージには反応しない
        if (event.getAuthor().equals(event.getJDA().getSelfUser())) return;

        //メッセージが ! から始まる場合
        if (event.getMessage().getContentRaw().startsWith("!")) {

            //Prefixを除いたものだけにする
            String Command = event.getMessage().getContentRaw().replaceAll("^!", "");

            //pingコマンド
            if (Command.equalsIgnoreCase("ping") || Command.startsWith("ping")) {
                event.getChannel().sendMessage("Pong!").queue();
                event.getChannel().sendMessage("WebSocket Ping is " + event.getJDA().getPing() + "ms").queue();
                return;
            }

            //pin+オプション付
            if (Command.startsWith("pin ")) {
                String[] args = Command.replaceAll("^pin ", "").split(" ");
                if (args[0].equalsIgnoreCase("enable")) PINDOME_ENABLED = true;
                else if (args[0].equalsIgnoreCase("disable")) PINDOME_ENABLED = false;
                else return;
                event.getChannel().sendMessage(PINDOME_ENABLED ? "ピン留めくん機能を有効化しました。" : "ピン留めくん機能を無効化しました。").queue();
                return;
            }

            //pinのみ
            if (Command.startsWith("pin")) event.getChannel().sendMessage("`!pin <enable/disable>`").queue();
        }
    }
}
