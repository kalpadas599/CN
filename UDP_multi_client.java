import java.io.*;
import java.net.*;


public class UDP_multi_client {

    
    public static void main(String[] args) throws SocketException, IOException {
        
      BufferedReader clientRead =new BufferedReader(new InputStreamReader(System.in));

      InetAddress IP = InetAddress.getByName("127.0.0.1");

      DatagramSocket clientSocket = new DatagramSocket();

      while(true)    //true
      {
      byte[] sendbuffer = new byte[1024];
      byte[] receivebuffer = new byte[1024];
      
      System.out.print("\nClient: ");
      String clientData = clientRead.readLine();
      
      sendbuffer = clientData.getBytes();  

      DatagramPacket sendPacket =new DatagramPacket(sendbuffer, sendbuffer.length, IP, 9876);
      
      clientSocket.send(sendPacket);

      if(clientData.equalsIgnoreCase("bye"))
      {
          System.out.println("Connection ended by client");
          break;
      }
      
      DatagramPacket receivePacket =new DatagramPacket(receivebuffer, receivebuffer.length);
      
      clientSocket.receive(receivePacket);
      
      String serverData = new String(receivePacket.getData());
      
      System.out.print("\nServer: " + serverData);
      
      }
      clientSocket.close();
    }
    
}
