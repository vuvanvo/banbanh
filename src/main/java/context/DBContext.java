package main.java.context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {
    private static final Logger logger = Logger.getLogger(DBContext.class.getName()); // Logger instance

    private String serverName = "Ciky";
    private String dbName = "Bakery";
    private String portNumber = "1433";
    private String instance = ""; // Leave empty for default instance
    private String userID = "sa";
    private String password = "123";

    public Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName+ ";encrypt=false;";
        if (instance != null && !instance.trim().isEmpty()) {
            url = "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" + instance + ";databaseName=" + dbName+ ";encrypt=false;";
        }

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // Optional for manual loading
        } catch (ClassNotFoundException e) {
            //logger.log(Level.SEVERE, "SQL Server JDBC Driver not found.", e); // Log error if driver is missing
            throw new SQLException("SQL Server JDBC Driver not found.", e);
        }

        try {
            Connection connection = DriverManager.getConnection(url, userID, password);
                logger.log(Level.INFO, "Connected to database successfully."); // Log success
            return connection;
        } catch (SQLException e) {
            //logger.log(Level.SEVERE, "Database connection failed.", e); // Log error if connection fails
            throw e; // Rethrow the SQLException
        }
    }

    public Connection getConnection(String userID, String password) throws SQLException {
        this.userID = userID;
        this.password = password;
        return this.getConnection();
    }
}
