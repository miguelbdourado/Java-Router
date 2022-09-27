package pt.isel.ls.controller.commands;

import pt.isel.ls.model.Headers;

import pt.isel.ls.model.Parameters;
import pt.isel.ls.model.path.Path;

public class CommandRequest {

    private Path path;
    private Parameters parameters;
    private Headers headers;

    public CommandRequest(Path path, Headers headers, Parameters parameters) {
        this.path = path;
        this.headers = headers;
        this.parameters = parameters;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Headers getHeaders() {
        return this.headers;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }
}
