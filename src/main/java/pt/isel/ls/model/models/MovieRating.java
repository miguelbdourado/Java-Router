package pt.isel.ls.model.models;

public class MovieRating {

    private int rating1;
    private int rating2;
    private int rating3;
    private int rating4;
    private int rating5;
    private String average;

    public MovieRating(int rating1, int rating2, int rating3, int rating4, int rating5, String average) {
        this.rating1 = rating1;
        this.rating2 = rating2;
        this.rating3 = rating3;
        this.rating4 = rating4;
        this.rating5 = rating5;
        this.average = average;
    }

    public int getRating1() {
        return rating1;
    }

    public int getRating2() {
        return rating2;
    }

    public int getRating3() {
        return rating3;
    }

    public int getRating4() {
        return rating4;
    }

    public int getRating5() {
        return rating5;
    }

    public String getAverage() {
        return average;
    }

}
