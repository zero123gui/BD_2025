package entity.Fatura;

import java.sql.Date;

public class Fatura {
    private int nroFatura;
    private Date dtLancamento;
    private Date dtVencimento;
    private double valorFatura;
    private int idFornecedor;
    private int idMotivoFatura;

    public int getNroFatura() {
        return this.nroFatura;
    }

    public void setNroFatura(int nroFatura) {
        this.nroFatura = nroFatura;
    }

    public Date getDtLancamento() {
        return this.dtLancamento;
    }

    public void setDtLancamento(Date dtLancamento) {
        this.dtLancamento = dtLancamento;
    }

    public Date getDtVencimento() {
        return this.dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public double getValorFatura() {
        return this.valorFatura;
    }

    public void setValorFatura(double valorFatura) {
        this.valorFatura = valorFatura;
    }

    public int getIdFornecedor() {
        return this.idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public int getIdMotivoFatura() {
        return this.idMotivoFatura;
    }

    public void setIdMotivoFatura(int idMotivoFatura) {
        this.idMotivoFatura = idMotivoFatura;
    }

}
