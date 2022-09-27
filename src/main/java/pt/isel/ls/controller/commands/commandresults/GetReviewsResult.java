package pt.isel.ls.controller.commands.commandresults;

import pt.isel.ls.model.models.Review;

import java.util.ArrayList;


public class GetReviewsResult extends CommandResult {

    private ArrayList<Review> reviews = new ArrayList<>();
    private int mid;

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void add(Review review) {
        reviews.add(review);
    }

    public void setMid(int movieId) {
        this.mid = movieId;
    }

    public int getMid() {
        return mid;
    }
}
