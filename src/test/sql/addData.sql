--insert Movies
INSERT INTO MOVIE VALUES(DEFAULT, 'indiana jones', 1980, 'action');
INSERT INTO MOVIE VALUES(DEFAULT, 'cyberpunk', 2020, 'action');
INSERT INTO MOVIE VALUES(DEFAULT, 'Boss baby', 2015, 'kids');
INSERT INTO MOVIE VALUES(DEFAULT, 'Shrek', 2010, 'adventure');
INSERT INTO MOVIE VALUES(DEFAULT, 'Uma Aventura', 2005, 'mistery');
INSERT INTO MOVIE VALUES(DEFAULT, 'ching chang klan', 1999, 'action');
--insert Users
INSERT INTO USERS VALUES(DEFAULT, 'Miguel Dourado', 'miguel@email.com');
INSERT INTO USERS VALUES(DEFAULT, 'Daniel Dron', 'daniel.@gmail.com');
INSERT INTO USERS VALUES(DEFAULT, 'Diogo Cunha', 'diogocunhamaster.yaumaluco.10020@email.com');
INSERT INTO USERS VALUES(DEFAULT, 'Adorfo Uder', 'uder.adorfo@gmail.com');
INSERT INTO USERS VALUES(DEFAULT, 'Ana Cacho', 'anacachopaula.@gmail.com');
INSERT INTO USERS VALUES(DEFAULT, 'Paulo Seixo', 'seixo.paulo@hotmail.com');
--insert Rating
INSERT INTO rating VALUES(1, 0, 0, 7, 0, 1);
INSERT INTO rating VALUES(2, 0, 6, 0, 0, 1);
INSERT INTO rating VALUES(3, 20, 12, 5, 3, 0);
INSERT INTO rating VALUES(4, 3, 0, 0, 0, 1);
INSERT INTO rating VALUES(5, 1, 3, 2, 8, 1);
INSERT INTO rating VALUES(6, 0, 0, 0, 0, 40);
--insert Review
INSERT INTO review VALUES(DEFAULT, 1, 2, 'it was alright', 'aaaaaaaaaaaaaaaaaaaaaa', 3);
INSERT INTO review VALUES(DEFAULT, 1, 2, 'meh couldve been better', 'very big review guys ahahah movie sux', 2);
INSERT INTO review VALUES(DEFAULT, 2, 4, 'it was fun', 'it was pretty fun', 4);
INSERT INTO review VALUES(DEFAULT, 3, 5, 'pretty enjoyable', 'it was pretty enjoyable', 4);
INSERT INTO review VALUES(DEFAULT, 2, 3, 'mediocre', 'it was pretty good', 3);
INSERT INTO review VALUES(DEFAULT, 10, 6, 'pretty good', 'it was pretty good', 5);
