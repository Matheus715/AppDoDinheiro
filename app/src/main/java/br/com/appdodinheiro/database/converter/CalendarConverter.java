package br.com.appdodinheiro.database.converter;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class CalendarConverter {

    @TypeConverter
    public Long paraLong(Calendar data) {
        if (data != null) {
            return data.getTimeInMillis();
        }
        return null;
    }

    @TypeConverter
    public Calendar paraCalendar(Long data) {
        Calendar momentoAtual = Calendar.getInstance();
        if (data != null) {
            momentoAtual.setTimeInMillis(data);
        }
        return momentoAtual;
    }

}