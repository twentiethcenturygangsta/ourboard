package com.twentiethcenturygangsta.ourboard.util;

public final class DatabaseUtils {
    private static final String UNDER_BAR = "_";

    private DatabaseUtils() {}

    public static String getSnakeNameForDatabase(String camelName) {
        String snakeName = "";
        for(int i = 0; i < camelName.length(); i++) {
            if(i == 0) {
                snakeName = snakeName + Character.toUpperCase(camelName.charAt(0));
            } else {
                if (Character.isUpperCase(camelName.charAt(i))) {
                    snakeName = snakeName + UNDER_BAR;
                    snakeName = snakeName + camelName.charAt(i);
                } else {
                    snakeName = snakeName + Character.toUpperCase(camelName.charAt(i));
                }
            }
        }
        return snakeName;
    }
}
