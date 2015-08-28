package com.settlements.models;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;

public class Plot {

	private Settlement settlement;
	private Set<Column> columns;
	private Settler owner;
	private Set<Settler> playersWithPermission;
	private World world;

	public Plot(Settlement settlement, World world, Set<Column> columns, Settler owner) {
		this.settlement = settlement;
		this.world = world;
		this.columns = columns;
		this.owner = owner;
		playersWithPermission = new HashSet<Settler>();
	}
	
	public Settlement getSettlement() {
		return settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	public Settler getOwner() {
		return owner;
	}

	public void setOwner(Settler owner) {
		this.owner = owner;
	}

	public Set<Settler> getPlayersWithPermission() {
		return playersWithPermission;
	}

	public void setPlayersWithPermission(Set<Settler> playersWithPermission) {
		this.playersWithPermission = new HashSet<Settler>();
	}
	
	public boolean addToPlayersWithPermission(Settler settler) {
		return playersWithPermission.add(settler);
	}
	
	public boolean removeFromPlayersWithPermission(Settler settler) {
		return playersWithPermission.remove(settler);
	}

	public Set<Column> getColumns() {
		return columns;
	}

	public World getWorld() {
		return world;
	}

}
