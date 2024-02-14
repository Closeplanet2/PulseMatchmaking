package com.pandapulsestudios.pulsematchmaking.Interface;

import com.pandapulsestudios.pulsecore.Chat.ChatAPI;
import com.pandapulsestudios.pulsecore.Chat.Enums.MessageType;
import com.pandapulsestudios.pulsematchmaking.Class.RoomOptions;
import com.pandapulsestudios.pulsematchmaking.Enums.CreateRandomRoomFeedback;
import com.pandapulsestudios.pulsematchmaking.Enums.JoinRandomRoomFeedback;
import com.pandapulsestudios.pulsematchmaking.NetworkObject.NetworkPlayer;
import com.pandapulsestudios.pulsematchmaking.NetworkObject.NetworkRoom;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;

public interface NetworkLobby {
    int maxPlayersPerRoom();
    int PlayerTtl();
    int EmptyRoomTtl();
    int numberOfPlayersPerTeam();
    GameMode defaultGamemode();
    GameMode gameGamemode();
    HashMap<String, Object> CustomRoomProperties();
    String oldWorldName();
    String newWorldName(String roomName);
    List<Location> SpawnLocations(World gameWorld);
    Location lobbyLocation(World gameWorld);
    Location defaultLocation();
    boolean reConnectPlayersOnJoin();

    default String lobbyName(){ return getClass().getSimpleName(); }
    default void RegisteredLobby(){ ChatAPI.SendChat(String.format("&3Registered Network Lobby: %s", lobbyName()), MessageType.ConsoleMessageNormal, true, null); }

    default void OnLobbyCreationSuccess(){}
    default void OnLobbyCreationFailed(){}
    default void OnJoinRandomRoomSuccess(NetworkRoom networkRoom, Player... players){}
    default void OnJoinRandomRoomFailed(JoinRandomRoomFeedback feedback, Player... players){}
    default void OnCreateRandomRoomSuccess(NetworkRoom networkRoom, Player... players){}
    default void OnCreateRandomRoomFailed(CreateRandomRoomFeedback  feedback, Player... players){}
    default void OnPlayerJoinRoom(NetworkRoom networkRoom, Player player){}
    default boolean OnPlayerDisconnectedRoom(NetworkRoom networkRoom, Player player){ return false; }
    default void OnPlayerLeaveRoom(NetworkRoom networkRoom, Player player, boolean hasTimeout, boolean isTeamEmpty){}

    default void OnLobbyCountdownStarted(NetworkRoom networkRoom){}
    default void OnLobbyCountdown(NetworkRoom networkRoom, int lobbyCountdown){}
    default void OnLobbyCountdownCancelled(NetworkRoom networkRoom){}
    default void OnLobbyCountdownEnded(NetworkRoom networkRoom){}

    default void OnSpawnGiven(NetworkRoom networkRoom, Location location, NetworkPlayer player, boolean isTeam, boolean finishedTask){}

    default boolean BlockBreakEvent(BlockBreakEvent event, NetworkRoom networkRoom){ return false; }
    default boolean BlockPlaceEvent(BlockPlaceEvent event, NetworkRoom networkRoom){ return false; }
    default boolean BlockCanBuildEvent(BlockCanBuildEvent event, NetworkRoom networkRoom){ return false; }
    default boolean BlockDamageEvent(BlockDamageEvent event, NetworkRoom networkRoom){ return false; }
    default boolean BlockIgniteEvent(BlockIgniteEvent event, NetworkRoom networkRoom){ return false; }
    default boolean EnchantItemEvent(EnchantItemEvent event, NetworkRoom networkRoom){ return false; }
    default boolean PrepareItemEnchantEvent(PrepareItemEnchantEvent event, NetworkRoom networkRoom){ return false; }
    default boolean EntityBreakDoorEvent(EntityBreakDoorEvent event, NetworkRoom networkRoom){ return false; }
    default boolean EntityCombustByBlockEvent(EntityCombustByBlockEvent event, NetworkRoom networkRoom){ return false; }
    default boolean EntityCombustByEntityEvent(EntityCombustByEntityEvent event, NetworkRoom networkRoom, boolean isAttacker){ return false; }
    default boolean EntityDamageByBlockEvent(EntityDamageByBlockEvent event, NetworkRoom networkRoom){ return false; }
    default boolean EntityDamageByEntityEvent(EntityDamageByEntityEvent event, NetworkRoom networkRoom, boolean isAttacker){ return false; }
    default void EntityDeathEvent(EntityDeathEvent event, NetworkRoom networkRoom){  }
    default boolean EntityExplodeEvent(EntityExplodeEvent event, NetworkRoom networkRoom){ return false; }
    default boolean EntityInteractEvent(EntityInteractEvent event, NetworkRoom networkRoom){ return false; }
    default void EntityPortalEnterEvent(EntityPortalEnterEvent event, NetworkRoom networkRoom){  }
    default boolean EntityRegainHealthEvent(EntityRegainHealthEvent event, NetworkRoom networkRoom){ return false; }
    default boolean EntityShootBowEvent(EntityShootBowEvent event, NetworkRoom networkRoom){ return false; }
    default boolean EntityTeleportEvent(EntityTeleportEvent event, NetworkRoom networkRoom){ return false; }
    default boolean FoodLevelChangeEvent(FoodLevelChangeEvent event, NetworkRoom networkRoom){ return false; }
    default boolean BrewEvent(BrewEvent event, NetworkRoom networkRoom){ return false; }
    default boolean CraftItemEvent(CraftItemEvent event, NetworkRoom networkRoom){ return false; }
    default boolean AsyncPlayerChatEvent(AsyncPlayerChatEvent event, NetworkRoom networkRoom){ return false; }
    default boolean PlayerBedEnterEvent(PlayerBedEnterEvent event, NetworkRoom networkRoom){ return false; }
    default boolean PlayerBedLeaveEvent(PlayerBedLeaveEvent event, NetworkRoom networkRoom){ return false; }
    default boolean PlayerBucketEmptyEvent(PlayerBucketEmptyEvent event, NetworkRoom networkRoom){ return false; }
    default boolean PlayerBucketFillEvent(PlayerBucketFillEvent event, NetworkRoom networkRoom){ return false; }
    default boolean PlayerDropItemEvent(PlayerDropItemEvent event, NetworkRoom networkRoom){ return false; }
    default void PlayerExpChangeEvent(PlayerExpChangeEvent event, NetworkRoom networkRoom){  }
    default boolean PlayerFishEvent(PlayerFishEvent event, NetworkRoom networkRoom){ return false; }
    default boolean PlayerGameModeChangeEvent(PlayerGameModeChangeEvent event, NetworkRoom networkRoom){ return false; }
    default boolean PlayerInteractEntityEvent(PlayerInteractEntityEvent event, NetworkRoom networkRoom){ return false; }
    default void PlayerItemBreakEvent(PlayerItemBreakEvent event, NetworkRoom networkRoom){  }
    default boolean PlayerItemHeldEvent(PlayerItemHeldEvent event, NetworkRoom networkRoom){ return false; }
    default void PlayerJoinEvent(PlayerJoinEvent event, NetworkRoom networkRoom){  }
    default void PlayerLevelChangeEvent(PlayerLevelChangeEvent event, NetworkRoom networkRoom){  }
    default boolean PlayerPortalEvent(PlayerPortalEvent event, NetworkRoom networkRoom){ return false; }
    default void PlayerRespawnEvent(PlayerRespawnEvent event, NetworkRoom networkRoom){  }
    default boolean PlayerShearEntityEvent(PlayerShearEntityEvent event, NetworkRoom networkRoom){ return false; }
    default boolean PlayerTeleportEvent(PlayerTeleportEvent event, NetworkRoom networkRoom){ return false; }
    default boolean PlayerToggleSprintEvent(PlayerToggleSprintEvent event, NetworkRoom networkRoom){ return false; }
    default boolean PlayerMove(Player player, Location lastLocation, Location newLocation) { return false; }
}
