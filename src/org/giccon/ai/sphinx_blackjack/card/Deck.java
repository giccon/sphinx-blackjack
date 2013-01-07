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
package org.giccon.ai.sphinx_blackjack.card;

import org.giccon.ai.sphinx_blackjack.exception.DeckEmptyException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Paul Minasian
 */
public abstract class Deck {
    protected List<Card> activeStack = new ArrayList<Card>();
    protected List<Card> passiveStack = new ArrayList<Card>();

    public Deck() {
    }

    public void shuffle() {
        Collections.shuffle(activeStack, new SecureRandom());
    }

    public void reset() {
        activeStack.addAll(passiveStack);
        passiveStack.clear();
        shuffle();
    }

    public boolean isEmpty() {
        return activeStack.isEmpty();
    }

    public Card dealCard() throws DeckEmptyException {
        if (activeStack.isEmpty()) {
            throw new DeckEmptyException("Deck is empty");
        }

        Card c = activeStack.remove(activeStack.size() - 1);
        passiveStack.add(c);

        return c;
    }
}
