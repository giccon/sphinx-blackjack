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

package org.giccon.ai.sphinx_blackjack.logic.gamestate;

import org.giccon.ai.sphinx_blackjack.logic.Dealer;
import org.giccon.ai.sphinx_blackjack.logic.GameManager;
import org.giccon.ai.sphinx_blackjack.logic.HandScore;
import org.giccon.ai.sphinx_blackjack.logic.Human;
import org.giccon.ai.sphinx_blackjack.logic.card.Deck;

/**
 * Author: Paul Minasian
 */
public class HumanPlayingState extends GameState {

    public HumanPlayingState(GameManager gm, Deck deck, Dealer dealer, Human human) {
        super(gm, deck, dealer, human);
    }

    @Override
    public void hit() {
        dealCardToPlayer(human);

        if (human.hasHandScoreOf21()) {
            gm.setGameState(gm.getDealerPlayingState());
            gm.fireStateChange(GameStateChanged.DEALER_PLAYING_STATE);
            gm.getGameState().playDealer();
            return;
        }

        HandScore handScore = human.getScore();
        int lowestScore = Integer.MAX_VALUE;
        for (Integer score : handScore.getPossibleScores()) {
            if (score < lowestScore) {
                lowestScore = score;
            }
        }

        if (lowestScore > 21) {
            setGameRoundEndState();
        } else {
            gm.fireStateChange(null);
        }
    }

    @Override
    public void stand() {
        gm.setGameState(gm.getDealerPlayingState());
        gm.fireStateChange(GameStateChanged.DEALER_PLAYING_STATE);
        gm.getGameState().playDealer();
    }

    @Override
    public void surrender() {

    }
}
