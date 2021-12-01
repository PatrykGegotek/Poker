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
    boolean isAllIn;

    Player(String name) {
        this.name = name;
        cards = new Vector<>(5);
        moneyLeft = 500;
        moneyOnTable = 0;
        isAllIn = false;
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

    public String showCards() {
        StringBuilder builder = new StringBuilder();
        builder.append("Your cards, ").append(name).append(":\n");
        int i = 1;
        for (Card card: cards) {
            builder.append(i++).append(". ").append(card).append("\n");
        }
        return builder.toString();
    }

    public void putMoneyOnTable(int money) {
        this.moneyLeft -= money;
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
