package com.github.theword;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import static com.github.theword.MC_QQ.*;
import static com.github.theword.Utils.parseWebSocketJson;
import static com.github.theword.Utils.say;

public class WSClient extends WebSocketClient {


    public WSClient() throws URISyntaxException {
        super(new URI(ConfigReader.getWsUrl()), httpHeaders);
    }

    /**
     * 连接打开时
     *
     * @param serverHandshake ServerHandshake
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        connectTime = 0;
        say("已成功连接 WebSocket 服务器。");
    }

    /**
     * 收到消息时触发
     * 向服务器游戏内公屏发送信息
     */
    @Override
    public void onMessage(String message) {
        if (ConfigReader.getEnable()) {
            parseWebSocketJson(message);
        }
    }

    /**
     * 关闭时
     *
     * @param i 关闭码
     * @param s 关闭信息
     * @param b 是否关闭
     */
    @Override
    public void onClose(int i, String s, boolean b) {
        if (serverOpen && wsClient != null) {
            wsClient.sendPing();
        }
    }

    /**
     * 触发异常时
     *
     * @param exception 所有异常
     */
    @Override
    public void onError(Exception exception) {
        if (serverOpen && wsClient != null) {
            connectTime++;
            if (ConfigReader.getEnableReconnectMsg()) {
                say("WebSocket 连接已断开,正在第 " + connectTime + " 次重新连接。");
            }
            try {
                wsClient = new WSClient();
                Thread.sleep(3000);
                wsClient.connectBlocking();
            } catch (URISyntaxException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        if (serverOpen && wsClient.isOpen()) {
            wsClient.send(message);
        } else {
            say("发送消息失败，没有连接到 WebSocket 服务器。");
        }
    }
}
