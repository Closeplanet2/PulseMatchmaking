package com.pandapulsestudios.pulsematchmaking.API;

import com.pandapulsestudios.pulsematchmaking.Class.RoomOptions;
import com.pandapulsestudios.pulsematchmaking.Enums.CreateRandomRoomFeedback;
import com.pandapulsestudios.pulsematchmaking.Enums.JoinRandomRoomFeedback;
import com.pandapulsestudios.pulsematchmaking.Interface.NetworkLobby;
import com.pandapulsestudios.pulsematchmaking.NetworkObject.NetworkRoom;
import com.pandapulsestudios.pulsematchmaking.PulseMatchmaking;
import org.bukkit.entity.Player;

import java.util.List;

public class NetworkAPI {

    public static List<String> ReturnLobbyNames(){
        return PulseMatchmaking.networkLobbies.keySet().stream().toList();
    }

    public static NetworkLobby ReturnNetworkLobby(String lobbyName){
        var networkLobbyWrapper = PulseMatchmaking.networkLobbies.getOrDefault(lobbyName, null);
        return networkLobbyWrapper == null ? null : networkLobbyWrapper.ReturnNetworkLobby();
    }

    public static void JoinRandomRoom(NetworkLobby networkLobby, Player... players){
        var networkLobbyWrapper = PulseMatchmaking.networkLobbies.getOrDefault(networkLobby.lobbyName(), null);
        if(networkLobbyWrapper == null){
            networkLobby.OnJoinRandomRoomFailed(JoinRandomRoomFeedback.NetworkLobbyDoesntExist, players);
            return;
        }

        for(var player : players){
            if(IsPlayerInRoom(player)){
                networkLobby.OnJoinRandomRoomFailed(JoinRandomRoomFeedback.PlayerIsInARoom, players);
                return;
            }
        }

        var roomOptions =  RoomOptions.JoinRandomRoomSettings(networkLobby);
        for(var roomName : networkLobbyWrapper.ReturnRooms().keySet()){
            var networkRoom = networkLobbyWrapper.ReturnRooms().get(roomName);
            if(RoomOptions.CompareRoomOptions(networkRoom, networkRoom.RoomOptions(), roomOptions, networkRoom.NumberOfPlayers(), players.length)){
                for(var player : players) networkRoom.JoinRoom(networkLobby, player);
                networkLobby.OnJoinRandomRoomSuccess(networkRoom, players);
                return;
            }
        }

        networkLobby.OnJoinRandomRoomFailed(JoinRandomRoomFeedback.NoRoomsFoundFromSearch, players);
    }

    public static void CreateRandomRoom(NetworkLobby networkLobby, String roomName, boolean isVisible, boolean isOpen, String roomPassword, Player... players){
        var networkLobbyWrapper = PulseMatchmaking.networkLobbies.getOrDefault(networkLobby.lobbyName(), null);
        if(networkLobbyWrapper == null){
            networkLobby.OnCreateRandomRoomFailed(CreateRandomRoomFeedback.NetworkLobbyDoesntExist, players);
            return;
        }

        if(networkLobbyWrapper.ReturnRooms().containsKey(roomName)){
            networkLobby.OnCreateRandomRoomFailed(CreateRandomRoomFeedback.RoomExistsWithName, players);
            return;
        }

        for(var player : players){
            if(IsPlayerInRoom(player)){
                networkLobby.OnCreateRandomRoomFailed(CreateRandomRoomFeedback.PlayerIsInARoom, players);
                return;
            }
        }

        var roomOptions =  RoomOptions.CreateRandomRoomSettings(networkLobby, isVisible, isOpen, roomPassword);
        var networkRoom = new NetworkRoom(networkLobby, roomName, roomOptions, players);
        networkLobbyWrapper.AddRoom(networkRoom);
        networkLobby.OnCreateRandomRoomSuccess(networkRoom, players);
    }

    public static NetworkRoom ReturnRoom(Player player){
        for(var lobbyName : PulseMatchmaking.networkLobbies.keySet()){
            var networkLobbyWrapper = PulseMatchmaking.networkLobbies.getOrDefault(lobbyName, null);
            if(networkLobbyWrapper == null) continue;
            var networkRoom = networkLobbyWrapper.ReturnRoom(player);
            if(networkRoom != null) return networkRoom;
        }
        return null;
    }


    public static boolean IsPlayerInRoom(Player player){
        for(var lobbyName : PulseMatchmaking.networkLobbies.keySet()){
            var networkLobbyWrapper = PulseMatchmaking.networkLobbies.get(lobbyName);
            if(networkLobbyWrapper.IsPlayerInRoom(player)) return true;
        }
        return false;
    }
}
