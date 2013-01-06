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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import org.giccon.ai.sphinx_blackjack.card.CardImageManager;

/**
 * The main application entry.
 * 
 * @author Paul Minasian
 * 
 */
public class SphinxBlackjackGui {

	private JFrame frmSphinxBlackjack;

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
	 * Create the application.
	 */
	public SphinxBlackjackGui() {
		initialize();
		
		CardImageManager.getInstance();
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
		frmSphinxBlackjack.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CardDrawPanel pnlCard = new CardDrawPanel();
		frmSphinxBlackjack.getContentPane().add(pnlCard, BorderLayout.CENTER);
	}
}
