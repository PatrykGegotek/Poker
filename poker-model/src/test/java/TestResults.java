import org.junit.Assert;
import org.junit.Test;
import lab5.patryk.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestResults {

    @Test
    public void isThereRoyalFlush() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.QUEEN));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.JACK));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.KING));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        Result result = new Result(cards);
        Score score = result.royalFlush();
        Assert.assertEquals(new Score(10, 0, 0, "Royal Flush of HEARTS\n"), score);
    }

    @Test
    public void isThereStraightFlush() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.NINE));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.EIGHT));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.JACK));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        Result result = new Result(cards);
        Score score = result.straightFlush();
        StringBuilder builder = new StringBuilder();
        Collections.sort(cards, new SortByRank());
        builder.append("Straight Flush: \n");
        for (int i = 0; i < 5; i++) {
            builder.append(cards.get(i).rank).append(" ");
        }
        String excpectedMessage = builder.toString();
        Score excpectedScore = new Score(9, 9, 0, excpectedMessage);

        Assert.assertEquals(excpectedScore, score);
    }

    @Test
    public void isThereFourOfAKind() {
        List<Card> cards = new ArrayList<>();
        Card four = new Card(Card.Suit.HEARTS, Card.Rank.SIX);
        Card fifth = new Card(Card.Suit.SPADES, Card.Rank.KING);

        cards.add(four);
        cards.add(new Card(Card.Suit.CLUBS, Card.Rank.SIX));
        cards.add(new Card(Card.Suit.DIAMONDS, Card.Rank.SIX));
        cards.add(fifth);
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.SIX));

        Result result = new Result(cards);
        Score score = result.fourOfAKind();
        String expected = "Four of " + four.rank.toString() + "\n" +
                "Fifth cards: " + fifth;

        Score expectedScore = new Score(8, four.rank.ordinal(), 0, expected);
        Assert.assertEquals(expectedScore, score);


        cards.clear();
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        cards.add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));
        cards.add(new Card(Card.Suit.DIAMONDS, Card.Rank.SIX));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.EIGHT));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.SIX));

        Result result2 = new Result(cards);
        Score score2 = result.fourOfAKind();
        Assert.assertNull(score2);
    }

    @Test
    public void isThereFullHouse() {

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));
        cards.add(new Card(Card.Suit.CLUBS, Card.Rank.THREE));
        cards.add(new Card(Card.Suit.SPADES, Card.Rank.SEVEN));
        cards.add(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.THREE));

        Result result = new Result(cards);
        Score score = result.fullHouse();

        String expectedMessage = "House of 3x " + Card.Rank.SEVEN + ", 2x " + Card.Rank.THREE;
        Score expectedScore = new Score(7, 5, 1, expectedMessage);

        Assert.assertEquals(expectedScore, score);
    }

    @Test
    public void isThereFlush() {

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT));
        cards.add(new Card(Card.Suit.DIAMONDS, Card.Rank.THREE));
        cards.add(new Card(Card.Suit.DIAMONDS, Card.Rank.KING));
        cards.add(new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));
        cards.add(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));

        Result result = new Result(cards);
        Score score = result.flush();

        String expectedMessage = "Flush of " + Card.Suit.DIAMONDS;
        Score expectedScore = new Score(6, 0, 0, expectedMessage);

        Assert.assertEquals(expectedScore, score);

    }

    @Test
    public void isThereStraight() {

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.NINE));
        cards.add(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.JACK));
        cards.add(new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.TEN));

        Result result = new Result(cards);
        Score score = result.straight();
        StringBuilder builder = new StringBuilder();
        Collections.sort(cards, new SortByRank());
        builder.append("Straight: \n");
        for (int i = 0; i < 5; i++) {
            builder.append(cards.get(i).rank).append(" ");
        }
        String excpectedMessage = builder.toString();
        Score excpectedScore = new Score(5, 9, 0, excpectedMessage);

        Assert.assertEquals(excpectedScore, score);


    }

    @Test
    public void isThereStraight2() {

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));
        cards.add(new Card(Card.Suit.CLUBS, Card.Rank.ACE));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.THREE));
        cards.add(new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE));
        cards.add(new Card(Card.Suit.HEARTS, Card.Rank.FOUR));

        Result result = new Result(cards);
        Score score = result.straight();
        StringBuilder builder = new StringBuilder();
        Collections.sort(cards, new SortByRank());
        builder.append("Straight: \n");
        for (int i = 0; i < 5; i++) {
            builder.append(cards.get(i).rank).append(" ");
        }
        String excpectedMessage = builder.toString();
        Score excpectedScore = new Score(5, 4, 0, excpectedMessage);

        Assert.assertEquals(excpectedScore, score);

    }

    @Test
    public void isThereThree() {

        List<Card> cards = new ArrayList<>();
        Card three1 = new Card(Card.Suit.HEARTS, Card.Rank.SIX);
        Card three2 = new Card(Card.Suit.CLUBS, Card.Rank.SIX);
        Card three3 = new Card(Card.Suit.HEARTS, Card.Rank.SIX);
        Card secondary = new Card(Card.Suit.SPADES, Card.Rank.KING);
        Card tertiary = new Card(Card.Suit.DIAMONDS, Card.Rank.THREE);

        cards.add(three1);
        cards.add(three2);
        cards.add(secondary);
        cards.add(three3);
        cards.add(tertiary);

        Result result = new Result(cards);
        Score score = result.three();
        String expected = "Three of " + three1.rank;

        Score expectedScore = new Score(4, three2.rank.ordinal(), secondary.rank.ordinal() * 12 +
                tertiary.rank.ordinal(), expected);
        Assert.assertEquals(expectedScore, score);

    }

    @Test
    public void areThereTwoPairs() {

        List<Card> cards = new ArrayList<>();
        Card pair11 = new Card(Card.Suit.HEARTS, Card.Rank.SIX);
        Card pair12 = new Card(Card.Suit.CLUBS, Card.Rank.SIX);
        Card pair21 = new Card(Card.Suit.HEARTS, Card.Rank.KING);
        Card pair22 = new Card(Card.Suit.SPADES, Card.Rank.KING);
        Card secondary = new Card(Card.Suit.DIAMONDS, Card.Rank.THREE);

        cards.add(pair11);
        cards.add(pair12);
        cards.add(secondary);
        cards.add(pair21);
        cards.add(pair21);

        Result result = new Result(cards);
        Score score = result.twoPairs();
        String expected = "Two pairs of: " +
                pair21.rank + " and " + pair11.rank;

        Score expectedScore = new Score(3, pair21.rank.ordinal() * 12 + pair11.rank.ordinal(),
                secondary.rank.ordinal(), expected);
        Assert.assertEquals(expectedScore, score);

    }
    @Test
    public void isTherePair() {

        List<Card> cards = new ArrayList<>();
        Card pair11 = new Card(Card.Suit.HEARTS, Card.Rank.SIX);
        Card pair12 = new Card(Card.Suit.CLUBS, Card.Rank.SIX);
        Card card1 = new Card(Card.Suit.SPADES, Card.Rank.KING);
        Card card2 = new Card(Card.Suit.HEARTS, Card.Rank.EIGHT);
        Card card3 = new Card(Card.Suit.DIAMONDS, Card.Rank.THREE);

        cards.add(pair11);
        cards.add(pair12);
        cards.add(card2);
        cards.add(card3);
        cards.add(card1);

        int tertiary = card1.rank.ordinal() * 144 + card2.rank.ordinal() * 12 + card3.rank.ordinal();

        Result result = new Result(cards);
        Score score = result.onePair();
        String expected = "Pair of " + pair11.rank;

        Score expectedScore = new Score(2, pair11.rank.ordinal(),
                tertiary, expected);
        Assert.assertEquals(expectedScore, score);
    }

    @Test
    public void isThereHighCard() {

        List<Card> cards = new ArrayList<>();
        Card high = new Card(Card.Suit.SPADES, Card.Rank.KING);
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.EIGHT);
        Card card2 = new Card(Card.Suit.HEARTS, Card.Rank.SIX);
        Card card3 = new Card(Card.Suit.DIAMONDS, Card.Rank.THREE);
        Card card4 = new Card(Card.Suit.CLUBS, Card.Rank.TWO);

        cards.add(card4);
        cards.add(high);
        cards.add(card2);
        cards.add(card3);
        cards.add(card1);

        int tertiary = card1.rank.ordinal() * 144 * 12 + card2.rank.ordinal() * 144 +
                card3.rank.ordinal() * 12 + card4.rank.ordinal();

        Result result = new Result(cards);
        Score score = result.highCard();
        String expected = "High card: " + high;

        Score expectedScore = new Score(1, high.rank.ordinal(),
                tertiary, expected);
        Assert.assertEquals(expectedScore, score);

    }

}

class SortByRank implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        return -1 * o1.rank.compareTo(o2.rank);
    }
}
