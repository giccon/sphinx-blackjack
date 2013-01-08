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
package org.giccon.ai.sphinx_blackjack.card;

import java.awt.*;

/**
 * @author Paul Minasian
 */
public class Card {

    private Rank rank;

    private Suit suit;

    private Image img;

    public Card(Rank rank, Suit suit, Image img) {
        this.rank = rank;
        this.suit = suit;
        this.img = img;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Image getImg() {
        return img;
    }

    public boolean isAce() {
        return rank.equals(Rank.ACE);
    }
}
