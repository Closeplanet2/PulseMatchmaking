package com.pandapulsestudios.pulsematchmaking.Interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomLobby {
    int minPlayers();
    int maxPlayers();
    int playerTTL();
    int roomTTL();
    boolean reconnectRoomRejoin();
    String lobbyGamemode();
    String gameGamemode();
    String blankWorld();
    float[] lobbyLocation();
}
