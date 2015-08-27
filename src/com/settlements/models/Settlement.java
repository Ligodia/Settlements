package com.settlements.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Settlement
{
    private String name;
    private SettlerGroup inhabitants;
    private Set<Column> land = new HashSet<>();
    private Map<Set<Column>, Double> forSaleLand = new HashMap<>();
    private Map<Set<Column>, Settler> plots = new HashMap<>();
    private int landAllocation;
    private SettlementType type;

    public Settlement(String name, Settler leader, Set<Column> land)
    {
        this.name = name;
        inhabitants = new SettlerGroup(leader);
        this.land = land;
        type = SettlementType.DWELLING;
    }

    public Map<Set<Column>, Double> getForSaleLand()
    {
        return forSaleLand;
    }

    public void setForSaleLand(Map<Set<Column>, Double> forSaleLand)
    {
        this.forSaleLand = forSaleLand;
    }

    public Map<Set<Column>, Settler> getPlots()
    {
        return plots;
    }

    public void setPlots(Map<Set<Column>, Settler> plots)
    {
        this.plots = plots;
    }

    public boolean addColumn(Column column)
    {
        return land.add(column);
    }

    public boolean removeColumn(Column column)
    {
        return land.remove(column);
    }

    public Set<Column> getLand()
    {
        return land;
    }

    public void setLand(Set<Column> land)
    {
        this.land = land;
    }

    public SettlerGroup getInhabitants()
    {
        return inhabitants;
    }

    public void setInhabitants(SettlerGroup inhabitants)
    {
        this.inhabitants = inhabitants;
    }

    public int getLandAllocation()
    {
        return landAllocation;
    }

    public void setLandAllocation(int landAllocation)
    {
        this.landAllocation = landAllocation;
    }

    public SettlementType getType()
    {
        return type;
    }

    public void setType(SettlementType type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getSize()
    {
        return inhabitants.getSize();
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null) return false;

        if(!(o instanceof Settlement)) return false;

        Settlement settlement = (Settlement) o;

        return settlement.name.equals(name)
                && settlement.inhabitants.equals(inhabitants)
                && settlement.land.equals(land)
                && settlement.landAllocation == landAllocation
                && settlement.type.equals(type);
    }

    @Override
    public int hashCode()
    {
        return 12 * name.hashCode() * inhabitants.hashCode()
                * land.hashCode() * landAllocation
                * type.hashCode();
    }

    @Override
    public String toString()
    {
        return String.format("Name:%s, Inhabitants:%s, Land:%s, " +
                        "Land Allocation:%d, Type:%s",
                name, inhabitants.toString(), land.toString(),
                landAllocation, type.toString());
    }
}
