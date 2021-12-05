import org.junit.Assert;
import org.junit.Test;
import lab5.patryk.*;

import java.util.ArrayList;
import java.util.List;

public class TestCards {


    @Test
    public void getFromDeck() {
        Deck deck = new Deck();
        Card card = deck.getDeck().get(2);
        deck.getCard();
        deck.getCard();
        Card card2 = deck.getCard();
        Assert.assertEquals(card2, card);
    }
}
