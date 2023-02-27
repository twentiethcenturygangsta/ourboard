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

    public static String getCamelNameForClass(String snakeName) {
        snakeName = snakeName.substring(0, 1).toUpperCase()
                + snakeName.substring(1);

        // Convert to StringBuilder
        StringBuilder builder
                = new StringBuilder(snakeName);

        // Traverse the string character by
        // character and remove underscore
        // and capitalize next letter
        for (int i = 0; i < builder.length(); i++) {

            // Check char is underscore
            if (builder.charAt(i) == '_') {

                builder.deleteCharAt(i);
                builder.replace(
                        i, i + 1,
                        String.valueOf(
                                Character.toUpperCase(
                                        builder.charAt(i))));
            }
        }

        return builder.toString();
    }
}
