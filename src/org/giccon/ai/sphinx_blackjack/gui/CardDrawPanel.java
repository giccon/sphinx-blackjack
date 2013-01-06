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

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import org.giccon.ai.sphinx_blackjack.card.Card;
import org.giccon.ai.sphinx_blackjack.card.CardImageManager;
import org.giccon.ai.sphinx_blackjack.card.Rank;
import org.giccon.ai.sphinx_blackjack.card.Suit;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Minasian
 * 
 */
public class CardDrawPanel extends JPanel {

	private static final long serialVersionUID = -4534795109399774313L;

	private CardImageManager cim;
	
	public CardDrawPanel() {
		setPreferredSize(new Dimension(800, 600));
		cim = CardImageManager.getInstance();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		drawDeckCards();
		drawDealerCards();
		drawPlayerCards();
		
		int cardWidth = cim.getCardWidth();
		int cardHeight = cim.getCardHeight();
		int x, y = 0;
		for (Suit suit : Suit.values()) {
			x = 0;
			for (Rank rank : Rank.values()) {
				Image img = cim.getCardImage(rank, suit);
				g.drawImage(img, x * cardWidth, y * cardHeight, (x + 1)
						* cardWidth, (y + 1) * cardHeight, 0, 0, cardWidth,
						cardHeight, null);
				x++;
			}
			y++;
		}

	}

	/**
	 * 
	 */
	private void drawPlayerCards() {
		
		
	}

	/**
	 * 
	 */
	private void drawDealerCards() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	private void drawDeckCards() {
		// TODO Auto-generated method stub
		
	}
}
