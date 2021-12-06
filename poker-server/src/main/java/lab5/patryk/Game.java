package lab5.patryk;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {
    private Deck deck;
    private List<Player> players;
    private List<Player> resigned;
    private int maxMoneyPutByPlayers;
    private int moneyOnTable;
    private List<Server.Client> clients;
    private String message;

    Game(List<Server.Client> clients) {
        players = new ArrayList<>(4);
        resigned = new ArrayList<>(4);
        moneyOnTable = 0;
        maxMoneyPutByPlayers = 0;
        deck = new Deck();
        this.clients = clients;
        System.out.println("New game begins");
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


    public void ante() throws IOException{
        for (Server.Client client: clients) {
            int money = client.getPlayer().moneyLeft;
            client.getOut().writeUTF("Every player must put 100$ on the table\nYou start with " +
                     money + "$. After that you'll have " + (money - 100) + "$ left");
            client.getPlayer().moneyOnTable += 100;
            client.getPlayer().moneyLeft -= 100;
            moneyOnTable += 100;
        }
        maxMoneyPutByPlayers = 100;
    }

    public void betting() throws IOException {
        for (Server.Client client : clients) {
            if (client.getPlayer().isAllIn) continue;
            client.getOut().writeUTF("Do you want to open bet (y/n)? ");
            System.out.println("hell yeaaahhh");
            message = client.getIn().readUTF();
            System.out.println(message);

            if(message.equals("y")) {
                Server.writeToAll(client.getPlayersName() + " opens bet!\n");
                startBet(client);
                askOthersToBet(client);
                return;
            } else {
                Server.writeToAll(client.getPlayersName() + " does not open bet\n");
            }
        }
        Server.writeToAll("None of the players raised the bet\n");
    }

    public void startBet(Server.Client raisee) throws IOException {
        Player currPlayer = raisee.getPlayer();
        message = raisee.getPlayersName() + ", you have " + currPlayer.moneyLeft +
                "$ left and " + currPlayer.moneyOnTable + "$ put on the table by you.\n" +
                "In total there is " + moneyOnTable + "$ on the table\n" +
                "How many dollars do you wish to raise the bet?\n" + "You need to have at least " +
                maxMoneyPutByPlayers + "$ on the table or go all in!";
        raisee.getOut().writeUTF(message);

        do {
            boolean doesNotPass = false;
            int amount = 0;

            do {
                try {
                    doesNotPass = true;
                    message = raisee.getIn().readUTF();
                    amount = Integer.parseInt(message);
                } catch (NumberFormatException e) {
                    raisee.getOut().writeUTF("Wrong input! Try one more time!\n");
                    doesNotPass = false;
                }
            } while (!doesNotPass);

            if (amount <= 0 || amount > currPlayer.moneyLeft) {
                raisee.getOut().writeUTF("Wrong number! Try one more time!\n");
            } else if (amount == currPlayer.moneyLeft) {

                Server.writeToAll(raisee.getPlayersName() + " goes all in!\n");
                currPlayer.moneyOnTable += currPlayer.moneyLeft;
                currPlayer.moneyLeft = 0;
                moneyOnTable += amount;
                maxMoneyPutByPlayers = currPlayer.moneyOnTable;
                break;
            } else {
                Server.writeToAll(raisee.getPlayersName() + " puts " + amount + "$ on the table\n");
                currPlayer.moneyOnTable += amount;
                currPlayer.moneyLeft -= amount;
                moneyOnTable += amount;
                maxMoneyPutByPlayers = currPlayer.moneyOnTable;
                break;
            }
        } while (true);
    }

    public boolean checkIfHasToGoAllIn(Server.Client client) throws  IOException {
        Player currPlayer = client.getPlayer();
        if(maxMoneyPutByPlayers > currPlayer.moneyOnTable + currPlayer.moneyLeft) {
            message = "You have to go all-in (1) or resign (0)!\n >";
            client.getOut().writeUTF(message);
            message = client.getIn().readUTF();

            if (message.equals("1")) {
                Server.writeToAll(client.getPlayersName() + " goes all in!\n");
                currPlayer.moneyOnTable += currPlayer.moneyLeft;
                moneyOnTable += currPlayer.moneyLeft;
                currPlayer.moneyLeft = 0;
                currPlayer.isAllIn = true;
            }
            else {
                Server.writeToAll(client.getPlayersName() + " resigns\n");
                Server.resigned.add(client);
                this.resigned.add(client.getPlayer());
                this.players.remove(client.getPlayer());
            }
            return true;
        }
        return false;
    }

    private int getAmountFromPlayer(Server.Client client) throws IOException {
        boolean doesNotPass = false;
        int amount = 0;

        do {
            try {
                doesNotPass = true;
                message = client.getIn().readUTF();
                amount = Integer.parseInt(message);
            } catch (NumberFormatException e) {
                client.getOut().writeUTF("Wrong input! Try one more time!\n");
                doesNotPass = false;
            }
        } while (!doesNotPass);
        return amount;
    }

    public void askOthersToBet(Server.Client raisee) throws IOException {
        for (Server.Client client : clients) {
            // DOES NOT HAVE ENOUGH MONEY AND HAS TO GO ALL IN, IS THE RAISEE OR IS ALREADY ALL IN
            if (client.equals(raisee) || client.getPlayer().isAllIn ||
                    checkIfHasToGoAllIn(client)) continue;
            Player currPlayer = client.getPlayer();

            message = client.getPlayersName() + ", you have " + currPlayer.moneyLeft +
                    "$ left and " + currPlayer.moneyOnTable + "$ on the table.\n" +
                    "How many dollars do you wish to raise the bet?\n" + "You need to have at least " +
                    maxMoneyPutByPlayers + "$ on the table or go all in!\n" +
                    "If want to resign write '0'\n";
            client.getOut().writeUTF(message);

            do {
                int amount = getAmountFromPlayer(client);

                // RESIGNS
                if(amount == 0) {
                    Server.writeToAll(client.getPlayersName() + " resigns!\n");
                    Server.resigned.add(client);
                    this.resigned.add(client.getPlayer());
                    this.players.remove(client.getPlayer());
                    break;
                }
                // PUTS MORE THAN IS ABLE TO OR WRONG AMOUNT
                else if (amount > currPlayer.moneyLeft || amount < 0) {
                    message = "Wrong value! Try one more time!\n";
                    client.getOut().writeUTF(message);
                }
                // EVENS OUT
                else if (amount + currPlayer.moneyOnTable == maxMoneyPutByPlayers) {
                    Server.writeToAll(client.getPlayersName() + " evens out the bet");
                    currPlayer.moneyOnTable += amount;
                    currPlayer.moneyLeft -= amount;
                    moneyOnTable += amount;
                    break;
                }
                // PUTS NOT ENOUGH MONEY
                else if (maxMoneyPutByPlayers > amount + currPlayer.moneyOnTable) {
                    message = "You have put not enough money (you have to put at least " +
                            ((maxMoneyPutByPlayers - currPlayer.moneyOnTable) - amount) +
                            "$ more)!\n";
                    client.getOut().writeUTF(message);
                }

                // PUTS MORE MONEY - START THE BET AGAIN
                else if (amount + currPlayer.moneyOnTable >= maxMoneyPutByPlayers )
                {
                    Server.writeToAll(client.getPlayersName() + " puts more to pool, bet begins anew!\n");
                    currPlayer.moneyOnTable += amount;
                    currPlayer.moneyLeft -= amount;
                    if (currPlayer.moneyLeft == 0) {
                        Server.writeToAll(client.getPlayersName() + " goes all in!\n");
                        currPlayer.isAllIn = true;
                    }
                    maxMoneyPutByPlayers = currPlayer.moneyOnTable;
                    moneyOnTable += amount;
                    askOthersToBet(client);
                    return;
                }
            } while (true);
        }
        for (Server.Client client: Server.resigned) {
            clients.remove(client);
        }
    }

    public void cardsExchange() throws IOException {
        List<Integer> cardsToExchange = new ArrayList<>();
        showPlayersTheirCards();
        for (Server.Client client: clients) {
            client.getOut().writeUTF("Choose which cards you'd like to exchange (separate them by a colon)\n");
            message = client.getIn().readUTF();
            for (int i = 0; i < message.length(); i++) {
                if(Character.isDigit(message.charAt(i))) {
                    cardsToExchange.add(Integer.parseInt(String.valueOf(message.charAt(i))));
                }
            }
            Collections.sort(cardsToExchange, Collections.<Integer>reverseOrder());
            for(Integer number: cardsToExchange) {
                client.getPlayer().removeCard(number - 1);
            }
            for (int i = 0; i < cardsToExchange.size(); i++) {
                client.getPlayer().receiveCard(deck.getCard());
            }
            client.getPlayer().sortCards();
            cardsToExchange.clear();
        }
        Server.writeToAll("Your current cards:\n");
        showPlayersTheirCards();
    }

    void checkWhoWins() throws IOException, InterruptedException {
        for (Player player: players) {
            TimeUnit.SECONDS.sleep(1);
            player.checkCardsCombinations();
            Server.writeToAll(player.name + "'s cards:");
            Server.writeToAll(player.showCards());
            Server.writeToAll(player.name + " has " + player.score.details + "\n");
        }
        Collections.sort(players, Collections.<Player>reverseOrder(new SortByScore()));
        Player winner = players.get(0);
        Server.writeToAll(winner.name + " wins with " + winner.score.details);
        winner.moneyOnTable = 0;
        winner.moneyLeft += moneyOnTable;
        moneyOnTable = 0;

    }

    void restart() {
        players.addAll(resigned);
        resigned.clear();
        clients.addAll(Server.resigned);
        Server.resigned.clear();
        for (Player player: players) {
            player.score = null;
            player.moneyOnTable = 0;
            player.cards.clear();
            player.isAllIn = false;
        }
        deck = new Deck();
    }


}



