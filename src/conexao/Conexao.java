package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    private static final String url = "jdbc:postgresql://localhost:5432/generico";
    private static final String username = "postgres";
    private static final String password = "061104";

    private static Connection conn;

    public static Connection getConnection(){

        try {
            if(conn == null || conn.isClosed()){
                conn = DriverManager.getConnection(url, username, password);
            }
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
