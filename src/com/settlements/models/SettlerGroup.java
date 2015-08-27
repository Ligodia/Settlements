package com.settlements.models;

import java.util.HashSet;
import java.util.Set;

public class SettlerGroup
{

    public static final int MAXLEADERS = 1;

    private Settler founder;
    private Set<Settler> leaders = new HashSet<>(1);
    private Set<Settler> settlers = new HashSet<>();

    public SettlerGroup(Settler leader, Set<Settler> settlers)
    {
        this.leaders.add(leader);
        this.settlers = settlers;
        founder = leader;
    }

    public SettlerGroup(Set<Settler> leaders, Set<Settler> settlers)
    {
        this.leaders = leaders;
        this.settlers = settlers;
    }

    public SettlerGroup(Set<Settler> leaders)
    {
        this.leaders = leaders;
    }

    public SettlerGroup(Settler leader)
    {
        this.leaders.add(leader);
        founder = leader;
    }

    public boolean addSettler(Settler settler)
    {
        return settlers.add(settler);
    }

    public boolean removeSettler(Settler settler)
    {
        return settlers.remove(settler);
    }

	public Settler getFounder() {
		return founder;
	}

    public boolean addLeader(Settler leader)
    {
        if (leaders.size() >= MAXLEADERS)
            return false;

        settlers.add(leader);
        return true;
    }

    public int getSize()
    {
        return leaders.size() + settlers.size();
    }

    public boolean contains(Settler settler)
    {
        return leaders.contains(settler) || settlers.contains(settler);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null) return false;

        if(!(obj instanceof SettlerGroup)) return false;

        SettlerGroup earthianGroup = (SettlerGroup) obj;

        return earthianGroup.leaders.equals(leaders)
                && earthianGroup.settlers.equals(settlers);
    }

    @Override
    public int hashCode()
    {
        return 78 * leaders.hashCode() * settlers.hashCode();
    }

    @Override
    public String toString()
    {
        return String.format("Leaders:%s, Settlers:%s",
                leaders.toString(), settlers.toString());
    }
}
