package br.com.appdodinheiro.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.appdodinheiro.database.converter.BigDecimalConverter;
import br.com.appdodinheiro.database.converter.CalendarConverter;
import br.com.appdodinheiro.database.dao.MetaDAO;
import br.com.appdodinheiro.database.dao.RegistroDAO;
import br.com.appdodinheiro.model.Meta;
import br.com.appdodinheiro.model.Registro;

@Database(entities = {Registro.class, Meta.class}, version = 3, exportSchema = false)
@TypeConverters({BigDecimalConverter.class, CalendarConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String NOME_BANCO_DE_DADOS = "agenda.db";

    public abstract RegistroDAO getRoomRegistroDAO();

    public abstract MetaDAO getRoomMetaDAO();

    public static AppDatabase getInstance(Context context) {
        return Room
                .databaseBuilder(context, AppDatabase.class, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries()
                .build();
    }
}