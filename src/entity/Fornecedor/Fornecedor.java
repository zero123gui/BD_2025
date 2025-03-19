package entity.Fornecedor;

public class Fornecedor {
    private int idFornecedor;
    private String nomeFornecedor;
    private String cnpj;
    private double saldo;
    private int idEndereco;
    private String complementoCasa;
    private int nroEndereco;

	public Fornecedor(int idFornecedor, String nomeFornecedor, String cnpj, double saldo, int idEndereco, String complementoCasa, int nroEndereco) {
		this.idFornecedor = idFornecedor;
		this.nomeFornecedor = nomeFornecedor;
		this.cnpj = cnpj;
		this.saldo = saldo;
		this.idEndereco = idEndereco;
		this.complementoCasa = complementoCasa;
		this.nroEndereco = nroEndereco;
	}

	public int getidFornecedor() {
		return this.idFornecedor;
	}

	public void setidFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getnomeFornecedor() {
		return this.nomeFornecedor;
	}

	public void setnomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public double getSaldo() {
		return this.saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public int getIdEndereco() {
		return this.idEndereco;
	}

	public void setIdEndereco(int idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getComplementoCasa() {
		return this.complementoCasa;
	}

	public void setComplementoCasa(String complementoCasa) {
		this.complementoCasa = complementoCasa;
	}

	public int getNroEndereco() {
		return this.nroEndereco;
	}

	public void setNroEndereco(int nroEndereco) {
		this.nroEndereco = nroEndereco;
	}

}
