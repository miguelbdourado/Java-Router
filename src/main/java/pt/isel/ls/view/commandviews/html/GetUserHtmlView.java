package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetUserResult;
import pt.isel.ls.model.models.Review;
import pt.isel.ls.model.models.User;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.Element;
import pt.isel.ls.view.utils.HtmlPageBuilder;
import pt.isel.ls.view.utils.HtmlTableBuilder;

import java.util.ArrayList;

import static pt.isel.ls.view.utils.HtmlWrapper.anch;
import static pt.isel.ls.view.utils.HtmlWrapper.h1;
import static pt.isel.ls.view.utils.HtmlWrapper.h2;
import static pt.isel.ls.view.utils.HtmlWrapper.li;
import static pt.isel.ls.view.utils.HtmlWrapper.ul;

public class GetUserHtmlView implements ICommandView {

    public String render(CommandResult result) {
        GetUserResult userResult = (GetUserResult) result;

        ArrayList<Review> reviews = userResult.getReviews();
        User user = userResult.getUser();

        if (user == null) {
            return "";
        }

        HtmlPageBuilder html = new HtmlPageBuilder();

        HtmlTableBuilder htmlTableBuilder = new HtmlTableBuilder();

        htmlTableBuilder.setHeaders("REVIEW ID", "MOVIE ID", "SUMMARY", "RATING");

        reviews.forEach(review -> {
            htmlTableBuilder.initRow();

            int rid = review.getId();
            int mid = review.getMovieId();

            htmlTableBuilder.addColumnToRow(
                    anch(String.valueOf(rid), "/movies/" + mid + "/reviews/" + rid)
            );
            htmlTableBuilder.addColumnToRow(String.valueOf(mid));
            htmlTableBuilder.addColumnToRow(review.getSummary());
            htmlTableBuilder.addColumnToRow(String.valueOf(review.getRating()));
        });

        Element list = ul(
                li("USER ID " + user.getId()),
                li("NAME: " + user.getName()),
                li("EMAIL: " + user.getEmail())
        );

        html.setTitle("User");
        html.addToBody(
                anch("Home", "/").addStyle("margin-right", "10px"),
                anch("Users", "/users"),

                h1("User details"),
                list,

                h2("Reviews"),
                htmlTableBuilder.getTable()
        );

        return html.getHtml().toString();
    }
}
