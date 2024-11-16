import java.math.BigInteger;
import java.util.Map;
import java.util.HashMap;

public class User {
    private String userName;
    private BigInteger encryptedMessage;
    private final RSA encryptionTechnique;
    private final KeyData publicKeyData;
    Map<String, MessageList<String>> sentMessages = new HashMap<>();
    Map<String, MessageList<String>> receivedMessages = new HashMap<>();

    public User(String userName) {
        if(CertificationAuthority.getInstance().findIfUserNameAlreadyExists(userName)) {
            throw new IllegalArgumentException("This username is taken!!");
        }
        this.userName = userName;

        encryptionTechnique = new RSA();
        publicKeyData = encryptionTechnique.getPublicKey();

        CertificationAuthority.getInstance().registerPublicKey(userName, publicKeyData);
        MailBox.getInstance().addUser(userName);
    }

    private void updateSentMessages(String userName, String message) {
        if(sentMessages.containsKey(userName)) {
            sentMessages.get(userName).addMessageToList(message, this.userName);
        } else {
            MessageList<String> newMessageList = new MessageList<>();
            newMessageList.addMessageToList(message, this.userName);
            sentMessages.put(userName, newMessageList);
        }
    }

    private void updateReceivedMessages(String userName, String message) {
        if(receivedMessages.containsKey(userName)) {
            receivedMessages.get(userName).addMessageToList(message, this.userName);
        } else {
            MessageList<String> newMessageList = new MessageList<>();
            newMessageList.addMessageToList(message, this.userName);
            receivedMessages.put(userName, newMessageList);
        }
    }

    private KeyData getReceiverKeyData(String receiverUserName) {
        KeyData receiverKeyData = CertificationAuthority.getInstance().getPublicKey(receiverUserName);
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

        // cant send message to themselves
        if (this.userName.equals(receiverUserName)) {
            throw new IllegalArgumentException("You cannot send a message to yourself.");
        }

        // find the receivers PublicKey
        KeyData receiverKeyData = getReceiverKeyData(receiverUserName);

        // Update this message into sent messages map
        updateSentMessages(receiverUserName, message);

        // encryptMessage using the public key and n of the reciever
        encryptedMessage = encryptionTechnique.encrypt(message, receiverKeyData.getKey(), receiverKeyData.getModValue());

        // send the message to mail box
        MailBox.getInstance().addMessage(receiverUserName ,this.userName, encryptedMessage);
    }

    public void getMessages() {
        MessageNode<BigInteger> currentNode = MailBox.getInstance().getMessages(this.userName).getHead();
        if (currentNode == null) {
            System.out.println("No messages found.");
            return; // Graceful handling of no messages
        }

        while (currentNode != null) {
            updateReceivedMessages(currentNode.senderUserName, getDecryptedMessage(currentNode.message));
            System.out.println("Sender: " + currentNode.senderUserName + ", Message: " + getDecryptedMessage(currentNode.message));
            currentNode = currentNode.next;
        }
        System.out.println();
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