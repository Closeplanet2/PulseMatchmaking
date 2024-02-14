package com.pandapulsestudios.pulsematchmaking.Class;

import com.pandapulsestudios.pulsematchmaking.Enums.GameState;
import com.pandapulsestudios.pulsematchmaking.Interface.NetworkLobby;
import com.pandapulsestudios.pulsematchmaking.NetworkObject.NetworkRoom;
import org.bukkit.World;

import java.util.HashMap;

public class RoomOptions {
    private int MaxPlayers;
    private int PlayerTtl;
    private int EmptyRoomTtl;
    private HashMap<String, Object> CustomRoomProperties;
    private boolean isVisible;
    private boolean isOpen;
    private int numberOfPlayersPerTeam;
    private String roomPassword;

    public Object ReturnRoomProperty(String propKey){
        return CustomRoomProperties.getOrDefault(propKey, null);
    }

    public static RoomOptions JoinRandomRoomSettings(NetworkLobby networkLobby){
        var roomOptions = new RoomOptions();
        roomOptions.MaxPlayers = networkLobby.maxPlayersPerRoom();
        roomOptions.PlayerTtl = networkLobby.PlayerTtl();
        roomOptions.EmptyRoomTtl = networkLobby.EmptyRoomTtl();
        roomOptions.CustomRoomProperties = new HashMap<>();
        roomOptions.isVisible = false;
        roomOptions.isOpen = false;
        roomOptions.numberOfPlayersPerTeam = networkLobby.numberOfPlayersPerTeam();
        roomOptions.roomPassword = null;
        return roomOptions;
    }

    public static RoomOptions CreateRandomRoomSettings(NetworkLobby networkLobby, boolean isVisible, boolean isOpen, String roomPassword){
        var roomOptions = new RoomOptions();
        roomOptions.MaxPlayers = networkLobby.maxPlayersPerRoom();
        roomOptions.PlayerTtl = networkLobby.PlayerTtl();
        roomOptions.EmptyRoomTtl = networkLobby.EmptyRoomTtl();
        roomOptions.CustomRoomProperties = networkLobby.CustomRoomProperties();
        roomOptions.isVisible = isVisible;
        roomOptions.isOpen = isOpen;
        roomOptions.roomPassword = roomPassword;
        roomOptions.numberOfPlayersPerTeam = networkLobby.numberOfPlayersPerTeam();
        return roomOptions;
    }

    public static boolean CompareRoomOptions(NetworkRoom networkRoom, RoomOptions current, RoomOptions searchBy, int playersInGame, int playersJoining){
        if(playersJoining + playersInGame > current.MaxPlayers) return false;
        if(!searchBy.CustomRoomProperties.isEmpty()){
            for(var propKey : searchBy.CustomRoomProperties.keySet()){
                if(!current.CustomRoomProperties.containsKey(propKey)) return false;
                var propValue = searchBy.CustomRoomProperties.get(propKey);
                if(propValue != current.CustomRoomProperties.get(propKey)) return false;
            }
        }
        if(!current.isVisible && searchBy.isVisible) return false;
        if(!current.isOpen) return false;
        if(current.numberOfPlayersPerTeam != searchBy.numberOfPlayersPerTeam) return false;
        if(current.roomPassword != null && searchBy.roomPassword == null) return false;
        if(current.roomPassword != null) return current.roomPassword.equals(searchBy.roomPassword);
        if(!networkRoom.IsGameState(GameState.Lobby)) return false;
        return true;
    }
}
