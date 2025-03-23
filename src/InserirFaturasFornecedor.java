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

    @SuppressWarnings("UnnecessaryReturnStatement")
    public void atualizarDadosFornecedor(int idF){
        System.out.print("Digite o Nro do Endereço: ");
        String nroEndereco = scanner.nextLine();
        scanner.nextLine(); // Limpa buffer caso tenha usado nextInt() antes
        
        System.out.print("\nDigite o complemento: ");
        String complementoEndereco = scanner.nextLine();

            String sqlUpdateFornecedor = "UPDATE fornecedor SET nroendereco = ?, complementoendereco = ? WHERE idFornecedor = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlUpdateFornecedor)) {
                stmt.setString(1, nroEndereco);
                stmt.setString(2, complementoEndereco);
                stmt.setInt(3, idF); // Certifique-se de que idFornecedor tem um valor válido
                stmt.executeUpdate();
                System.out.println("Endereço atualizado com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("1 - Transação OK\n2 - Transação Incompleta\n3 - Rollback");
            int op = scanner.nextInt();
            if(op == 1){
                try{
                    conn.commit();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            } else if (op == 2){
                return;
            } else if(op == 3){
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

            String sqlUpdate = """
                UPDATE fornecedor SET saldo = saldo + ? WHERE idFornecedor = ?           
            """;

            try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsert);
                    PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {

                insertStmt.setDate(1, dtVencimento);
                insertStmt.setDouble(2, valorFatura);
                insertStmt.setInt(3, idFornecedor);
                insertStmt.setInt(4, idMotivoFatura);
                insertStmt.executeUpdate();

                updateStmt.setDouble(1, valorFatura);
                updateStmt.setInt(2, idFornecedor);
                updateStmt.executeUpdate();
                System.out.println("Fatura cadastrada com sucesso!");
            }

            System.out.println("Deseja atualizar os dados do Fornecedor");
            System.err.println("Sim - 1\nNão - 2");
            int op = scanner.nextInt();
            if (op==1) {
                atualizarDadosFornecedor(idFornecedor);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar fatura: " + e.getMessage());
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
