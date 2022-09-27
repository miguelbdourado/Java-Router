package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CreateNewMovieResult;
import pt.isel.ls.model.models.Movie;

import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.PlainTableBuilder;

public class CreateNewMovieView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        StringBuilder sb = new StringBuilder();
        CreateNewMovieResult moviesResult = (CreateNewMovieResult) result;

        Movie movie = moviesResult.getMovie();

        if (movie == null) {
            sb.append("Error creating movie");
            return sb.toString();
        }

        PlainTableBuilder tableBuilder = new PlainTableBuilder();
        String movieGenre = movie.getGenre() != null ? movie.getGenre() : "";

        tableBuilder.setHeaders("Movie ID", "Movie Title", "Release Year", "Genre");
        tableBuilder.addRow(String.valueOf(movie.getId()), movie.getTitle(),
                String.valueOf(movie.getReleaseYear()), movieGenre);

        sb.append(tableBuilder.getString());
        return sb.toString();
    }


}

