package pt.isel.ls.controller.commands.handlers;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.GetMovieRatingResult;
import pt.isel.ls.controller.commands.handlers.utils.TransactionManager;
import pt.isel.ls.model.models.MovieRating;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class GetMovieRatingByMid implements CommandHandler {

    private final String description = "Gets the rating information for the movie identified mid";

    @Override
    public CommandResult execute(CommandRequest commandRequest) {

        GetMovieRatingResult result = new GetMovieRatingResult();
        TransactionManager transactionManager = new TransactionManager();

        Path path = commandRequest.getPath();

        int mid;
        try {
            mid = Integer.parseInt(path.getVariable("mid"));
        } catch (NumberFormatException | NullPointerException throwable) {
            result.setErrorMessage("Path Variable: mid has to be a valid number.");
            result.setStatus(CommandStatus.PATH_ERROR);
            return result;
        }

        transactionManager.executeTransaction(((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT *, (rating1 + rating2 + rating3 + rating4 + rating5) "
                            + "as votessum FROM rating WHERE movieid = ?");
            preparedStatement.setInt(1, mid);
            ResultSet rs = preparedStatement.executeQuery();

            rs.next();
            int votesum = rs.getInt("votessum");
            int rating1 = rs.getInt("rating1");
            int rating2 = rs.getInt("rating2");
            int rating3 = rs.getInt("rating3");
            int rating4 = rs.getInt("rating4");
            int rating5 = rs.getInt("rating5");
            double average = (rating1 + 2 * rating2 + 3 * rating3 + 4 * rating4 + 5 * rating5) / (float) votesum;
            String averageRounded = new DecimalFormat("#.##").format(average);
            result.setRating(new MovieRating(rating1, rating2, rating3, rating4, rating5, averageRounded));

            rs.close();
            preparedStatement.close();
        }));
        result.setMid(mid);
        result.setStatus(CommandStatus.SUCCESSFUL_GET);
        result.setHeaders(commandRequest.getHeaders());
        return result;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
