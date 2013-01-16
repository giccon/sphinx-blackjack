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
package org.giccon.ai.sphinx_blackjack.gui;

import org.giccon.ai.sphinx_blackjack.logic.GameManager;
import org.giccon.ai.sphinx_blackjack.logic.Player;
import org.giccon.ai.sphinx_blackjack.logic.card.CardImageManager;

import javax.swing.*;
import java.awt.*;

/**
 * The main application entry.
 *
 * @author Paul Minasian
 */
public class SphinxBlackjackGui {

    private JFrame frmSphinxBlackjack;

    /**
     * Create the application.
     */
    public SphinxBlackjackGui() {
        initialize();

        CardImageManager.getInstance();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SphinxBlackjackGui window = new SphinxBlackjackGui();
                    window.frmSphinxBlackjack.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialise the contents of the frame.
     */
    private void initialize() {
        frmSphinxBlackjack = new JFrame();
        frmSphinxBlackjack.setTitle("Sphinx Blackjack");
        frmSphinxBlackjack.setMinimumSize(new Dimension(800, 600));
        frmSphinxBlackjack.setResizable(false);
        frmSphinxBlackjack.setBounds(100, 100, 450, 300);
        frmSphinxBlackjack.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GameManager gm = GameManager.getInstance();
        Player dealer = gm.getDealer();
        Player human = gm.getHuman();
        CardDrawPanel pnlCard = new CardDrawPanel(dealer, human);
        gm.notifyObservers();
        frmSphinxBlackjack.getContentPane().add(pnlCard, BorderLayout.CENTER);
    }
}
