package com.ryuuta0217.sampleJDA.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import static com.ryuuta0217.sampleJDA.Main.PINDOME_ENABLED;

public class PinCommand extends Command {
    public PinCommand() {
        this.name = "pin";
        this.guildOnly = false;
    }
    @Override
    protected void execute(CommandEvent event) {
        if(event.getArgs().isEmpty()) event.reply("`!pin <enable/disable>`");
        else {
            if (event.getArgs().equalsIgnoreCase("enable")) PINDOME_ENABLED.put(event.getGuild().getId(), true);
            else if (event.getArgs().equalsIgnoreCase("disable")) PINDOME_ENABLED.put(event.getGuild().getId(), false);
            else return;
            event.reply(PINDOME_ENABLED.get(event.getGuild().getId()) ? "ピン留めくん機能を有効化しました。" : "ピン留めくん機能を無効化しました。");
        }
    }
}
