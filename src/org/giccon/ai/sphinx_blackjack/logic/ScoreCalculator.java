/*
 * This file is part of Sphinx Blackjack.
 *
 * Sphinx Blackjack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Sphinx Blackjack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Sphinx Blackjack.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.giccon.ai.sphinx_blackjack.logic;

import org.giccon.ai.sphinx_blackjack.config.GlobalConfig;
import org.giccon.ai.sphinx_blackjack.logic.card.Card;
import org.giccon.ai.sphinx_blackjack.logic.card.Rank;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: Paul Minasian
 */
public class ScoreCalculator {

    private static final Map<Rank, Integer> SCORETABLE = new HashMap<Rank, Integer>();
    private static final int BLACKJACK_WIN_SCORE = GlobalConfig.getBlackjackWinScore();

    static {
        initializeScoreTable(SCORETABLE);
    }

    private static void initializeScoreTable(Map<Rank, Integer> scoretable) {
        scoretable.put(Rank.ACE, 1);
        scoretable.put(Rank.TWO, 2);
        scoretable.put(Rank.THREE, 3);
        scoretable.put(Rank.FOUR, 4);
        scoretable.put(Rank.FIVE, 5);
        scoretable.put(Rank.SIX, 6);
        scoretable.put(Rank.SEVEN, 7);
        scoretable.put(Rank.EIGHT, 8);
        scoretable.put(Rank.NINE, 9);
        scoretable.put(Rank.TEN, 10);
        scoretable.put(Rank.JACK, 10);
        scoretable.put(Rank.QUEEN, 10);
        scoretable.put(Rank.KING, 10);
    }

    public HandScore calculateScore(List<Card> hand) {
        int score1 = 0;
        int score2 = 0;
        boolean isFirstAce = true;
        for (Card c : hand) {
            score1 += getCardValue(c, false);

            if (c.isAce() && isFirstAce) {
                score2 += getCardValue(c, true);
                isFirstAce = false;
            } else {
                score2 += getCardValue(c, false);
            }
        }

        if (score2 == BLACKJACK_WIN_SCORE) {
            score1 = score2;
        } else if (score2 > BLACKJACK_WIN_SCORE) {
            score2 = score1;
        }

        HandScore handScore = new HandScore();
        handScore.addScore(score1);
        if (score1 != score2) {
            handScore.addScore(score2);
        }
        return handScore;
    }

    public int calculateFinalScore(List<Card> hand) {
        HandScore handScore = calculateScore(hand);
        Iterator it = handScore.getPossibleScores().iterator();
        int finalScore = (Integer) it.next();

        while (it.hasNext()) {
            int score = (Integer) it.next();
            if (score > BLACKJACK_WIN_SCORE && finalScore > score) {
                finalScore = score;
            } else if (score <= BLACKJACK_WIN_SCORE && finalScore < score) {
                finalScore = score;
            }
        }

        return finalScore;
    }

    private int getCardValue(Card c, boolean highAceScore) {
        int value;
        Rank rank = c.getRank();
        if (rank.equals(Rank.ACE) && highAceScore) {
            value = 11;
        } else {
            value = SCORETABLE.get(rank);
        }

        return value;
    }

    public boolean isBlackjackHand(List<Card> hand) {
        if (hand.size() > 2) {
            return false;
        }

        boolean isAce = false;
        int score = 0;
        for (Card c : hand) {
            if (c.isAce() && !isAce) {
                isAce = true;
                score += getCardValue(c, true);
            } else {
                score += getCardValue(c, false);
            }
        }

        return (score == BLACKJACK_WIN_SCORE);
    }
}
