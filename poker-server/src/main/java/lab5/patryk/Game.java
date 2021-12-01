package lab5.patryk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Game {
    private Deck deck;
    private List<Player> players;
    private List<Player> resigned;
    private int lastRaise;
    private int moneyOnTable;
    private Vector<Server.Client> clients;
    private String message;


    Game(Vector<Server.Client> clients) {
        players = new ArrayList<>(4);
        resigned = new ArrayList<>(4);
        moneyOnTable = 0;
        deck = new Deck();
        this.clients = clients;
        System.out.println("New game begins");
    }

    // Cards handling:

    public void showCards() {
        for (Player player: players)
        {
            player.showYourCards();
        }
        for (Player player: resigned)
        {
            player.showYourCards();
        }
    }

    public void putMoneyOnTable(int money) {
        moneyOnTable += money;
    }

    // Player handling:

    public void addPlayer(Player player) {
        players.add(player);
        System.out.printf("%s added to the game!\n", player.getName());
    }

    public void givePlayersCars() {
        for (int i = 0; i < 5; i++) {
            for (Player player: players) {
                player.receiveCard(deck.getCard());
            }
        }
        for (Player player: players) {
            player.sortCards();
        }

    }

    // Game phases:

    public void startGame() throws IOException {
        for (Server.Client client: clients) {
            client.getOut().writeUTF("New game begins!");
        }
    }

    public void getNames() throws IOException {
        for(Server.Client client : clients) {
            client.getOut().writeUTF("Provide your name: ");
            message = client.getIn().readUTF();
            client.setPlayer(new Player(message));
            this.addPlayer(client.getPlayer());
        }
    }

    public void showPlayersTheirCards() throws IOException {
        for (Server.Client client: clients) {
            client.getOut().writeUTF(client.getPlayer().showCards());
        }
    }

//    public void start() {
//        for (Player player: players) {
//            int propMoney = player.putMoneyOnTable();
//            moneyOnTable += propMoney;
//            System.out.printf("%s puts %d$\n\n", player.getName(), propMoney);
//        }
//    }

//TODO:
//    Until the first bet is made each player in turn may "check",
//    which is to not place a bet, or "open", which is to make the first bet.
//    After the first bet each player may "fold", which is to drop out of the hand
//    losing any bets they have already made; "call", which is to match the highest
//    bet so far made; or "raise", which is to increase the previous high bet.

    public void ante() throws IOException{
        for (Server.Client client: clients) {
            int money = client.getPlayer().moneyLeft;
            client.getOut().writeUTF("Every player must put 100$ on the table\nYou start with " +
                     money + "$. After that you'll have " + (money - 100) + "$ left");
            client.getPlayer().putMoneyOnTable(100);
            this.putMoneyOnTable(100);
        }
    }

    public void betting() throws IOException {
        for (Server.Client client: clients) {
            client.getOut().writeUTF("It's time to bet.\nDo you want to:\n" +
                    "Raise(1) \nCheck(2) \nFold(3) \n? \n> ");
//            do {
//                message = client.getIn().readUTF();
//                switch (message) {
//                    case 1: client.getPlayer().
//                }
//
//            } while


        }
    }

//    public void playersTurn() {
//        Scanner scanner = new Scanner(System.in);
//        for (Player player: players) {
//
//            player.showYourCards();
//            System.out.printf("%s, do you want to play[1], resign[2] or exchange your cards[3]??\n", player.getName());
//            int decision = scanner.nextInt();
//
//            while(decision != 1 && decision != 2) {
//                System.out.println("Wrong number! Try again");
//                decision = scanner.nextInt();
//            }
//
//            if (decision == 1) {
//                System.out.printf("%s stays in the game!\n", player.getName());
//                int propMoney = player.putMoneyOnTable();
//                moneyOnTable += propMoney;
//                System.out.printf("%s puts %d$\n\n", player.getName(), propMoney);
//            } else {
//                System.out.printf("%s leaves the game\n", player.getName());
//                resigned.add(player);
//            }
//            System.out.println();
//        }
//
//        for (Player player: resigned) {
//            players.remove(player);
//        }
//    }



}
