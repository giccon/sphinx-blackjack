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
package org.giccon.ai.sphinx_blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.giccon.ai.sphinx_blackjack.card.Card;

/**
 * @author Paul Minasian
 *
 */
public abstract class Player {
	
	private List<Card> hand;
	
	public Player()	{
		hand = new ArrayList<Card>();
	}
	
	public List<Card> getHand() {
		return Collections.unmodifiableList(hand);
	}
	
	public void receiveCard(Card card)
	{
		hand.add(card);
	}
	
	public void returnHand() {
		hand.clear();
	}
	
	public int getScore() {
		return 0;
	}
	
	public boolean hasBlackjackHand() {
		return true;
	}
}