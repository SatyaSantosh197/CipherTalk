import java.math.BigInteger;

public class User {
    private String userName;
    private BigInteger encryptedMessage;
    private final RSA encryptionTechnique;
    private final KeyData PublicKeyData;
    Map<String, MessageList> sentMessages = new HashMap<>();
    Map<String, MessageList> receivedMessages = new HashMap<>();

    public User(String userName) {
        if(CertificationAuthority.findIfUserNameAlreadyExists(userName)) {
            throw new IllegalArgumentException("This username is taken!!");
        }
        this.userName = userName;

        encryptionTechnique = new RSA();
        PublicKeyData = encryptionTechnique.getPublicKey();

        CertificationAuthority.registerPublickey(userName, PublicKeyData);
        MailBox.getInstance().addUser(userName);
    }

    private void updateSentMessages(String userName, String message) {
        if(sentMessages.containsKey(userName)) {
            sentMessages.get(userName).addMessage(message, this.userName);
        } else {
            MessageList newMessageList = new MessageList();
            newMessageList.addMessage(message, this.userName);
            sentMessages.put(userName, newMessageList);
        }
    }

    private KeyData getReceiverKeyData(String receiverUserName) {
        KeyData receiverKeyData = CertificationAuthority.getPublicKey(receiverUserName);
        if (receiverKeyData == null) {
            throw new IllegalArgumentException("User with userName: " + receiverUserName + " not found");
        }
        return receiverKeyData;
    }

    public String getDecryptedMessage(BigInteger encryptedMessage) {
        return encryptionTechnique.decrypt(encryptedMessage);
    }

    public BigInteger getEncryptedMessage() {
        return encryptedMessage;
    }

//    public KeyData getPublicKey(){
//        return PublicKeyData;
//    }


    public void sendMessage(String message, String receiverUserName) {

        // find the receivers PublicKey
        KeyData receiverKeyData = getReceiverKeyData(receiverUserName);

        // Update this message into sent messages map
        updateSentMessages(receiverUserName, message);

        // encryptMessage using the public key and n of the reciever
        encryptedMessage = encryptionTechnique.encrypt(message, receiverKeyData.getPublicKey(), receiverKeyData.getModValue());

        // send the message to mail box
        MailBox.addMessage(this.userName, encryptedMessage);
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
