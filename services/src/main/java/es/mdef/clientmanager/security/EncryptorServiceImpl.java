package es.mdef.clientmanager.security;

import org.owasp.esapi.Encryptor;
import org.owasp.esapi.codecs.Base64;
import org.owasp.esapi.crypto.CipherText;
import org.owasp.esapi.crypto.PlainText;
import org.owasp.esapi.errors.EncryptionException;
import org.owasp.esapi.reference.crypto.JavaEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


/**
 * User: jonsurbe
 * Date: 23/05/14
 * Time: 11:27
 */
@Service
public class EncryptorServiceImpl implements EncryptorService {
    static final Logger LOG = LoggerFactory.getLogger(EncryptorService.class);
    private static final String PBE_ALGORITHM = "PBEWITHMD5ANDTRIPLEDES";
    private static final String ALGORITHM = "AES";

    // hardcoded for demonstration use. In production you might get the
    // salt from the filesystem and the password from a appserver JNDI value.
    private static final String SALT = "WR9bdtN3tMHg75PDK9PoIQ==";
    private static final char[] PASSWORD = "7AXyrRttFnPJHgzD".toCharArray();

    // the key
    private transient SecretKey key;

    /**
     * Constructor creates secret key. In production we may want
     * to avoid keeping the secret key hanging around in memory for
     * very long.
     */
    public EncryptorServiceImpl() {
        try {
            // create the PBE key
            KeySpec spec = new PBEKeySpec(PASSWORD, Base64.decode(SALT), 1024);
            SecretKey skey = SecretKeyFactory.getInstance(PBE_ALGORITHM).generateSecret(spec);
            // recast key as straightforward AES without padding.
            key = new SecretKeySpec(skey.getEncoded(), ALGORITHM);
        } catch (SecurityException ex) {
            LOG.error("constructor=",ex);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("constructor=",e);
        } catch (InvalidKeySpecException e) {
            LOG.error("constructor=",e);
        }
    }

    /**
     * Decrypt String
     */
    @Override
    public String decryptString(String ciphertext) {
        String plaintext = null;

        if (ciphertext != null) {
            try {
                Encryptor encryptor = JavaEncryptor.getInstance();
                CipherText ct = CipherText.fromPortableSerializedBytes(Base64.decode(ciphertext));
                plaintext = encryptor.decrypt(key, ct).toString();
            } catch (EncryptionException e) {
                LOG.error("decrypt=",e);
            }
        }

        return plaintext;
    }

    /**
     * Encrypt String
     */
    @Override
    public String encryptString(String plaintext) {
        String ciphertext= null;

        if (plaintext!= null) {
            try {
                Encryptor encryptor = JavaEncryptor.getInstance();
                CipherText ct = encryptor.encrypt(key, new PlainText(plaintext));
                ciphertext = Base64.encodeBytes(ct.asPortableSerializedByteArray());
            } catch (EncryptionException e) {
                LOG.error("encrypt=",e);
            }
        }

        return ciphertext;
    }
}
