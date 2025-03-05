import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InserirFaturasFornecedor {
    @SuppressWarnings("FieldMayBeFinal")
    private Connection conn;
    @SuppressWarnings("FieldMayBeFinal")
    private Scanner scanner;

    public InserirFaturasFornecedor(Connection conn) {
        this.conn = conn; // Usa a conexão recebida pelo construtor
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarFaturaFornecedor(){
        System.out.println("Informe o id do fornecedor");
        int idFornecedor = scanner.nextInt();
        scanner.nextLine();//limpa buffer

        String sqlCheckFornecedor = "SELECT nomeFornecedor from fornecedor WHERE idFornecedor = ?";

        try(PreparedStatement checkStmt = conn.prepareStatement(sqlCheckFornecedor)){
            checkStmt.setInt(1, idFornecedor);
            ResultSet rs = checkStmt.executeQuery();

            if(!rs.next()){
                System.out.println("Fornecedor não encontrado.");
                return;
            }

            String nomeFornecedor = rs.getString("nomeFornecedor");
            System.out.println("Fornecedor: " + nomeFornecedor);

            System.out.print("Informe o ID do Motivo da Fatura: ");
            int idMotivoFatura = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Informe a data de vencimento (YYYY-MM-DD): ");
            String dtVencimento = scanner.nextLine();

            System.out.print("Informe o valor da fatura: ");
            double valorFatura = scanner.nextDouble();
            scanner.nextLine(); // Limpa o buffer

            // Insere a fatura no banco de dados
            String sqlInsert = """
                INSERT INTO fatura (dtLancamento, dtVencimento, valorFatura, idFornecedor, idMotivoFatura)
                VALUES (CURRENT_DATE, ?, ?, ?, ?)
            """;

            try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsert)) {
                insertStmt.setString(1, dtVencimento);
                insertStmt.setDouble(2, valorFatura);
                insertStmt.setInt(3, idFornecedor);
                insertStmt.setInt(4, idMotivoFatura);
                insertStmt.executeUpdate();
                System.out.println("Fatura cadastrada com sucesso!");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar fatura: " + e.getMessage());
        }
    }

    public void menuConsulta() {
        int opcao;
        do {
            System.out.println("===== Menu de Cadastro de Fatura =====");
            System.out.println("1. Cadastrar Fatura.");
            System.out.println("2. Voltar ao menu principal.");
            System.out.print("Escolha uma opcao: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> cadastrarFaturaFornecedor();
                case 2 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (opcao != 2);
    }

    public static void main(String[] args) {
        try (Connection conn = Conexao.getConnection()) {
            InserirFaturasFornecedor inserirFaturas = new InserirFaturasFornecedor(conn);
            inserirFaturas.menuConsulta();
        } catch (SQLException e) {
            System.err.println("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
}
