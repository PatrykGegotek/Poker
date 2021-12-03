package lab5.patryk;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client
{
    final static int ServerPort = 1234;
    static boolean exit = false;

    public static void main(String[] args) throws UnknownHostException, IOException {
        InetAddress ip = InetAddress.getByName("localhost");
        final Socket s = new Socket(ip, ServerPort);
        final Scanner scanner = new Scanner(System.in);
        final DataInputStream in = new DataInputStream(s.getInputStream());
        final DataOutputStream out = new DataOutputStream(s.getOutputStream());

        Thread sendMessage = new Thread( new SendMessage(s, out, scanner));
        Thread readMessage = new Thread( new ReadMessage(s, in));

        sendMessage.start();
        readMessage.start();

    }
}

class ReadMessage implements Runnable {
    Socket s;
    DataInputStream in;

    ReadMessage(Socket s, DataInputStream in) {
        this.s = s;
        this.in = in;
    }

    @Override
    public void run() {

        while (!Client.exit) {
            try {
                String message = in.readUTF();
                if (message.equals("Exit")) {
                    Client.exit= true;
                    break;
                }
                System.out.println(message);
            } catch (IOException e) {
                break;
            }
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SendMessage implements Runnable {
    Socket s;
    DataOutputStream out;
    Scanner scanner;

    SendMessage(Socket s, DataOutputStream out, Scanner scanner) {
        this.s = s;
        this.out = out;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        while (!Client.exit) {
            String message = scanner.nextLine();
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                break;
            }
        }
    }
}



