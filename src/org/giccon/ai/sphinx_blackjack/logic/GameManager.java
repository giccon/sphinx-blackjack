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
package org.giccon.ai.sphinx_blackjack.logic;

import org.giccon.ai.sphinx_blackjack.Dealer;
import org.giccon.ai.sphinx_blackjack.Human;
import org.giccon.ai.sphinx_blackjack.card.Card;
import org.giccon.ai.sphinx_blackjack.card.Deck;
import org.giccon.ai.sphinx_blackjack.card.StandardDeck;

import java.util.List;

/**
 * Author: Paul Minasian
 */
public class GameManager {

    private static final GameManager instance = new GameManager();

    private ScoreCalculator scoreCalculator = new ScoreCalculator();

    private Deck deck = new StandardDeck();

    private Dealer dealer = new Dealer();

    private Human human = new Human();

    private GameManager() {
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

    public int getNumberOfCardsLeft() {
        return deck.getNumberOfCards();
    }

    public HandScore getHandScore(List<Card> hand) {
        return scoreCalculator.calculateScore();
    }
}
