package com.settlements.models;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Represents a column of blocks from level 0 to 255.
 *
 * @author Dan
 */
public class Column
{
    private World world;
    private int x, z;

    public Column(World world, int x, int z)
    {

        this.world = world;
        this.x = x;
        this.z = z;
    }

    public Column(Location loc)
    {

        this.world = loc.getWorld();
        this.x = loc.getBlockX();
        this.z = loc.getBlockZ();
    }

    public World getWorld()
    {
        return world;
    }

    public int getX()
    {
        return x;
    }

    public int getZ()
    {
        return z;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;

        if (!(obj instanceof Column)) return false;

        Column other = (Column) obj;

        return other.getWorld().equals(world)
                && other.getX() == x
                && other.getZ() == x;
    }

    @Override
    public int hashCode()
    {
        return 19 * world.hashCode() * x * z;
    }

    @Override
    public String toString()
    {
        return String.format("World:%s, x:%d, z:%d", world.toString(), x, z);
    }
}
