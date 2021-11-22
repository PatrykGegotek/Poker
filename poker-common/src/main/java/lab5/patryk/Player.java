package lab5.patryk;

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
        cards = new Vector<>(7);
        moneyLeft = 500;
        moneyOnTable = 0;
    }

    public void receiveCard(Card card) {
        cards.add(card);
    }

    public void showYourCards() {
        if (cards.get(1) != null) {
            System.out.printf("Your current cards, %s:\n", name);
            System.out.println(cards.get(0));
            System.out.println(cards.get(1));
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
