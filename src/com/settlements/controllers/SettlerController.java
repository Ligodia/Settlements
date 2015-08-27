package com.settlements.controllers;

import com.settlements.models.Settler;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dan on 23-Aug-15.
 */
public class SettlerController
{
    private Map<Player, Settler> settlers = new HashMap<Player, Settler>();

    /**
     * Gets the player's associated settler object
     *
     * @param player the player whose settler object to get
     * @return the settler object associated with the player
     */
    public Settler getSettler(Player player)
    {
        return settlers.get(player);
    }

    /**
     * Creates a new settler object and
     * associates it with the given player
     *
     * @param player the player to associate
     *               the new settler object with
     * @return the new settler object
     */
    public Settler newSettler(Player player)
    {
        Settler settler = new Settler(player);
        settlers.put(player, settler);
        return settler;
    }
}
