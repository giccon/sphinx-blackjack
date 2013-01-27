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

import org.giccon.ai.sphinx_blackjack.logic.GameManager;
import org.giccon.ai.sphinx_blackjack.logic.HandScore;
import org.giccon.ai.sphinx_blackjack.logic.Human;
import org.giccon.ai.sphinx_blackjack.logic.Player;
import org.giccon.ai.sphinx_blackjack.logic.card.Card;
import org.giccon.ai.sphinx_blackjack.logic.card.CardImageManager;
import org.giccon.ai.sphinx_blackjack.speech.VoiceCommand;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Paul Minasian
 */
public class RenderState {
    protected static final int DECK_CARD_Y_COORD = 20;
    protected static final int DEALER_CARD_Y_COORD = 50;
    protected static final int HUMAN_CARD_Y_COORD = 250;
    protected static final int COMMAND_CARD_Y_COORD = 450;
    protected static final int CASH_INFO_Y_COORD = 420;
    protected static final int CARD_VISIBILITY_WIDTH = 30;
    protected static final Font arial14 = new Font("Arial", Font.BOLD, 14);
    protected static final Font arial12 = new Font("Arial", Font.BOLD, 12);
    protected static VoiceCommand voiceCommand = VoiceCommand.UNRECOGNIZED;
    protected CardImageManager cim = CardImageManager.getInstance();
    protected GameManager gm = GameManager.getInstance();
    protected JPanel canvas;
    protected Player dealer;
    protected Player human;

    public RenderState(JPanel canvas, Player dealer, Player human) {
        this.canvas = canvas;
        this.dealer = dealer;
        this.human = human;
    }

    public static void setVoiceCommand(VoiceCommand voiceCommand) {
        RenderState.voiceCommand = voiceCommand;
    }

    public void render(Graphics g) {
        drawDeckCards(g);
        drawDealerCards(g, true);
        drawHumanCards(g);
        drawCommandLegend(g);
        drawPlayerCashInfo(g);
    }

    protected void drawHumanCards(Graphics g) {
        java.util.List<Card> hand = human.getHand();
        drawCards(g, hand, HUMAN_CARD_Y_COORD, false);
        int xCoord = canvas.getWidth() / 2 - 10;
        drawScore(g, human.getScore(), xCoord, HUMAN_CARD_Y_COORD + cim.getCardHeight() + 15);
    }

    protected void drawDealerCards(Graphics g, boolean showFirstCardClosed) {
        java.util.List<Card> hand = dealer.getHand();
        drawCards(g, hand, DEALER_CARD_Y_COORD, showFirstCardClosed);
        int xCoord = canvas.getWidth() / 2 - 10;
        drawScore(g, dealer.getScore(), xCoord, DEALER_CARD_Y_COORD + cim.getCardHeight() + 15);
    }

    protected void drawDeckCards(Graphics g) {
        int cardWidth = cim.getCardWidth();
        int cardHeight = cim.getCardHeight();

        int nrCard = gm.getNumberOfCardsLeft();
        int size = 10;
        if (nrCard < size) {
            size = nrCard;
        }

        for (int i = 0; i < size; i++) {
            int xPos = canvas.getWidth() - cardWidth - 15 + i;
            g.drawImage(cim.getBackOfCardImage(), xPos, DECK_CARD_Y_COORD, xPos + cardWidth,
                    DECK_CARD_Y_COORD + cardHeight, 0, 0, cardWidth, cardHeight, null);
        }
    }

    protected void drawCards(Graphics g, java.util.List<Card> hand, int cardYCoord, boolean showFirstCardClosed) {
        int cardWidth = cim.getCardWidth();
        int cardHeight = cim.getCardHeight();

        int xCoord = calculateXCoordOfHand(hand);

        int x = 0;
        for (Card c : hand) {
            int drawX = xCoord + x * CARD_VISIBILITY_WIDTH;
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

    protected int calculateXCoordOfHand(java.util.List<Card> hand) {
        int cardWidth = cim.getCardWidth();

        int adjustment = (cardWidth - CARD_VISIBILITY_WIDTH) / 2;

        return (canvas.getWidth() / 2) - (CARD_VISIBILITY_WIDTH * hand.size() / 2) - adjustment;
    }

    protected void drawScore(Graphics g, HandScore handScore, int xCoord, int yCoord) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        java.util.List<Integer> scores = handScore.getPossibleScores();
        for (Integer score : scores) {
            sb.append(score);
            if (i++ < scores.size() - 1) {
                sb.append("/");
            }
        }
        g.setFont(arial14);
        g.setColor(Color.GRAY);
        g.drawString(sb.toString(), xCoord, yCoord);
    }

    protected void drawCommandLegend(Graphics g) {
        g.setColor(Color.RED);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(5F));
        g2D.drawLine(0, COMMAND_CARD_Y_COORD - 20, canvas.getWidth(), COMMAND_CARD_Y_COORD - 20);

        g.setColor(Color.RED);
        g.setFont(arial14);
        g.drawString("Please speak one of the below commands when applicable:", 10, COMMAND_CARD_Y_COORD);

        if (voiceCommand != VoiceCommand.UNRECOGNIZED) {
            g.setColor(Color.BLUE);
            g.drawString(" You've said: " + voiceCommand.getCommand(), 450, COMMAND_CARD_Y_COORD);
        }

        g.setFont(arial12);
        g.setColor(Color.BLACK);
        g.drawString("bet all | bet one | bet five | bet ten | bet twenty five | bet fifty | bet hundred",
                10, COMMAND_CARD_Y_COORD + 30);

        g.drawString("take all | take one | take five | take ten | take twenty five | take fifty | take hundred",
                10, COMMAND_CARD_Y_COORD + 50);

        g.drawString("go deal | go hit | go stand", 10, COMMAND_CARD_Y_COORD + 70);
        g.setColor(Color.BLUE);
        g.drawString("restart", 10, COMMAND_CARD_Y_COORD + 100);

    }

    protected void drawPlayerCashInfo(Graphics g) {
        Human h = (Human) human;
        g.setColor(Color.BLACK);
        g.drawString("Total: " + h.getCash(), 10, CASH_INFO_Y_COORD);
        g.drawString("Bet: " + h.getBet(), canvas.getWidth() - 100, CASH_INFO_Y_COORD);
    }
}
