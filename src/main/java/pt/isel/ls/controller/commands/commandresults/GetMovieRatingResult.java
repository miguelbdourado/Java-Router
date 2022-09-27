package pt.isel.ls.controller.commands.commandresults;

import pt.isel.ls.model.models.MovieRating;

public class GetMovieRatingResult extends CommandResult {

    private MovieRating rating;
    private int mid;

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getMid() {
        return this.mid;
    }

    public MovieRating getRating() {
        return rating;
    }

    public void setRating(MovieRating rating) {
        this.rating = rating;
    }

}
