public class Main {
    public static void main(String[] args) {
        User srikar = new User("sriakr");
        User santosh = new User("santosh");
        User vihaan = new User("vihaan");

        sriakr.sendMessage("hello", "santosh");
        satya.sendMessage("Hi", "sriakr");
        vihaan.sendMessage("hello!", "sriakr");

        System.out.println(userA.getDecryptedMessage(userB.getEncryptedMessage()));
        System.out.println(userB.getDecryptedMessage(userA.getEncryptedMessage()));
        System.out.println(userA.getDecryptedMessage(userC.getEncryptedMessage()));
    }
}