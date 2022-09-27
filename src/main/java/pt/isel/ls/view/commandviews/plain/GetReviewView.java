package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetReviewResult;
import pt.isel.ls.model.models.Review;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.PlainTableBuilder;

public class GetReviewView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        GetReviewResult reviewResult = (GetReviewResult) result;
        Review review = reviewResult.getReview();

        if (review == null) {
            return "This review does not exist.";
        }

        PlainTableBuilder tableBuilder = new PlainTableBuilder();

        tableBuilder.setHeaders("REVIEW ID", "MOVIE ID", "CRITIC ID", "SUMMARY", "COMPLETE REVIEW", "RATING");
        tableBuilder.addRow(String.valueOf(review.getId()), String.valueOf(review.getMovieId()),
                String.valueOf(review.getCriticId()), review.getSummary(), review.getCompleteReview(),
                String.valueOf(review.getRating()));

        return tableBuilder.getString();
    }


}
