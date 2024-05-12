package com.social.networking.api.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class Utils {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey() {
        MessageDigest sha = null;
        try {
            key = "b2h0bmFvdA".getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt) {
        try {
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getUrlEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            strToDecrypt = strToDecrypt.replace("/", "_").replaceAll("\\+", "-");
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getUrlDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateRandomString(String prefix) throws NoSuchAlgorithmException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssssss");
        Date date = new Date();
        String result = format.format(date);
        Random random = SecureRandom.getInstanceStrong();
        result += random.nextInt(9);
        if (prefix == null) {
            return result;
        } else {
            return prefix + "-" + result;
        }

    }

    public static String generateRandomStringSS(String prefix) throws NoSuchAlgorithmException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        Date date = new Date();
        String result = format.format(date);
        Random random = SecureRandom.getInstanceStrong();
        result += random.nextInt(9);
        if (prefix == null) {
            return result;
        } else {
            return prefix + "-" + result;
        }

    }

    /*public static void main(String[] args) throws NoSuchAlgorithmException {
        final String secretKey = "b2h0bmFvdA";
    }*/

    /**
     * This method is used to create a predicate that filters out duplicate objects based on multiple key extractors.
     * It uses a ConcurrentHashMap to store the keys of the objects that have already been seen.
     *
     * @param <T>           The type of the objects to be filtered.
     * @param keyExtractors Functions that extract the keys from the objects.
     * @return A predicate that filters out duplicate objects based on the extracted keys.
     */
    @SafeVarargs
    public static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        // A ConcurrentHashMap is used to store the keys of the objects that have already been seen.
        // This allows for efficient and thread-safe checking of duplicate keys.
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();
        // The returned predicate checks if the keys of the given object have already been seen.
        // If they have, the predicate returns false, indicating that the object is a duplicate.
        // If they have not, the predicate returns true, indicating that the object is not a duplicate.
        return t -> {
            // Extract the keys from the object using the provided key extractors.

            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());
            // Check if the keys have already been seen.
            // If they have, return false to indicate a duplicate.
            // If they have not, add the keys to the seen map and return true to indicate a non-duplicate.
            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }
}
