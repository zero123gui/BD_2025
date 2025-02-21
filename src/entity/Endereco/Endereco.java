package entity.Endereco;

public class Endereco {
    private int idEndereco;
    private String cep;
    private int idBairro;
    private int idCidade;
    private int idLogradouro;

    public int getIdEndereco() {
        return this.idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getIdBairro() {
        return this.idBairro;
    }

    public void setIdBairro(int idBairro) {
        this.idBairro = idBairro;
    }

    public int getIdCidade() {
        return this.idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    public int getIdLogradouro() {
        return this.idLogradouro;
    }

    public void setIdLogradouro(int idLogradouro) {
        this.idLogradouro = idLogradouro;
    }

}
