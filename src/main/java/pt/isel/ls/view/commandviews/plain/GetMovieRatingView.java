package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetMovieRatingResult;
import pt.isel.ls.model.models.MovieRating;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.PlainTableBuilder;

public class GetMovieRatingView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        StringBuilder sb = new StringBuilder();
        GetMovieRatingResult moviesResult = (GetMovieRatingResult) result;
        MovieRating rating = moviesResult.getRating();

        if (rating == null) {
            sb.append("This movie does not exist.");
            return sb.toString();
        }

        PlainTableBuilder tableBuilder = new PlainTableBuilder();

        tableBuilder.setHeaders("Rating 1", "Rating 2", "Rating 3", "Rating 4", "Rating 5", "Average");
        tableBuilder.addRow(String.valueOf(rating.getRating1()),
                String.valueOf(rating.getRating2()), String.valueOf(rating.getRating3()),
                String.valueOf(rating.getRating4()), String.valueOf(rating.getRating5()),
                String.valueOf(rating.getAverage()));

        sb.append(tableBuilder.getString());
        return sb.toString();
    }
}

