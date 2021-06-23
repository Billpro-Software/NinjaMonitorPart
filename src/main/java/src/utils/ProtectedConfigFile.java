package src.utils;

/**
 * Created by scs0574 on 10/26/15.
 */
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

/**
 * Manages login password encryption.
 *
 * @author ninja team
 */
public class ProtectedConfigFile {

    public static final String ENCRYPTION_METHOD = "PBEWithMD5AndDES";
    public static final String ENCODING = "UTF-8";
    /**
     *
     */
    private static Boolean FAC_DECRYPT_LOGIN_PASSWORD_ENABLED  = true;

    private static final char[] PASSWORD = "Ninja2015NovemberISR".toCharArray();

    private static final byte[] SALT = {
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
    };


    public void runDecryptEncrypt(String phrase) throws Exception{
        String originalPassword = phrase;
        System.out.println("Original password: " + originalPassword);
        String encryptedPassword = encrypt(originalPassword);
        System.out.println("Encrypted password: " + encryptedPassword);
        String decryptedPassword = decrypt(encryptedPassword);
        System.out.println("Decrypted password: " + decryptedPassword);
        String origDecryptedPassword = decrypt(originalPassword);
        System.out.println("Original Decrypted password: " + origDecryptedPassword);
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ProtectedConfigFile protectedConfigFile = new ProtectedConfigFile();
        // prints the encrypted version of the program arguments
       for(String arg:args){
           protectedConfigFile.runDecryptEncrypt("mdwc2010");
       }
    }

    /**
     * Encrypt a string using base64 encryption
     * @param property
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public static String encrypt(String property) throws GeneralSecurityException, UnsupportedEncodingException, ProtectedConfigFileLoadException {
        if (property == null){
            throw new ProtectedConfigFileLoadException("Invalid property",5501);
        }
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ENCRYPTION_METHOD);
        if (keyFactory == null){
            throw new ProtectedConfigFileLoadException("Invalid Key Factory",5502);
        }
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance(ENCRYPTION_METHOD);
        if (pbeCipher == null){
            throw new ProtectedConfigFileLoadException("Invalid pbeCipher",5503);
        }
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return base64Encode(pbeCipher.doFinal(property.getBytes(ENCODING)));
    }

    /**
     *
     * @param bytes
     * @return
     */
    private static String base64Encode(byte[] bytes) {
        // NB: This class is internal, and you probably should use another impl

        //String encryptedValue = new String(Base64.getEncoder().encode(bytes));

        //return encryptedValue;
        return "";//new BASE64Encoder().encode(bytes);
    }

    public static String decrypt(String property) throws ProtectedConfigFileLoadException {
        if (!FAC_DECRYPT_LOGIN_PASSWORD_ENABLED){
            return property;
        }
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance(ENCRYPTION_METHOD);
            SecretKey key = null;
            key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
            Cipher pbeCipher = Cipher.getInstance(ENCRYPTION_METHOD);
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
            return new String(pbeCipher.doFinal(base64Decode(property)), ENCODING);
        }
        catch (Exception e) {
            throw new ProtectedConfigFileLoadException("Failure during password decryption , Invalid connection password or user name"+e.getMessage(),5800);
        }

    }

    private static byte[] base64Decode(String property) throws IOException {
        // NB: This class is internal, and you probably should use another impl
        //byte[] decordedValue = Base64.getDecoder().decode(property);
        //return decordedValue;
        return null;//BASE64Decoder().decodeBuffer(property);
    }


}