package com.ccoins.coins.utils;

import java.time.LocalTime;

public class CodeUtils {

    public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String NUMBERS = "0123456789";

    public static  final String ALPHANUMERIC = LETTERS.concat(NUMBERS);

    public static String getRandomString(int codeLength)
    {
        LocalTime now = LocalTime.now();
        StringBuilder codigo = new StringBuilder(now.toString());
        codigo = new StringBuilder(codigo.toString().split("\\.")[1]);

        if(codigo.length() > codeLength){
            int quantityToReduce =  codigo.length() - codeLength;
            codigo = new StringBuilder(codigo.substring(0, codigo.length() - quantityToReduce));
        }

        if(codigo.length() < codeLength){
            int quantityToAdd =  codeLength - codigo.length();
            codigo.append("0".repeat(Math.max(0, quantityToAdd)));
        }

        // Reemplazar cada dígito numérico por su equivalente ASCII
        for (int i = 1; i <= 10; i++) {

            int number = i - 1;
            char charater;

            if(i % 2 == 0) {
                charater = (char) ('A' + (number));
            }else{
                charater = (char) ('Z' - (number));
            }

            codigo = new StringBuilder(codigo.toString().replaceAll(Integer.toString(i), Character.toString(charater)));
        }
        return codigo.toString();
    }
}
