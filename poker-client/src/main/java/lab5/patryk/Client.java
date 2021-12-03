package lab5.patryk;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client
{
    static final int SERVER_PORT = 1234;
    static boolean exit = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        InetAddress ip = InetAddress.getByName("localhost");
        Socket s = new Socket(ip, SERVER_PORT);
        final Scanner scanner = new Scanner(System.in);
        final DataInputStream in = new DataInputStream(s.getInputStream());
        final DataOutputStream out = new DataOutputStream(s.getOutputStream());

        Thread sendMessage = new Thread( new SendMessage(s, out, scanner));
        Thread readMessage = new Thread( new ReadMessage(s, in));

        sendMessage.start();
        readMessage.start();

        readMessage.join();
        s.close();
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

        while (true) {
            try {
                String message = in.readUTF();
                if (message.equals("Exit")) {
                    break;
                }
                System.out.println(message);
            } catch (IOException e) {
                break;
            }
        }
        Client.exit = true;
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



