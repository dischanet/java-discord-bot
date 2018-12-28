package com.ryuuta0217.sampleJDA.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;

import java.awt.*;
import java.util.Date;

public class AboutCommand extends Command {
    public AboutCommand() {
        this.name = "about";
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        String c = "";

        //将来的にContributorが増えた時の為
        if(c.length() > 2000) c = c.substring(0, 1997)+"...";
        event.reply(new EmbedBuilder()
                .setAuthor("JDA Example Bot by **dischanet dev team[ryuuta0217]**", "https://github.com/ryuuta0217", "https://avatars3.githubusercontent.com/u/33993422")
                .setColor(Color.GREEN)
                .addField("Contributors", (c.isEmpty() ? "誰もいないようです..." : c), false)
                .build());
    }
}
