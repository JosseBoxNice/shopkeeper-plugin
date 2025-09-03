package com.joosua.shopkeepers.itemcreator.state;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStateManager {
    private final ConcurrentHashMap<UUID, PlayerState> states = new ConcurrentHashMap<>();

    public PlayerState get(UUID id) { 
        return states.computeIfAbsent(id, k -> new PlayerState()); 
    }
}
