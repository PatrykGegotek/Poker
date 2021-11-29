package lab5.patryk;
import java.net.*;
import java.io.*;

public class Server
{

    private Socket socket;
    private ServerSocket server;
    private DataInputStream input;
    private DataOutputStream output;
    private BufferedReader bufferedReader;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Server created");
            System.out.println("Waiting for client...");

            socket = server.accept();
            System.out.println("Client accepted");

            try {
                communicate();
                close();
            } catch (IOException u) {
                System.out.println(u);
            }


        } catch (IOException u){
            System.out.println(u);
        }
    }

    void communicate() {
        try
        {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            String messageFrom = "";
            String messageTo = "";

            while (!messageFrom.equals("Exit")) {
                try {
                    System.out.println("Message from client:");
                    messageFrom = input.readUTF();
                    System.out.println(messageFrom);
                    System.out.println("Message to client:");
                    messageTo = bufferedReader.readLine();
                    output.writeUTF(messageTo);
                    output.flush();
                } catch (IOException u) {
                    System.out.println(u);
                    break;
                }
            }
        } catch (IOException u) {
            System.out.println(u);
        }
    }

    void close() throws IOException {
        try {
            System.out.println("CLosing connection");
            output.close();
            bufferedReader.close();
            socket.close();
            input.close();
        } catch (IOException u) {
            System.out.println(u);
        }

    }

    public static void main( String[] args )
    {
//        Scanner sc = new Scanner(System.in);
//        sc.nextInt();
        Server server = new Server(4444);
    }
}
