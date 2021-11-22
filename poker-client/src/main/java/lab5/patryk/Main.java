package lab5.patryk;

import lab5.patryk.*;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        Deck deck = new Deck();
//        for(Card card: deck.deck) {
//            System.out.println(card);
//        }

        Game game = new Game();


        Score sc = Result.ranking(deck.getDeck());

//        game.addPlayer(new Player("Matthiew"));
//        game.addPlayer(new Player("Andrew"));
//        game.addPlayer(new Player("Johnny"));
//
//        game.start();
//        game.givePlayersCars(deck);
//
//        game.flop(deck);
//        game.turn(deck);
//        game.river(deck);
    }
}
