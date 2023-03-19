package com.github.twentiethcenturygangsta.ourboard.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class EncryptionConfig {
    private static final String CHARSET = "UTF-8";
    public static String key;

    private EncryptionConfig() {};

    public static String encrypt(String value) throws Exception {
        StringBuilder result = new StringBuilder();
        String valueInKey = "";
        int j = 0;

        byte[] bytes = value.getBytes(StandardCharsets.US_ASCII);

        for(int i=0; i<bytes.length; i++) {
            if(i >= key.length()) {
                valueInKey = key.substring(j, j+1);
                j++;

            } else {
                valueInKey = key.substring(i, i+1);
            }

            byte[] val_bytes = Character.toString((bytes[i]) + value.length()).getBytes();
            byte[] key_bytes = valueInKey.getBytes();

            result.append(Character.toString(val_bytes[0] ^ key_bytes[0]));
        }

        //encode into base64
        byte [] encryptedIvText = result.toString().getBytes(StandardCharsets.UTF_8);
        return new String(Base64.getEncoder().encode(encryptedIvText), CHARSET);
    }
}
