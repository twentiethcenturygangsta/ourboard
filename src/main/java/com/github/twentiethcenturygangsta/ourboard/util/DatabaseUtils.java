package com.github.twentiethcenturygangsta.ourboard.util;

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

    public static String getCamelNameForClass(String snakeName, char delimiter) {
        boolean shouldConvertNextCharToLower = true;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < snakeName.length(); i++) {
            char currentChar = snakeName.charAt(i);
            if (currentChar == delimiter) {
                shouldConvertNextCharToLower = false;
            } else if (shouldConvertNextCharToLower) {
                builder.append(Character.toLowerCase(currentChar));
            } else {
                builder.append(Character.toUpperCase(currentChar));
                shouldConvertNextCharToLower = true;
            }
        }
        return builder.toString();
    }
}
