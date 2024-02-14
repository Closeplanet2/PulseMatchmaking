package com.pandapulsestudios.pulsematchmaking;

import com.pandapulsestudios.pulsecore.Java.JavaClassAPI;
import com.pandapulsestudios.pulsematchmaking.Class.NetworkLobbyWrapper;
import com.pandapulsestudios.pulsematchmaking.Interface.CustomLobby;
import com.pandapulsestudios.pulsematchmaking.Interface.NetworkLobby;
import com.pandapulsestudios.pulsevariable.API.JavaAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class PulseMatchmaking extends JavaPlugin {
    public static PulseMatchmaking Instance;

    public static HashMap<String, NetworkLobbyWrapper> networkLobbies = new HashMap<>();
    public static HashMap<UUID, String> lastLobby = new HashMap<>();

    @Override
    public void onEnable() {
        Instance = this;
        JavaClassAPI.RegisterRaw(this);
    }

    public static void RegisterLobbiesRaw(JavaPlugin javaPlugin) {
        try { RegisterLobbies(javaPlugin); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static void RegisterLobbies(JavaPlugin javaPlugin) throws Exception {
        var interfaceClasses = JavaAPI.ReturnALlClassOfTypes(javaPlugin, CustomLobby.class);
        for(var classInterface : interfaceClasses.get(CustomLobby.class)){
            var networkLobby = (NetworkLobby) classInterface.getConstructor().newInstance();
            networkLobbies.put(networkLobby.lobbyName(), new NetworkLobbyWrapper(networkLobby));
            networkLobby.RegisteredLobby();
        }
    }
}
