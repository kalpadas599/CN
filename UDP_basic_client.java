import java.net.*;
import java.io.*;

class Udpclient
{
public static void main(String args[]) throws Exception
{
DatagramSocket ds=new DatagramSocket();

byte[] b="This is udp client".getBytes();
int port=2000;
InetAddress ip=InetAddress.getByName("localhost");

DatagramPacket dp=new DatagramPacket(b,b.length,ip,port);
ds.send(dp);
}
}
