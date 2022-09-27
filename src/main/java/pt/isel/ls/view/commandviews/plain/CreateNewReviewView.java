package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CreateNewReviewResult;
import pt.isel.ls.model.models.Review;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.PlainTableBuilder;

public class CreateNewReviewView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        StringBuilder sb = new StringBuilder();
        CreateNewReviewResult reviewResult = (CreateNewReviewResult) result;
        Review review = reviewResult.getReview();

        if (review == null) {
            sb.append("Error creating the review.");
            return sb.toString();
        }

        PlainTableBuilder tableBuilder = new PlainTableBuilder();

        tableBuilder.setHeaders("Review ID", "Critic ID", "Movie ID", "Review Summary", "Complete Review",
                "Review Rating");
        tableBuilder.addRow(String.valueOf(review.getId()), String.valueOf(review.getCriticId()),
                String.valueOf(review.getMovieId()), review.getSummary(), review.getCompleteReview(),
                String.valueOf(review.getRating()));

        sb.append(tableBuilder.getString());
        return sb.toString();
    }
}
