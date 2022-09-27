package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetReviewsResult;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.PlainTableBuilder;

public class GetReviewsView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        GetReviewsResult reviewsResult = (GetReviewsResult) result;

        PlainTableBuilder tableBuilder = new PlainTableBuilder();
        tableBuilder.setHeaders("REVIEW ID", "MOVIE ID", "CRITIC ID", "SUMMARY", "COMPLETE REVIEW", "RATING");

        reviewsResult.getReviews().forEach(review ->
                tableBuilder.addRow(String.valueOf(review.getId()), String.valueOf(review.getMovieId()),
                        String.valueOf(review.getCriticId()), review.getSummary(), review.getCompleteReview(),
                        String.valueOf(review.getRating()))
        );

        return tableBuilder.getString();
    }

}
