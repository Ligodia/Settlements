package com.settlements.models;

import java.util.*;

public class SettlerGroup extends HashMap<Settler, SettlerType>
{
    public static final int MAXLEADERS = 1;

    public SettlerGroup(Settler founder, Set<Settler> leaders, Set<Settler> settlers)
    {
        this.put(founder, SettlerType.FOUNDER);

        for (Settler leader : leaders)
            this.put(leader, SettlerType.LEADER);

        for (Settler settler : settlers)
            this.put(settler, SettlerType.SETTLER);
    }

    public SettlerGroup(Settler founder, Set<Settler> settlers)
    {
        this.put(founder, SettlerType.FOUNDER);

        for (Settler settler : settlers)
            this.put(settler, SettlerType.SETTLER);
    }

    public SettlerGroup(Settler founder)
    {
        this.put(founder, SettlerType.FOUNDER);
    }

    public SettlerGroup(SettlerGroup settlerGroup)
    {
        this.putAll(settlerGroup);
    }

    public Error addSettler(Settler settler)
    {
        if(settler == null) throw new IllegalArgumentException();

        return put(settler, SettlerType.SETTLER) == null
                ? null : Error.PLAYER_ALREADY_IN_SETTLEMENT;
    }

    public Error removeSettler(Settler settler)
    {
        if(settler == null) throw new IllegalArgumentException();

        return remove(settler) == null ? Error.PLAYER_NOT_IN_SETTLEMENT : null;
    }

    public boolean contains(Settler settler)
    {
        return containsKey(settler);
    }
}
