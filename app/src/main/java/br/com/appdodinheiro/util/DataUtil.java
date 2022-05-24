package br.com.appdodinheiro.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataUtil {

    public static String formataData(Calendar data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data.getTime());
    }
}