package lab5.patryk;

import java.util.*;

public class Player {

    String name;
    List<Card> cards;
    int moneyLeft;
    int moneyOnTable;
    boolean isAllIn;
    Score score;

    Player(String name) {
        this.name = name;
        cards = new Vector<>(5);
        moneyLeft = 500;
        moneyOnTable = 0;
        isAllIn = false;
        score = null;
    }

    public void receiveCard(Card card) {
        cards.add(card);
    }

    public void removeCard(int i) {
        cards.remove(i);
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
        int i = 1;
        for (Card card: cards) {
            builder.append(i++).append(". ").append(card).append("\n");
        }
        return builder.toString();
    }

    public void checkCardsCombinations() {
        Result result = new Result(cards);
        score = result.ranking();
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

class SortByScore implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        return              -(o1.score.primaryRank - o2.score.primaryRank) * 144 +
                            (o1.score.tertiaryRank - o2.score.tertiaryRank) * 12 +
                            o1.score.tertiaryRank - o2.score.tertiaryRank;

    }
}