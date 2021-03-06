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
package org.giccon.ai.sphinx_blackjack.logic.card;

import java.awt.*;

/**
 * @author Paul Minasian
 */
public class StandardDeck extends Deck {

    public StandardDeck() {
        initActiveStack();
        shuffle();
    }

    private void initActiveStack() {
        CardImageManager cim = CardImageManager.getInstance();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                Image img = cim.getCardImage(rank, suit);
                Card c = new Card(rank, suit, img);
                activeStack.add(c);
            }
        }
    }
}
