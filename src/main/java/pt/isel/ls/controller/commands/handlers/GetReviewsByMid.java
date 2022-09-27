package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.GetReviewsResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;
import pt.isel.ls.model.models.Review;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetReviewsByMid implements CommandHandler {

    private final String description = "Gets information for every review for the movie identified by mid";

    @Override
    public GetReviewsResult execute(CommandRequest commandRequest) {

        GetReviewsResult result = new GetReviewsResult();

        TransactionManager transactionManager = new TransactionManager();

        Path path = commandRequest.getPath();

        int movieId;
        try {
            movieId = Integer.parseInt(path.getVariable("mid"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Path Variable: mid has to be a valid number.");
            result.setStatus(CommandStatus.PATH_ERROR);
            return result;
        }

        transactionManager.executeTransaction((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM review WHERE movieid = ?");
            preparedStatement.setInt(1, movieId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int reviewId = rs.getInt("id");
                int criticId = rs.getInt("criticid");
                String summary = rs.getString("summary");
                String completeReview = rs.getString("completeReview");
                int rating = rs.getInt("rating");

                result.add(new Review(reviewId, criticId, movieId, summary, completeReview, rating));
            }

            result.setMid(movieId);

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
