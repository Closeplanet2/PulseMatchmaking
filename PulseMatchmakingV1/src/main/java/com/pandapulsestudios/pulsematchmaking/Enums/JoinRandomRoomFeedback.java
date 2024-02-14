package com.pandapulsestudios.pulsematchmaking.Enums;

public enum JoinRandomRoomFeedback {
    NetworkLobbyDoesntExist("Network Lobby Doesn't exist!", null, true),
    PlayerIsInARoom("Players cannot be in more than one room!", null, true),
    NoRoomsFoundFromSearch("No rooms have been found for players to join!", null, false);

    public String serverMessage;
    public String playerMessage;
    public boolean isError;
    JoinRandomRoomFeedback(String serverMessage, String playerMessage, boolean isError){
        this.serverMessage = serverMessage;
        this.playerMessage = playerMessage;
        this.isError = isError;
    }
}
