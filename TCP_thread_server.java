import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class MultiClientServer {
    private static int clientIdCounter = 1;
    private static final Map<Integer, ClientHandler> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        int port = 12345;
        System.out.println("Server started on port " + port);

        
        new Thread(() -> {
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String serverMessage;
            try {
                while ((serverMessage = consoleInput.readLine()) != null) {
                    if (serverMessage.equalsIgnoreCase("bye")) {
                        broadcast("Server is shutting down. Goodbye!");
                        System.exit(0);
                    }
                    broadcast("Server: " + serverMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                int clientId = clientIdCounter++;
                ClientHandler handler = new ClientHandler(clientSocket, clientId);
                clients.put(clientId, handler);
                handler.start();
                System.out.println("Client " + clientId + " connected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message) {
        for (ClientHandler client : clients.values()) {
            client.sendMessage(message);
        }
    }

    public static void removeClient(int clientId) {
        clients.remove(clientId);
        System.out.println("Client " + clientId + " disconnected.");
    }

    static class ClientHandler extends Thread {
        private final Socket socket;
        private final int clientId;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket, int clientId) {
            this.socket = socket;
            this.clientId = clientId;
        }

        public void sendMessage(String msg) {
            if (out != null) {
                out.println(msg);
            }
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                sendMessage("Welcome! You are Client " + clientId);

                String line;
                while ((line = in.readLine()) != null) {
                    if (line.equalsIgnoreCase("bye")) {
                        System.out.println("Client " + clientId + " has left the chat.");
                        break;
                    }
                    System.out.println("Client " + clientId + ": " + line);
                }
            } catch (IOException e) {
                System.out.println("Error with client " + clientId);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MultiClientServer.removeClient(clientId);
            }
        }
    }
}
