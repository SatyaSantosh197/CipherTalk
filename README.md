Here is a professional and concise README file for **CipherTalk**:

---

# CipherTalk

CipherTalk is a secure chat application built with RSA encryption, enabling private and encrypted communication between users. This project demonstrates proficiency in Java programming, cryptographic principles, and client-server architecture.

## Features

- **User Registration:** Users can register with unique usernames to establish their identity.
- **Secure Messaging:** Messages are encrypted using RSA public keys and can only be decrypted by the recipient using their private key.
- **Message Fetching:** Users can retrieve and decrypt messages sent to them through a secure mailbox system.
- **Server-Client Communication:** Implements a multithreaded server to handle multiple clients simultaneously.
- **Data Integrity:** Prevents unauthorized access by ensuring only registered users can send or fetch messages.

## Technology Stack

- **Programming Language:** Java
- **Encryption Technique:** RSA (Public/Private Key Cryptography)
- **Networking:** Socket Programming (TCP)
- **Data Storage:** In-memory storage for users and messages

## How It Works

1. **Registration:**
   - Users register with the command `REGISTER <username>`.
   - A unique RSA key pair is generated for each user.
   - The server stores the public key for secure communication.

2. **Sending Messages:**
   - Command: `SEND <receiver> <message>`
   - The sender encrypts the message using the receiver's public key.
   - The server stores the encrypted message in the recipient’s mailbox.

3. **Fetching Messages:**
   - Command: `FETCH`
   - Users retrieve all encrypted messages sent to them.
   - Messages are decrypted on the client-side using the user’s private key.

4. **Exiting:**
   - Command: `EXIT`
   - Gracefully disconnects the client from the server.

## Installation and Usage

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/SatySantosh197/CipherTalk.git
   cd CipherTalk
   ```

2. **Compile the Project:**
   ```bash
   javac *.java
   ```

3. **Run the Server:**
   ```bash
   java Server
   ```

4. **Run the Client:**
   ```bash
   java Client
   ```

5. **Use Commands:**
   - `REGISTER <username>`
   - `SEND <receiver> <message>`
   - `FETCH`
   - `EXIT`


## Using CipherTalk with Telnet

1. **Start the Server:**
   - Run the server on your machine:
     ```bash
     java Server
     ```

2. **Connect to the Server Using Telnet:**
   - Open a terminal and connect to the server using Telnet:
     ```bash
     telnet <server-ip> <server-port>
     ```
   - Replace `<server-ip>` with the server's IP address (e.g., `localhost` for local testing) and `<server-port>` with the port number where the server is running (e.g., `12345`).

3. **Interact with the Server:**
   - Once connected, use the following commands:
     - **Register a username:**
       ```plaintext
       REGISTER <username>
       ```
     - **Send a message:**
       ```plaintext
       SEND <receiver> <message>
       ```
     - **Fetch messages:**
       ```plaintext
       FETCH
       ```
     - **Exit the session:**
       ```plaintext
       EXIT
       ```

4. **Example Interaction:**
   - Register as `Alice`:
     ```plaintext
     REGISTER Alice
     ```
   - Send a message to `Bob`:
     ```plaintext
     SEND Bob Hello, Bob!
     ```
   - Fetch messages:
     ```plaintext
     FETCH
     ```
   - Exit the session:
     ```plaintext
     EXIT
     ```

5. **Disconnecting:**
   - Close the Telnet connection by typing `CTRL+]`, then enter `quit` to exit Telnet.

### Note:
Ensure Telnet is installed on your system. For example:
- On **Windows**, enable Telnet from "Turn Windows Features On or Off."
- On **Linux/macOS**, Telnet is usually pre-installed, or you can install it using your package manager.

## Project Structure

- **Server.java:** Manages client connections and routes commands.
- **ClientHandler.java:** Handles individual client communication.
- **User.java:** Represents a user, managing their keys and messages.
- **RSA.java:** Implements RSA encryption and decryption logic.
- **CertificationAuthority.java:** Stores and validates user public keys.
- **MailBox.java:** Handles storing and retrieving encrypted messages.

## Key Highlights

- **Security:** Implements end-to-end encryption using RSA.
- **Multithreading:** Allows multiple clients to connect simultaneously.
- **Scalability:** Designed to add additional features like user authentication and persistent storage easily.

## Future Improvements

- **Authentication:** Add password-based authentication for users.
- **Database Integration:** Replace in-memory storage with a database.
- **User Interface:** Develop a graphical user interface (GUI) for better user experience.
- **Encryption Algorithms:** Support additional encryption algorithms like AES for hybrid encryption.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---
