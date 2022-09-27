package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.model.Parameters;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.GetMoviesResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;
import pt.isel.ls.model.models.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetMovies implements CommandHandler {

    private final String description = "Returns a list with all movies";

    @Override
    public CommandResult execute(CommandRequest commandRequest) {

        GetMoviesResult result = new GetMoviesResult();
        TransactionManager transactionManager = new TransactionManager();

        Parameters parameters = commandRequest.getParameters();

        String query = "SELECT * FROM movie";
        int top = 5;
        int skip = 0;
        boolean paging = true;
        try {
            String topString = parameters.getParameter("top");
            String skipString = parameters.getParameter("skip");

            if (!(topString == null || skipString == null)) {
                top = Integer.parseInt(topString);
                skip = Integer.parseInt(skipString);
            }

            query += " LIMIT ? OFFSET ?";
        } catch (NumberFormatException throwable) {
            result.setErrorMessage("Parameter: top and skip have to be valid numbers.");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        } catch (NullPointerException throwable) {
            paging = false;
        }

        String finalQuery = query;
        boolean finalPaging = paging;
        int finalTop = top;
        int finalSkip = skip;
        transactionManager.executeTransaction((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(finalQuery);
            if (finalPaging) {
                preparedStatement.setInt(1, finalTop);
                preparedStatement.setInt(2, finalSkip);
            }
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int movieId = rs.getInt("id");
                String movieTitle = rs.getString("title");
                int releaseYear = rs.getInt("releaseyear");
                String genre = rs.getString("genre");

                result.addMovie(new Movie(movieId, movieTitle, releaseYear, genre));
            }

            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM movie");
            rs = preparedStatement.executeQuery();

            rs.next();

            int movieCount = rs.getInt(1);
            result.setTotalCount(movieCount);

            rs.close();


            preparedStatement.close();
        });
        result.setStatus(CommandStatus.SUCCESSFUL_GET);
        result.setHeaders(commandRequest.getHeaders());
        result.setSkip(skip);
        result.setTop(top);
        return result;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
