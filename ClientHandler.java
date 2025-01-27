import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private String username;
    private User user;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            out.println("Welcome to RSA Chat Server!");
            out.println("Commands: REGISTER <username>, SEND <receiver> <message>, FETCH <username>, EXIT");

            String input = null;
            while (true) {
                input = in.readLine(); // Read input from the client
                System.out.println();

                if (input == null) {
                    // Handle client disconnection gracefully
                    out.println("Client disconnected.");
                    break;
                }

                input = input.trim(); // Remove extra spaces

                if (input.equalsIgnoreCase("EXIT")) {
                    // Exit condition
                    out.println("Goodbye!");
                    break;
                }

                if (input.isEmpty()) {
                    // Handle empty input
                    out.println("Please enter a valid command.");
                    continue;
                }

                // Process the command
                String response = processCommand(input);
                input = null;

                if (response != null && !response.isEmpty()) {
                    out.println(response);
                } else {
                    out.println("An error occurred while processing your request. Please try again.");
                }
            }

        } catch (IOException e) {
            System.err.println("Error communicating with client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Failed to close client socket: " + e.getMessage());
            }
        }
    }


    private String processCommand(String input) {
        String temp = input.trim();
        if (temp.isEmpty()) return "Please enter a valid command.";

        String[] parts = input.split(" ", 3);
        String command = parts[0].toUpperCase();

        final String res = switch (command) {
            case "REGISTER" -> registerUser(parts);
            case "FETCH" -> fetchMessages(parts);
            case "SEND" -> sendMessage(parts);
            default -> "Invalid command. Commands: REGISTER, SEND, FETCH, EXIT";
        };
        return res;
    }

    private String registerUser(String[] parts) {
        if (this.username != null) {
            return "You are already registered as " + this.username + ".";
        }

        if (parts.length < 2) return "Usage: REGISTER <username>";
        this.username = parts[1].toLowerCase();

        if (CertificationAuthority.getInstance().findIfUserNameAlreadyExists(this.username)) {
            return "Username already taken!";
        }

        try {
            user = new User(this.username); // User will be registered in CertificationAuthority and MailBox automatically
            return "User " + this.username + " registered successfully!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    private String sendMessage(String[] parts) {
        if (this.username == null) {
            return "You must register before sending messages.";
        }

        if (parts.length < 3) return "Usage: SEND <receiver> <message>";
        String receiver = parts[1].toLowerCase();
        String message = parts[2];

        if (message.trim().isEmpty()) {
            return "Message cannot be empty.";
        }

        if (!CertificationAuthority.getInstance().findIfUserNameAlreadyExists(receiver)) {
            return "Receiver not found. Please check the username.";
        }

        try {
            user.sendMessage(message, receiver);
            return "Message sent to " + receiver + ": \"" + message + "\"";
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }

    private String fetchMessages(String[] parts) {
        if (this.username == null || !CertificationAuthority.getInstance().findIfUserNameAlreadyExists(this.username)) {
            return "You must register before fetching messages.";
        }

        try {
            MessageList<BigInteger> messages = MailBox.getInstance().getMessages(this.username);

            if (messages == null || messages.getHead() == null) {
                return "No messages found.";
            }

            StringBuilder response = new StringBuilder("Messages for ").append(this.username).append(":\n");
            MessageNode<BigInteger> current = messages.getHead();

            while (current != null) {
                String decryptedMessage = user.getDecryptedMessage(current.message);
                response.append("From ").append(current.senderUserName).append(": ").append(decryptedMessage).append("\n");
                current = current.next;
            }

            return response.toString();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
