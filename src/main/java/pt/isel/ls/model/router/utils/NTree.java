package pt.isel.ls.model.router.utils;

import pt.isel.ls.model.Method;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.model.path.PathTemplate;
import pt.isel.ls.controller.commands.handlers.CommandHandler;

import java.util.HashMap;

public class NTree {

    private HashMap<Method, MethodNode> firstLevel;

    public NTree() {
        firstLevel = new HashMap<>();
    }

    public void addCommand(Method method, PathTemplate pathTemplate, CommandHandler commandHandler) {
        if (!firstLevel.containsKey(method)) {
            firstLevel.put(method, new MethodNode(method));
        }
        firstLevel.get(method).addPathHandler(new PathHandlerNode(pathTemplate, commandHandler));
    }

    public CommandHandler getCommand(Method method, Path path) {
        if (!firstLevel.containsKey(method)) {
            return null;
        }
        return firstLevel.get(method).getCommandHandlerGivenPath(path);
    }

    public HashMap<Method, MethodNode> getHashMap() {
        return firstLevel;
    }
}