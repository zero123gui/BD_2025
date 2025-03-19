package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Conexao {
    
    private static final String url = "jdbc:postgresql://localhost:5432/generico";
    private static String username;
    private static String password;

    private static Connection conn;

    public static Connection getConnection(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("\tLista de Usuarios");
        System.out.println("1. Usuario-Admin: postgres\n2. Usuario-Consultar: userConsultar\t3. Usuario-Inserir: userInserir");

        System.out.print("Digite o nome do usuario: ");
        username = scanner.nextLine();
        System.err.println("Digite a senha");
        password = scanner.nextLine();
        
        scanner.close();

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

    public static void fecharConexao() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Conexão fechada com sucesso!");
        }
    }
}
