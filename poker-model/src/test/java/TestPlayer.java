import lab5.patryk.Card;
import lab5.patryk.Player;
import lab5.patryk.Score;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestPlayer {

    @Test
    public void addCRemoveSortAndShowCards() {
        Player player = new Player("Eva");
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.SIX);
        Card card2 = new Card(Card.Suit.CLUBS, Card.Rank.EIGHT);
        Card card3 = new Card(Card.Suit.SPADES, Card.Rank.KING);
        Card card4 = new Card(Card.Suit.HEARTS, Card.Rank.EIGHT);
        Card card5 = new Card(Card.Suit.DIAMONDS, Card.Rank.THREE);


        //ADD
        player.receiveCard(card1);
        player.receiveCard(card2);
        player.receiveCard(card3);
        player.receiveCard(card4);
        player.receiveCard(card5);

        Assert.assertEquals(5, player.getCards().size());

        //REMOVE

        player.removeCard(4);
        player.removeCard(0);
        Assert.assertEquals(3, player.getCards().size());
        Assert.assertEquals(player.getCards().get(0), card2);

        //SORT
        player.sortCards();
        List<Card> expected = new ArrayList<Card>();
        expected.add(card3);
        expected.add(card4);
        expected.add(card2);

        Assert.assertEquals(expected, player.getCards());

        //SHOW

        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (Card card: expected) {
            builder.append(i++).append(". ").append(card).append("\n");
        }

        Assert.assertEquals(builder.toString(), player.showCards());
    }

    @Test
    public void cardsCombination() {
        Player player = new Player("Eva");
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.TEN);
        Card card2 = new Card(Card.Suit.CLUBS, Card.Rank.JACK);
        Card card3 = new Card(Card.Suit.SPADES, Card.Rank.KING);
        Card card4 = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        Card card5 = new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN);

        player.receiveCard(card1);
        player.receiveCard(card2);
        player.receiveCard(card3);
        player.receiveCard(card4);
        player.receiveCard(card5);

        player.checkCardsCombinations();
        Assert.assertEquals(5, player.getScore().primaryRank);
    }

}
