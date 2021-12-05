package lab5.patryk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

//public class TestServer {
//
//    @Before
//    public void runServer() {
//
//        Thread runServer = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Server server = new Server();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        runServer.start();
//    }
//
//    public void runClient() {
//
//        final int SERVER_PORT = 1234;
//        boolean exit = false;
//
//        Thread runClient = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    InetAddress ip = InetAddress.getByName("localhost");
//                    Socket s = new Socket(ip, SERVER_PORT);
//                    TimeUnit.SECONDS.sleep(5);
//                    s.close();
//                } catch (IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//    }
//
//    @Test
//    public void provideNumberOfPlayers() throws IOException {
//        String input = "2";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//
//        runServer();
//        runClient();
//        runClient();
//        Assert.assertEquals(2, server.amount);
//

//    }
//
//}
