package com.svlugovoy.hibernatedemo;

import com.svlugovoy.hibernatedemo.dao.ActorDao;
import com.svlugovoy.hibernatedemo.dao.DirectorDao;
import com.svlugovoy.hibernatedemo.dao.MovieDao;
import com.svlugovoy.hibernatedemo.dao.impl.ActorDaoImpl;
import com.svlugovoy.hibernatedemo.dao.impl.DirectorDaoImpl;
import com.svlugovoy.hibernatedemo.dao.impl.MovieDaoImpl;
import com.svlugovoy.hibernatedemo.domain.Actor;
import com.svlugovoy.hibernatedemo.domain.Director;
import com.svlugovoy.hibernatedemo.domain.Movie;
import com.svlugovoy.hibernatedemo.domain.dto.MovieProjection;
import com.svlugovoy.hibernatedemo.domain.enums.Gender;
import com.svlugovoy.hibernatedemo.exception.DaoOperationException;
import com.svlugovoy.hibernatedemo.util.EntityManagerUtil;
import com.svlugovoy.hibernatedemo.util.FileReader;
import com.svlugovoy.hibernatedemo.util.JdbcUtil;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Runner {

    private final static String TABLE_INITIALIZATION_SQL_FILE = "db/migration/table_initialization.sql";
    private final static String TABLE_POPULATION_SQL_FILE = "db/migration/table_population.sql";

    private static DataSource dataSource;
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManagerUtil emUtil;

    private static ActorDao actorDao;
    private static DirectorDao directorDao;
    private static MovieDao movieDao;


    public static void main(String[] args) {
        initDatasource();
        initPersistence();
        initTablesInDB();
        populateTablesInDB();
        initDaos();

        List<Actor> actors = actorDao.findAll(false);
        actors.forEach(System.out::println);
        System.out.println("#####***######");

        List<Actor> actorsWithMovies = actorDao.findAll(true);
        actorsWithMovies.forEach(System.out::println);
        System.out.println("#####***######");

        Actor newActor = Actor.builder()
                .firstName("Salma").lastName("Hayek").birthday(LocalDate.of(1966, Month.SEPTEMBER, 2))
                .gender(Gender.FEMALE).build();
        System.out.println(newActor);
        Long savedId = actorDao.save(newActor);
        System.out.println(savedId);
        Actor newActorById = actorDao.findById(savedId);
        System.out.println(newActorById);
        System.out.println("#####***######");

        Actor actorById = actorDao.findById(100005L);
        System.out.println(actorById);

        actorById.setFirstName("UPDATED");
        actorDao.update(actorById);

        Actor updatedActorById = actorDao.findById(100005L);
        System.out.println(updatedActorById);
        System.out.println("#####***######");

        actorDao.remove(100005L);
        try {
            System.out.println(actorDao.findById(100005L));
            throw new RuntimeException("Should not be here");
        } catch (DaoOperationException e) {
            System.out.println("Actor deleted successful");
        }
        System.out.println("#####***######");

        Director director = directorDao.findById(100001L);
        List<Actor> actorsInFilmsFromDirector = actorDao.findActorsInFilmsFromDirector(director);
        actorsInFilmsFromDirector.forEach(System.out::println);
        System.out.println("#####***######");

        Actor actor = actorDao.findById(100001L);
        List<Movie> moviesWithActor = movieDao.findAllMoviesWithActor(actor);
        moviesWithActor.forEach(System.out::println);
        System.out.println("#####***######");

        List<MovieProjection> moviesNamesWithActor = movieDao.findAllMoviesNamesWithActor(actor);
        moviesNamesWithActor.forEach(System.out::println);
        System.out.println("#####***######");

    }

    private static void initDatasource() {
        dataSource = JdbcUtil.createPostgresDataSource(
                "jdbc:postgresql://localhost:5432/testdb", "test", "test");
    }

    private static void initPersistence() {
        entityManagerFactory = Persistence.createEntityManagerFactory("ActorsDirectorsMoviesPostgres");
        emUtil = new EntityManagerUtil(entityManagerFactory);
    }

    private static void initTablesInDB() {
        String createTablesSql = FileReader.readWholeFileFromResources(TABLE_INITIALIZATION_SQL_FILE);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            connection.setReadOnly(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            Statement statement = connection.createStatement();
            statement.execute(createTablesSql);
            connection.commit();
        } catch (SQLException e) {
            throw new DaoOperationException("Shit happened during tables init.", e);
        }
    }

    private static void populateTablesInDB() {
        String createTablesSql = FileReader.readWholeFileFromResources(TABLE_POPULATION_SQL_FILE);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute(createTablesSql);
            connection.commit();
        } catch (SQLException e) {
            throw new DaoOperationException("Shit happened during tables population.", e);
        }
    }

    private static void initDaos() {
        actorDao = new ActorDaoImpl(entityManagerFactory);
        directorDao = new DirectorDaoImpl(entityManagerFactory);
        movieDao = new MovieDaoImpl(entityManagerFactory);
    }

}
