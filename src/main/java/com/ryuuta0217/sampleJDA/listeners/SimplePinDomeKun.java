package com.ryuuta0217.sampleJDA.listeners;

import com.ryuuta0217.sampleJDA.Main;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class SimplePinDomeKun extends ListenerAdapter {
    @Override
    //メッセージにリアクションが追加された時に呼び出されます。
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        //ピン留め機能が有効じゃないなら何もせず終了
        if(!Main.PINDOME_ENABLED) return;

        //メッセージ管理の権限のチェック
        if(event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            //追加されたリアクションが 📌 かどうか
            if(event.getReactionEmote().getName().equals("\uD83D\uDCCC")) {
                //📌 ならチャンネルのピン留めリストを確認
                if(event.getChannel().getPinnedMessages().complete().stream().noneMatch(m -> m.getId().equals(event.getMessageId()))) {
                    //ピン留めリストに存在しなかったらピン留め
                    event.getChannel().pinMessageById(event.getMessageId()).complete();
                }
            }
        } else {
            //権限がないときは、リアクションを追加した人のDMに権限がない旨を伝える。
            event.getUser().openPrivateChannel().complete().sendMessage(String.format("サーバー %s チャンネル %s のメッセージ %s をピン留めしようとしましたが、Botが権限を所有していません。\n" +
                            "管理者に要求するか、権限をお持ちの場合は権限を %s に付与してください。",
                    event.getGuild().getName(),
                    event.getChannel().getAsMention(),
                    event.getChannel().getMessageById(event.getMessageId()).complete().getContentRaw().length() > 7 ? event.getChannel().getMessageById(event.getMessageId()).complete().getContentRaw().substring(0, 7)+"..." : event.getChannel().getMessageById(event.getMessageId()).complete().getContentRaw(),
                    event.getJDA().getSelfUser().getAsMention())).queue();
        }
    }

    @Override
    //メッセージから 📌 のリアクションが削除された時に呼び出されます。
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        //ピン留め機能が有効じゃないなら何もせず終了
        if(!Main.PINDOME_ENABLED) return;

        //メッセージ管理の権限のチェック
        if(event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            //削除されたリアクションが 📌 かどうか
            if(event.getReactionEmote().getName().equals("\uD83D\uDCCC")) {
                //📌 ならチャンネルのピン留めリストを確認
                if(event.getChannel().getPinnedMessages().complete().stream().anyMatch(m -> m.getId().equals(event.getMessageId()))) {
                    //ピン留めリストに存在したらピン留めを解除
                    event.getChannel().unpinMessageById(event.getMessageId()).complete();
                }
            }
        } else {
            //ピン留めされているメッセージの中に対象のメッセージが存在するかどうか。存在しないなら権限がない旨を伝えずに終了。
            if(event.getChannel().getPinnedMessages().complete().stream().noneMatch(m -> m.getId().equals(event.getMessageId()))) return;

            //リアクションを削除した人のDMに権限がない旨を伝える。
            event.getUser().openPrivateChannel().complete().sendMessage(String.format("サーバー %s チャンネル %s のメッセージ %s のピン留めを削除しようとしましたが、Botが権限を所有していません。\n" +
                    "管理者に要求するか、権限をお持ちの場合は権限を %s に付与してください。",
                    event.getGuild().getName(),
                    event.getChannel().getName(),
                    event.getChannel().getMessageById(event.getMessageId()).complete().getContentRaw().length() > 7 ? event.getChannel().getMessageById(event.getMessageId()).complete().getContentRaw().substring(0, 7)+"..." : event.getChannel().getMessageById(event.getMessageId()).complete().getContentRaw(),
                    event.getJDA().getSelfUser().getAsMention())).queue();
        }
    }
}
