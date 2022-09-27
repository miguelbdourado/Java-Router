package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.model.Parameters;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.CreateNewMovieResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;
import pt.isel.ls.model.models.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateNewMovie implements CommandHandler {

    private final String description = "Creates a new Movie given the parameters:\n "
            + "title - the movie's name\n"
            + "releaseYear - he movie's release year\n"
            + "genre (optional) - the movie's genre";

    @Override
    public CommandResult execute(CommandRequest commandRequest) {

        CreateNewMovieResult result = new CreateNewMovieResult();
        TransactionManager transactionManager = new TransactionManager();
        Parameters parameters = commandRequest.getParameters();

        int releaseYear;
        try {
            releaseYear = Integer.parseInt(parameters.getParameter("releaseYear"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Parameter: releaseYear has to be a number.");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        }

        String movieTitle = parameters.getParameter("title");
        if (movieTitle == null) {
            result.setErrorMessage("Parameter: title missing.");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        }
        String genre = parameters.getParameter("genre");

        transactionManager.executeTransaction((connection) -> {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO movie VALUES(DEFAULT, ?, ?, ?);",
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(3, genre);
            preparedStatement.setInt(2, releaseYear);
            preparedStatement.setString(1, movieTitle);
            preparedStatement.setInt(2, releaseYear);
            preparedStatement.setString(3, genre);
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int movieId = rs.getInt("id");

            preparedStatement = connection.prepareStatement("INSERT INTO rating VALUES(?, 0, 0, 0, 0, 0);");
            preparedStatement.setInt(1, movieId);
            preparedStatement.execute();

            Movie movie = new Movie(movieId, movieTitle, releaseYear, genre);

            rs.close();
            result.setMovie(movie);
            preparedStatement.close();
        });

        result.setStatus(CommandStatus.SUCCESSFUL_POST);
        result.setHeaders(commandRequest.getHeaders());
        return result;
    }

    public String getDescription() {
        return description;
    }
}
