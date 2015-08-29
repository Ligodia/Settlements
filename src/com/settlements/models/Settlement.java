package com.settlements.models;

import java.util.HashSet;
import java.util.Set;

public class Settlement extends SettlerGroup
{
    private String name;
    private Set<Column> land;
    private int landAllocation;
    private SettlementType type;
	private Settlement parentSettlement;
	private Set<Settlement> childSettlements;
    private boolean forSale;
    private double forSalePrice;

    public Settlement(String name, Settler founder, Set<Column> land, Settlement parentSettlement)
    {
        super(founder);

        if(((name == null || founder == null) && parentSettlement == null) || land == null)
            throw new IllegalArgumentException();

        this.name = name;
        this.land = land;
        this.parentSettlement = parentSettlement;
        parentSettlement.addChildSettlement(this);
        type = SettlementType.DWELLING;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        if (name == null) throw new IllegalArgumentException();

        this.name = name;
    }

    public Set<Settler> getLeaders()
    {
        Set<Settler> leaders = new HashSet<>();

        for (Settler settler : keySet())
            if (get(settler) == SettlerType.LEADER
                    || get(settler) == SettlerType.LEADER)
                leaders.add(settler);

        return leaders;
    }

    public Set<Column> getLand()
    {
        return land != null ? new HashSet<>(land) : null;
    }

    public Error removeColumn(Column column)
    {
        return land != null && land.remove(column) ? null : Error.BLOCK_ISNT_CLAIMED;
    }

    public Error addColumn(Column column)
    {
        if(land == null) land = new HashSet<Column>();

        return land.add(column) ? null : Error.COLUMN_ALREADY_ADDED;
    }

    public int getLandAllocation()
    {
        return landAllocation;
    }

    public Error setLandAllocation(int landAllocation)
    {
        if(landAllocation < 0) return Error.NO_NEGATIVES;

        this.landAllocation = landAllocation;
        return null;
    }

    public SettlementType getType()
    {
        return type;
    }

    public void setType(SettlementType type)
    {
        if(type == null) throw new IllegalArgumentException();

        this.type = type;
    }

	public Set<Settlement> getChildSettlements() 
	{
		return new HashSet<Settlement>(childSettlements);
	}

	public Error addChildSettlement(Settlement childSettlement)
	{
        if(this.childSettlements == null)
            childSettlements = new HashSet<Settlement>();

		return childSettlements.add(childSettlement) ? null : Error.NO_SUCH_CHILD_SETTLEMENT;
	}
	
	public Error removeChildSettlement(Settlement childSettlement)
    {
		return this.childSettlements == null || !childSettlements.remove(childSettlement)
                ? Error.NO_SUCH_CHILD_SETTLEMENT : null;
	}

	public Settlement getParentSettlement() 
	{
		return parentSettlement;
	}

	public void setParentSettlement(Settlement parentSettlement)
	{
		this.parentSettlement = parentSettlement;
	}

    public Error setForSale(double price)
    {
        if(price < 0) return Error.NO_NEGATIVES;

        forSale = true;
        forSalePrice = price;
        return null;
    }

    public Error setNotForSale()
    {
        if(!forSale) return Error.WAS_NOT_FOR_SALE;

        forSale = false;

        return null;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null) return false;

        if(!(o instanceof Settlement)) return false;

        Settlement settlement = (Settlement) o;

        return settlement.name.equals(name)
                && settlement.land.equals(land)
                && settlement.landAllocation == landAllocation
                && settlement.type.equals(type);
    }

    @Override
    public int hashCode()
    {
        return 12 * name.hashCode() *  land.hashCode()
                * landAllocation * type.hashCode();
    }

    @Override
    public String toString()
    {
        return String.format("Name:%s, Inhabitants:%s, Land:%s, " +
                        "Land Allocation:%d, Type:%s",
                name, land.toString(),
                landAllocation, type.toString());
    }
}
