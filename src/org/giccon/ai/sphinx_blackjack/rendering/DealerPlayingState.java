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

package org.giccon.ai.sphinx_blackjack.rendering;

import org.giccon.ai.sphinx_blackjack.logic.Player;
import org.giccon.ai.sphinx_blackjack.logic.card.Card;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Paul Minasian
 */
public class DealerPlayingState extends RenderState {

    public DealerPlayingState(JPanel canvas, Player dealer, Player human) {
        super(canvas, dealer, human);
    }

    protected void drawDealerCards(Graphics g) {
        java.util.List<Card> hand = dealer.getHand();
        drawCards(g, hand, DEALER_CARD_Y_COORD, false);
        int xCoord = canvas.getWidth() / 2 - 10;
        drawScore(g, dealer.getScore(), xCoord, DEALER_CARD_Y_COORD + cim.getCardHeight() + 15);
    }
}
