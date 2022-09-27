package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetMovieRatingResult;
import pt.isel.ls.model.models.MovieRating;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.FormBuilder;
import pt.isel.ls.view.utils.HtmlPageBuilder;
import pt.isel.ls.view.utils.HtmlTableBuilder;

import static pt.isel.ls.view.utils.HtmlWrapper.anch;
import static pt.isel.ls.view.utils.HtmlWrapper.h1;
import static pt.isel.ls.view.utils.HtmlWrapper.h2;

public class GetMovieRatingHtmlView implements ICommandView {

    public String render(CommandResult result) {
        GetMovieRatingResult moviesResult = (GetMovieRatingResult) result;
        MovieRating rating = moviesResult.getRating();

        if (rating == null) {
            return "";
        }

        HtmlTableBuilder tableBuilder = new HtmlTableBuilder();
        tableBuilder.setHeaders("Rating 1", "Rating 2", "Rating 3", "Rating 4", "Rating 5", "Average");
        tableBuilder.addRow(String.valueOf(rating.getRating1()),
                String.valueOf(rating.getRating2()), String.valueOf(rating.getRating3()),
                String.valueOf(rating.getRating4()), String.valueOf(rating.getRating5()),
                String.valueOf(rating.getAverage()));

        int mid = moviesResult.getMid();

        FormBuilder ratingFormBuilder = new FormBuilder("", "POST")
                .addInput("Classificação:", "rating", "number", "1", "5")
                .setSubmitButton("Adicionar classificação");

        HtmlPageBuilder html = new HtmlPageBuilder();
        html.setTitle("Movie Ratings");
        html.addToBody(
                anch("Home", "/").addStyle("margin-right", "10px"),
                anch("Movie", "/movies/" + mid),
                h1("Ratings"),
                tableBuilder.getTable(),
                h2("Add rating"),
                ratingFormBuilder.getFormElement()
        );

        return html.getHtml().toString();
    }
}
