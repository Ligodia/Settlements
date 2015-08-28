package com.settlements.controllers;

import java.util.Set;

import org.bukkit.World;

import com.settlements.models.Column;
import com.settlements.models.Error;
import com.settlements.models.Plot;
import com.settlements.models.Settlement;
import com.settlements.models.Settler;

public class PlotController {

	public Error newPlot(Settlement settlement, World world, Set<Column> columns, Settler owner) {
		
		settlement.getLand().contains(columns);
		
		return null;
	}
	
	public Error addColumns(Plot plot, Column corner1, Column corner2) 
	{
		boolean addedColumn = false;

		for (int x = corner1.getX(); x <= corner2.getX(); x++)
			for (int z = corner1.getZ(); z <= corner2.getZ(); z++)

				if (plot.getColumns().add(new Column(plot.getWorld(), x, z)))
					addedColumn = true;

		return addedColumn ? null : Error.ALL_COLUMNS_ALREADY_IN_PLOT;
	}
	
	public Error giveSettlerPermission(Plot plot, Settler settler) {
		return plot.addToPlayersWithPermission(settler) ? null : Error.PLAYER_ALREADY_HAS_PERMISSIONS;
	}

}
