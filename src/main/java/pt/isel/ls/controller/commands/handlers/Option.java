package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.OptionResult;


public class Option implements CommandHandler {

    private final String description = "Returns the list of commands and their descriptions";

    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        OptionResult result = new OptionResult();
        result.setStatus(CommandStatus.OPTION);
        result.setHeaders(commandRequest.getHeaders());
        return result;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

