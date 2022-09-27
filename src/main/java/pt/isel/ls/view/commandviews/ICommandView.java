package pt.isel.ls.view.commandviews;

import pt.isel.ls.controller.commands.commandresults.CommandResult;

public interface ICommandView {

    String render(CommandResult result);
}
