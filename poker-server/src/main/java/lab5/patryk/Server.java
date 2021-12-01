package lab5.patryk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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

        public String getClientName() {
            return clientName;
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

    Server() throws IOException {
        ss = new ServerSocket(1234);
        listenForCLients();
    }

    void listenForCLients() throws IOException {
        int i=1;
        while (i <= 4) {
            Socket socket = ss.accept();
            System.out.println("New client!");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());


            Client client = new Client("Player" + i++, out, in);

            clients.add(client);
            for (Client cl: clients) {
                cl.out.writeUTF("Waiting for " + (4 - clients.size()) + " more players" );
            }
        }
    }

    void closeConnection() throws IOException {
        for (Client client: clients) {
            client.out.writeUTF("Exit");
            client.in.close();
            client.out.close();
        }
        ss.close();
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        String message = "";
        Server server = new Server();
        Game game = new Game(clients);

        game.startGame();
        game.getNames();
        game.givePlayersCars();
        game.showPlayersTheirCards();
        game.ante();



        server.closeConnection();
    }
}

