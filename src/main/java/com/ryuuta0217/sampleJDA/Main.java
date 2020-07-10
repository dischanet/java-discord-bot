package com.ryuuta0217.sampleJDA;

import com.ryuuta0217.sampleJDA.listeners.CommandListener;
import com.ryuuta0217.sampleJDA.listeners.SimplePinDomeKun;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static String PREFIX = "!";
    public static boolean PINDOME_ENABLED = false;
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

        //Discordへのログイン
        JDABuilder.createDefault(token)
                .setAutoReconnect(true)
                .setMaxReconnectDelay(60)
                .addEventListeners(new CommandListener(), new SimplePinDomeKun())
                .build();
    }
}