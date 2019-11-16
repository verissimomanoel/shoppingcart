/*
 * Util.java
 *
 * This is a free software.
 */
package br.com.sample.shoppingcart.api.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;

/**
 * Project Utility Class.
 *
 * @author Manoel Veríssimo dos Santos Neto
 */
public final class Util {
    private static final int ITERATION_COUNT = 19;

    private static final String CHARSET_UTF_8 = "UTF-8";

    private final static byte[] SALT = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x34,
            (byte) 0xE3, (byte) 0x03 };

    private final static String KEY_ENCRYPT_DECRYPT = "0242ac110003779ea6214b650";

    private final static String DEFAULT_SEPARATOR = ",";

    /**
     * Private constructor to ensure singleton.
     */
    private Util() {
    }

    /**
     * Encripyt password using algorithm PBEWithMD5AndDES.
     *
     * @param value
     * @return
     */
    public static String encrypt(final String value) {
        SecretKey key = null;
        KeySpec keySpec = new PBEKeySpec(KEY_ENCRYPT_DECRYPT.toCharArray(), SALT, ITERATION_COUNT);

        try {
            key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

            byte[] utf8 = value.getBytes(CHARSET_UTF_8);
            byte[] enc = ecipher.doFinal(utf8);

            return Base64.getEncoder().encodeToString(enc);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Fail on decrypt value.", e);
        }
    }

    /**
     * Returns the concatenated informed collection, considering the separator.
     *
     * @param collection
     * @param separator
     * @return
     */
    public static String getCollectionAsString(final Collection<? extends Object> collection, final String separator) {
        StringBuilder builder = new StringBuilder();

        if (collection != null && !collection.isEmpty()) {
            Iterator<?> iterator = collection.iterator();

            while (iterator.hasNext()) {
                builder.append(iterator.next());

                if (iterator.hasNext()) {
                    builder.append(separator.trim()).append(" ");
                }
            }
        }
        return builder.toString();
    }

    /**
     * Returns the concatenated informed collection, considering the separator.
     *
     * @param collection
     * @return
     */
    public static String getCollectionAsString(final Collection<? extends Object> collection) {
        return getCollectionAsString(collection, DEFAULT_SEPARATOR);
    }
}
