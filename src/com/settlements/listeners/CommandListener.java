package com.settlements.listeners;

import com.settlements.Settlements;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Dan on 23-Aug-15.
 */
public class CommandListener implements CommandExecutor
{
    private final Settlements plugin;

    public CommandListener(Settlements plugin)
    {
        super();
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("settlements")) {

            sender.sendMessage("Settlements is a plugin based around settlements.");
        }

        return true;
    }
}
