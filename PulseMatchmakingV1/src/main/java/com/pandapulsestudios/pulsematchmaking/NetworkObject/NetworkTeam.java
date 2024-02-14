package com.pandapulsestudios.pulsematchmaking.NetworkObject;

import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.Enums.MessageType;
import com.pandapulsestudios.pulsematchmaking.Enums.GameState;
import com.pandapulsestudios.pulsematchmaking.Enums.PlayerState;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetworkTeam {
    private HashMap<Player, PlayerState> playerList = new HashMap<>();

    public NetworkTeam(List<Player> players){
        for(var player : players) playerList.put(player, PlayerState.PlayingGame);
    }

    public boolean IsPlayer(Player player){
        return playerList.containsKey(player);
    }

    public void DisconnectedFromRoom(Player player){
        playerList.put(player, PlayerState.WaitingToReconnect);
    }

    public boolean LeaveRoom(Player player){
        playerList.remove(player);
        return playerList.isEmpty();
    }

    public List<Player> ReturnPlayers(){
        return new ArrayList<Player>(playerList.keySet());
    }

    public void SendMessageToPlayer(String message){
        for(var player : playerList.keySet()) ChatAPI.SendChat(message, MessageType.PlayerMessageFromPlugin, false, null, player);
    }

    public void TeleportPlayer(Location location){
        for(var player : playerList.keySet()) player.teleport(location);
    }

    public void ChangeGamemode(GameMode gameMode){
        for(var player : playerList.keySet()) player.setGameMode(gameMode);
    }
}
