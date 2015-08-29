package com.settlements.controllers;

import com.settlements.models.Column;
import com.settlements.models.Settlement;
import com.settlements.models.Settler;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dan on 23-Aug-15.
 */
public class SettlerController
{
    private Map<Player, Settler> settlers = new HashMap<Player, Settler>();

    private ColumnController columnController;

    public SettlerController(ColumnController columnController)
    {
        this.columnController = columnController;
    }

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

    /**
     * Checks if a player is inside the bounds of the
     * specified settlement.
     *
     * @param settler       the settler to check the
     *                      location of.
     * @param settlement    the settlement to check for
     *                      the settler
     * @return  true if the settler is standing within
     *          the bounds of the settlement.
     */
    public boolean isStandingInSettlement(Settler settler, Settlement settlement)
    {
        Location settlerLoc = settler.getPlayer().getLocation();

        for(Column settlementColumn : settlement.getLand())
        {
            if(columnController.containsLocation(settlementColumn,settlerLoc))
                return true;
        }

        return false;
    }
}
