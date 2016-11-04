package es.mdef.clientmanager.security;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 27/05/14
 * Time: 21:57
 */
public interface EncryptorService {
    String decryptString(String ciphertext);

    String encryptString(String plaintext);
}
