package com.ccoins.coins.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {

    public static List<String> splitStringByComma(String str){
        return new ArrayList<>(Arrays.asList(str.split(",\\s")));
    }
}
