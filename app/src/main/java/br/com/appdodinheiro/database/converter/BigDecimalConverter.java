package br.com.appdodinheiro.database.converter;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class BigDecimalConverter {

    @TypeConverter
    public String paraString(BigDecimal valor) {
        if (valor != null) {
            return valor.toString();
        }
        return null;
    }

    @TypeConverter
    public BigDecimal paraCalendar(String valor) {
        if (valor != null) {
            return new BigDecimal(valor);
        }
        return null;
    }

}
