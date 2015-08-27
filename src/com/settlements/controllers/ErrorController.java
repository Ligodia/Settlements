package com.settlements.controllers;

import com.settlements.models.Settler;
import com.settlements.models.Error;

public class ErrorController
{

    public void sendErrorMessage(Error error, Settler recipient)
    {

        recipient.getPlayer().sendMessage("Error: " + error.getMessage());
    }
}
