package pt.isel.ls.controller.commands.handlers.utils;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {


    public Connection createConnection() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(System.getenv("JDBC_DATABASE_URL"));
        return ds.getConnection();
    }

    public void executeTransaction(SqlTransaction transaction) {
        try (Connection connection = createConnection()) {
            try {
                connection.setAutoCommit(false);

                transaction.run(connection);

                connection.commit();
            } catch (SQLException throwable) {
                connection.rollback();
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
