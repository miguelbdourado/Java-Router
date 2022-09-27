CREATE TABLE MOVIE(
    id serial PRIMARY KEY,
    title varchar(50) NOT NULL,
    releaseYear int NOT NULL,
    genre varchar(20)
);

CREATE TABLE USERS(
    id serial PRIMARY KEY,
    name varchar(50) NOT NULL,
    email varchar(50) NOT NULL CHECK (email Like '%@%')
);

CREATE TABLE RATING(
    movieID int NOT NULL,
    rating1 int,
    rating2 int,
    rating3 int,
    rating4 int,
    rating5 int,
    PRIMARY KEY(movieID),
    FOREIGN KEY (movieID) REFERENCES MOVIE(id)
);

CREATE TABLE REVIEW(
    id serial PRIMARY KEY,
    criticID int NOT NULL,
    movieID int NOT NULL,
    summary varchar(1000) NOT NULL,
    completeReview varchar(5000) NOT NULL,
    rating int NOT NULL CHECK(rating >= 1 AND rating <= 5),
    FOREIGN KEY (movieID) REFERENCES MOVIE(id)
);