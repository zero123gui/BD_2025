package entity.Fornecedor;

public class TelefoneFornecedor {
    private int idTelefoneFornecedor;
    private String nroTelefone;
    private int idFornecedor;
    private int idDDD;

    public int getIdTelefoneFornecedor() {
        return this.idTelefoneFornecedor;
    }

    public void setIdTelefoneFornecedor(int idTelefoneFornecedor) {
        this.idTelefoneFornecedor = idTelefoneFornecedor;
    }

    public String getNroTelefone() {
        return this.nroTelefone;
    }

    public void setNroTelefone(String nroTelefone) {
        this.nroTelefone = nroTelefone;
    }

    public int getIdFornecedor() {
        return this.idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public int getIdDDD() {
        return this.idDDD;
    }

    public void setIdDDD(int idDDD) {
        this.idDDD = idDDD;
    }

}
