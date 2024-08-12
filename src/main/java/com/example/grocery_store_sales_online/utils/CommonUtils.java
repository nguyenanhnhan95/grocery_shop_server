package com.example.grocery_store_sales_online.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.Normalizer;
import java.util.regex.Pattern;
@Slf4j
public class CommonUtils {
    private CommonUtils(){
    }
    public static String convertToAlias(String s) {
        if (s != null) {
            try {
                String temp = Normalizer.normalize(s.toLowerCase(),
                        Normalizer.Form.NFD);
                Pattern pattern = Pattern
                        .compile("\\p{InCombiningDiacriticalMarks}+");
                return pattern.matcher(temp).replaceAll("").replaceAll(" +", "_")
                        .replaceAll("Ä‘", "d")
                        .replaceAll("[^a-zA-Z0-9 _]", "")
                        .replaceAll("-+", "_");
            }catch (Exception ex){
                log.error("Exception occurred while convert CommonUtils.convertToAlias to alias , Exception message {}", ex.getMessage());
                return s;
            }

        }
        return null;
    }
}
