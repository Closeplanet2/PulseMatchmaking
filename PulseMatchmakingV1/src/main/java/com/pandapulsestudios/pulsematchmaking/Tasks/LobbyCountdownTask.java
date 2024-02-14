package com.pandapulsestudios.pulsematchmaking.Tasks;

import com.pandapulsestudios.pulsematchmaking.Interface.NetworkLobby;
import com.pandapulsestudios.pulsematchmaking.NetworkObject.NetworkRoom;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyCountdownTask extends BukkitRunnable {
    
    private NetworkLobby networkLobby;
    private NetworkRoom networkRoom;
    private int time;
    
    public LobbyCountdownTask(NetworkLobby networkLobby, NetworkRoom networkRoom, int time){
        this.networkLobby = networkLobby;
        this.networkRoom = networkRoom;
        this.time = time;
    }

    @Override
    public void run() {
        time -= 1;
        networkLobby.OnLobbyCountdown(networkRoom, time);
        if(time == 0){
            networkLobby.OnLobbyCountdownEnded(networkRoom);
            cancel();
        }
    }
}
