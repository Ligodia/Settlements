package com.settlements.models;

import org.bukkit.entity.Player;

public class Settler
{
    private Player player;
    private Rank rank;

    public Settler(Player player)
    {
        this.player = player;
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
        return 67 * player.hashCode() * rank.hashCode();
    }
}
