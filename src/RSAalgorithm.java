public class RSAalgorithm {
    public static void main(String[] args) {
        User userA = new User(1024);
        User userB = new User(2048);
        User userC = new User(1025);
            
        userA.sendMessage("hello", userB);
        userB.sendMessage("Hi", userA);
        userC.sendMessage("Hey", userA);

//        System.out.println(userA.getMessage());
//        System.out.println(userB.getMessage());

//        System.out.println(userA.getEncryptedMessage());
//        System.out.println();
//        System.out.println(userB.getEncryptedMessage());

        System.out.println(userA.getDecryptedMessage(userB.getEncryptedMessage()));
        System.out.println(userB.getDecryptedMessage(userA.getEncryptedMessage()));
        System.out.println(userA.getDecryptedMessage(userA.getEncryptedMessage()));
    }
}