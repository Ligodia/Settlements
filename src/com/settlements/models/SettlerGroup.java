package com.settlements.models;

import java.util.*;

public class SettlerGroup
{
    public enum SettlerType { FOUNDER, LEADER, SETTLER; }

    public static final int MAXLEADERS = 1;

    private Map<Settler, SettlerType> settlers = new HashMap<>();

    public SettlerGroup(Settler founder, Set<Settler> settlers)
    {
        this.settlers.put(founder, SettlerType.FOUNDER);

        for (Settler settler : settlers)
            this.settlers.put(settler, SettlerType.SETTLER);
    }

    public SettlerGroup(Settler founder, Set<Settler> leaders, Set<Settler> settlers)
    {
        this.settlers.put(founder, SettlerType.FOUNDER);

        for (Settler leader : leaders)
            this.settlers.put(leader, SettlerType.LEADER);

        for (Settler settler : settlers)
            this.settlers.put(settler, SettlerType.SETTLER);
    }

    public SettlerGroup(Settler founder)
    {
        this.settlers.put(founder, SettlerType.FOUNDER);
    }

    public int getSize()
    {
        return settlers.size();
    }

    public void addSettler(Settler settler)
    {
        settlers.put(settler, SettlerType.SETTLER);
    }

    public void removeSettler(Settler settler)
    {
        settlers.remove(settler);
    }

    public boolean contains(Settler settler)
    {
        return settlers.containsKey(settler);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null) return false;

        if(!(obj instanceof SettlerGroup)) return false;

        SettlerGroup earthianGroup = (SettlerGroup) obj;

        return earthianGroup.settlers.equals(settlers);
    }

    @Override
    public int hashCode()
    {
        return 78 * settlers.hashCode();
    }

    @Override
    public String toString()
    {
        return String.format("Leaders:%s", settlers.toString());
    }
}
