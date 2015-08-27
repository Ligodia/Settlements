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

    private ColumnController columnController;
    private Map<String, Settlement> settlements = new HashMap<String, Settlement>();

    public SettlementController(ColumnController columnController)
    {
        this.columnController = columnController;
    }

    /**
     * Calculate the settlement type of the settlement.
     *
     * @param settlement the settlement to be calculated
     * @return the type of the settlement
     */
    public SettlementType calcType(Settlement settlement)
    {

        for (SettlementType type : Arrays.asList(SettlementType.values()))
            if (type.getSizeCo() >= settlement.getSize())
                return type;

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

        //
        if (!columnController.isAdjacent(column, settlement.getLand()))
            return Error.BLOCK_IS_NOT_ADJACENT;

        // If town has no column claiming blocks left return error.
        if (!(settlement.getLand().size() < settlement.getLandAllocation()))
            return Error.NO_REMAINING_BLOCKS;

        // If column is already claimed return error.
        for (Settlement oneOfAllSettlements : settlements.values())
            if (oneOfAllSettlements.getLand().contains(column))
                return Error.LAND_ALREADY_CLAIMED;

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
	public Error unclaimColumn(Settlement settlement, Column column) {

		if (settlement.getLand().size() == 1)
			return Error.LAST_BLOCK;

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

			if (settlement.removeColumn(column))
				return null;
			else
				return Error.BLOCK_ISNT_CLAIMED;

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

        if (!settlement.getInhabitants().contains(settler))
        {

            settlement.getInhabitants().addSettler(settler);
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

        if (settlement.getInhabitants().contains(settler))
        {

            settlement.getInhabitants().removeSettler(settler);
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
     * @param name   the name of the new settlement
     * @param leader the leader of the new settlement
     * @param land   the starting land of the new settlement
     * @return the created settlement, returns null if a settlement with the
     * same name already exists
     */
    public Error createSettlement(String name, Settler leader, Set<Column> land)
    {

        if (settlements.containsKey(name))
            return Error.SETTLEMENT_ALREADY_EXISTS;

        Settlement settlement = new Settlement(name, leader, land);

        settlements.put(name, settlement);

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

    /**
     * Creates a plot with the given set of columns
     *
     * @param settlement the settlement to create the plot in
     * @param plot the plot to create
     * @return error if unsuccessful null otherwise
     */
    public Error createPlot(Settlement settlement, Set<Column> plot) {

        if(!settlement.getLand().containsAll(plot)) return Error.BLOCK_ISNT_CLAIMED;

        for(Set<Column> forSalePlots : settlement.getForSaleLand().keySet())
        {
            for(Column forSaleColumn : forSalePlots)
            {
                if (plot.contains(forSaleColumn))
                {
                    return Error.PLOT_OVERLAP;
                }
            }
        }

        for(Set<Column> plots : settlement.getPlots().keySet())
        {
            for(Column column : plots)
            {
                if (plot.contains(column))
                {
                    return Error.PLOT_OVERLAP;
                }
            }
        }

        settlement.getForSaleLand().put(plot, -1d);

        return null;
    }

    /**
     * Removes a plot with the given set of columns
     *
     * @param settlement the settlement to remove the plot from
     * @param plot the plot to remove
     * @return error if unsuccessful null otherwise
     */
    public Error removePlot(Settlement settlement, Set<Column> plot)
    {
        if(settlement.getPlots().remove(plot) != null) return null;

        if(settlement.getForSaleLand().remove(plot) != null) return null;

        return Error.NO_SUCH_PLOT;
    }
}
