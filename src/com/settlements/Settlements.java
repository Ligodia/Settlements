package com.settlements;

import com.settlements.controllers.ColumnController;
import com.settlements.controllers.SettlerController;
import com.settlements.controllers.SettlementController;
import com.settlements.listeners.CommandListener;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Settlements extends JavaPlugin {

	private ColumnController columnController = new ColumnController();
	private SettlerController settlerController = new SettlerController(
			columnController);
	private SettlementController settlementController = new SettlementController(
			columnController, settlerController);

	@Override
	public void onEnable() {
		this.getCommand("settlements").setExecutor(new CommandListener(this));
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
}
