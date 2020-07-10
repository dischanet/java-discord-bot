package com.ryuuta0217.sampleJDA.listeners;

import com.ryuuta0217.sampleJDA.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static com.ryuuta0217.sampleJDA.Main.PINDOME_ENABLED;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //Bot自身のメッセージには反応しない
        if (event.getAuthor().equals(event.getJDA().getSelfUser())) return;

        //メッセージが ! から始まる場合
        if (event.getMessage().getContentRaw().startsWith(Main.PREFIX)) {

            //Prefixを除いたものだけにする
            String Command = event.getMessage().getContentRaw().replaceAll("^" + Main.PREFIX, "");

            //about
            if(Command.equalsIgnoreCase("about")) {
                MessageEmbed eb = new EmbedBuilder()
                        .setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl())
                        .setTitle("Java JDA Example Bot by [dischadev]ryuuta0217", "https://github.com/ryuuta0217")
                        .setDescription("pingコマンド、pinコマンド、aboutコマンドが使用可能です :relaxed:")
                        .build();
                event.getChannel().sendMessage(eb).queue();
                return;
            }

            //ping
            if (Command.equalsIgnoreCase("ping") || Command.startsWith("ping")) {
                event.getChannel().sendMessage("Pong!").queue();
                event.getChannel().sendMessage("Gateway Ping is " + event.getJDA().getGatewayPing() + "ms").queue();
                event.getChannel().sendMessage("RestAction Ping is " + event.getJDA().getRestPing().complete()).queue();
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
            if (Command.startsWith("pin")) event.getChannel().sendMessage("`" + Main.PREFIX + "pin <enable/disable>`").queue();
        }
    }
}
