package lab5.patryk;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client
{
    final static int ServerPort = 1234;

    public static void main(String[] args) throws UnknownHostException, IOException {
        Scanner scanner = new Scanner(System.in);
        InetAddress ip = InetAddress.getByName("localhost");
        Socket s = new Socket(ip, ServerPort);

        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        String messageFrom = "";
        String messageTo;

        try {
            while(true) {
                messageFrom = in.readUTF();

                if (messageFrom.equals("Exit")) {
                    break;
                }
                else if (messageFrom.equals("Provide your name: ")) {
                    System.out.println(messageFrom);
                    TimeUnit.SECONDS.sleep(2);
                    messageTo = scanner.nextLine();
                    out.writeUTF(messageTo);
                } else
                System.out.println(messageFrom);

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if(messageFrom.equals("Exit")) s.close();

    }
}

