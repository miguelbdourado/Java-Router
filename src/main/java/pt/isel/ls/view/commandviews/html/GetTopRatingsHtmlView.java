package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetTopRatingsResult;
import pt.isel.ls.model.models.Movie;
import pt.isel.ls.utils.Pair;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.HtmlPageBuilder;
import pt.isel.ls.view.utils.HtmlTableBuilder;

import java.util.ArrayList;

import static pt.isel.ls.view.utils.HtmlWrapper.anch;
import static pt.isel.ls.view.utils.HtmlWrapper.h1;

public class GetTopRatingsHtmlView implements ICommandView {

    public String render(CommandResult result) {
        GetTopRatingsResult topRatingsResult = (GetTopRatingsResult) result;
        ArrayList<Pair<Movie, String>> movies = topRatingsResult.getMovies();

        if (movies.isEmpty()) {
            return "";
        }

        HtmlPageBuilder html = new HtmlPageBuilder();
        HtmlTableBuilder tableBuilder = new HtmlTableBuilder();

        tableBuilder.setHeaders("MOVIE ID", "MOVIE TITLE", "RELEASE YEAR", "AVERAGE RATING");
        movies.forEach(pair -> {
            tableBuilder.initRow();
            int mid = pair.getKey().getId();
            tableBuilder.addColumnToRow(
                    anch(String.valueOf(mid),"/movies/" + mid)
            );
            tableBuilder.addColumnToRow(pair.getKey().getTitle());
            tableBuilder.addColumnToRow(String.valueOf(pair.getKey().getReleaseYear()));
            tableBuilder.addColumnToRow(pair.getValue());
        });

        html.setTitle("Top Movies List");
        html.addToBody(
                anch("Home", "/").addStyle("margin-right", "10px"),
                anch("Movies", "/movies?skip=0&top=5").addStyle("margin-right", "10px"),
                h1("Top Movies"),
                tableBuilder.getTable()
        );

        return html.getHtml().toString();
    }
}
