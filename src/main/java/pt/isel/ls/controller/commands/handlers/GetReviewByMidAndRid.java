package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.GetReviewResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;
import pt.isel.ls.model.models.Review;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetReviewByMidAndRid implements CommandHandler {

    private final String description = "Gets information for the review identified by rid "
            + "submitted to the movie identified by mid";

    @Override
    public GetReviewResult execute(CommandRequest commandRequest) {

        GetReviewResult result = new GetReviewResult();
        TransactionManager transactionManager = new TransactionManager();

        Path path = commandRequest.getPath();
        int movieId;
        int reviewId;

        try {
            movieId = Integer.parseInt(path.getVariable("mid"));
            reviewId = Integer.parseInt(path.getVariable("rid"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Path Variable: mid and rid have to be valid numbers.");
            result.setStatus(CommandStatus.PATH_ERROR);
            return result;
        }

        transactionManager.executeTransaction((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT criticid, movieid, summary, completereview, rating "
                            + "FROM review WHERE movieid = ? AND id = ?");
            preparedStatement.setInt(1, movieId);
            preparedStatement.setInt(2, reviewId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int criticId = rs.getInt("criticid");
                String summary = rs.getString("summary");
                String completeReview = rs.getString("completereview");
                int rating = rs.getInt("rating");

                result.setReview(new Review(reviewId, criticId, movieId, summary, completeReview, rating));
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