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

import org.giccon.ai.sphinx_blackjack.config.GlobalConfig;
import org.giccon.ai.sphinx_blackjack.logic.Dealer;
import org.giccon.ai.sphinx_blackjack.logic.GameManager;
import org.giccon.ai.sphinx_blackjack.logic.Human;
import org.giccon.ai.sphinx_blackjack.logic.card.Deck;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Author: Paul Minasian
 */
public class DealerPlayingState extends GameState implements ActionListener {
    private static final int DEALER_STAND_SCORE = GlobalConfig.getDealerStandScore();
    private Timer timer;

    public DealerPlayingState(GameManager gm, Deck deck, Dealer dealer, Human human) {
        super(gm, deck, dealer, human);

        timer = new Timer(1500, this);
        timer.setInitialDelay(2000);
    }

    @Override
    public void playDealer() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Integer> scores = dealer.getScore().getPossibleScores();
        int handScore = scores.iterator().next();
        for (int score : scores) {
            if (score <= GlobalConfig.getBlackjackWinScore() && handScore < score) {
                handScore = score;
            }
        }

        if (dealer.getScore().getPossibleScores().iterator().next() < DEALER_STAND_SCORE) {
            dealCardToPlayer(dealer);
            gm.fireStateChange(null);
        } else {
            timer.stop();
            setGameRoundEndState();
        }
    }
}
