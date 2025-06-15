import java.io.*;
import java.net.*;


public class UDP_multi_server {

   
    public static void main(String[] args) throws SocketException, IOException {
        
         DatagramSocket serverSocket = new DatagramSocket(9876);
         boolean bye=false;

      while(true) 
        {
          byte[] receivebuffer = new byte[1024];
          byte[] sendbuffer  = new byte[1024];

          DatagramPacket recvdpkt = new DatagramPacket(receivebuffer, receivebuffer.length);
          serverSocket.receive(recvdpkt);
          
          InetAddress IP = recvdpkt.getAddress();
          int portno = recvdpkt.getPort();

          String clientdata = new String(recvdpkt.getData());

          System.out.println("\nClient : "+ clientdata);
          
          System.out.print("\nServer : ");
          
          BufferedReader serverRead = new BufferedReader(new InputStreamReader (System.in) );
          String serverdata = serverRead.readLine();
          
          sendbuffer = serverdata.getBytes();
          
          DatagramPacket sendPacket = new DatagramPacket(sendbuffer, sendbuffer.length, IP,portno);
          serverSocket.send(sendPacket); 
         
          if(serverdata.equalsIgnoreCase("bye"))
          {
              System.out.println("connection ended by server");
              break;
          }
                  
      }
        serverSocket.close();
    }
            
    }
