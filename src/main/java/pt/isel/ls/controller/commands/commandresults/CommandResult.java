package pt.isel.ls.controller.commands.commandresults;

import pt.isel.ls.model.Headers;

public class CommandResult {

    private CommandStatus status;
    private Headers headers;
    private String errorMessage;

    public CommandStatus getStatus() {
        return status;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Headers getHeaders() {
        return this.headers;
    }
}

