package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.model.Parameters;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.SubmitNewRatingResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;

import java.sql.PreparedStatement;

public class SubmitNewRatingForMovie implements CommandHandler {

    private final String description = "Submits a new rating for the movie identified by mid, given the parameter:\n"
            + "rating - integer between 1 and 5.";

    @Override
    public CommandResult execute(CommandRequest commandRequest) {

        SubmitNewRatingResult result = new SubmitNewRatingResult();

        Parameters parameters = commandRequest.getParameters();
        Path path = commandRequest.getPath();

        int movieId;
        int rating;
        try {
            movieId = Integer.parseInt(path.getVariable("mid"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Path Variable: mid has to be a valid number.");
            result.setStatus(CommandStatus.PATH_ERROR);
            return result;
        }

        try {
            rating = Integer.parseInt(parameters.getParameter("rating"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Parameter: rating has to be a valid number");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        }

        if (rating > 5 || rating < 1) {
            result.setErrorMessage("Parameter: rating has to be a value between 1 and 5");
            result.setStatus(CommandStatus.PARAMETERS_ERROR);
            return result;
        }

        String columnRatingName = "rating" + rating;

        TransactionManager transactionManager = new TransactionManager();
        transactionManager.executeTransaction((connection) -> {
            //TODO: resolve bug: this says rating submitted when movie does not exist
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE RATING SET " + columnRatingName
                    + " = (SELECT " + columnRatingName + " FROM RATING WHERE movieid = ?) + 1 WHERE movieid = ?");
            preparedStatement.setInt(1, movieId);
            preparedStatement.setInt(2, movieId);
            preparedStatement.executeUpdate();
        });

        result.setStatus(CommandStatus.SUCCESSFUL_POST);
        result.setRating(rating);
        result.setMovieid(movieId);
        result.setHeaders(commandRequest.getHeaders());
        return result;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
