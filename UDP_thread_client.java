
import java.io.*;
import java.net.*;

public class UDP_thread_client {

    public static void main(String[] args) throws IOException {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverIP = InetAddress.getByName("127.0.0.1");

        new Thread(() -> {
            try {
                while (true) {
                    byte[] receiveBuffer = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    clientSocket.receive(receivePacket);

                    String serverMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("\nServer: " + serverMessage);
                }
            } catch (SocketException e) {

                System.out.println("Connection closed. Exiting receiver thread...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String clientMessage = clientInput.readLine();
            byte[] sendBuffer = clientMessage.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverIP, 9876);
            clientSocket.send(sendPacket);

            if (clientMessage.equalsIgnoreCase("bye")) {
                System.out.println("Disconnected from server.");
                clientSocket.close();
                System.exit(0);
            }
        }
    }
}
