package com.ryuuta0217.sampleJDA;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.ryuuta0217.sampleJDA.commands.AboutCommand;
import com.ryuuta0217.sampleJDA.commands.PinCommand;
import com.ryuuta0217.sampleJDA.commands.PingCommand;
import com.ryuuta0217.sampleJDA.listeners.ReadyListener;
import com.ryuuta0217.sampleJDA.listeners.SimplePinDomeKun;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class Main {
    public static Map<String, Boolean> PINDOME_ENABLED;
    public static void main(String[] args) throws LoginException, IOException {

        //Token取得関連
        String token;
        if(System.getenv("JAVA_SAMPLE_BOT_TOKEN") == null) {
            if(!new File("./token").exists()) {
                System.out.println("Tokenが見つかりません。終了します。");
                return;
            } else token = Files.readAllLines(new File("./token").toPath()).get(0);
        } else {
            token = System.getenv("JAVA_SAMPLE_BOT_TOKEN");
        }

        CommandClientBuilder ccb = new CommandClientBuilder();

        ccb.setPrefix("!");
        ccb.setAlternativePrefix("s!");
        ccb.setOwnerId("249869841706516481");
        ccb.addCommands(new PinCommand(), new PingCommand(), new AboutCommand());

        //Discordへのログイン
        new JDABuilder()
                .setToken(token)
                .setAutoReconnect(true)
                .setMaxReconnectDelay(60)
                .addEventListener(ccb.build(), new SimplePinDomeKun(), new ReadyListener())
                .build();
    }
}