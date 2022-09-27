package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CreateNewMovieResult;
import pt.isel.ls.model.models.Movie;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.HtmlPageBuilder;
import pt.isel.ls.view.utils.HtmlTableBuilder;

import static pt.isel.ls.view.utils.HtmlWrapper.h1;

public class CreateNewMovieHtmlView implements ICommandView {

    public String render(CommandResult result) {
        CreateNewMovieResult moviesResult = (CreateNewMovieResult) result;
        Movie movie = moviesResult.getMovie();

        HtmlTableBuilder tableBuilder = new HtmlTableBuilder();

        if (movie == null) {
            return "";
        }

        tableBuilder.setHeaders("Movie ID", "Movie Title", "Release Year", "Genre");
        tableBuilder.addRow(String.valueOf(movie.getId()), movie.getTitle(),
                String.valueOf(movie.getReleaseYear()), movie.getGenre());

        HtmlPageBuilder html = new HtmlPageBuilder();
        html.setTitle("New Movie Created");
        html.addToBody(
                h1("Movie"),
                tableBuilder.getTable()
        );

        return html.getHtml().toString();
    }
}
