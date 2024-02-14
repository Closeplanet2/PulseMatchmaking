package com.pandapulsestudios.pulsematchmaking.NetworkObject;

import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.Enums.MessageType;
import com.pandapulsestudios.pulsematchmaking.Enums.GameState;
import com.pandapulsestudios.pulsematchmaking.Enums.PlayerState;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NetworkPlayer {
    private Player player;
    private NetworkTeam networkTeam;
    private PlayerState playerState;
    private Location spawnLocation;

    public NetworkPlayer(Player player) {
        this.player = player;
        this.playerState = PlayerState.PlayingGame;
    }

    public NetworkPlayer(NetworkTeam networkTeam) {
        this.networkTeam = networkTeam;
    }

    public boolean IsPlayer(Player player){
        if(this.player == null) return networkTeam.IsPlayer(player);
        else return this.player == player;
    }

    public void DisconnectedFromRoom(Player player){
        if(this.player == null) networkTeam.DisconnectedFromRoom(player);
        else playerState = PlayerState.WaitingToReconnect;
    }

    public boolean LeaveRoom(Player player){
        return this.player != null || networkTeam.LeaveRoom(player);
    }

    public boolean IsTeam(){return player == null;}

    public List<Player> ReturnPlayers(){
        var data = new ArrayList<Player>();
        if(player == null) data.addAll(networkTeam.ReturnPlayers());
        else data.add(player);
        return data;
    }

    public void AssignAndSpawn(Location location){
        spawnLocation = location;
        TeleportPlayer(location);
    }

    public void SendMessageToPlayer(String message){
        if(player == null) networkTeam.SendMessageToPlayer(message);
        else ChatAPI.SendChat(message, MessageType.PlayerMessageFromPlugin, false, null, player);
    }

    public void TeleportPlayer(Location location){
        if(player == null) networkTeam.TeleportPlayer(location);
        else this.player.teleport(location);
    }

    public void ChangeGamemode(GameMode gameMode){
        if(player == null) networkTeam.ChangeGamemode(gameMode);
        else this.player.setGameMode(gameMode);
    }
}
