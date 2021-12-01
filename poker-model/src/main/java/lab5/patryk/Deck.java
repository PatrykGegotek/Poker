package lab5.patryk;

import java.util.*;

public class Deck {

    private List<Card> deck;

    Deck() {
        deck = new LinkedList<>();
        int i = 0;
        for(Card.Suit suit: Card.Suit.values())
            for(Card.Rank rank: Card.Rank.values()) {
                deck.add(new Card(suit, rank));
                i++;
            }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card getCard() {
        Card a = deck.get(0);
        deck.remove(0);
        return a;
    }

    public List<Card> getDeck() {
        return deck;
    }
}
