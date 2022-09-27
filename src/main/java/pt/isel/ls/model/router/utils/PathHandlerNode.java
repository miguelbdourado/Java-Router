package pt.isel.ls.model.router.utils;

import pt.isel.ls.model.path.PathTemplate;
import pt.isel.ls.controller.commands.handlers.CommandHandler;

public class PathHandlerNode {
    //Either add a complete PathTemplate or divide it by '/'

    private PathTemplate pathTemplate;
    private CommandHandler commandHandler;

    public PathHandlerNode(PathTemplate pathTemplate, CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.pathTemplate = pathTemplate;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public PathTemplate getPathTemplate() {
        return pathTemplate;
    }

}
