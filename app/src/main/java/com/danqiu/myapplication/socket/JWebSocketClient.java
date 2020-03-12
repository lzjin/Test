package com.danqiu.myapplication.socket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Socket客服端
 */
public class JWebSocketClient extends WebSocketClient {
    private final static String TAG="zz";

    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.e(TAG, "onOpen()");
    }

    @Override
    public void onMessage(String message) {
        Log.e(TAG, "onMessage()"+message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e(TAG, "onClose()");
    }

    @Override
    public void onError(Exception ex) {
        Log.e(TAG, "onError()");
    }
}
