package lab5.patryk;

import java.util.*;

public class Player {

    String name;
    List<Card> cards;
    int moneyLeft;
    int moneyOnTable;
    boolean isAllIn;
    Score score;

    public Player(String name) {
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
        Collections.sort(this.cards, Collections.<Card>reverseOrder(new SortBySuit()));
        Collections.sort(this.cards, Collections.<Card>reverseOrder(new SortByRank()));
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

    public List<Card> getCards() {
        return cards;
    }

    public Score getScore() {
        return score;
    }
}

class SortByScore implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        if (o1.score.primaryRank == o2.score.primaryRank) {
            if (o1.score.secondaryRank == o2.score.secondaryRank) {
                if (o1.score.tertiaryRank == o2.score.tertiaryRank) {
                    return 0;
                }
                else return o1.score.tertiaryRank - o2.score.tertiaryRank;
            }
            else return o1.score.secondaryRank - o2.score.secondaryRank;
        }
        else return o1.score.primaryRank - o2.score.primaryRank;
    }
}