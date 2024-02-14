package com.pandapulsestudios.pulsematchmaking.Enums;

public enum CreateRandomRoomFeedback {
    NetworkLobbyDoesntExist("Network Lobby Doesn't exist!", null, true),
    PlayerIsInARoom("Players cannot be in more than one room!", null, true),
    RoomExistsWithName("The name you are trying to give the room Exists!", null, true);

    public String serverMessage;
    public String playerMessage;
    public boolean isError;
    CreateRandomRoomFeedback(String serverMessage, String playerMessage, boolean isError){
        this.serverMessage = serverMessage;
        this.playerMessage = playerMessage;
        this.isError = isError;
    }
}
