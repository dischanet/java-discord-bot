package com.ryuuta0217.sampleJDA.listeners;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static com.ryuuta0217.sampleJDA.Main.PINDOME_ENABLED;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //Bot自身のメッセージには反応しない
        if (event.getAuthor().equals(event.getJDA().getSelfUser())) return;

        //pingコマンド
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!ping")) {
            event.getChannel().sendMessage("Pong!").queue();
            event.getChannel().sendMessage("WebSocket Ping is " + event.getJDA().getPing() + "ms").queue();
            return;
        }

        //pinのみ
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!pin")) event.getChannel().sendMessage("`!pin <enable/disable>`").queue();

        //pin+オプション付
        if (event.getMessage().getContentRaw().matches("^(?i)!pin .*")) {
            String args = event.getMessage().getContentRaw().replaceFirst("^!pin ", "");
            if (args.equalsIgnoreCase("enable")) PINDOME_ENABLED = true;
            else if (args.equalsIgnoreCase("disable")) PINDOME_ENABLED = false;
            else return;
            event.getChannel().sendMessage(PINDOME_ENABLED ? "ピン留めくん機能を有効化しました。" : "ピン留めくん機能を無効化しました。").queue();
        }
    }
}
