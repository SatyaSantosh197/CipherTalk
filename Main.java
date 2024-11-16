import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        Map<String, User> users = new HashMap<>();
        //Multi-threaded chat applicaton

        // Goal: Multi-threaded server
        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Create a new user");
            System.out.println("2. List all users");
            System.out.println("3. Send a message");
            System.out.println("4. Get received messages");
            System.out.println("5. Exit");

            int option = scan.nextInt();
            scan.nextLine();

            System.out.println();
            switch (option) {
                case 1: // create a new user with username
                    System.out.print("Enter username: ");
                    String username = scan.nextLine();
                    if (users.containsKey(username.toLowerCase())) {
                        System.out.println("Username already taken!");
                    } else {
                        User newUser = new User(username);
                        users.put(username.toLowerCase(), newUser);
                        System.out.println("User created successfully!\n\n");
                    }
                    break;

                case 2: // make a list of all users by their username

                    System.out.println("\nRegistered users:");
                    for (String user : users.keySet()) {
                        System.out.println(user);
                    }
                    System.out.println();
                    System.out.println();

                    break;

                case 3: // to send a message
                    System.out.print("Enter your username: ");
                    String senderUserName = scan.nextLine().toLowerCase();
                    System.out.print("Enter receiver's username: ");
                    String receiverUserName = scan.nextLine().toLowerCase();
                    System.out.print("Enter the message: ");
                    String message = scan.nextLine();

                    try {
                        User sender = users.get(senderUserName);
                        if (sender == null) {
                            throw new IllegalArgumentException("Sender not found!");
                        }
                        if (message.trim().isEmpty()) {
                            throw new IllegalArgumentException("Message cannot be empty.");
                        }
                        sender.sendMessage(message, receiverUserName);
                        System.out.println("Message sent successfully!");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;


                case 4:
                    System.out.print("Enter your username: ");
                    String receivingUsername = scan.nextLine().toLowerCase();
                    User receiverForMessages = users.get(receivingUsername);
                    if (receiverForMessages == null) {
                        System.out.println("User not found!");
                    } else {
                        receiverForMessages.getMessages();
                    }
                    System.out.println();
                    System.out.println();
                    break;

                case 5: // chhose to exit
                    System.out.println("Exiting...");
                    scan.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}