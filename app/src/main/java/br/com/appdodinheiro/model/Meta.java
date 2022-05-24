package br.com.appdodinheiro.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
public class Meta implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ID = 0;
    private String nome;
    private BigDecimal ValorTotal;
    private Calendar DataDeFechamento;
    private int Dia;
    private int Mes;
    private int Ano;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public BigDecimal getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.ValorTotal = valorTotal;
    }

    public Calendar getDataDeFechamento() {
        return DataDeFechamento;
    }

    public void setDataDeFechamento(Calendar dataDeFechamento) {
        this.DataDeFechamento = dataDeFechamento;
    }

    public Meta(String nome, BigDecimal valorTotal, Calendar dataDeFechamento) {
        this.nome = nome;
        this.ValorTotal = valorTotal;
        this.DataDeFechamento = dataDeFechamento;
        this.Dia = dataDeFechamento.get(Calendar.DAY_OF_MONTH);
        this.Mes = dataDeFechamento.get(Calendar.MONTH);
        this.Ano = dataDeFechamento.get(Calendar.YEAR);
    }

    public Meta() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        Dia = dia;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public int getAno() {
        return Ano;
    }

    public void setAno(int ano) {
        Ano = ano;
    }

    public boolean temIdValido() {
        return ID > 0;
    }
}
