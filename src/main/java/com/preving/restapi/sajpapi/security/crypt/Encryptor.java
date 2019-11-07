package com.preving.restapi.sajpapi.security.crypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.BlowfishEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.PaddedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;

public class Encryptor {

    private BufferedBlockCipher cipher;
    private KeyParameter key;

    // Initialize the cryptographic engine.
    // The key array should be at least 8 bytes long.

    public Encryptor(byte[] key) {
        /*
        cipher = new PaddedBlockCipher(
                 new CBCBlockCipher(
                 new DESEngine() ) );
         */

        cipher = new PaddedBlockCipher(new CBCBlockCipher(new BlowfishEngine()));
        this.key = new KeyParameter(key);
    }


    public Encryptor(String key) {
        this(key.getBytes());
    }

    // Private routine that does the gritty work.
    private byte[] callCipher(byte[] data) throws CryptoException {
        int size = cipher.getOutputSize(data.length);
        byte[] result = new byte[size];
        int olen = cipher.processBytes(data, 0, data.length, result, 0);
        olen += cipher.doFinal(result, olen);

        if (olen < size) {
            byte[] tmp = new byte[olen];
            System.arraycopy(
                    result, 0, tmp, 0, olen);
            result = tmp;
        }

        return result;
    }

    // Encrypt arbitrary byte array, returning the
    // encrypted data in a different byte array.
    public synchronized byte[] encrypt(byte[] data)
            throws CryptoException {
        if (data == null || data.length == 0) {
            return new byte[0];
        }

        cipher.init(true, key);
        return callCipher(data);
    }

    // Encrypts a string.
    public byte[] encryptString(String data)
            throws CryptoException {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }

        return encrypt(data.getBytes());
    }

    // Decrypts arbitrary data.

    public synchronized byte[] decrypt(byte[] data)
            throws CryptoException {
        if (data == null || data.length == 0) {
            return new byte[0];
        }

        cipher.init(false, key);
        return callCipher(data);
    }

    // Decrypts a string that was previously encoded
    // using encryptString.

    public String decryptString(byte[] data)
            throws CryptoException {
        if (data == null || data.length == 0) {
            return "";
        }

        return new String(decrypt(data));
    }

    /**
     * Encripta la cadena utilizando Blowfish, al resultado le hace una codificación en Base 64 y posteriormente retorna un hash
     * @param data
     * @return
     * @throws CryptoException
     */
    public String encryptAndHashString(String data) throws CryptoException {
        byte encrypted[] = encryptString(data);
        return DigestUtils.shaHex(Base64.encode(encrypted));
    }

    public String encryptAndHashMD5String(String data) throws CryptoException {
        byte encrypted[] = encryptString(data);
        return DigestUtils.md5Hex(encrypted);
    }

    public static void main(String args[]) throws CryptoException {
        Encryptor e = new Encryptor("javierMontesinos..19720802");
        System.out.println(e.encryptAndHashMD5String("fj.montesinos@preving.com"));
    }
    
}
