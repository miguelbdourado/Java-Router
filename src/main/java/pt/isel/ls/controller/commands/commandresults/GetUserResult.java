package pt.isel.ls.controller.commands.commandresults;

import pt.isel.ls.model.models.Review;
import pt.isel.ls.model.models.User;

import java.util.ArrayList;

public class GetUserResult extends CommandResult {

    private User user;
    private ArrayList<Review> reviews = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

}
