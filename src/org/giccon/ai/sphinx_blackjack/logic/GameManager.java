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
import org.giccon.ai.sphinx_blackjack.card.Deck;
import org.giccon.ai.sphinx_blackjack.card.StandardDeck;
import org.giccon.ai.sphinx_blackjack.logic.gamestate.*;

import java.util.Observable;

/**
 * Author: Paul Minasian
 */
public class GameManager extends Observable {

    private static final GameManager INSTANCE = new GameManager();

    private final GameState gameIdleState = new GameIdleState();
    private final GameState humanPlayingState = new HumanPlayingState();
    private final GameState dealerPlayingState = new DealerPlayingState();
    private final GameState gameRoundEndState = new GameRoundEndState();
    private final GameState gameOverState = new GameOverState();
    private GameState gameState;

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

        // Game enters the game idle state.
        gameState = new GameIdleState();
        notifyObservers(GameStateChanged.GAME_IDLE_STATE);
    }

    public static GameManager getInstance() {
        return INSTANCE;
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
}
