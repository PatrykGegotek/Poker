import org.junit.Assert;
import org.junit.Test;
import lab5.patryk.*;

import java.util.ArrayList;
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

}
