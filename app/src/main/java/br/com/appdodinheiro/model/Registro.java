package br.com.appdodinheiro.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
public class Registro implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ID = 0;
    private String Nome;
    private BigDecimal Valor;
    private Calendar Data;
    private String Tipo;
    private int Dia;
    private int Mes;
    private int Ano;

    public Registro(String nome, BigDecimal valor, Calendar data, String tipo) {
        Nome = nome;
        Valor = valor;
        Data = data;
        Tipo = tipo;
        Dia = data.get(Calendar.DAY_OF_MONTH);
        Mes = data.get(Calendar.MONTH);
        Ano = data.get(Calendar.YEAR);
    }

    public Registro(){

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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public BigDecimal getValor() {
        return Valor;
    }

    public void setValor(BigDecimal valor) {
        Valor = valor;
    }

    public Calendar getData() {
        return Data;
    }

    public void setData(Calendar data) {
        Data = data;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public boolean temIdValido() {
        return ID > 0;
    }
}
