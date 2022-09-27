package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetMoviesResult;
import pt.isel.ls.model.models.Movie;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.Element;
import pt.isel.ls.view.utils.FormBuilder;
import pt.isel.ls.view.utils.HtmlPageBuilder;
import pt.isel.ls.view.utils.HtmlTableBuilder;

import java.util.ArrayList;

import static pt.isel.ls.view.utils.HtmlWrapper.anch;
import static pt.isel.ls.view.utils.HtmlWrapper.div;
import static pt.isel.ls.view.utils.HtmlWrapper.h1;

public class GetMoviesHtmlView implements ICommandView {

    public String render(CommandResult result) {
        GetMoviesResult moviesResult = (GetMoviesResult) result;
        ArrayList<Movie> movies = moviesResult.getMovies();

        HtmlPageBuilder html = new HtmlPageBuilder();
        html.setTitle("Movie List");

        HtmlTableBuilder tableBuilder = new HtmlTableBuilder();
        tableBuilder.setHeaders("MOVIE ID", "TITLE", "RELEASE YEAR", "GENRE");

        movies.forEach(movie -> {
            String mid = String.valueOf(movie.getId());
            tableBuilder.initRow();
            tableBuilder.addColumnToRow(anch(mid, "/movies/" + movie.getId()));
            tableBuilder.addColumnToRow(movie.getTitle());
            tableBuilder.addColumnToRow(String.valueOf(movie.getReleaseYear()));
            tableBuilder.addColumnToRow(movie.getGenre() == null ? " " : movie.getGenre());
        });

        html.addToBody(
                anch("Home", "/").addStyle("margin-right", "10px"),
                h1("Movies"),
                tableBuilder.getTable()
        );

        Element pagingDiv = div().addStyle("margin-bottom", "25px");

        int skipValue = moviesResult.getSkip();
        int topValue = moviesResult.getTop();
        if (skipValue > 0) {
            pagingDiv.addElement(anch("Previous Page", "/movies?skip=" + (skipValue - topValue) + "&top=" + topValue)
                    .addStyle("margin-right", "10px"));
        }
        int totalCount = moviesResult.getMovieCount();

        if (topValue == 0) {
            topValue = totalCount;
        }

        for (int i = 0; i < Math.ceil(totalCount / (float) topValue); i++) {
            pagingDiv.addElement(anch(String.valueOf(i), "/movies?skip=" + (i * topValue) + "&top=" + topValue)
                    .addStyle("margin-right", "10px"));
        }

        if (skipValue + topValue < totalCount) {
            pagingDiv.addElement(
                    anch("Next Page", "/movies?skip=" + (skipValue + topValue) + "&top=" + topValue)
            );
        }

        FormBuilder formBuilder = new FormBuilder("", "POST")
                .addInput("Titulo:", "title", "text")
                .addInput("Release Year:", "releaseYear", "number", "0", "")
                .addInput("Genre:", "genre", "text")
                .setSubmitButton("Create Movie");

        html.addToBody(pagingDiv);
        html.addToBody(formBuilder.getFormElement());

        return html.getHtml().toString();
    }
}
