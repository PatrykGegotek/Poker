package lab5.patryk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Server {

    static class Client {
        private String clientName;
        private DataOutputStream out;
        private DataInputStream in;
        private Player player;

        public Client(String name, DataOutputStream out, DataInputStream in) {
            this.clientName = name;
            this.out = out;
            this.in = in;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }

        public String getPlayersName() {
            return player.getName();
        }

        public DataOutputStream getOut() {
            return out;
        }

        public DataInputStream getIn() {
            return in;
        }
    }



    private ServerSocket ss;
    private static Vector<Client> clients = new Vector<>();
    public static Vector<Client> resigned = new Vector<>();
    public int amount = 0;

    public Server() throws IOException {
        ss = new ServerSocket(1234);
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many players are going to play?");
        amount = scanner.nextInt();
        listenForCLients(amount);
    }

    private void listenForCLients(int amount) throws IOException {
        int i=1;
        while (i <= amount) {
            Socket socket = ss.accept();
            System.out.println("New client!");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());


            Client client = new Client("Player" + i++, out, in);

            clients.add(client);
            writeToAll("Waiting for " + (amount - clients.size()) + " more players");
        }
    }

    private void closeConnection() throws IOException {
        for (Client client: clients) {
            client.out.writeUTF("Exit");
            client.in.close();
            client.out.close();
        }
        ss.close();
    }

    public static void writeToAll(String message) throws IOException {
        for (Client client: clients) {
            client.getOut().writeUTF(message);
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new Server();
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(clients);
        String answer = "";

        game.startGame();
        game.getNames();
        do {
            game.givePlayersCars();
            Server.writeToAll("Your cards:\n");
            game.showPlayersTheirCards();
            game.ante();
            game.betting();
            game.cardsExchange();
            game.betting();
            game.checkWhoWins();
            game.restart();
            System.out.println("Start new game? (y/n): ");
            answer = scanner.nextLine();
        } while (answer.equals("y"));

        writeToAll("End of the game\n");

        server.closeConnection();
    }
}

