package com.settlements;

import com.settlements.controllers.ColumnController;
import com.settlements.controllers.SettlerController;
import com.settlements.controllers.SettlementController;
import com.settlements.listeners.CommandListener;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Settlements extends JavaPlugin {

	private SettlerController settlerController = new SettlerController();
	private ColumnController columnController = new ColumnController();
	private SettlementController settlementController = new SettlementController(
			columnController);

	@Override
	public void onEnable() {
		this.getCommand("settlements").setExecutor(new CommandListener(this));
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
}
