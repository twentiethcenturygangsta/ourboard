package com.twentiethcenturygangsta.ourboard.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptionConfig {

    public static String encrypt(String key, String value) throws Exception {
        String result = "";
        int j = 0;

        byte[] bytes = value.getBytes(StandardCharsets.US_ASCII);

        for(int i=0; i<bytes.length; i++) {
            String val1 = Character.toString((bytes[i]) + value.length());

            if(i >= key.length()) {
                String key1 = key.substring(j, j+1);
                j++;

                byte[] val1_bytes = val1.getBytes();
                byte[] key1_bytes = key1.getBytes();

                val1 = Character.toString(val1_bytes[0] ^ key1_bytes[0]);
            } else {
                String key1 = key.substring(i, i+1);

                byte[] val1_bytes = val1.getBytes();
                byte[] key1_bytes = key1.getBytes();

                val1 = Character.toString(val1_bytes[0] ^ key1_bytes[0]);
            }
            result += val1;
        }

        //encode into base64
        byte [] encryptedIvText = result.getBytes(StandardCharsets.UTF_8);
        return new String(Base64.getEncoder().encode(encryptedIvText), "UTF-8");
    }

    public static String decrypt(String key, String value) throws Exception {
        String result = "";
        int j = 0;

        //decode with base64 decoder
        byte [] decrypt_bytes = Base64.getDecoder().decode(value);

        for(int i=0; i<decrypt_bytes.length; i++) {
            String val2 = Character.toString(decrypt_bytes[i]);

            byte[] val2_bytes = val2.getBytes();

            if(i >= key.length()) {
                String key2 = key.substring(j, j + 1);
                j++;

                byte[] key2_bytes = key2.getBytes();

                val2 = Character.toString(val2_bytes[0] ^ key2_bytes[0]);
            } else {
                String key2 = key.substring(i, i + 1);

                byte[] key2_bytes = key2.getBytes();

                val2 = Character.toString(val2_bytes[0] ^ key2_bytes[0]);
            }

            byte[] bytes = val2.getBytes();

            val2 = Character.toString((bytes[0] - decrypt_bytes.length));

            result += val2;
        }

        return result;
    }
}
