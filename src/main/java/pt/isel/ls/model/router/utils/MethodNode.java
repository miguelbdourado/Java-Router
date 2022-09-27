package pt.isel.ls.model.router.utils;

import pt.isel.ls.model.Method;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.controller.commands.handlers.CommandHandler;

import java.util.ArrayList;
import java.util.Iterator;

public class MethodNode implements Iterable {
    private Method method;
    private final ArrayList<PathHandlerNode> children;

    public MethodNode(Method method) {
        setMethod(method);
        children = new ArrayList<>();
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return this.method;
    }

    public void addPathHandler(PathHandlerNode node) {
        if (!this.children.contains(node)) {
            this.children.add(node);
        }
    }

    public CommandHandler getCommandHandlerGivenPath(Path path) {
        for (PathHandlerNode node : children) {
            if (node.getPathTemplate().isTemplateOf(path)) {
                return node.getCommandHandler();
            }
        }
        return null;
    }

    @Override
    public Iterator<PathHandlerNode> iterator() {
        return children.iterator();
    }

}
