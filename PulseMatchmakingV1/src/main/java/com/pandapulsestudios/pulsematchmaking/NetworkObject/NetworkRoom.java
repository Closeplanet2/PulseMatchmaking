package com.pandapulsestudios.pulsematchmaking.NetworkObject;

import com.pandapulsestudios.pulsecore.World.WorldAPI;
import com.pandapulsestudios.pulsematchmaking.Class.RoomOptions;
import com.pandapulsestudios.pulsematchmaking.Enums.GameState;
import com.pandapulsestudios.pulsematchmaking.Interface.NetworkLobby;
import com.pandapulsestudios.pulsematchmaking.PulseMatchmaking;
import com.pandapulsestudios.pulsematchmaking.Tasks.LobbyCountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class NetworkRoom {
    public String networkRoomName;
    private final RoomOptions roomOptions;
    private World oldWorld;
    private World gameWorld;
    private List<Location> spawnLocations;
    private Location lobbyLocation;

    private List<NetworkPlayer> playersInRoom;
    private GameState gameState;

    private NetworkLobby networkLobby;
    private LobbyCountdownTask lobbyCountdownTask;

    public NetworkRoom(NetworkLobby networkLobby, String networkRoomName, RoomOptions roomOptions, Player... players){
        this.networkLobby = networkLobby;
        this.networkRoomName = networkRoomName;
        this.roomOptions = roomOptions;
        this.playersInRoom = new ArrayList<>();
        gameState = GameState.Lobby;
        this.oldWorld = Bukkit.getWorld(networkLobby.oldWorldName());
        for(var player : players){
            player.setGameMode(networkLobby.defaultGamemode());
            player.teleport(networkLobby.lobbyLocation(Bukkit.getWorld(networkLobby.oldWorldName())));
        }
        this.gameWorld = WorldAPI.CreateCopy(networkLobby.oldWorldName(), networkLobby.newWorldName(networkRoomName).replace(":", ""), false, null);
        this.spawnLocations = networkLobby.SpawnLocations(this.gameWorld);
        this.lobbyLocation = networkLobby.lobbyLocation(this.gameWorld);
        for(var player : players) player.teleport(this.lobbyLocation);
        JoinRoom(networkLobby, players);
    }

    public RoomOptions RoomOptions(){return roomOptions;}
    public int NumberOfPlayers(){return playersInRoom.size();}
    public boolean IsPlayerInRoom(Player player){
        for(var networkPlayer : playersInRoom){
            if(networkPlayer.IsPlayer(player)) return true;
        }
        return false;
    }
    public boolean IsGameState(GameState gameState){return this.gameState == gameState;}
    public World GameWorld(){return gameWorld;}
    public List<Player> ReturnALlPlayers(){
        var data = new ArrayList<Player>();
        for(var networkPlayer : playersInRoom) data.addAll(networkPlayer.ReturnPlayers());
        return data;
    }

    public void JoinRoom(NetworkLobby networkLobby, Player... players){
        if(networkLobby.numberOfPlayersPerTeam() > 1){
            for(var i = 0; i < players.length / networkLobby.numberOfPlayersPerTeam(); i++){
                var groupOfPlayers = new ArrayList<Player>();
                for(var x = i * networkLobby.numberOfPlayersPerTeam(); x < (i + 1) * networkLobby.numberOfPlayersPerTeam(); x++){
                    if(x >= players.length) break;
                    groupOfPlayers.add(players[x]);
                }
                playersInRoom.add(new NetworkPlayer(new NetworkTeam(groupOfPlayers)));
            }
        }else for(var player : players) playersInRoom.add(new NetworkPlayer(player));
        for(var player : players) networkLobby.OnPlayerJoinRoom(this, player);
    }

    public void DisconnectFromRoom(NetworkLobby networkLobby, Player player){
        if(networkLobby.OnPlayerDisconnectedRoom(this, player)) LeaveRoom(networkLobby, player, false);
        else{
            for(var networkPlayer : playersInRoom){
                if(networkPlayer.IsPlayer(player)){
                    networkPlayer.DisconnectedFromRoom(player);
                    //TODO reconnection countdown
                }
            }
        }
        PulseMatchmaking.lastLobby.put(player.getUniqueId(), networkLobby.lobbyName());
    }

    public void LeaveRoom(NetworkLobby networkLobby, Player player, boolean hasDisconnected){
        for(var networkPlayer : playersInRoom){
            if(networkPlayer.IsPlayer(player)){
                var isTeamEmpty = networkPlayer.LeaveRoom(player);
                if(isTeamEmpty) playersInRoom.remove(networkPlayer);
                networkLobby.OnPlayerLeaveRoom(this, player, hasDisconnected, isTeamEmpty);
                return;
            }
        }
    }

    public void StartLobbyCountdown(JavaPlugin javaPlugin, int seconds){
        if(gameState != GameState.Lobby) return;
        if(javaPlugin == null) javaPlugin = PulseMatchmaking.Instance;
        gameState = GameState.LobbyStarting;
        networkLobby.OnLobbyCountdownStarted(this);
        lobbyCountdownTask = new LobbyCountdownTask(networkLobby, this, seconds);
        lobbyCountdownTask.runTaskTimer(javaPlugin, 0, 20);
    }

    public void EndLobbyCountdown(){
        if(gameState != GameState.LobbyStarting) return;
        gameState = GameState.Lobby;
        networkLobby.OnLobbyCountdownCancelled(this);
        lobbyCountdownTask.cancel();
    }

    public void SendPlayersToSpawns(){
        if(gameState != GameState.LobbyStarting) return;
        gameState = GameState.PREPARATION;
        if(playersInRoom.size() != spawnLocations.size()) return;
        for(var i = 0; i < playersInRoom.size(); i++){
            var networkPlayer = playersInRoom.get(i);
            var spawnLocation = spawnLocations.get(i);
            networkPlayer.AssignAndSpawn(spawnLocation);
            networkPlayer.ChangeGamemode(networkLobby.gameGamemode());
            networkLobby.OnSpawnGiven(this, spawnLocation, networkPlayer, networkPlayer.IsTeam(), i == spawnLocations.size() - 1);
        }
    }

    public void BroadcastMessage(String message){
        for(var networkPlayer : playersInRoom) networkPlayer.SendMessageToPlayer(message);
    }

    public void SendMessageToPlayer(Player player, String message){
        for(var networkPlayer : playersInRoom){
            if(networkPlayer.IsPlayer(player)){
                networkPlayer.SendMessageToPlayer(message);
            }
        }
    }
}
