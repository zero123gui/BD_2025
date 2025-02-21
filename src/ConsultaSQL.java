import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaSQL {
    private Connection conn;

    public ConsultaSQL(Connection conn) {
        this.conn = conn;
    }
    
    public ResultSet getFaturasFornecedor(int idFornecedor) throws SQLException {
        String sql = """
            SELECT 
                f.nroFatura,
                mf.motivo AS motivoFatura,
                f.dtVencimento,
                f.valorFatura,
                fr.saldo
            FROM 
                fatura f
            JOIN 
                motivoFatura mf ON f.idMotivoFatura = mf.idMotivoFatura
            JOIN 
                fornecedor fr ON f.idFornecedor = fr.idFornecedor
            WHERE 
                f.idFornecedor = ?
            """;
    
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idFornecedor);
        return statement.executeQuery();
    }

    public void getDadosFornecedor(int idFornecedor) throws SQLException{
        String sql = """
            SELECT 
                f.idFornecedor, 
                f.nomeFornecedor,
                f.cnpj, 
                f.idEndereco, 
                f.complementoEndereco,
                e.cep, 
                b.nomeBairro, 
                l.nomeLogradouro, 
                c.nomeCidade,
                tf.nroTelefone,
                ef.email
            FROM 
                fornecedor f
            JOIN 
                endereco e ON e.idEndereco = f.idEndereco
            JOIN 
                cidade c ON c.idCidade = e.idCidade
            JOIN 
                bairro b ON b.idBairro = e.idBairro
            JOIN 
                logradouro l ON l.idLogradouro = e.idLogradouro
            JOIN 
                telefonefornecedor tf ON tf.idFornecedor = f.idFornecedor
            JOIN 
                emailfornecedor ef ON ef.idFornecedor = f.idFornecedor
            """;
            try (PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
    
                System.out.println("===== Dados do Fornecedor com id: "+ idFornecedor +" =====");

                // Recupera os dados do ResultSet
                String nomeFornecedor = resultSet.getString("nomeFornecedor");
                String cnpj = resultSet.getString("cnpj");
                String complementoEndereco = resultSet.getString("complementoEndereco");
                String cep = resultSet.getString("cep");
                String bairro = resultSet.getString("nomeBairro");
                String logradouro = resultSet.getString("nomeLogradouro");
                String cidade = resultSet.getString("nomeCidade");
                String nroTelefone = resultSet.getString("nroTelefone");
                String email = resultSet.getString("email");
    
                // Exibe os dados recuperados
                System.out.println("Nome: " + nomeFornecedor);
                System.out.println("CNPJ: " + cnpj);
                System.out.println("Endereço: " + logradouro + ", " + bairro + ", " + cidade + ", CEP: " + cep + " (" + complementoEndereco + ")");
                System.out.println("Fones:"+ nroTelefone);
                System.out.println("Emails:"+ email);
                System.out.println("- - - - - - - - - - - - - - - - - - - ");
                
            } catch (SQLException e) {
                System.err.println("Erro ao listar Fornecedor: " + e.getMessage());
            }
    }

    public String getNomeFornecedor(int idFornecedor) throws SQLException {
        String sqlNome = "SELECT nomeFornecedor FROM fornecedor WHERE idFornecedor = ?";
        PreparedStatement statement = conn.prepareStatement(sqlNome);
        statement.setInt(1, idFornecedor);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next() ? resultSet.getString("nomeFornecedor") : null;
    }
}
