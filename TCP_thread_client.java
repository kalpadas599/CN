import java.io.*;
import java.net.*;

public class MultiClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(host, port)) {
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            Thread readThread = new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = serverInput.readLine()) != null) {
                        System.out.println(serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Server closed the connection.");
                } finally {
                    System.out.println("Client shutting down.");
                    System.exit(0); 
                }
            });

            readThread.start();

            String input;
            while ((input = userInput.readLine()) != null) {
                out.println(input);
                if (input.equalsIgnoreCase("bye")) {
                    System.out.println("You have disconnected.");
                    break;
                }
            }

            socket.close(); 
        } catch (IOException e) {
            System.out.println("Unable to connect to server.");
        }
    }
}
