import java.net.*;
import java.io.*;
import java.util.*;

public class UDPThreadServer {
    static int clientCount = 0;
    static Map<String, InetSocketAddress> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        System.out.println("Server started...");
      
        new Thread(() -> {
            try {
                while (true) {
                    byte[] receiveBuffer = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    serverSocket.receive(receivePacket);

                    InetAddress clientIP = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    String clientKey = clientIP.toString() + ":" + clientPort;

                    if (!clientMap.containsKey(clientKey)) {
                        clientCount++;
                        System.out.println("Client " + clientCount + " joined from " + clientKey);
                        clientMap.put(clientKey, new InetSocketAddress(clientIP, clientPort));
                    }

                    String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println(clientKey + ": " + clientMessage);

                    if (clientMessage.equalsIgnoreCase("bye")) {
                        clientMap.remove(clientKey);
                        System.out.println("Client left: " + clientKey);
                        if (clientMap.isEmpty()) {
                            System.out.println("No clients left. Server shutting down...");
                            serverSocket.close();
                            System.exit(0);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String serverMessage = serverInput.readLine();
            byte[] sendBuffer = serverMessage.getBytes();

            for (InetSocketAddress clientAddr : clientMap.values()) {
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddr.getAddress(), clientAddr.getPort());
                serverSocket.send(sendPacket);
            }
        }
    }
}
