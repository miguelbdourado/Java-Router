package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.GetUserResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;
import pt.isel.ls.model.models.Review;
import pt.isel.ls.model.models.User;
import pt.isel.ls.model.path.Path;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetUserByUid implements CommandHandler {

    private final String description = "Returns the details for the user identified by uid";

    @Override
    public GetUserResult execute(CommandRequest commandRequest) {

        GetUserResult result = new GetUserResult();
        TransactionManager transactionManager = new TransactionManager();

        Path path = commandRequest.getPath();
        int userId;

        try {
            userId = Integer.parseInt(path.getVariable("uid"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Path Variable: uid has to be a valid number.");
            result.setStatus(CommandStatus.PATH_ERROR);
            return result;
        }

        transactionManager.executeTransaction((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE users.id = ?");
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("name");
                String userEmail = rs.getString("email");

                result.setUser(new User(id, username, userEmail));
            }

            if (result.getUser() == null) {
                result.setStatus(CommandStatus.PATH_ERROR);
                result.setErrorMessage("This user does not exist.");
                rs.close();
                preparedStatement.close();
                return;
            }

            preparedStatement = connection.prepareStatement("SELECT * FROM review WHERE review.criticid = ?");
            preparedStatement.setInt(1, userId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int criticid = rs.getInt("criticid");
                int movieid = rs.getInt("movieid");
                String summary = rs.getString("summary");
                int rating = rs.getInt("rating");

                result.addReview(new Review(id, criticid, movieid, summary, null, rating));
            }

            result.setStatus(CommandStatus.SUCCESSFUL_GET);
            rs.close();
            preparedStatement.close();
        });
        result.setHeaders(commandRequest.getHeaders());
        return result;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
