package lab5.patryk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Result {

    List<Card> cards;

    public Result(List<Card> cards) {
        this.cards = cards;
        Collections.sort(cards, Collections.<Card>reverseOrder(new SortByRank()));
    }

    // returns number from 1 to 10 depending on the poker hand ranking
    public Score ranking() {
        Score score = royalFlush();
        if(score == null) score = straightFlush();
        if(score == null) score = fourOfAKind();
        if(score == null) score = fullHouse();
        if(score == null) score = flush();
        if(score == null) score = straight();
        if(score == null) score = three();
        if(score == null) score = twoPairs();
        if(score == null) score = onePair();
        if(score == null) score = highCard();
        return score;
    }

    public Score royalFlush() {


        boolean isRoyal = true;
        Card.Suit colour = cards.get(0).suit;
        for (int i = 0; i < 5; i++) {
            if ((cards.get(i).rank.ordinal() != 12 - i) || cards.get(i).suit != colour) {
                isRoyal = false;
                break;
            }
        }

        if (isRoyal) {
            return new Score(10, 0, 0, "Royal Flush of " + cards.get(0).suit.toString() + "\n");
        } else
            return null;
    }

    public Score straightFlush() {
        Card.Suit colour = cards.get(0).suit;

        // Sprawdza color
        for (int i = 1; i < 5; i++) {
            if (cards.get(i).suit != colour) {
                return null;
            }
        }

        Score score = straight();

        if (score != null) {
            return new Score(9, score.secondaryRank, 0, "Straight Flush" + score.details.substring(8));
        } else
            return null;
    }

    public Score fourOfAKind() {
        boolean isFour = false;
        int secondary = 0;
        Card fifth = null;

        if (cards.get(0).rank.equals(cards.get(1).rank) &&
                cards.get(0).rank.equals(cards.get(2).rank) &&
                cards.get(0).rank.equals(cards.get(3).rank)) {
            isFour = true;
            secondary = cards.get(2).rank.ordinal();
            fifth = cards.get(4);
        }

        if (cards.get(4).rank.equals(cards.get(1).rank) &&
                cards.get(4).rank.equals(cards.get(2).rank) &&
                cards.get(4).rank.equals(cards.get(3).rank)) {
            isFour = true;
            secondary = cards.get(2).rank.ordinal();
            fifth = cards.get(0);
        }

        if (isFour) {
            String string = "Four of " + cards.get(2).rank.toString() + "\n" +
                    "Fifth cards: " + fifth;
            return new Score(8, secondary, 0, string);
        } else
            return null;
    }

    public Score fullHouse() {
        boolean isFull = false;
        Card.Rank secondary = null;
        Card.Rank tertiary = null;
        if (cards.get(0).rank.equals(cards.get(1).rank) &&
                cards.get(1).rank.equals(cards.get(2).rank) &&
                cards.get(3).rank.equals(cards.get(4).rank)) {
            isFull = true;
            secondary = cards.get(0).rank;
            tertiary = cards.get(4).rank;
        }

        if (cards.get(0).rank.equals(cards.get(1).rank) &&
                cards.get(2).rank.equals(cards.get(3).rank) &&
                cards.get(3).rank.equals(cards.get(4).rank)) {
            isFull = true;
            secondary = cards.get(4).rank;
            tertiary = cards.get(0).rank;
        }

        if (isFull) {
            return new Score(7, secondary.ordinal(), tertiary.ordinal(), "House of 3x " + secondary +
                    ", 2x " + tertiary);
        } else return null;
    }

    public Score flush() {
        boolean isFlush = cards.get(0).suit.equals(cards.get(1).suit) &&
                cards.get(0).suit.equals(cards.get(2).suit) &&
                cards.get(0).suit.equals(cards.get(3).suit) &&
                cards.get(0).suit.equals(cards.get(4).suit);
        if (isFlush) {
            return new Score(6, 0, 0, "Flush of " + cards.get(0).suit);
        } else return null;
    }


    public Score straight() {

        boolean isStraight = true;
        int secondary = 0;

        // Sprawdza kombinacje 5 kart
        for (int j = 12; j >= 4; j--) {
            isStraight = true;
            for (int i = 0; i < 5; i++) {
                if ((cards.get(i).rank.ordinal() != j - i)) {
                    isStraight = false;
                    break;
                }
            }
            if (isStraight) {
                secondary = j;
                break;
            }
        }

        // Ostatnia możliwość - 5,4,3,2,AS

        if (!isStraight) {
            if (cards.get(0).rank != Card.Rank.ACE)
                return null;
            isStraight = true;
            secondary = 4;
            for (int i = 1; i < 5; i++) {
                if ((cards.get(i).rank.ordinal() != 4 - i)) {
                    isStraight = false;
                    break;
                }
            }
        }

        if (isStraight) {
            StringBuilder builder = new StringBuilder();
            builder.append("Straight: \n");
            for (int i = 0; i < 5; i++) {
                builder.append(cards.get(i).rank).append(" ");
            }
            return new Score(5, secondary, 0, builder.toString());
        } else
            return null;
    }

    public Score three() {
        boolean isThree = false;
        int secondary = 0;
        int tetriary = 0;

        if (cards.get(0).rank == cards.get(1).rank &&
                cards.get(1).rank == cards.get(2).rank) {
            isThree = true;
            secondary = cards.get(2).rank.ordinal();
            tetriary = cards.get(3).rank.ordinal() * 12 + cards.get(4).rank.ordinal();
        }
        if (cards.get(1).rank == cards.get(2).rank &&
                cards.get(2).rank == cards.get(3).rank) {
            isThree = true;
            secondary = cards.get(2).rank.ordinal();
            tetriary = cards.get(0).rank.ordinal() * 12 + cards.get(4).rank.ordinal();
        }
        if (cards.get(2).rank == cards.get(3).rank &&
                cards.get(3).rank == cards.get(4).rank) {
            isThree = true;
            secondary = cards.get(2).rank.ordinal();
            tetriary = cards.get(0).rank.ordinal() * 12 + cards.get(1).rank.ordinal();
        }

        if (isThree) {
            return new Score(4, secondary, tetriary, "Three of " + cards.get(2).rank);
        } else return null;
    }

    public Score twoPairs() {
        boolean arePairs = false;
        int secondary = 0;
        int tetriary = 0;

        if (cards.get(0).rank == cards.get(1).rank &&
                cards.get(2).rank == cards.get(3).rank) {
            arePairs = true;
            secondary = cards.get(0).rank.ordinal() * 12 + cards.get(2).rank.ordinal();
            tetriary = cards.get(4).rank.ordinal();
        } else if (cards.get(0).rank == cards.get(1).rank &&
                cards.get(3).rank == cards.get(4).rank) {
            arePairs = true;
            secondary = cards.get(0).rank.ordinal() * 12 + cards.get(3).rank.ordinal();
            tetriary = cards.get(2).rank.ordinal();
        } else if (cards.get(1).rank == cards.get(2).rank &&
                cards.get(3).rank == cards.get(4).rank) {
            arePairs = true;
            secondary = cards.get(1).rank.ordinal() * 12 + cards.get(3).rank.ordinal();
            tetriary = cards.get(0).rank.ordinal();
        }

        if (arePairs) {
            return new Score(3, secondary, tetriary, "Two pairs of: " +
                    cards.get(1).rank + " and " + cards.get(3).rank);
        } else return null;
    }

    public Score onePair() {
        List<Card> leftCards = new ArrayList<>();
        leftCards.addAll(cards);
        boolean isPair = false;
        Card secondary = null;
        for (int i = 0; i < 4; i++) {
            if (cards.get(i).rank == cards.get(i + 1).rank) {
                leftCards.remove(cards.get(i));
                leftCards.remove(cards.get(i+1));
                isPair = true;
                secondary = cards.get(i);
                break;
            }
        }

        int tertiary = leftCards.get(0).rank.ordinal();
        tertiary = tertiary * 12 + leftCards.get(1).rank.ordinal();
        tertiary = tertiary * 12 + leftCards.get(2).rank.ordinal();



        if (isPair) {
            return new Score(2, secondary.rank.ordinal(), tertiary, "Pair of " + secondary.rank);
        } else return null;
    }

    public Score highCard() {
        int tertiary = cards.get(1).rank.ordinal();
        tertiary = tertiary * 12 + cards.get(2).rank.ordinal();
        tertiary = tertiary * 12 + cards.get(3).rank.ordinal();
        tertiary = tertiary * 12 + cards.get(4).rank.ordinal();
        return new Score(1, cards.get(0).rank.ordinal(), tertiary, "High card: " + cards.get(0));
    }
}
