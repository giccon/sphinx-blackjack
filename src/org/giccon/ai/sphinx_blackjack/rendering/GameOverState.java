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

import javax.swing.*;
import java.awt.*;

/**
 * Author: Paul Minasian
 */
public class GameOverState extends RenderState {

    public GameOverState(JPanel canvas, Player dealer, Player human) {
        super(canvas, dealer, human);
    }

    @Override
    public void render(Graphics g) {
        drawDeckCards(g);
        drawDealerCards(g, false);
        drawHumanCards(g);
        drawCommandLegend(g);
        drawPlayerCashInfo(g);
        drawGameOverInfo(g);
    }

    private void drawGameOverInfo(Graphics g) {
        int xCoord = canvas.getWidth() / 2 - 30;
        int yCoord = DEALER_CARD_Y_COORD + cim.getCardHeight() + 45;
        g.setColor(Color.BLUE);
        g.drawString("Game Over! Say restart", xCoord, yCoord);
    }
}
