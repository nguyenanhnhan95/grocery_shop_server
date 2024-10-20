package com.example.grocery_store_sales_online.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class CommonUtils {
    private CommonUtils(){
    }
    public static String convertToAlias(String s) {
        if (s != null) {
            try {
                String temp = Normalizer.normalize(s.toUpperCase(),
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
    public static String generateNameAlias(String s) {
        if (s != null) {
            String temp = Normalizer.normalize(s.toLowerCase(), Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String a = pattern.matcher(temp).replaceAll("").replaceAll("đ", "d")
                    .replaceAll("[^a-zA-Z0-9]", " ")
                    .trim()
                    .replaceAll(" +", "_");
            return a ;
        } else {
            return "";
        }
    }
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return ""; // Nếu không có phần mở rộng
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
    public static String getFileExtensionFromTypeContent(String fileName) {
        if (fileName == null || fileName.lastIndexOf(CommonConstants.SLASH) == -1) {
            return ""; // Nếu không có phần mở rộng
        }
        return fileName.substring(fileName.lastIndexOf(CommonConstants.SLASH) + 1);
    }
    public static List<Long> convertStringToListLong(String commaSeparatedIds) {
        if (commaSeparatedIds == null || commaSeparatedIds.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(commaSeparatedIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
    public static List<String> convertStringToList(String commaSeparatedIds) {
        if (commaSeparatedIds == null || commaSeparatedIds.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(commaSeparatedIds.split(","))
                .collect(Collectors.toList());
    }
}
