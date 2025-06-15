
import java.net.*;

class TCP_basic_client {

    public static void main(String arg[]) {
        try {
            Socket clisc = new Socket("localhost", 3000);
            System.out.println("*****Find Server******");
        } catch (Exception e) {
        }
    }
}
