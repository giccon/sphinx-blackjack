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
import org.giccon.ai.sphinx_blackjack.logic.Human;
import org.giccon.ai.sphinx_blackjack.logic.card.Deck;

/**
 * Author: Paul Minasian
 */
public class GameRoundEndState extends GameState {
    public GameRoundEndState(GameManager gm, Deck deck, Dealer dealer, Human human) {
        super(gm, deck, dealer, human);
    }

    @Override
    public void deal() {
        dealer.disposeHand();
        human.disposeHand();
        sharedDeal();
    }

    @Override
    public void addBet(int cashAmount) {
        sharedAddBet(cashAmount);
    }

    @Override
    public void withdrawBet(int cashAmount) {
        sharedWithdrawBet(cashAmount);
    }
}
