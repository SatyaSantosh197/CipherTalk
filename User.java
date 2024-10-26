import java.math.BigInteger;

public class User {
    private String userName;
    private BigInteger encryptedMessage;
    private final RSA encryptionTechnique;
    private final KeyData PublicKeyData;

    public User(String userName) {
        if(CertificationAuthority.findIfUserNameAlreadyExists(userName)) {
            throw new IllegalArgumentException("This username is taken!!");
        }
        this.userName = userName;
        encryptionTechnique = new RSA();
        PublicKeyData = encryptionTechnique.getPublicKey();
        CertificationAuthority.registerPublickey(userName, PublicKeyData);
    }

    public KeyData getPublicKey(){
        return PublicKeyData;
    }

    public void sendMessage(String message, String receiverUserName) {
        KeyData receiverKeyData = CertificationAuthority.getPublicKey(receiverUserName);

        if (receiverKeyData == null) {
            throw new IllegalArgumentException("User with userName: " + receiverUserName + " not found");
        }

        // encryptMessage using the public key and n of the reciever
        encryptedMessage = encryptionTechnique.encrypt(message, receiverKeyData.getPublicKey(), receiverKeyData.getModValue());

    }

    public String getDecryptedMessage(BigInteger encryptedMessage) {
        return encryptionTechnique.decrypt(encryptedMessage);
    }

    public BigInteger getEncryptedMessage() {
        return encryptedMessage;
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
