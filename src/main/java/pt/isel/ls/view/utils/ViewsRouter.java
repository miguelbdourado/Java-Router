package pt.isel.ls.view.utils;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.commandviews.html.ErrorView;

import java.util.HashMap;

public class ViewsRouter {

    private final HashMap<Class<? extends CommandResult>, ICommandView> htmlViews = new HashMap<>();
    private final HashMap<Class<? extends CommandResult>, ICommandView> plainViews = new HashMap<>();

    public void addView(String accept, Class<? extends CommandResult> result, ICommandView commandView) {

        if (accept.toUpperCase().equals("TEXT/HTML")) {
            htmlViews.put(result, commandView);
        } else {
            plainViews.put(result, commandView);
        }

    }

    public ICommandView getView(String accept, CommandResult result) {

        CommandStatus status = result.getStatus();
        if (status == CommandStatus.SUCCESSFUL_POST || status == CommandStatus.SUCCESSFUL_GET
                || status == CommandStatus.SUCCESSFUL_DELETE) {
            if (accept.toUpperCase().equals("TEXT/HTML")) {
                return htmlViews.get(result.getClass());
            } else {
                return plainViews.get(result.getClass());
            }
        } else {
            return new ErrorView();
        }
    }
}
