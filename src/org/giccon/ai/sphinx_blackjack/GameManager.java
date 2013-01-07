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

import org.giccon.ai.sphinx_blackjack.card.Deck;
import org.giccon.ai.sphinx_blackjack.card.StandardDeck;

/**
 * Author: Paul Minasian
 */
public class GameManager {

    private static final GameManager instance = new GameManager();
    Deck deck;
    private Dealer dealer;
    private Human human;

    private GameManager() {
        deck = new StandardDeck();

        dealer = new Dealer();
        human = new Human();

        for (int i = 0; i < 10; i++) {
            if (!deck.isEmpty()) {
                human.receiveCard(deck.dealCard());
            }
        }

        for (int i = 0; i < 2; i++) {
            if (!deck.isEmpty()) {
                dealer.receiveCard(deck.dealCard());
            }
        }
    }

    public static GameManager getInstance() {
        return instance;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Human getHuman() {
        return human;
    }


}
