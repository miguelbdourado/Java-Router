package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.model.Parameters;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.CreateNewReviewResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;
import pt.isel.ls.model.models.Review;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateNewReviewForMovieByMid implements CommandHandler {

    private final String description = "Creates a new Review for the movie identified by mid, given the parameters:\n"
            + "uid - user identifier\n"
            + "reviewSummary - the review summary\n"
            + "review - the complete review\n"
            + "rating - the review rating (integer from 1 to 5)";

    @Override
    public CommandResult execute(CommandRequest commandRequest) {

        CreateNewReviewResult result = new CreateNewReviewResult();
        Parameters parameters = commandRequest.getParameters();
        Path path = commandRequest.getPath();

        String summary = parameters.getParameter("reviewSummary");
        String review = parameters.getParameter("review");

        if (summary == null || review == null) {
            result.setErrorMessage("Parameter: reviewSummary and review can not be empty.");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        }

        int uid;
        int movieId;
        int rating;

        try {
            uid = Integer.parseInt(parameters.getParameter("uid"));
            rating = Integer.parseInt(parameters.getParameter("rating"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Parameter: uid and rating have to be valid numbers.");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        }

        try {
            movieId = Integer.parseInt(path.getVariable("mid"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Path Variable: mid is not a valid number.");
            result.setStatus(CommandStatus.PATH_ERROR);
            return result;
        }

        if (rating > 5 || rating < 1) {
            result.setErrorMessage("Parameter: rating has to be a number between 1 and 5.");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        }

        String columnRatingName = "rating" + rating;

        TransactionManager transactionManager = new TransactionManager();
        transactionManager.executeTransaction(((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE RATING SET " + columnRatingName
                    + " = " + columnRatingName + "+ 1 WHERE movieid = ?;");

            preparedStatement.setInt(1, movieId);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO review VALUES(DEFAULT, ?, ?, ?, ?, ?) RETURNING id");
            preparedStatement.setInt(1, uid);
            preparedStatement.setInt(2, movieId);
            preparedStatement.setString(3, summary);
            preparedStatement.setString(4, review);
            preparedStatement.setInt(5, rating);
            ResultSet rs = preparedStatement.executeQuery();

            rs.next();
            Review reviewObject = new Review(rs.getInt("id"), uid, movieId, summary, review, rating);
            rs.close();
            result.setReview(reviewObject);
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
