package com.settlements.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.settlements.models.Column;
import com.settlements.models.Error;
import com.settlements.models.Settlement;
import com.settlements.models.SettlementType;
import com.settlements.models.Settler;

public class SettlementController
{
    public static final int MINIMUM_SIZE = 1;

    private ColumnController columnController;
    private SettlerController settlerController;

    private Map<String, Settlement> settlements = new HashMap<String, Settlement>();

    public SettlementController(
            ColumnController columnController,
            SettlerController settlerController)
    {
        this.columnController = columnController;
        this.settlerController = settlerController;
    }

    /**
     * Calculate the settlement type of the settlement.
     *
     * @param settlement the settlement to be calculated
     * @return the type of the settlement
     */
    public SettlementType calcType(Settlement settlement)
    {
        SettlementType settlementType = SettlementType.DWELLING;

        for (SettlementType type : Arrays.asList(SettlementType.values()))
        {
            if (type.getSizeCo() >= settlement.size())
                return settlementType;
            settlementType = type;
        }

        return SettlementType.EMPIRE;
    }

    /**
     * Check if the column can be added to the settlement and adds it if so.
     *
     * @param settlement    The settlement to claim
     * @param column        The column to claim
     * @return              Error code
     */
    public Error claimColumn(Settlement settlement, Column column)
    {
        if (!columnController.isAdjacent(column, settlement.getLand()))
            return Error.BLOCK_IS_NOT_ADJACENT;

        // If town has no column claiming blocks left return error.
        if (!(settlement.getLand().size() < settlement.getLandAllocation()))
            return Error.NO_REMAINING_BLOCKS;

        // If column is already claimed return error.
        for (Settlement oneOfAllSettlements : settlements.values())
            if (oneOfAllSettlements.getLand().contains(column))
                return Error.LAND_ALREADY_CLAIMED;

        settlement.addColumn(column);

        return null;
    }

    /**
     * Checks that you are unclaiming neither the last column of the settlement
     * nor unclaiming a block that would make two separate isolated before removing the
     * column from the settlement.
     *
     * @param settlement	The settlement you are removing the column from
     * @param column    	The column you are removing from the settlement
     * @return 				The error code
     */
	public Error unclaimColumn(Settler settler, Settlement settlement, Column column) {

        if (!settlement.contains(settler))
            return Error.MUST_BE_MEMBER_OF_SETTLEMENT;

		if (settlement.getLand().size() <= MINIMUM_SIZE)
			return Error.MINIMUM_SIZE;

		Set<Column> connectedLand = new HashSet<Column>();
		Set<Column> checkedLand = new HashSet<Column>();
		Column[] connectedLandArray;

		checkedLand.add(column);

		for (Column adj : columnController.getAdjacent(column))
			if (settlement.getLand().contains(adj)) {
				connectedLand.add(adj);
				break;
			}

		/*
		 * The below if statement should not happen as far as I can tell. It
		 * only occurs if the column is not the only column in the settlement,
		 * but is disconnected from any other settlement plots, which obviously
		 * should not happen. I can remove it, but it risks the method throwing
		 * an exception with the for loop below. Opinion?
		 */
		if (connectedLand.size() == 0) {
			settlement.removeColumn(column);
            return null;
		}

		do 
		{
			connectedLandArray = connectedLand.toArray(new Column[connectedLand.size()]);

			for (Column columnInLand : connectedLandArray) {
				for (Column adj : columnController.getAdjacent(columnInLand))
					if (settlement.getLand().contains(adj)
							&& !checkedLand.contains(adj))
						connectedLand.add(adj);

				checkedLand.add(columnInLand);
				connectedLand.remove(columnInLand);
			}
		} 
		while (connectedLand.size() != 0);

		if (checkedLand.size() == settlement.getLand().size())

			return settlement.removeColumn(column);

		return Error.WILL_ISOLATE_BLOCK;
	}
    

    /**
     * Adds an inhabitant to the settlement
     *
     * @param settlement the settlement the inhabitant is to be added to
     * @param settler   the prospective inhabitant
     * @return true if successful
     */
    public Error addInhabitant(Settlement settlement, Settler settler)
    {
        if (!settlement.contains(settler))
        {
            settlement.addSettler(settler);
            settlement.setType(calcType(settlement));
            return null;
        }
        else
        {
            return Error.PLAYER_ALREADY_IN_SETTLEMENT;
        }
    }

    public Error removeInhabitant(Settlement settlement, Settler settler)
    {

        if (settlement.contains(settler))
        {
            settlement.removeSettler(settler);
            settlement.setType(calcType(settlement));
            return null;
        }
        else
        {
            return Error.PLAYER_NOT_IN_SETTLEMENT;
        }
    }

    /**
     * Creates a settlement.
     *
     * @param name      the name of the new settlement
     * @param leader    the leader of the new settlement
     * @param land      the starting land of the new settlement
     * @return the created settlement, returns null if a settlement with the
     * same name already exists
     */
    public Error createSettlement(String name, Settler leader, Set<Column> land)
    {
        if (settlements.containsKey(name))
            return Error.SETTLEMENT_ALREADY_EXISTS;

        Settlement settlement = new Settlement(name, leader, land, null);

        settlements.put(name, settlement);

        return null;
    }

    /**
     * Creates a child settlement with no name or leader. Analogous to a plot
     *
     * @param creator   the original creator of the new settlement
     * @param land      the starting land of the new settlement
     * @param parent    the parent of the settlement you are creating
     * @return the created settlement, returns null if a settlement with the
     * same name already exists
     */
    public Error createSubSettlement(Settler creator, Set<Column> land, Settlement parent)
    {
        if(!parent.contains(creator)) return Error.MUST_BE_MEMBER_OF_SETTLEMENT;

        if(!settlerController.isStandingInSettlement(creator, parent))
            return Error.MUST_BE_STANDING_IN_SETTLEMENT;

        Settlement settlement = new Settlement(null, null, land, parent);

        parent.addChildSettlement(settlement);

        return null;
    }

    /**
     * Removes a settlement.
     *
     * @param name the name of the settlement to be removed
     * @return if successful returns null else returns an error
     */
    public Error removeSettlement(String name)
    {

        if (settlements.remove(name) == null)
            return Error.SETTLEMENT_DOESNT_EXIST;

        return null;
    }
}
