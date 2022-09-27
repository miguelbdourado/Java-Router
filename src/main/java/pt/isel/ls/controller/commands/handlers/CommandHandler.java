package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.controller.commands.commandresults.CommandResult;

public interface CommandHandler {

    CommandResult execute(CommandRequest commandRequest);

    String getDescription();
}