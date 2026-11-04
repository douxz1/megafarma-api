
package megafarma.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {

    private static final String URL =
            System.getenv("DB_URL");

    private static final String USUARIO =
            System.getenv("DB_USER");

    private static final String SENHA =
            System.getenv("DB_PASSWORD");

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                URL, USUARIO, SENHA
        );
    }
}
