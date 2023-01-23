package com.twentiethcenturygangsta.JamBoard.TestClass;

public class NumberUtil {

    public static int parseInt(String args) {
        int number;
        try {
            number = Integer.parseInt(args);
        } catch (NumberFormatException e) {
            number = 0;
        }
        return number;
    }
}
