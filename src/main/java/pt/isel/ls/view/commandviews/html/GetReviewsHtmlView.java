package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetReviewsResult;
import pt.isel.ls.model.models.Review;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.HtmlPageBuilder;
import pt.isel.ls.view.utils.HtmlTableBuilder;

import java.util.ArrayList;

import static pt.isel.ls.view.utils.HtmlWrapper.anch;
import static pt.isel.ls.view.utils.HtmlWrapper.h1;

public class GetReviewsHtmlView implements ICommandView {

    public String render(CommandResult result) {
        GetReviewsResult reviewsResult = (GetReviewsResult) result;
        ArrayList<Review> reviews = reviewsResult.getReviews();

        HtmlPageBuilder html = new HtmlPageBuilder();
        HtmlTableBuilder tableBuilder = new HtmlTableBuilder();
        tableBuilder.setHeaders("REVIEW ID", "CRITIC ID", "SUMMARY", "COMPLETE REVIEW", "RATING");

        int mid = reviewsResult.getMid();
        if (!reviews.isEmpty()) {
            reviews.forEach(review -> {
                int rid = review.getId();
                tableBuilder.initRow();
                tableBuilder.addColumnToRow(
                        anch(String.valueOf(rid), "/movies/" + mid + "/reviews/" + rid)
                );
                tableBuilder.addColumnToRow(String.valueOf(review.getCriticId()));
                tableBuilder.addColumnToRow(review.getSummary());
                tableBuilder.addColumnToRow(review.getCompleteReview());
                tableBuilder.addColumnToRow(String.valueOf(review.getRating()));
            });
        }


        html.setTitle("Review List");
        html.addToBody(
                anch("Home", "/").addStyle("margin-right", "10px"),
                anch("Movie", "/movies/" + mid),
                h1("Reviews"),
                tableBuilder.getTable()
        );

        return html.getHtml().toString();
    }
}
