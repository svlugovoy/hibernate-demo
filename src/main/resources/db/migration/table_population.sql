
-- The Shawshank Redemption
INSERT INTO actors (first_name, last_name, birthday, gender, instagram) VALUES ('Tim', 'Robbins', '1958-10-16', 'MALE', 'instagram.com/tim');
INSERT INTO actors (first_name, last_name, birthday, gender, instagram) VALUES ('Morgan', 'Freeman', '1937-06-01', 'MALE', null);
INSERT INTO directors (first_name, last_name, birthday, gender, instagram) VALUES ('Frank', 'Darabont', '1959-01-28', 'MALE', null);
INSERT INTO movies (name, year_of_creation, genre, directors_id) VALUES ('The Shawshank Redemption', 1994, 'Drama', 1);
INSERT INTO actors_movies (actors_id, movies_id) VALUES (1, 1);
INSERT INTO actors_movies (actors_id, movies_id) VALUES (2, 1);

-- Fight Club
INSERT INTO actors (first_name, last_name, birthday, gender, instagram) VALUES ('Brad', 'Pitt', '1963-12-18', 'MALE', 'instagram.com/pittb');
INSERT INTO actors (first_name, last_name, birthday, gender, instagram) VALUES ('Edward', 'Norton', '1969-08-18', 'MALE', 'instagram.com/ednort');
INSERT INTO actors (first_name, last_name, birthday, gender, instagram) VALUES ('Meat', 'Loaf', '1947-09-27', 'MALE', null);
INSERT INTO directors (first_name, last_name, birthday, gender, instagram) VALUES ('David', 'Fincher', '1962-08-28', 'm', 'instagram.com/dfit');
INSERT INTO movies (name, year_of_creation, genre, directors_id) VALUES ('Fight Club', 1999, 'Drama', 2);
INSERT INTO actors_movies (actors_id, movies_id) VALUES (3, 2);
INSERT INTO actors_movies (actors_id, movies_id) VALUES (4, 2);
INSERT INTO actors_movies (actors_id, movies_id) VALUES (5, 2);

-- Kill Bill: Vol. 1
INSERT INTO actors (first_name, last_name, birthday, gender, instagram) VALUES ('Uma', 'Thurman', '1970-04-29', 'FEMALE', 'instagram.com/umma');
INSERT INTO directors (first_name, last_name, birthday, gender, instagram) VALUES ('Quentin', 'Tarantino', '1963-03-27', 'MALE', 'instagram.com/qwerty');
INSERT INTO movies (name, year_of_creation, genre, directors_id) VALUES ('Kill Bill: Vol. 1', 2003, 'Action', 3);
INSERT INTO actors_movies (actors_id, movies_id) VALUES (6, 3);