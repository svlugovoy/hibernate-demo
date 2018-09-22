package com.svlugovoy.hibernatedemo;

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

public class Runner {

    private final static String TABLE_INITIALIZATION_SQL_FILE = "db/migration/table_initialization.sql";
    private final static String TABLE_POPULATION_SQL_FILE = "db/migration/table_population.sql";

    private static DataSource dataSource;
    private static EntityManagerUtil emUtil;
    private static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        initDatasource();
        initPersistence();
        initTablesInDB();
        populateTablesInDB();

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

}
