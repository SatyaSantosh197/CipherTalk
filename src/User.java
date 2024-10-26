import java.math.BigInteger;

public class User {
    private BigInteger encryptedMessage;
    private final RSA encryptionTechnique;


    public User(int keySize) {
        if (keySize < 1024 || keySize > 2048) {
            throw new IllegalArgumentException("Key size must be between 1024 and 2048 bits.");
        }
        encryptionTechnique = new RSA(keySize);
    }
    public BigInteger getPublicKey(){
        return encryptionTechnique.getPublicKey();
    }
    public BigInteger getModValue() {
        return encryptionTechnique.getModValue();
    }
    public void sendMessage(String message, User user) {
        encryptedMessage = encryptionTechnique.encrypt(message, user.getPublicKey(), user.getModValue());
    }
//    public String getMessage() {
//        return this.message;
//    }
    public BigInteger getEncryptedMessage() {
        return encryptedMessage;
    }
    public String getDecryptedMessage(BigInteger encryptedMessage) {
        return encryptionTechnique.decrypt(encryptedMessage);
    }
}


// User A
// Public key
// Private Key

// User B
// Public key
// Private Key

// A -> B
// encryption using B's public key at A
// decryption using B's private key at B
