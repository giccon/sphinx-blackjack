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

import org.giccon.ai.sphinx_blackjack.logic.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Paul Minasian
 */
public abstract class Player {

    private static final ScoreCalculator scoreCalculator = new ScoreCalculator();
    private List<Card> hand;

    public Player() {
        hand = new ArrayList<Card>();
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public void receiveCard(Card card) {
        hand.add(card);
    }

    public void disposeHand() {
        hand.clear();
    }

    public HandScore getScore() {
        return scoreCalculator.calculateScore(hand);
    }

    public int getFinalScore() {
        return scoreCalculator.calculateFinalScore(hand);
    }

    public boolean hasBlackjackHand() {
        return scoreCalculator.isBlackjackHand(hand);
    }

    public boolean hasHandScoreOf21() {
        HandScore handScore = getScore();
        for (Integer score : handScore.getPossibleScores()) {
            if (score == 21) {
                return true;
            }
        }
        return false;
    }
}
