/**
 *  This file is part of Sphinx Blackjack.
 *
 *  Sphinx Blackjack is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Sphinx Blackjack is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Sphinx Blackjack.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.giccon.ai.sphinx_blackjack.gui;

import org.giccon.ai.sphinx_blackjack.Player;
import org.giccon.ai.sphinx_blackjack.card.Card;
import org.giccon.ai.sphinx_blackjack.card.CardImageManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Paul Minasian
 */
public class CardDrawPanel extends JPanel {

    private static final long serialVersionUID = -4534795109399774313L;
    private static final int DEALER_CARD_Y_COORD = 50;
    private static final int HUMAN_CARD_Y_COORD = 300;
    private static final int COMMAND_CARD_Y_COORD = 500;
    private static final int CARD_VISIBILITY_WIDTH = 30;
    private CardImageManager cim;
    private Player dealer;
    private Player human;

    public CardDrawPanel(Player dealer, Player human) {
        setPreferredSize(new Dimension(800, 600));
        cim = CardImageManager.getInstance();

        this.dealer = dealer;
        this.human = human;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawDeckCards(g);
        drawDealerCards(g);
        drawHumanCards(g);
        drawCommandLegend(g);

    }

    private void drawHumanCards(Graphics g) {
        drawCards(g, human.getHand(), HUMAN_CARD_Y_COORD, false);
    }

    private void drawDealerCards(Graphics g) {
        boolean showFirstCardClosed = dealer.getHand().size() == 2;
        drawCards(g, dealer.getHand(), DEALER_CARD_Y_COORD, showFirstCardClosed);
    }

    private void drawDeckCards(Graphics g) {

    }

    private void drawCards(Graphics g, List<Card> hand, int cardYCoord, boolean showFirstCardClosed) {
        int cardWidth = cim.getCardWidth();
        int cardHeight = cim.getCardHeight();

        int adjustment = (cardWidth - CARD_VISIBILITY_WIDTH) / 2;
        int startPos = (getWidth() / 2) - (CARD_VISIBILITY_WIDTH * hand.size() / 2) - adjustment;

        int x = 0;
        for (Card c : hand) {
            int drawX = startPos + x * CARD_VISIBILITY_WIDTH;
            Image img;
            if (showFirstCardClosed && x == 0) {
                img = cim.getBackOfCardImage();
            } else {
                img = c.getImg();
            }
            g.drawImage(img, drawX, cardYCoord, drawX + cardWidth, cardYCoord + cardHeight, 0, 0, cardWidth,
                    cardHeight, null);
            x++;
        }
    }

    private void drawCommandLegend(Graphics g) {
        g.drawString("Voice-controlled Interface: Deal | Hit | Stand", 10, COMMAND_CARD_Y_COORD);
    }
}
