package br.com.appdodinheiro.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.appdodinheiro.model.Meta;

@Dao
public interface MetaDAO {

    @Insert
    void salva(Meta meta);

    @Query("SELECT * FROM meta")
    List<Meta> todos();

    @Delete
    void remove(Meta meta);

    @Update
    void edita(Meta meta);
}
