package com.settlements.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Settlement
{
    private String name;
    private SettlerGroup inhabitants;
    private Set<Column> land;
    private int landAllocation;
    private SettlementType type;
	private Settlement parentSettlement;
	private Set<Settlement> childSettlements;
    private boolean forSale;
    private double forSalePrice;

    public Settlement(String name, Settler founder, Set<Column> land, Settlement parentSettlement)
    {
        this.name = name;
        inhabitants = new SettlerGroup(founder);
        this.land = land;
        this.parentSettlement = parentSettlement;
        parentSettlement.addChildSettlement(this);
        type = SettlementType.DWELLING;
    }

    public boolean addColumn(Column column)
    {
        //TODO:Check for and return errors
        if(land == null) land = new HashSet<Column>();

        return land.add(column);
    }

    public Error removeColumn(Column column)
    {
        if(land == null) return Error.NO_SUCH_COLUMN;

        return land.remove(column) ? null : Error.NO_SUCH_COLUMN;
    }

    public Set<Column> getLand()
    {
        return land;
    }

    public SettlerGroup getInhabitants()
    {
        return inhabitants;
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
		int size = 0;

		if (childSettlements.size() == 0)
			return inhabitants.getSize();
		else
			size += inhabitants.getSize();

		for (Settlement subSettlement : childSettlements)
			size += subSettlement.getSize();

		return size;
    }

	public Set<Settlement> getChildSettlements() 
	{
		return childSettlements;
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

    public void setForSale(double price)
    {
        forSale = true;
        forSalePrice = price;
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
