package pt.isel.ls.model.router;

import pt.isel.ls.controller.commands.handlers.CommandHandler;
import pt.isel.ls.model.Method;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.model.path.PathTemplate;
import pt.isel.ls.model.router.utils.NTree;

import java.util.HashMap;
import java.util.Optional;

public class Router {

    private final NTree commands;

    public Router() {
        commands = new NTree();
    }

    //Add new commands
    public void addRoute(Method method, PathTemplate template, CommandHandler handler) {
        commands.addCommand(method, template, handler);
    }

    //Get the CommandHandler of the given pair method, path.
    public Optional<RouteResult> findRoute(Method method, Path path) {
        return Optional.of(new RouteResult(commands.getCommand(method, path)));
    }

    public HashMap<PathTemplate, String> getAllDescriptions() {
        HashMap<PathTemplate, String> descriptionOfResult = new HashMap<>();

        commands.getHashMap().forEach((key, value) -> {
            value.iterator().forEachRemaining(child -> {
                descriptionOfResult.put(child.getPathTemplate(), child.getCommandHandler().getDescription());
            });
        });

        return descriptionOfResult;
    }

}