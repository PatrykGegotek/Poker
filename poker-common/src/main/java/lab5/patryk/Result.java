package lab5.patryk;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class Result {

    // returns number from 1 to 10 depending on the poker hand ranking
    public static Score ranking(List<Card> cards) {
        Collections.sort(cards, new SortByRank());
        Collections.sort(cards, new SortBySuit());
        System.out.println(cards);
        Score score;
        score = royalFlush(cards);
        if (score != null)
            System.out.println(score.details);
        return score;
    }

    private static Score royalFlush(List<Card> cards) {
        boolean isRoyal = true;
        for (int j = 0; j < 3; j++) {
            int k = 0;
            for (int i = j; i < j + 5; i++) {
                if (cards.get(i).rank.ordinal() != 12-k)
                {
                    isRoyal = false;
                    break;
                }
                k++;
            }
            if (isRoyal) break;
        }

        if (isRoyal)
        {
            return new Score(10, 0, 0, "Royal Flush of " + cards.get(0).suit.toString() + "\n");
        } else
            return null;
    }

    private static Score straightFlush(List<Card> cards) {
        boolean isStraight = true;
        for (int j = 0; j < 3; j++) {
            int rank = cards.get(j).rank.ordinal();
            int k = 0;
            for (int i = j; i < j + 5; i++) {
                if (cards.get(i).rank.ordinal() != rank-k)
                {
                    isStraight = false;
                    break;
                }
                k++;
            }
            if (isStraight) break;
        }
        if (isStraight)
        {
            return new Score(10, 0, 0, "Royal Flush of " + cards.get(0).suit.toString() + "\n");
        } else
            return null;
    }

}
