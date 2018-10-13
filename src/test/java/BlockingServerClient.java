import java.io.*;
import java.net.Socket;

/**
 * Client for {@link BlockingServer}
 */
public class BlockingServerClient {

    public static void main(String[] args) throws IOException {
        Runnable testClient = new Runnable() {
            public void run() {
                try {
                    new BlockingServerClient().startClient();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(testClient, "Client 1").start();
        new Thread(testClient, "Client 2").start();
    }

    private void startClient() throws IOException {
        String hostName = "localhost";
        int portNumber = 5000;

        String threadName = Thread.currentThread().getName();
        String[] messages = {
                threadName + " > message1",
                threadName + " > message2",
                threadName + " > message3",
                threadName + " > Done"
        };

        Socket echoSocket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

        for (String message : messages) {
            BufferedReader bufferedReader = new BufferedReader(new StringReader(message));
            String userInput;
            while ((userInput = bufferedReader.readLine()) != null) {
                out.println(userInput);
                System.out.println("server response : " + in.readLine());
            }
        }
    }
}
