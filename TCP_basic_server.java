
import java.net.*;

class TCP_basic_server {

    public static void main(String arg[]) {
        try {
            System.out.println("Waiting for Client.....");
            ServerSocket serv = new ServerSocket(3000);
            Socket sc = serv.accept();
        } catch (Exception e) {
        }
    }
}
