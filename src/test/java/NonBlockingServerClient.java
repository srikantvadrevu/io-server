import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NonBlockingServerClient {
    public void startClient() throws IOException, InterruptedException {
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 9093);
        SocketChannel client = SocketChannel.open(hostAddress);

        String threadName = Thread.currentThread().getName();

        String[] messages = {
                threadName + ": message1",
                threadName + ": message2",
                threadName + ": message3"
        };

        for (String message : messages) {
            ByteBuffer buffer = ByteBuffer.allocate(74);
            buffer.put(message.getBytes());
            buffer.flip();
            client.write(buffer);
            System.out.println(message);
            buffer.clear();
            Thread.sleep(5000);
        }
        client.close();
    }

    public static void main(String[] args) {
        Runnable client = new Runnable() {
            public void run() {
                try {
                    new NonBlockingServerClient().startClient();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        new Thread(client, "client-A").start();
        new Thread(client, "client-B").start();
    }
}
