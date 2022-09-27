package pt.isel.ls.model.models;

public class Review {
    private final int id;
    private final int criticId;
    private final int movieId;
    private final String summary;
    private final String completeReview;
    private final int rating;

    public Review(int id, int criticId, int movieId, String summary, String completeReview, int rating) {
        this.id = id;
        this.criticId = criticId;
        this.movieId = movieId;
        this.summary = summary;
        this.completeReview = completeReview;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public int getCriticId() {
        return criticId;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getSummary() {
        return summary;
    }

    public String getCompleteReview() {
        return completeReview;
    }

    public int getRating() {
        return rating;
    }
}
