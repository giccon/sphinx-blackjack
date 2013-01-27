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
 * Author: Paul Minasian
 */
public class SphinxBlackjackApplet extends JApplet {
    /**
     * Init the application.
     */
    public void init() {
        // Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    initialize();
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Application initialization didn't complete successfully.");
        }
    }

    /**
     * Initialise the contents of the frame.
     */
    private void initialize() {
        Thread.currentThread().setName("SphinxBlackjackApplet");
        setMinimumSize(new Dimension(800, 600));
        setMaximumSize(new Dimension(800, 600));
        setBounds(100, 100, 450, 300);

        CardImageManager.getInstance();
        GameManager gm = GameManager.getInstance();
        Player dealer = gm.getDealer();
        Player human = gm.getHuman();

        CardDrawPanel pnlCard = new CardDrawPanel(dealer, human);
        pnlCard.setOpaque(true);
        setContentPane(pnlCard);
        gm.setGui(pnlCard);
        gm.notifyObservers();
        gm.startSpeechEngine();
    }
}
