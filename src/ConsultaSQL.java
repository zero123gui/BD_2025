import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaSQL {
    private Connection conn;

    public ConsultaSQL(Connection conn) {
        this.conn = conn;
    }
    
    public ResultSet getFaturasFornecedor(int idFornecedor) throws SQLException{
        String sql = """
            SELECT 
                f.nroFatura,
                f.dtVencimento,
                f.valorFatura,
                f.idMotivoFatura, 
                te.tipo,
            FROM 
                fatura f
            JOIN 
                motivoFatura mf ON f.idMotivoFatura = mf.idMotivoFatura
            WHERE 
                f.idFornecedor = ?
            """;
            
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idFornecedor);
        return statement.executeQuery();
    }

    public String getNomeFornecedor(int idFornecedor) throws SQLException {
        String sqlNome = "SELECT nomeFornecedor FROM fornecedor WHERE idFornecedor = ?";
        PreparedStatement statement = conn.prepareStatement(sqlNome);
        statement.setInt(1, idFornecedor);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next() ? resultSet.getString("nomeFornecedor") : null;
    }
}
