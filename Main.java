public class Main {
    public static void main(String[] args) {
        User userA = new User(1024);
        User userB = new User(2048);
        User userC = new User(1025);
            
        userA.sendMessage("hello", userB);
        userB.sendMessage("Hi", userA);
        userC.sendMessage("Hey", userA);

        System.out.println(userA.getDecryptedMessage(userB.getEncryptedMessage()));
        System.out.println(userB.getDecryptedMessage(userA.getEncryptedMessage()));
        System.out.println(userA.getDecryptedMessage(userC.getEncryptedMessage()));
    }
}