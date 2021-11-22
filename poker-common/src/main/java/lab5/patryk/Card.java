package lab5.patryk;

import java.util.Comparator;

public class Card {

    public enum Rank {
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    public enum Suit {
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES
    }

    public Suit suit;
    public Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        if (suit != card.suit) return false;
        return rank == card.rank;
    }

    @Override
    public int hashCode() {
        int result = suit.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return rank.toString() + ' ' + suit;
    }

}

class SortByRank implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        return -o1.rank.compareTo(o2.rank);
    }
}

class SortBySuit implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        return o1.suit.compareTo(o2.suit);
    }
}
