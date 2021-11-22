package lab5.patryk;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Game {
    List<Card> cards;
    List<Player> players;
    List<Player> resigned;
    int moneyOnTable;

    Game() {
        cards = new Vector<>(5);
        players = new ArrayList<>(4);
        resigned = new ArrayList<>(4);
        moneyOnTable = 0;
        System.out.println("New game begins");
    }

    // Cards handling:

    public void addCard(Card card) {
        cards.add(card);
    }

    public void showCards() {
        System.out.println("Cards on the table:");
        System.out.println(cards);
        System.out.println();
    }

    // Player handling:

    public void addPlayer(Player player) {
        players.add(player);
        System.out.printf("%s added to the game!\n", player.getName());
    }

    public void givePlayersCars(Deck deck) {
        for (int i = 0; i < 2; i++) {
            for (Player player: players) {
                player.receiveCard(deck.getCard());
            }
        }
    }

    public void showPlayersCards() {
        for (Player player: players) {
            player.showYourCards();
        }
    }

    // Game phases:

    public void start() {
        for (Player player: players) {
            int propMoney = player.putMoneyOnTable();
            moneyOnTable += propMoney;
            System.out.printf("%s puts %d$\n\n", player.getName(), propMoney);
        }
    }

    public void flop(Deck deck) {
        for (int i = 0; i < 3; i++) {
            addCard(deck.getCard());
        }
        System.out.println("FLOP\n");
        showCards();
        playersTurn();
    }

    public void turn(Deck deck) {
        addCard(deck.getCard());
        System.out.println("TURN\n");
        showCards();
        playersTurn();
    }

    public void river(Deck deck) {
        addCard(deck.getCard());
        System.out.println("RIVER\n");
        showCards();
        playersTurn();
    }

    public void playersTurn() {
        Scanner scanner = new Scanner(System.in);
        for (Player player: players) {

            player.showYourCards();
            System.out.printf("%s, do you want to play[1] or resign[2]?\n", player.getName());
            int decision = scanner.nextInt();

            while(decision != 1 && decision != 2) {
                System.out.println("Wrong number! Try again");
                decision = scanner.nextInt();
            }

            if (decision == 1) {
                System.out.printf("%s stays in the game!\n", player.getName());
                int propMoney = player.putMoneyOnTable();
                moneyOnTable += propMoney;
                System.out.printf("%s puts %d$\n\n", player.getName(), propMoney);
            } else {
                System.out.printf("%s leaves the game\n", player.getName());
                resigned.add(player);
            }
            System.out.println();
        }

        for (Player player: resigned) {
            players.remove(player);
        }
    }
}
