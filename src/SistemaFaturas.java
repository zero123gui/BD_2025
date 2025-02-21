import conexao.Conexao;
import java.sql.Connection;
import java.util.Scanner;

public class SistemaFaturas {

    private Scanner scanner;
    Connection conn = Conexao.getConnection();

    public SistemaFaturas() {
        this.scanner = new Scanner(System.in);
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("===== Sistema de Contas a Pagar =====");
            System.out.println("1. Cadastrar Fatura a Pagar de um Fornecedor");
            System.out.println("2. Consultar Faturas a Pagar de um Fornecedor");
            System.out.println("3. Sair"); 
            System.out.print("Escolha uma opcao: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1://cadastrar fatura
                    System.out.println("Ainda em desenvolvimento!");
                    break;
                case 2://consultar fatura fornecedor
                    ConsultarFaturasFornecedor consultar = new ConsultarFaturasFornecedor(conn);
                    consultar.menuConsulta();
                    break;
                case 3:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (opcao != 3);
    }

    public static void main(String[] args) {
        SistemaFaturas sistema = new SistemaFaturas();
        sistema.menu();

    }
}
