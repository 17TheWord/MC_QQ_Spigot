package com.github.theword;

import com.github.theword.returnBody.ActionbarReturnBody;
import com.github.theword.returnBody.BaseReturnBody;
import com.github.theword.returnBody.MessageReturnBody;
import com.github.theword.returnBody.SendTitleReturnBody;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.github.theword.MC_QQ.instance;
import static com.github.theword.parse.ParseJsonToClass.parseMessageToTextComponent;

public class Utils {

    /**
     * 定义方法 Say()
     * 向服务器后台发送信息
     */
    public static void say(String msg) {
        CommandSender sender = Bukkit.getConsoleSender();
        sender.sendMessage("[MC_QQ] " + msg);
    }

    /**
     * 来自 NoneBot 的 JSON 消息的处理
     */
    static void parseWebSocketJson(String message) {
        // 组合消息
        Gson gson = new Gson();
        BaseReturnBody baseReturnBody = gson.fromJson(message, BaseReturnBody.class);
        JsonElement data = baseReturnBody.getData();
        switch (baseReturnBody.getApi()) {
            case "broadcast":
                MessageReturnBody messageList = gson.fromJson(data, MessageReturnBody.class);
                TextComponent textComponent = parseMessageToTextComponent(messageList.getMessageList());
                instance.getServer().spigot().broadcast(textComponent);
                break;
            case "send_title":
                SendTitleReturnBody sendTitleReturnBody = gson.fromJson(data, SendTitleReturnBody.class);
                for (Player player : instance.getServer().getOnlinePlayers()) {
                    player.sendTitle(
                            sendTitleReturnBody.getSendTitle().getTitle(),
                            sendTitleReturnBody.getSendTitle().getSubtitle(),
                            sendTitleReturnBody.getSendTitle().getFadein(),
                            sendTitleReturnBody.getSendTitle().getStay(),
                            sendTitleReturnBody.getSendTitle().getFadeout()
                    );
                }
                break;
            case "actionbar":
                ActionbarReturnBody actionMessageList = gson.fromJson(data, ActionbarReturnBody.class);
                TextComponent actionTextComponent = parseMessageToTextComponent(actionMessageList.getMessageList());
                for (Player player : instance.getServer().getOnlinePlayers()) {
                    sendActionBar(player, actionTextComponent);
                }
                break;
        }
    }

    static void sendActionBar(Player player, TextComponent textComponent) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
    }

    /**
     * 字符串转为 unicode 编码
     *
     * @param string 字符串
     * @return unicode编码
     */
    static String unicodeEncode(String string) {
        char[] utfBytes = string.toCharArray();
        StringBuilder unicodeBytes = new StringBuilder();
        for (char utfByte : utfBytes) {
            String hexB = Integer.toHexString(utfByte);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes.append("\\u").append(hexB);
        }
        return unicodeBytes.toString();
    }
}
