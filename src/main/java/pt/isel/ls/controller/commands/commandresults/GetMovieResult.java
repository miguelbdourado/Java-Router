package pt.isel.ls.controller.commands.commandresults;

import pt.isel.ls.model.models.Movie;
import pt.isel.ls.model.models.Review;

import java.util.ArrayList;

public class GetMovieResult extends CommandResult {

    private Movie movie;
    ArrayList<Review> reviews = new ArrayList<>();

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
}
