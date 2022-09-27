package pt.isel.ls.model.router;

import pt.isel.ls.controller.commands.handlers.CommandHandler;

public class RouteResult {
    //Contains the CommandHandler or nothing

    private CommandHandler commandHandler;

    public RouteResult(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

}
