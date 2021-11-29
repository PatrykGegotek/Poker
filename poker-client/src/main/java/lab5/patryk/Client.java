package lab5.patryk;

import java.io.*;
import java.net.Socket;

public class Client
{
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private BufferedReader bufferedReader;

    public Client(String address, int port) {

        try
        {
            socket = new Socket(address, port);
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connected");

            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException u) {
            System.out.println(u);
        }

        communicate();
        close();

    }

    void communicate() {
        String messageTo = "";
        String messageFrom = "";

        while(!messageTo.equals("Exit")) {
            try {
                System.out.println("Message to server:");
                messageTo = bufferedReader.readLine();
                output.writeUTF(messageTo);
                output.flush();
                System.out.println("Message from server:");
                messageFrom = input.readUTF();
                System.out.println(messageFrom);
            }
            catch (IOException u) {
                System.out.println(u);
                break;
            }
        }
    }

    void close() {
        try {
            System.out.println("Closing connection");
            bufferedReader.close();
            input.close();
            output.close();
            socket.close();
        }
        catch (IOException u) {
            System.out.println(u);
        }
    }


    public static void main( String[] args )
    {

        Client client = new Client("127.0.0.1", 4444);



//        Deck deck = new Deck();
//        Game game = new Game();
//
////        Score sc = Result.ranking(deck.getDeck());
//
//        game.addPlayer(new Player("Matthiew"));
//        game.addPlayer(new Player("Andrew"));
//        game.addPlayer(new Player("Johnny"));
//
//        game.givePlayersCars(deck);
//        game.showCards();
    }
}
