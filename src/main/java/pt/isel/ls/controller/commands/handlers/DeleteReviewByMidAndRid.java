package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.DeleteReviewResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeleteReviewByMidAndRid implements CommandHandler {

    private final String description = "Deletes the review identified by rid "
            + "submitted to the movie identified by mid";

    @Override
    public CommandResult execute(CommandRequest commandRequest) {
        DeleteReviewResult result = new DeleteReviewResult();
        TransactionManager transactionManager = new TransactionManager();

        Path path = commandRequest.getPath();

        int movieId;
        int reviewId;
        try {
            movieId = Integer.parseInt(path.getVariable("mid"));
            reviewId = Integer.parseInt(path.getVariable("rid"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Parameter: mid and rid have to be valid numbers.");
            result.setStatus(CommandStatus.PATH_ERROR);
            return result;
        }

        transactionManager.executeTransaction((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT rating FROM review WHERE id = ? AND movieid = ?");
            preparedStatement.setInt(1, reviewId);
            preparedStatement.setInt(2, movieId);
            ResultSet rs = preparedStatement.executeQuery();

            rs.next();

            try {
                rs.getInt(1);
            } catch (Exception e) {
                result.setStatus(CommandStatus.PARAMETERS_ERROR);
                result.setErrorMessage("Error in review ID or Movie ID");
                return;
            }


            int rating = rs.getInt("rating");
            String columnRatingName = "rating" + rating;

            preparedStatement = connection.prepareStatement("UPDATE rating SET " + columnRatingName + " = "
                    + columnRatingName + " - 1 WHERE movieid = ?;"
                    + "DELETE FROM review WHERE id = ? AND movieid = ?;");
            preparedStatement.setInt(1, movieId);
            preparedStatement.setInt(2, reviewId);
            preparedStatement.setInt(3, movieId);
            preparedStatement.executeUpdate();
        });

        if (result.getStatus() != CommandStatus.PARAMETERS_ERROR) {
            result.setStatus(CommandStatus.SUCCESSFUL_DELETE);
            result.setHeaders(commandRequest.getHeaders());
        }
        return result;
    }

    public String getDescription() {
        return description;
    }
}
