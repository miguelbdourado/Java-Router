package pt.isel.ls.model.models;

public class Movie {
    private final int id;
    private final String title;
    private final int releaseYear;
    private final String genre;

    public Movie(int id, String title, int releaseYear, String genre) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getGenre() {
        return genre;
    }
}
