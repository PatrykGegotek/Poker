package lab5.patryk;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Player {

    String name;
    List<Card> cards;
    int moneyLeft;
    int moneyOnTable;

    Player(String name) {
        this.name = name;
        cards = new Vector<>(5);
        moneyLeft = 500;
        moneyOnTable = 0;
    }

    public void receiveCard(Card card) {
        cards.add(card);
    }

    public void sortCards() {
        Collections.sort(this.cards, new SortBySuit());
        Collections.sort(this.cards, new SortByRank());
    }

    public void showYourCards() {
        System.out.printf("Your cards, %s:\n", name);
        int i = 1;
        for (Card card: cards) {
            System.out.println(Integer.toString(i++) + ". " + card);
        }
    }

    public int putMoneyOnTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%s, how much money do you want to put on the table\n", name);
        int propMoney = scanner.nextInt();
        this.moneyLeft -= propMoney;
        System.out.printf("You have left %d$\n", moneyLeft);
        return propMoney;
    }

    public String getName() {
        return name;
    }

    public int getMoneyOnTable() {
        return moneyOnTable;
    }

    public void setMoneyOnTable(int moneyOnTable) {
        this.moneyOnTable = moneyOnTable;
    }

    public int getMoneyLeft() {
        return moneyLeft;
    }

    public void setMoneyLeft(int moneyLeft) {
        this.moneyLeft = moneyLeft;
    }
}
