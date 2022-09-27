package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.model.Parameters;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.GetTopRatingsResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;
import pt.isel.ls.model.models.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class GetTopRatings implements CommandHandler {

    private final String description = "Returns a list with the movies, given the following parameters: \n"
            + "n - max number of movies to list\n"
            + "average (highest / lowest) - movies with the highest/lowest average ratings:\n"
            + "min - minimum number of votes";

    @Override
    public GetTopRatingsResult execute(CommandRequest commandRequest) {

        GetTopRatingsResult result = new GetTopRatingsResult();
        TransactionManager transactionManager = new TransactionManager();
        Parameters parameters = commandRequest.getParameters();

        int n;
        int min;
        try {
            n = Integer.parseInt(parameters.getParameter("n"));
            min = Integer.parseInt(parameters.getParameter("min"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Parameter: n and min have to be valid numbers.");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        }

        String average = parameters.getParameter("average");
        if (average == null || (!average.equals("highest") && !average.equals("lowest"))) {
            result.setErrorMessage("Parameter: average can only be highest or lowest.");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        }

        transactionManager.executeTransaction((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT movieid, "
                    + "title, releaseYear, genre,"
                    + " (totalSum::float / numberOfVotes::float) "
                    + "as average FROM (\n"
                    + "SELECT \n"
                    + "movieid,\n"
                    + "(1*rating1 + 2*rating2 + 3*rating3 + 4*rating4 + 5*rating5) as totalSum,\n"
                    + "(rating1 + rating2 + rating3 + rating4 + rating5) as numberOfVotes\n"
                    + "FROM rating ) as subQuery join movie on subQuery.movieid = movie.id\n"
                    + "WHERE numberOfVotes > ?\n"
                    + "ORDER BY (totalSum::float / numberOfVotes::float) "
                    + (average.equals("highest") ? "DESC" : "ASC") + "\n"
                    + "LIMIT ?;");
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2, n);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int movieId = rs.getInt("movieid");
                String averageRating = new DecimalFormat("#.##").format(rs.getDouble("average"));
                String title = rs.getString("title");
                int releaseYear = rs.getInt("releaseYear");
                String genre = rs.getString("genre");

                result.add(new Movie(movieId, title, releaseYear, genre), averageRating);
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
