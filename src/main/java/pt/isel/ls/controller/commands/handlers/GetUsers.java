package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.GetUsersResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;
import pt.isel.ls.model.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetUsers implements CommandHandler {

    private final String description = "Returns the list of users";

    @Override
    public GetUsersResult execute(CommandRequest commandRequest) {

        GetUsersResult result = new GetUsersResult();

        TransactionManager transactionManager = new TransactionManager();

        transactionManager.executeTransaction((connection) -> {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("id");
                String username = rs.getString("name");
                String userEmail = rs.getString("email");

                result.addUser(new User(userId, username, userEmail));
            }

            rs.close();
            preparedStatement.close();
        });
        result.setHeaders(commandRequest.getHeaders());
        result.setStatus(CommandStatus.SUCCESSFUL_GET);
        return result;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
