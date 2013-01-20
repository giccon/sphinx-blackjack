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

import edu.cmu.sphinx.decoder.ResultListener;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.PropertyException;
import edu.cmu.sphinx.util.props.PropertySheet;
import org.giccon.ai.sphinx_blackjack.logic.card.Deck;
import org.giccon.ai.sphinx_blackjack.logic.card.StandardDeck;
import org.giccon.ai.sphinx_blackjack.logic.gamestate.*;
import org.giccon.ai.sphinx_blackjack.speech.MicrophoneException;
import org.giccon.ai.sphinx_blackjack.speech.SpeechRecognitionException;
import org.giccon.ai.sphinx_blackjack.speech.SpeechRecognitionManager;
import org.giccon.ai.sphinx_blackjack.speech.VoiceCommand;

import java.awt.*;
import java.util.Observable;

/**
 * Author: Paul Minasian
 */
public class GameManager extends Observable {

    private static final GameManager INSTANCE = new GameManager();
    private final GameState gameIdleState;
    private final GameState humanPlayingState;
    private final GameState dealerPlayingState;
    private final GameState gameRoundEndState;
    private final GameState gameOverState;
    private final ResultListenerHandler resultListenerHandler;
    private final SpeechRecognitionManager srm;
    private final Deck deck;
    private final Dealer dealer;
    private final Human human;
    private GameState gameState;

    private GameManager() {
        deck = new StandardDeck();
        dealer = new Dealer();
        human = new Human();

        gameIdleState = new GameIdleState(this, deck, dealer, human);
        humanPlayingState = new HumanPlayingState(this, deck, dealer, human);
        dealerPlayingState = new DealerPlayingState(this, deck, dealer, human);
        gameRoundEndState = new GameRoundEndState(this, deck, dealer, human);
        gameOverState = new GameOverState(this, deck, dealer, human);
        resultListenerHandler = new ResultListenerHandler();
        srm = SpeechRecognitionManager.getInstance();

        // Game enters the game idle state.
        gameState = gameIdleState;
        fireStateChange(GameStateChanged.GAME_IDLE_STATE);

        try {
            srm.initSpeechRecognitionEngine();
            srm.startSpeechRecognitionEngine();
            srm.addResultListener(resultListenerHandler);
        } catch (SpeechRecognitionException ignore) {
            // todo handle speech recognition exception
        } catch (MicrophoneException ignore) {
            // todo handle microphone exception
        }
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

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameIdleState() {
        return gameIdleState;
    }

    public GameState getHumanPlayingState() {
        return humanPlayingState;
    }

    public GameState getDealerPlayingState() {
        return dealerPlayingState;
    }

    public GameState getGameRoundEndState() {
        return gameRoundEndState;
    }

    public GameState getGameOverState() {
        return gameOverState;
    }

    public void handleVoiceCommand(VoiceCommand vc) {
        switch (vc) {
            case DEAL:
                gameState.deal();
                break;
            case HIT:
                gameState.hit();
                break;
            case STAND:
                gameState.stand();
                break;
            case SPLIT:
                break;
            case DOUBLE:
                break;
            case PLUS_ONE:
                gameState.addBet(1);
                break;
            case PLUS_FIVE:
                gameState.addBet(5);
                break;
            case PLUS_TEN:
                gameState.addBet(10);
                break;
            case PLUS_TWENTY_FIVE:
                gameState.addBet(25);
                break;
            case PLUS_FIFTY:
                gameState.addBet(50);
                break;
            case PLUS_HUNDRED:
                gameState.addBet(100);
                break;
            case MINUS_ONE:
                gameState.withdrawBet(1);
                break;
            case MINUS_FIVE:
                gameState.withdrawBet(5);
                break;
            case MINUS_TEN:
                gameState.withdrawBet(10);
                break;
            case MINUS_TWENTY_FIVE:
                gameState.withdrawBet(25);
                break;
            case MINUS_FIFTY:
                gameState.withdrawBet(50);
                break;
            case MINUS_HUNDRED:
                gameState.withdrawBet(100);
                break;
            case RESTART:
                break;
            case HELP:
                break;
            case QUIT: {
/*                try {
                    srm.removeResultListener(resultListenerHandler);
                    srm.stopSpeechRecognitionEngine();
                    srm.shutdownSpeechRecognitionEngine();
                } catch (SpeechRecognitionException ignore) {
                    // ignore
                }*/
                break;
            }
            case UNRECOGNIZED:
                break;
            default:
                break;
        }
    }

    public void fireStateChange(GameStateChanged arg) {
        setChanged();
        notifyObservers(arg);
    }

    private class ResultListenerHandler implements ResultListener {
        @Override
        public void newResult(Result result) {
            final String command = result.getBestFinalResultNoFiller();
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        handleVoiceCommand(VoiceCommand.getVoiceCommand(command));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void newProperties(PropertySheet ps) throws PropertyException {
            // n/a
        }
    }
}
