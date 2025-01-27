import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {
   private static final int portNumber = 3000;

   public static void main(String[] args) {
       System.out.println("Server is running on port " + portNumber);

       try(ServerSocket serverSocket = new ServerSocket(portNumber)) {
           while(true) {
                   Socket clientSocket = serverSocket.accept();
                   System.out.println("New Clinet connected: " + clientSocket.getInetAddress());
                   new Thread(new ClientHandler((clientSocket))).start();
           }
       } catch(IOException e) {
           System.out.println("Server Error: " + e.getMessage());
       }
   }
}