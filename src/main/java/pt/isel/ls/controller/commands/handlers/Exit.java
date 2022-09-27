package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.ExitResult;

public class Exit implements CommandHandler {

    private final String description = "Exits the program";

    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        ExitResult result = new ExitResult();
        result.setStatus(CommandStatus.EXIT);
        return result;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
