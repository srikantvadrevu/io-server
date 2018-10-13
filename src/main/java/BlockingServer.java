import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Blocking IO Server
 */
public class BlockingServer {

    public static void main(String[] args) throws IOException {
        int portNumber = 5000;
        ServerSocket serverSocket = new ServerSocket(portNumber);
        boolean listening = true;
        while (listening) {
            final Socket clientSocket = serverSocket.accept();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));

                        String request, response;
                        while ((request = in.readLine()) != null) {
                            response = request;
                            out.println(response.toUpperCase());
                            if (request.toLowerCase().contains("done")) {
                                break;
                            }
                        }
                        clientSocket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
        }
        serverSocket.close();
    }
}
