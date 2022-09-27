package pt.isel.ls.controller.commands.commandresults;

import pt.isel.ls.model.models.Movie;
import pt.isel.ls.utils.Pair;

import java.util.ArrayList;

public class GetTopRatingsResult extends CommandResult {

    private ArrayList<Pair<Movie, String>> movies = new ArrayList<>();

    public ArrayList<Pair<Movie, String>> getMovies() {
        return movies;
    }

    public void add(Movie movie, String rating) {
        movies.add(new Pair<>(movie, rating));
    }

}
