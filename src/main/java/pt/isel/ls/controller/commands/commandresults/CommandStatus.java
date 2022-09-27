package pt.isel.ls.controller.commands.commandresults;

public enum CommandStatus {


    SUCCESSFUL_GET(200), SUCCESSFUL_POST(200), SUCCESSFUL_DELETE(200),
    PATH_ERROR(404), PARAMETERS_ERROR(400), SQL_ERROR(500), ROUTE_NOT_FOUND_ERROR(404),
    INVALID_COMMAND_ERROR(0), EXIT(-1), OPTION(1), CLOSED_SERVER_SUCCESS(2);

    private final int statusCode;

    CommandStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}