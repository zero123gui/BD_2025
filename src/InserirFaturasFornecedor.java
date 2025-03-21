import conexao.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    public void atualizarDadosFornecedor(int idF){
        try {
            System.out.println("Digite o novo nome");
            String nomeFornecedor = scanner.nextLine();

            String sql = "UPDATE fornecedor SET nomeFornecedor = ? WHERE idFornecedor = ?";
            PreparedStatement stmt = conn.prepareStatement("UPDATE fornecedor SET nomeFornecedor = ? WHERE idFornecedor = ?");

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void cadastrarFaturaFornecedor(){
        
        System.out.println("Informe o id do fornecedor");
        int idFornecedor = scanner.nextInt();
        scanner.nextLine();//limpa buffer

        String sqlCheckFornecedor = "SELECT nomeFornecedor from fornecedor WHERE idFornecedor = ?";
        
        try(PreparedStatement checkStmt = conn.prepareStatement(sqlCheckFornecedor)){
            conn.setAutoCommit(false);
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
            String dtVencimentoStr = scanner.nextLine();

            Date dtVencimento = converterParaSQLDate(dtVencimentoStr);

            System.out.print("Informe o valor da fatura: ");
            double valorFatura = scanner.nextDouble();
            scanner.nextLine(); // Limpa o buffer

            // Insere a fatura no banco de dados
            String sqlInsert = """
                INSERT INTO fatura (dtLancamento, dtVencimento, valorFatura, idFornecedor, idMotivoFatura)
                VALUES (CURRENT_DATE, ?, ?, ?, ?)
            """;

            try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsert)) {
                insertStmt.setDate(1, dtVencimento);
                insertStmt.setDouble(2, valorFatura);
                insertStmt.setInt(3, idFornecedor);
                insertStmt.setInt(4, idMotivoFatura);
                insertStmt.executeUpdate();
                System.out.println("Fatura cadastrada com sucesso!");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar fatura: " + e.getMessage());
        }

        System.out.println("Deseja atualizar o nome do Fornecedor");
        System.err.println("Sim - 1\nNão - 2");
        int op = scanner.nextInt();
        if (op==1) {
            atualizarDadosFornecedor(idFornecedor);
        }
        try {
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void menuInsere() {
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

    private Date converterParaSQLDate(String dataStr) {
        try {
            LocalDate localDate = LocalDate.parse(dataStr); // Converte a string para LocalDate
            return Date.valueOf(localDate); // Converte LocalDate para java.sql.Date
        } catch (DateTimeParseException e) {
            return null; // Retorna null se a data for inválida
        }
    }

    public static void main(String[] args) {
        try (Connection conn = Conexao.getConnection()) {
            InserirFaturasFornecedor inserirFaturas = new InserirFaturasFornecedor(conn);
            inserirFaturas.menuInsere();
        } catch (SQLException e) {
            System.err.println("Erro na conexão com o banco de dados: " + e.getMessage());
        }
    }
}
