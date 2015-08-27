package com.settlements.models;

import org.bukkit.entity.Player;

public class Settler
{
    private Player player;
    private Settlement settlement;
    private Rank rank;

    public Settler(Player player)
    {
        this.player = player;
    }

    public Settlement getSettlement()
    {
        return settlement;
    }

    public void setSettlement(Settlement settlement)
    {
        this.settlement = settlement;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Rank getRank()
    {
        return rank;
    }

    public void setRank(Rank rank)
    {
        this.rank = rank;
    }

    @Override
    public String toString()
    {
        return "Rank:" + rank.toString() + ", " + player.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null) return false;

        if(!(o instanceof Settler)) return false;

        Settler settler = (Settler) o;

        return settler.player.equals(player);
    }

    @Override
    public int hashCode()
    {
        return 67 * player.hashCode() * settlement.hashCode() * rank.hashCode();
    }
}
