package lab5.patryk;

import java.util.Objects;

public class Score {
    int primaryRank;
    int secondaryRank;
    int tertiaryRank;
    String details;

    public Score(int r1, int r2, int r3, String string) {
        primaryRank = r1;
        secondaryRank = r2;
        tertiaryRank = r3;
        details = string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return primaryRank == score.primaryRank && secondaryRank == score.secondaryRank && tertiaryRank == score.tertiaryRank && details.equals(score.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryRank, secondaryRank, tertiaryRank, details);
    }
}
