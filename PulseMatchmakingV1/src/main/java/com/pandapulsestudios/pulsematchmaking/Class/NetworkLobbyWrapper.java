package com.pandapulsestudios.pulsematchmaking.Class;

import com.pandapulsestudios.pulsematchmaking.Interface.NetworkLobby;
import com.pandapulsestudios.pulsematchmaking.NetworkObject.NetworkRoom;
import com.pandapulsestudios.pulsematchmaking.PulseMatchmaking;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class NetworkLobbyWrapper {
    private NetworkLobby networkLobby;
    private HashMap<String, NetworkRoom> networkRoomHashMap;

    public NetworkLobbyWrapper(NetworkLobby networkLobby){
        this.networkLobby = networkLobby;
        this.networkRoomHashMap = new HashMap<>();
    }

    public NetworkLobby ReturnNetworkLobby(){return networkLobby;}
    public HashMap<String, NetworkRoom> ReturnRooms(){
        return networkRoomHashMap;
    }
    public NetworkRoom ReturnRoomByName(String networkRoomName){ return networkRoomHashMap.getOrDefault(networkRoomName, null); }
    public void AddRoom(NetworkRoom networkRoom){ networkRoomHashMap.put(networkRoom.networkRoomName, networkRoom); }

    public boolean IsPlayerInRoom(Player player){
        for(var roomName : networkRoomHashMap.keySet()) if(networkRoomHashMap.get(roomName).IsPlayerInRoom(player)) return true;
        return false;
    }

    public void PlayerJoinEvent(PlayerJoinEvent event){
        if(IsPlayerInRoom(event.getPlayer())){
            var networkRoom = ReturnRoom(event.getPlayer());
            if(networkLobby.reConnectPlayersOnJoin()){
                //TODO send the player back to the game
            }else{
                networkRoom.LeaveRoom(networkLobby, event.getPlayer(), false);
                event.getPlayer().teleport(networkLobby.defaultLocation());
            }
        }else{
            event.getPlayer().teleport(networkLobby.defaultLocation());
        }
        PulseMatchmaking.lastLobby.remove(event.getPlayer().getUniqueId());
    }

    public NetworkRoom ReturnRoom(Player player){
        for(var roomName : networkRoomHashMap.keySet()){
            var networkRoom = networkRoomHashMap.get(roomName);
            if(networkRoom.IsPlayerInRoom(player)) return networkRoom;
        }
        return null;
    }

}
