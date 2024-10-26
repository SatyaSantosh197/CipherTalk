import java.math.BigInteger;

public class User {
    private BigInteger encryptedMessage;
    private final RSA encryptionTechnique;
    private final KeyData PublicKeyData;

    public User(int keySize) {
        if (keySize < 1024 || keySize > 2048) {
            throw new IllegalArgumentException("Key size must be between 1024 and 2048 bits.");
        }
        encryptionTechnique = new RSA(keySize);
        PublicKeyData = new KeyData(encryptionTechnique.getPublicKey(), encryptionTechnique.getModVlaue());
    }

    public BigInteger getPublicKey(){
        return PublicKeyData.getPublicKey();
    }

    public BigInteger getModValue() {
        return PublicKeyData.getModValue();
    }

    public void sendMessage(String message, User user) {
        encryptedMessage = encryptionTechnique.encrypt(message, user.getPublicKey(), user.getModValue());
    }

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
