package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetReviewResult;
import pt.isel.ls.model.models.Review;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.Element;
import pt.isel.ls.view.utils.HtmlPageBuilder;

import static pt.isel.ls.view.utils.HtmlWrapper.anch;
import static pt.isel.ls.view.utils.HtmlWrapper.h1;
import static pt.isel.ls.view.utils.HtmlWrapper.li;
import static pt.isel.ls.view.utils.HtmlWrapper.ul;


public class GetReviewHtmlView implements ICommandView {

    public String render(CommandResult result) {
        GetReviewResult reviewResult = (GetReviewResult) result;
        Review review = reviewResult.getReview();


        if (review == null) {
            return "";
        }

        int movieId = review.getMovieId();
        int userId = review.getCriticId();

        Element list = ul(
                li("ID: " + review.getId()),
                li(anch("MOVIE ID: " + movieId, "/movies/" + movieId)),
                li(anch("USER ID: " + userId, "/users/" + userId)),
                li("SUMMARY: " + review.getSummary()),
                li("REVIEW: " + review.getCompleteReview()),
                li("RATING: " + review.getRating())
        );

        HtmlPageBuilder html = new HtmlPageBuilder();
        html.setTitle("Review");
        html.addToBody(
                anch("Home", "/").addStyle("margin-right", "10px"),
                anch("Movie Reviews", "/movies/" + movieId + "/reviews"),

                h1("Review details"),
                list
        );

        return html.getHtml().toString();
    }
}
