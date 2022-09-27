package pt.isel.ls.controller.commands.commandresults;

import pt.isel.ls.model.models.Review;

public class CreateNewReviewResult extends CommandResult {

    private Review review;


    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
