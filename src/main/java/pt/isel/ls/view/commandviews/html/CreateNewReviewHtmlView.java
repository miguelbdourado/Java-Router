package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CreateNewReviewResult;
import pt.isel.ls.model.models.Review;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.HtmlPageBuilder;
import pt.isel.ls.view.utils.HtmlTableBuilder;

import static pt.isel.ls.view.utils.HtmlWrapper.h1;

public class CreateNewReviewHtmlView implements ICommandView {
    public String render(CommandResult result) {
        CreateNewReviewResult reviewResult = (CreateNewReviewResult) result;
        Review review = reviewResult.getReview();

        HtmlTableBuilder tableBuilder = new HtmlTableBuilder();

        if (review == null) {
            return "";
        }

        tableBuilder.setHeaders("Review ID", "Critic ID", "Movie ID", "Review Summary", "Complete Review",
                "Review Rating");
        tableBuilder.addRow(String.valueOf(review.getId()), String.valueOf(review.getCriticId()),
                String.valueOf(review.getMovieId()), review.getSummary(), review.getCompleteReview(),
                String.valueOf(review.getRating()));

        HtmlPageBuilder html = new HtmlPageBuilder();
        html.setTitle("New Review Created");
        html.addToBody(
                h1("Review"),
                tableBuilder.getTable()
        );

        return html.getHtml().toString();
    }
}
