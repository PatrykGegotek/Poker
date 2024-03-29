package lab5.patryk;

import java.util.*;

public class Deck {

    private List<Card> deck;

    public Deck() {
        deck = new LinkedList<>();
        for(Card.Suit suit: Card.Suit.values())
            for(Card.Rank rank: Card.Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        shuffle();
    }

    /**
     * Tasowanie kart
     */
    private void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Zdjęcie jednej kart z wierzchu
     */
    public Card getCard() {
        Card a = deck.get(0);
        deck.remove(0);
        return a;
    }

    public List<Card> getDeck() {
        return deck;
    }
}
