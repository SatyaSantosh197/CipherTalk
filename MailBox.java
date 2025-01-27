import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MailBox {
    // Singleton Instance
    private static final MailBox instance = new MailBox();

    public static MailBox getInstance() {
        return instance;
    }

    private Map<String, MessageList<BigInteger>> hashMap = new HashMap<>();

    // Add user to MailBox with an empty MessageList
    public void addUser(String userName) {
        if(!hashMap.containsKey(userName)) {
            hashMap.put(userName, new MessageList<>());
        }
    }

    // Add the message of the intended user into the mail-box
    public void addMessage(String receiverUserName, String senderUserName, BigInteger encryptedMessage) {
        if (hashMap.containsKey(receiverUserName)) {
            hashMap.get(receiverUserName).addMessageToList(encryptedMessage, senderUserName);
        } else {
            MessageList<BigInteger> newMessageList = new MessageList<>();
            newMessageList.addMessageToList(encryptedMessage, senderUserName);
            hashMap.put(receiverUserName, newMessageList);
        }
    }

    // Fetch all the messages of the user by his userName
    public MessageList<BigInteger> getMessages(String userName) {
        MessageList<BigInteger> messages = hashMap.get(userName);
        if (messages == null || messages.getHead() == null) {
            throw new IllegalArgumentException("No messages found.");
        }
        return messages;
    }

}