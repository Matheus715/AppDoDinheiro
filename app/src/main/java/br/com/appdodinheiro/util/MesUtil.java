package br.com.appdodinheiro.util;

import java.util.Calendar;

public class MesUtil {

    public static String getMesEmTexto(Calendar hoje, String mesEmTexto) {
        if (hoje.get(Calendar.MONTH) == 0) {
            mesEmTexto = "Janeiro";
        } else if (hoje.get(Calendar.MONTH) == 1) {
            mesEmTexto = "Fevereiro";
        } else if (hoje.get(Calendar.MONTH) == 2) {
            mesEmTexto = "Março";
        } else if (hoje.get(Calendar.MONTH) == 3) {
            mesEmTexto = "Abril";
        } else if (hoje.get(Calendar.MONTH) == 4) {
            mesEmTexto = "Maio";
        } else if (hoje.get(Calendar.MONTH) == 5) {
            mesEmTexto = "Junho";
        } else if (hoje.get(Calendar.MONTH) == 6) {
            mesEmTexto = "Julho";
        } else if (hoje.get(Calendar.MONTH) == 7) {
            mesEmTexto = "Agosto";
        } else if (hoje.get(Calendar.MONTH) == 8) {
            mesEmTexto = "Setembro";
        } else if (hoje.get(Calendar.MONTH) == 9) {
            mesEmTexto = "Outubro";
        } else if (hoje.get(Calendar.MONTH) == 10) {
            mesEmTexto = "Novembro";
        } else if (hoje.get(Calendar.MONTH) == 11) {
            mesEmTexto = "Dezembro";
        }
        return mesEmTexto;
    }

    public static int getMesEmInt(String mesEmString) {
        int mesEmInt = 0;

        if (mesEmString == "Janeiro") {
            mesEmInt = 0;
        } else if (mesEmString == "Fevereiro") {
            mesEmInt = 1;
        } else if (mesEmString == "Março") {
            mesEmInt = 2;
        } else if (mesEmString == "Abril") {
            mesEmInt = 3;
        } else if (mesEmString == "Maio") {
            mesEmInt = 4;
        } else if (mesEmString == "Junho") {
            mesEmInt = 5;
        } else if (mesEmString == "Julho") {
            mesEmInt = 6;
        } else if (mesEmString == "Agosto") {
            mesEmInt = 7;
        } else if (mesEmString == "Setembro") {
            mesEmInt = 8;
        } else if (mesEmString == "Outubro") {
            mesEmInt = 9;
        } else if (mesEmString == "Novembro") {
            mesEmInt = 10;
        } else if (mesEmString == "Dezembro") {
            mesEmInt = 11;
        }

        return mesEmInt;
    }
}
