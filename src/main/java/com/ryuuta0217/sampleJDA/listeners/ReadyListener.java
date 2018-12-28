package com.ryuuta0217.sampleJDA.listeners;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;

import static com.ryuuta0217.sampleJDA.Main.PINDOME_ENABLED;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        event.getJDA().getGuilds().forEach(g -> {
            PINDOME_ENABLED = new HashMap<>();
            PINDOME_ENABLED.put(g.getId(), false);
        });
    }
}
