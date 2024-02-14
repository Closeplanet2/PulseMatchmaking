package com.pandapulsestudios.pulsematchmaking;

import com.pandapulsestudios.pulsematchmaking.Interfaces.CustomLobby;
import com.pandapulsestudios.pulsematchmaking.Interfaces.LobbyCallbacks;

@CustomLobby(minPlayers = 2, maxPlayers = 2, playerTTL = -1, roomTTL = -1, reconnectRoomRejoin = true,
lobbyGamemode = "", gameGamemode = "", blankWorld = "", lobbyLocation = {0f, 0f, 0f, 0f, 0f})
public class TestLobby implements LobbyCallbacks {

}
