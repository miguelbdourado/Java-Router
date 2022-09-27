package pt.isel.ls.controller.commands.commandresults;

public class SubmitNewRatingResult extends CommandResult {

    private int rating;
    private int movieid;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }
}
