package com.settlements.controllers;

import com.settlements.models.Column;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Manipulates columns
 *
 * @author Dan
 */
public class ColumnController
{

    /**
     * Checks whether the location can be found in the columns
     * boundaries.
     *
     * @param column   The column you're checking in
     * @param location The location you're checking for
     * @return true if the location is in the column
     */
    public boolean containsLocation(Column column, Location location)
    {

        int columnX = column.getX();
        double locationX = location.getX();
        int columnZ = column.getZ();
        double locationZ = location.getZ();

        return (columnX <= locationX) &&
                (locationX < columnX + 1) &&
                (columnZ <= locationZ) &&
                (locationZ < columnZ + 1);
    }

    /**
     * Gets a list of blocks that the given column contains
     *
     * @param column The column containing the returned blocks
     * @return A list of blocks in the column from 0 to 255
     */
    public List<Block> getBlocks(Column column)
    {

        List<Block> blocks = new ArrayList<Block>();

        for (int y = 0; y < 256; y++)
        {

            blocks.add(new Location
                    (
                            column.getWorld(),
                            column.getX(), y,
                            column.getZ()
                    ).getBlock());
        }

        return blocks;
    }

    /**
     * Returns the set of columns adjacent to a given column
     *
     * @param column the column at the centre of the returned columns
     * @return the adjacent columns
     */
    public Set<Column> getAdjacent(Column column)
    {

        Set<Column> columns = new HashSet<>();

        columns.add(new Column(column.getWorld(), column.getX() - 1, column.getZ()));
        columns.add(new Column(column.getWorld(), column.getX() + 1, column.getZ()));
        columns.add(new Column(column.getWorld(), column.getX(), column.getZ() - 1));
        columns.add(new Column(column.getWorld(), column.getX(), column.getZ() + 1));

        return columns;
    }

    public boolean isAdjacent(Column column, Set<Column> columns)
    {
        for (Column eachColumn : getAdjacent(column))
            if (columns.contains(eachColumn)) return true;

        return false;
    }
}
