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

import org.giccon.ai.sphinx_blackjack.Player;
import org.giccon.ai.sphinx_blackjack.card.Card;
import org.giccon.ai.sphinx_blackjack.card.CardImageManager;
import org.giccon.ai.sphinx_blackjack.logic.GameManager;
import org.giccon.ai.sphinx_blackjack.logic.HandScore;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Paul Minasian
 */
public class Renderer {

    private static final int DECK_CARD_Y_COORD = 20;
    private static final int DEALER_CARD_Y_COORD = 50;
    private static final int HUMAN_CARD_Y_COORD = 250;
    private static final int COMMAND_CARD_Y_COORD = 450;
    private static final int CARD_VISIBILITY_WIDTH = 30;
    private static final Font arial14 = new Font("Arial", Font.BOLD, 14);
    private static final Font arial12 = new Font("Arial", Font.BOLD, 12);
    private CardImageManager cim = CardImageManager.getInstance();
    private GameManager gm = GameManager.getInstance();
    private JPanel canvas;
    private Player dealer;
    private Player human;

    public Renderer(JPanel canvas, Player dealer, Player human) {
        this.canvas = canvas;
        this.dealer = dealer;
        this.human = human;
    }

    public void render(Graphics g) {
        drawDeckCards(g);
        drawDealerCards(g);
        drawHumanCards(g);
        drawCommandLegend(g);
    }

    private void drawHumanCards(Graphics g) {
        java.util.List<Card> hand = human.getHand();
        drawCards(g, hand, HUMAN_CARD_Y_COORD, false);
        int xCoord = calculateXCoordOfHand(hand);
        drawScore(g, human.getScore(), xCoord + 40, HUMAN_CARD_Y_COORD + cim.getCardHeight() + 15);
    }

    private void drawDealerCards(Graphics g) {
        java.util.List<Card> hand = dealer.getHand();
        boolean showFirstCardClosed = (hand.size() == 2);
        drawCards(g, hand, DEALER_CARD_Y_COORD, showFirstCardClosed);
        int xCoord = calculateXCoordOfHand(hand);
        drawScore(g, dealer.getScore(), xCoord + 40, DEALER_CARD_Y_COORD + cim.getCardHeight() + 15);
    }

    private void drawDeckCards(Graphics g) {
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

    private void drawCards(Graphics g, java.util.List<Card> hand, int cardYCoord, boolean showFirstCardClosed) {
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

    private int calculateXCoordOfHand(java.util.List<Card> hand) {
        int cardWidth = cim.getCardWidth();

        int adjustment = (cardWidth - CARD_VISIBILITY_WIDTH) / 2;

        return (canvas.getWidth() / 2) - (CARD_VISIBILITY_WIDTH * hand.size() / 2) - adjustment;
    }

    private void drawScore(Graphics g, HandScore handScore, int xCoord, int yCoord) {
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

    private void drawCommandLegend(Graphics g) {
        g.setColor(Color.RED);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(5F));
        g2D.drawLine(0, COMMAND_CARD_Y_COORD - 20, canvas.getWidth(), COMMAND_CARD_Y_COORD - 20);

        g.setColor(Color.RED);
        g.setFont(arial14);
        g.drawString("Voice-controlled Interface: Please speak one of the below " +
                "commands when applicable:", 10, COMMAND_CARD_Y_COORD);

        g.setFont(arial12);
        g.setColor(Color.BLACK);
        g.drawString("plus one | plus five | plus ten | plus twenty five | plus fifty | plus hundred",
                10, COMMAND_CARD_Y_COORD + 30);

        g.drawString("minus one | minus five | minus ten | minus twenty five | minus fifty | minus hundred",
                10, COMMAND_CARD_Y_COORD + 50);

        g.drawString("deal | hit | stand | double | split", 10, COMMAND_CARD_Y_COORD + 70);
        g.setColor(Color.BLUE);
        g.drawString("restart | help", 10, COMMAND_CARD_Y_COORD + 100);

    }
}