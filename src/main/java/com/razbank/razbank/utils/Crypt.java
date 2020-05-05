package com.razbank.razbank.utils;

import com.google.crypto.tink.*;
import com.google.crypto.tink.config.TinkConfig;
import com.google.crypto.tink.daead.DeterministicAeadKeyTemplates;
import com.google.crypto.tink.subtle.Hex;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

public class Crypt {

    private static final String FILENAME = "keyset.json";
    public static Crypt instance = null;
    private static DeterministicAead daead;

    public static Crypt getInstance() throws GeneralSecurityException, IOException {
        if(instance==null) {
            synchronized (Crypt.class) {
                if (instance == null) {
                    instance = new Crypt();
                    instance.config();
                }
            }
        }
        return instance;
    }

    public void config() throws GeneralSecurityException, IOException {
        TinkConfig.register();
        KeysetHandle keysetHandle;
        File tempFile = new File(FILENAME);
        if(!tempFile.exists()) {
            keysetHandle = KeysetHandle.generateNew(DeterministicAeadKeyTemplates.AES256_SIV);
            savePrivateKey(keysetHandle);
        }
        keysetHandle = loadPrivateKey();

        daead = keysetHandle.getPrimitive(DeterministicAead.class);

    }

    public String encryptMessage(String plainText){
        Optional<byte[]> cipherText = null;
        try{
            cipherText = Optional.ofNullable(daead.encryptDeterministically(plainText.getBytes(), null));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return Hex.encode(cipherText.orElseThrow(()-> new NullPointerException("It couldn't encrypt text")));
    }

    public String decryptMessage(String cryptText){
        Optional<byte[]> decryptedText = null;
        try{
            decryptedText = Optional.ofNullable(daead.decryptDeterministically(Hex.decode(cryptText),null));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return new String(decryptedText.orElseThrow(()-> new NullPointerException("It couldn't decrypt text")));
    }

    private void savePrivateKey(KeysetHandle keysetHandle) throws IOException {
        CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(new File(FILENAME)));
    }

    private KeysetHandle loadPrivateKey() throws IOException, GeneralSecurityException {
        return CleartextKeysetHandle.read(JsonKeysetReader.withFile(new File(FILENAME)));
    }

}
