package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetMovieResult;
import pt.isel.ls.model.models.Movie;
import pt.isel.ls.model.models.Review;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.FormBuilder;
import pt.isel.ls.view.utils.HtmlPageBuilder;
import pt.isel.ls.view.utils.HtmlTableBuilder;

import java.util.ArrayList;

import static pt.isel.ls.view.utils.HtmlWrapper.anch;
import static pt.isel.ls.view.utils.HtmlWrapper.h1;
import static pt.isel.ls.view.utils.HtmlWrapper.h2;

public class GetMovieHtmlView implements ICommandView {

    public String render(CommandResult result) {
        GetMovieResult movieResult = (GetMovieResult) result;
        Movie movie = movieResult.getMovie();

        int mid = movie.getId();
        HtmlTableBuilder tableBuilderMovie = new HtmlTableBuilder();
        tableBuilderMovie.setHeaders("ID", "TITLE", "RELEASE YEAR", "GENRE");
        tableBuilderMovie.initRow();
        tableBuilderMovie.addColumnToRow(
                anch(String.valueOf(mid), "/movies/" + mid)
        );
        tableBuilderMovie.addColumnToRow(movie.getTitle());
        tableBuilderMovie.addColumnToRow(String.valueOf(movie.getReleaseYear()));
        tableBuilderMovie.addColumnToRow(movie.getGenre() == null ? " " : movie.getGenre());

        HtmlTableBuilder tableBuilderReviews = new HtmlTableBuilder();
        tableBuilderReviews.setHeaders("REVIEW ID", "CRITIC ID", "SUMMARY", "RATING");

        ArrayList<Review> reviews = movieResult.getReviews();
        reviews.forEach(review -> {
            int rid = review.getId();

            tableBuilderReviews.initRow();
            tableBuilderReviews.addColumnToRow(
                    anch(String.valueOf(rid), "/movies/" + mid + "/reviews/" + rid)
            );
            tableBuilderReviews.addColumnToRow(
                    anch(String.valueOf(review.getCriticId()), "/users/" + review.getCriticId())
            );
            tableBuilderReviews.addColumnToRow(review.getSummary());
            tableBuilderReviews.addColumnToRow(String.valueOf(review.getRating()));
        });

        FormBuilder reviewFormBuilder = new FormBuilder("/movies/" + mid + "/reviews", "POST")
                .addInput("User ID:", "uid", "number", "0", "")
                .addInput("Summary:", "reviewSummary", "text")
                .addInput("Full review:", "review", "text")
                .addInput("Rating:", "rating", "number", "1", "5")
                .setSubmitButton("Create Review");


        HtmlPageBuilder html = new HtmlPageBuilder();
        html.setTitle("Review List");
        html.addToBody(
                anch("Home", "/").addStyle("margin-right", "10px"),
                anch("Movies", "/movies?skip=0&top=5").addStyle("margin-right", "10px"),
                anch("Reviews", "/movies/" + mid + "/reviews").addStyle("margin-right", "10px"),
                anch("Ratings", "/movies/" + mid + "/ratings"),
                h1("Movie Details"),
                tableBuilderMovie.getTable(),
                h1("Reviews"),
                tableBuilderReviews.getTable(),
                h2("Create Review"),
                reviewFormBuilder.getFormElement()
        );

        return html.getHtml().toString();
    }
}
