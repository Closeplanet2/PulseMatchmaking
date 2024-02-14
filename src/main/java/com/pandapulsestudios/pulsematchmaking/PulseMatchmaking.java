package com.pandapulsestudios.pulsematchmaking;

import com.pandapulsestudios.pulsematchmaking.Interfaces.LobbyCallbacks;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class PulseMatchmaking extends JavaPlugin {

    public static HashMap<String, LobbyCallbacks> networkLobbies = new HashMap<>();
}
