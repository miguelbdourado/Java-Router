package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetMoviesResult;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.PlainTableBuilder;


public class GetMoviesView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        GetMoviesResult moviesResult = (GetMoviesResult) result;
        PlainTableBuilder tableBuilder = new PlainTableBuilder();

        tableBuilder.setHeaders("MOVIE ID", "TITLE", "RELEASE YEAR", "GENRE");
        moviesResult.getMovies().forEach(movie ->
                tableBuilder.addRow(String.valueOf(movie.getId()), movie.getTitle(),
                        String.valueOf(movie.getReleaseYear()),
                        movie.getGenre() == null ? "NO GENRE" : movie.getGenre())
        );

        return tableBuilder.getString();
    }
}
