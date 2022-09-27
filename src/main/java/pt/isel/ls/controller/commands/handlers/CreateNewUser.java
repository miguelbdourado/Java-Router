package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.model.Parameters;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.CreateNewUserResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;
import pt.isel.ls.model.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateNewUser implements CommandHandler {

    private final String description = "Creates a new User given the parameters:\n "
            + "name - the user's name\n "
            + "email - the user's unique email.";

    @Override
    public CommandResult execute(CommandRequest commandRequest) {

        CreateNewUserResult result = new CreateNewUserResult();
        TransactionManager transactionManager = new TransactionManager();

        Parameters parameters = commandRequest.getParameters();

        String username = parameters.getParameter("name");
        String userEmail = parameters.getParameter("email");

        if (username == null || userEmail == null) {
            result.setErrorMessage("Parameter: name and email can not be missing.");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        }

        transactionManager.executeTransaction(((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES(DEFAULT, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, userEmail);
            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            User user = new User(rs.getInt("id"), username, userEmail);
            rs.close();
            result.setUser(user);
            preparedStatement.close();
        }));

        result.setStatus(CommandStatus.SUCCESSFUL_POST);
        result.setHeaders(commandRequest.getHeaders());
        return result;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
