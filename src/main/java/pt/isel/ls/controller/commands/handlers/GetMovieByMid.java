package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.model.models.Review;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.GetMovieResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;
import pt.isel.ls.model.models.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class GetMovieByMid implements CommandHandler {

    private final String description = "Get the details for the movie identified by mid";

    @Override
    public CommandResult execute(CommandRequest commandRequest) {

        GetMovieResult result = new GetMovieResult();
        TransactionManager transactionManager = new TransactionManager();

        Path path = commandRequest.getPath();

        int movieId;
        try {
            movieId = Integer.parseInt(path.getVariable("mid"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Parameter: mid has to be a valid number.");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        }

        transactionManager.executeTransaction(((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movie WHERE movie.id = ?");
            preparedStatement.setInt(1, movieId);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            result.setMovie(new Movie(rs.getInt("id"), rs.getString("title"),
                    rs.getInt("releaseyear"), rs.getString("genre")));

            preparedStatement = connection.prepareStatement("SELECT * FROM review WHERE review.movieid = ?");
            preparedStatement.setInt(1, movieId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int criticid = rs.getInt("criticid");
                int movieid = rs.getInt("movieid");
                String summary = rs.getString("summary");
                int rating = rs.getInt("rating");

                result.addReview(new Review(id, criticid, movieid, summary, null, rating));
            }

            rs.close();
            preparedStatement.close();
        }));

        result.setStatus(CommandStatus.SUCCESSFUL_GET);
        result.setHeaders(commandRequest.getHeaders());
        return result;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
