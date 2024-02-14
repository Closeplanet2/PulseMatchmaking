package com.pandapulsestudios.pulsematchmaking.Events;

import com.pandapulsestudios.pulsecore.Events.CustomCoreEvents;
import com.pandapulsestudios.pulsecore.Events.PulseCoreEvents;
import com.pandapulsestudios.pulsematchmaking.PulseMatchmaking;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;

@CustomCoreEvents
public class CoreEventListener implements PulseCoreEvents {
    @Override
    public boolean BlockBreakEvent(BlockBreakEvent event){ return false; }
    @Override
    public boolean BlockPlaceEvent(BlockPlaceEvent event){ return false; }
    @Override
    public boolean BlockCanBuildEvent(BlockCanBuildEvent event){ return false; }
    @Override
    public boolean BlockDamageEvent(BlockDamageEvent event){ return false; }
    @Override
    public boolean BlockIgniteEvent(BlockIgniteEvent event){ return false; }
    @Override
    public boolean EnchantItemEvent(EnchantItemEvent event){ return false; }
    @Override
    public boolean PrepareItemEnchantEvent(PrepareItemEnchantEvent event){ return false; }
    @Override
    public boolean EntityBreakDoorEvent(EntityBreakDoorEvent event){ return false; }
    @Override
    public boolean EntityCombustByBlockEvent(EntityCombustByBlockEvent event){ return false; }
    @Override
    public boolean EntityCombustByEntityEvent(EntityCombustByEntityEvent event, boolean isAttacker){ return false; }
    @Override
    public boolean EntityDamageByBlockEvent(EntityDamageByBlockEvent event){ return false; }
    @Override
    public boolean EntityDamageByEntityEvent(EntityDamageByEntityEvent event, boolean isAttacker){ return false; }
    @Override
    public void EntityDeathEvent(EntityDeathEvent event){  }
    @Override
    public boolean EntityExplodeEvent(EntityExplodeEvent event){ return false; }
    @Override
    public boolean EntityInteractEvent(EntityInteractEvent event){ return false; }
    @Override
    public void EntityPortalEnterEvent(EntityPortalEnterEvent event){  }
    @Override
    public boolean EntityRegainHealthEvent(EntityRegainHealthEvent event){ return false; }
    @Override
    public boolean EntityShootBowEvent(EntityShootBowEvent event){ return false; }
    @Override
    public boolean EntityTeleportEvent(EntityTeleportEvent event){ return false; }
    @Override
    public boolean FoodLevelChangeEvent(FoodLevelChangeEvent event){ return false; }
    @Override
    public boolean BrewEvent(BrewEvent event){ return false; }
    @Override
    public boolean CraftItemEvent(CraftItemEvent event){ return false; }
    @Override
    public boolean AsyncPlayerChatEvent(AsyncPlayerChatEvent event){ return false; }
    @Override
    public boolean PlayerBedEnterEvent(PlayerBedEnterEvent event){ return false; }
    @Override
    public boolean PlayerBedLeaveEvent(PlayerBedLeaveEvent event){ return false; }
    @Override
    public boolean PlayerBucketEmptyEvent(PlayerBucketEmptyEvent event){ return false; }
    @Override
    public boolean PlayerBucketFillEvent(PlayerBucketFillEvent event){ return false; }
    @Override
    public boolean PlayerDropItemEvent(PlayerDropItemEvent event){ return false; }
    @Override
    public void PlayerExpChangeEvent(PlayerExpChangeEvent event){  }
    @Override
    public boolean PlayerFishEvent(PlayerFishEvent event){ return false; }
    @Override
    public boolean PlayerGameModeChangeEvent(PlayerGameModeChangeEvent event){ return false; }
    @Override
    public boolean PlayerInteractEntityEvent(PlayerInteractEntityEvent event){ return false; }
    @Override
    public void PlayerItemBreakEvent(PlayerItemBreakEvent event){  }
    @Override
    public boolean PlayerItemHeldEvent(PlayerItemHeldEvent event){ return false; }
    @Override
    public void PlayerJoinEvent(PlayerJoinEvent event){
        var lastLobby = PulseMatchmaking.lastLobby.getOrDefault(event.getPlayer().getUniqueId(), null);
        if(lastLobby == null) return;
        var networkLobbyWrapper = PulseMatchmaking.networkLobbies.getOrDefault(lastLobby, null);
        if(networkLobbyWrapper != null) networkLobbyWrapper.PlayerJoinEvent(event);
    }

    @Override
    public void PlayerQuitEvent(PlayerQuitEvent event) {
        for(var networkLobbyWrapperUUID : PulseMatchmaking.networkLobbies.keySet()){
            var networkLobbyWrapper = PulseMatchmaking.networkLobbies.get(networkLobbyWrapperUUID);
            var networkRoom = networkLobbyWrapper.ReturnRoom(event.getPlayer());
            if(networkRoom != null) networkRoom.DisconnectFromRoom(networkLobbyWrapper.ReturnNetworkLobby(), event.getPlayer());
        }
    }
    @Override
    public void PlayerLevelChangeEvent(PlayerLevelChangeEvent event){  }
    @Override
    public boolean PlayerPortalEvent(PlayerPortalEvent event){ return false; }
    @Override
    public void PlayerRespawnEvent(PlayerRespawnEvent event){  }
    @Override
    public boolean PlayerShearEntityEvent(PlayerShearEntityEvent event){ return false; }
    @Override
    public boolean PlayerTeleportEvent(PlayerTeleportEvent event){ return false; }
    @Override
    public boolean PlayerToggleSprintEvent(PlayerToggleSprintEvent event){ return false; }
    @Override
    public boolean PlayerMove(Player player, Location lastLocation, Location newLocation) { return false; }
}
