package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;

public class GetMainHandler implements CommandHandler {

    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        CommandResult result = new CommandResult();
        result.setStatus(CommandStatus.SUCCESSFUL_GET);
        result.setHeaders(commandRequest.getHeaders());
        return result;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
