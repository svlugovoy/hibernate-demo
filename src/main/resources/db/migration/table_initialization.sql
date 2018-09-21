/*

Movies_db database stores information about movies, directors and actors.

Each movie has only one director (many to one).
Actors/Movies - many to many.

Each actor has stored first and last names, gender and birthday which are mandatory. Instagram is a optional unique value.
Each director has stored first and last names, gender and birthday which are mandatory. Instagram is a optional unique value.
Each movie has stored name, year of creation, genre and reference to director which are mandatory.

  TECH NOTES AND NAMING CONVENTION
- All tables, columns and constraints are named using "snake case" naming convention
- All table names must be plural (e.g. "companies", not "company")
- All tables (except link tables) should have a single-value identifier of type BIGINT, which is a primary key
- All primary key, foreign key, and unique constraint should be named according to the naming convention.
- All "1 - optional 1" relations should be handled using the same primary key value for both tables. E.g. child table
should have a column that stores primary key from a parent table, which is a foreign key and primary key at the same time

- All primary keys should be named according to the following rule "table_name_PK"
- All foreign keys should be named according to the following rule "table_name_reference_table_name_FK"
- All alternative keys (unique) should be named according to the following rule "table_name_column_name_AK"

*/

DROP TABLE IF EXISTS actors CASCADE;
DROP TABLE IF EXISTS directors CASCADE;
DROP TABLE IF EXISTS movies CASCADE;
DROP TABLE IF EXISTS actors_movies;

CREATE TABLE actors (
  id SERIAL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  birthday DATE NOT NULL,
  gender CHAR(1) NOT NULL,
  instagram VARCHAR(255),
  CONSTRAINT actors_PK PRIMARY KEY (id),
  CONSTRAINT actors_instagram_AK UNIQUE (instagram)
);

CREATE TABLE directors (
  id SERIAL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  birthday DATE NOT NULL,
  gender CHAR(1) NOT NULL,
  instagram VARCHAR(255),
  CONSTRAINT directors_PK PRIMARY KEY (id),
  CONSTRAINT directors_instagram_AK UNIQUE (instagram)
);

CREATE TABLE movies (
  id SERIAL,
  name VARCHAR(255) NOT NULL,
  year_of_creation SMALLINT NOT NULL,
  genre VARCHAR(255) NOT NULL,
  directors_id BIGINT NOT NULL,
  CONSTRAINT movies_PK PRIMARY KEY (id),
  CONSTRAINT movies_directors_FK FOREIGN KEY (directors_id) REFERENCES directors
);

CREATE TABLE actors_movies (
  actors_id BIGINT,
  movies_id BIGINT,
  CONSTRAINT actors_movies_PK PRIMARY KEY (actors_id, movies_id),
  CONSTRAINT actors_movies_actors_FK FOREIGN KEY (actors_id) REFERENCES actors ON DELETE CASCADE,
  CONSTRAINT actors_movies_movies_FK FOREIGN KEY (movies_id) REFERENCES movies
);


