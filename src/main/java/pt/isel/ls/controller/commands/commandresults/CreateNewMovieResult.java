package pt.isel.ls.controller.commands.commandresults;

import pt.isel.ls.model.models.Movie;

public class CreateNewMovieResult extends CommandResult {

    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
