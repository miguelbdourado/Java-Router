package pt.isel.ls.controller.commands.handlers.utils;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTransaction {
    void run(Connection connection) throws SQLException;
}
