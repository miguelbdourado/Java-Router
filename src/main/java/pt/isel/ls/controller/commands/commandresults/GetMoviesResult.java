package pt.isel.ls.controller.commands.commandresults;

import pt.isel.ls.model.models.Movie;

import java.util.ArrayList;

public class GetMoviesResult extends CommandResult {

    private ArrayList<Movie> movies = new ArrayList<>();
    private int skip;
    private int top;
    private int movieCount;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public int getSkip() {
        return this.skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public void setTotalCount(int movieCount) {
        this.movieCount = movieCount;
    }

    public int getMovieCount() {
        return movieCount;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
