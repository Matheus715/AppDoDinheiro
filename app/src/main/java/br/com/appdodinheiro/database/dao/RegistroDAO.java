package br.com.appdodinheiro.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.appdodinheiro.model.Registro;

@Dao
public interface RegistroDAO {

    @Insert
    void salva(Registro registro);

    @Query("SELECT * FROM registro ORDER BY data DESC")
    List<Registro> todos();

    @Delete
    void remove(Registro registro);

    @Update
    void edita(Registro registro);

    @Query("SELECT * FROM registro WHERE tipo = :tipo  ORDER BY data DESC")
    List<Registro> registros(String tipo);

    @Query("SELECT * FROM registro " +
            "WHERE tipo = :tipo " +
            "AND data >= :hoje " +
            "ORDER BY data DESC")
    List<Registro> proximasRegistros(String tipo, long hoje);

    @Query("SELECT * FROM registro " +
            "WHERE tipo = :tipo " +
            "AND mes = :mes " +
            "AND ano = :ano " +
            "ORDER BY data DESC")
    List<Registro> registrosDoMesEAno(String tipo, int mes, int ano);

    @Query("SELECT * FROM registro " +
            "WHERE mes = :mes " +
            "AND ano = :ano " +
            "ORDER BY data DESC")
    List<Registro> todosRegistrosDoMesEAno(int mes, int ano);

    @Query("SELECT * FROM registro " +
            "WHERE nome = :nome")
    List<Registro> todosRegistrosDoNome(String nome);

}
