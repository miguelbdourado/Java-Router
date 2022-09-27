package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetMovieResult;
import pt.isel.ls.model.models.Movie;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.PlainTableBuilder;

public class GetMovieView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        GetMovieResult moviesResult = (GetMovieResult) result;

        Movie movie = moviesResult.getMovie();

        if (movie == null) {
            return "This movie does not exist";
        }

        PlainTableBuilder tableBuilder = new PlainTableBuilder();

        tableBuilder.setHeaders("MOVIE ID", "TITLE", "RELEASE YEAR", "GENRE");
        tableBuilder.addRow(String.valueOf(movie.getId()), movie.getTitle(), String.valueOf(movie.getReleaseYear()),
                movie.getGenre() == null ? "NO GENRE" : movie.getGenre());

        return tableBuilder.getString();
    }
}
