package com.ryuuta0217.sampleJDA.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PingCommand extends Command {
    public PingCommand() {
        this.name = "ping";
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("Pong!");
        event.reply("WebSocket Ping is " + event.getJDA().getPing() + "ms");
    }
}
