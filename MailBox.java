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
        hashMap.putIfAbsent(userName, new MessageList<>());
    }

    public void addMessage(String receiverUserName, String senderUserName, BigInteger encryptedMessage) {
        if (hashMap.containsKey(receiverUserName)) {
            hashMap.get(receiverUserName).addMessage(encryptedMessage, senderUserName);
        } else {
            MessageList<BigInteger> newMessageList = new MessageList<>();
            newMessageList.addMessage(encryptedMessage, senderUserName);
            hashMap.put(receiverUserName, newMessageList);
        }
    }
}
