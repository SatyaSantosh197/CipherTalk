import java.math.BigInteger;
import java.util.Map;
import java.util.HashMap;

public class User {
    private String userName;
    private final RSA encryptionTechnique;
    private final KeyData publicKeyData;

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

    public void sendMessage(String message, String receiverUserName) {

        // cant send message to themselves
        if (this.userName.equals(receiverUserName)) {
            throw new IllegalArgumentException("You cannot send a message to yourself.");
        }

        // find the receiver's PublicKey
        KeyData receiverKeyData = getReceiverKeyData(receiverUserName);

        // Update this message into sent messages map (future enhancement)
        updateSentMessages(receiverUserName, message);

        // encryptMessage using the public key and n of the receiver
        BigInteger encryptedMessage = encryptionTechnique.encrypt(message, receiverKeyData.getKey(), receiverKeyData.getModValue());

        // send the message to mailbox
        MailBox.getInstance().addMessage(receiverUserName ,this.userName, encryptedMessage);
    }

    private KeyData getReceiverKeyData(String receiverUserName) {
        KeyData receiverKeyData = CertificationAuthority.getInstance().getPublicKey(receiverUserName);
        if (receiverKeyData == null) {
            throw new IllegalArgumentException("User with userName: " + receiverUserName + " not found");
        }
        return receiverKeyData;
    }


    // This is verified and it is correct: santosh
    public String getDecryptedMessage(BigInteger encryptedMessage) {
        return encryptionTechnique.decrypt(encryptedMessage);
    }


    // Deprecated Method used in Single Thread Version
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

    // Message History: this part contains the methods and data structures which handles messages sent and messages received.
    Map<String, MessageList<String>> sentMessages = new HashMap<>();
    Map<String, MessageList<String>> receivedMessages = new HashMap<>();

    private void updateSentMessages(String receiverName, String message) {
        if(sentMessages.containsKey(receiverName)) {
            sentMessages.get(receiverName).addMessageToList(message, this.userName);
        } else {
            MessageList<String> newMessageList = new MessageList<>();
            newMessageList.addMessageToList(message, this.userName);
            sentMessages.put(receiverName, newMessageList);
        }
    }

    private void updateReceivedMessages(String senderName, String message) {
        if(receivedMessages.containsKey(senderName)) {
            receivedMessages.get(senderName).addMessageToList(message, this.userName);
        } else {
            MessageList<String> newMessageList = new MessageList<>();
            newMessageList.addMessageToList(message, this.userName);
            receivedMessages.put(senderName, newMessageList);
        }
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