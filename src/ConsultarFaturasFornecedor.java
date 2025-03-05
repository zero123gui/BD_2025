import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsultarFaturasFornecedor {

    @SuppressWarnings("FieldMayBeFinal")
    private Scanner scanner;
    @SuppressWarnings("FieldMayBeFinal")
    private ConsultaSQL consultaSQL;
    @SuppressWarnings("FieldMayBeFinal")
    private Connection conn; // Conexão compartilhada

    public ConsultarFaturasFornecedor(Connection conn) {
        this.conn = conn; // Usa a conexão recebida pelo construtor
        this.scanner = new Scanner(System.in);
        this.consultaSQL = new ConsultaSQL(conn);
    }

    public void listarFornecedores() {
        // Consulta SQL com joins para obter todos os dados necessários
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
    
            System.out.println("===== Lista de Fornecedores =====");
    
            while (resultSet.next()) {
                // Recupera os dados do ResultSet
                int idFornecedor = resultSet.getInt("idFornecedor");
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
                System.out.println("Número do Fornecedor: " + idFornecedor);
                System.out.println("Nome: " + nomeFornecedor);
                System.out.println("CNPJ: " + cnpj);
                System.out.println("Endereço: " + logradouro + ", " + bairro + ", " + cidade + ", CEP: " + cep + " (" + complementoEndereco + ")");
                System.out.println("Fones:"+ nroTelefone);
                System.out.println("Emails:"+ email);
                System.out.println("- - - - - - - - - - - - - - - - - - - ");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar Fornecedores: " + e.getMessage());
        }
    }
    
    public void listarFaturas(){
        System.out.print("Entre com o id do Fornecedor: ");
        int idFornecedor = scanner.nextInt();//recebe o id do Fornecedor que se deseja verificar os dados
        try {
            String nomeFornecedor = consultaSQL.getNomeFornecedor(idFornecedor);
            if (nomeFornecedor == null) {
                System.out.println("Fornecedor não encontrado.");
                return;
            }
            consultaSQL.getDadosFornecedor(idFornecedor);//imprime os dados do fornecedor
    
            ResultSet resultSet = consultaSQL.getFaturasFornecedor(idFornecedor);//retorna as faturas associadas ao fornecedor
            System.out.println("Faturas do Fornecedor " + nomeFornecedor + ":");
            System.out.println(" _________________________________________________________________________________________");
            System.out.println("| Nro Fatura |  Motivo Fatura        | Data Vencimento | Valor Fatura  | Saldo Fornecedor |");
            System.out.println("|------------|-----------------------|-----------------|---------------|------------------|");


            boolean temFatura = false;
            while (resultSet.next()) {
                temFatura = true;
                int nroFatura = resultSet.getInt("nroFatura");
                String motivoFatura = resultSet.getString("motivoFatura");
                String dtVencimento = resultSet.getString("dtVencimento");
                double valorFatura = resultSet.getDouble("valorFatura");
                double saldo = resultSet.getDouble("saldo");

                System.out.println(String.format("| %-10d | %-21s | %-15s | %-13.2f | %-16.2f |",
                    nroFatura, motivoFatura, dtVencimento, valorFatura, saldo));
            }

            if (!temFatura) {
                System.out.println("| Não há faturas registradas para este Fornecedor.                                 |");
            }
    
            System.out.println("|__________________________________________________________________________________|");
    
        } catch (SQLException e) {
            System.err.println("Erro ao listar histórico de faturas do Fornecedor: " + e.getMessage());
        }
    }

    public void menuConsulta() {
        int opcao;
        do {
            System.out.println("===== Menu de consulta de Fornecedores =====");
            System.out.println("1. Listar todos os Fornecedores.");
            System.out.println("2. Lista as faturas de um Fornecedor.");
            System.out.println("3. Voltar ao menu principal.");
            System.out.print("Escolha uma opcao: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> listarFornecedores();
                case 2 -> listarFaturas();
                case 3 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (opcao != 3);
    }

    public static void main(String[] args) {
        try (Connection conn = Conexao.getConnection()) {
            ConsultarFaturasFornecedor consultarFornecedor = new ConsultarFaturasFornecedor(conn);
            consultarFornecedor.menuConsulta();
        } catch (SQLException e) {
            System.err.println("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
}
