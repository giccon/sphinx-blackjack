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

import javax.swing.*;
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
    private Component gui;
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
    }

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public void startSpeechEngine() {
        try {
            if (srm.getState() == SpeechRecognitionManager.State.DEINITIALIZED) {
                srm.initSpeechRecognitionEngine();
            }
            if (srm.getState() == SpeechRecognitionManager.State.INITIALIZED) {
                srm.startSpeechRecognitionEngine();
                srm.addResultListener(resultListenerHandler);
            }
        } catch (SpeechRecognitionException e) {
            showErrorAndExitProgram(e);
        } catch (MicrophoneException e) {
            showErrorAndExitProgram(e);
        }
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

    public void setGui(Component gui) {
        this.gui = gui;
    }

    public void fireStateChange(GameStateChanged arg) {
        setChanged();
        notifyObservers(arg);
    }

    public void fireVoiceCommand(VoiceCommand arg) {
        setChanged();
        notifyObservers(arg);
    }

    public Winner getWinner() {
        if (human.hasBlackjackHand() && dealer.hasBlackjackHand()) {
            return Winner.PUSH;
        }

        if (human.hasBlackjackHand()) {
            return Winner.HUMAN;
        }

        if (dealer.hasBlackjackHand()) {
            return Winner.DEALER;
        }

        int humanScore = human.getFinalScore();
        if (humanScore > 21) {
            return Winner.DEALER;
        }

        int dealerScore = dealer.getFinalScore();
        if (humanScore == dealerScore) {
            return Winner.PUSH;
        } else if (dealerScore > 21) {
            return Winner.HUMAN;
        } else if (dealerScore > humanScore) {
            return Winner.DEALER;
        }

        return Winner.HUMAN;
    }

    private void showErrorAndExitProgram(Exception e) {
        if (gui != null) {
            JOptionPane.showMessageDialog(gui,
                    "An error took place: " + e.getMessage() + ". Going to exit the program now.");
        }
        System.exit(-1);
    }

    private void handleVoiceCommand(VoiceCommand vc) {
        if (vc != VoiceCommand.UNRECOGNIZED) {
            fireVoiceCommand(vc);
        }

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
            case SURRENDER:
                gameState.surrender();
                break;
            case BET_ALL:
                gameState.addBet(human.getCash());
                break;
            case BET_ONE:
                gameState.addBet(1);
                break;
            case BET_FIVE:
                gameState.addBet(5);
                break;
            case BET_TEN:
                gameState.addBet(10);
                break;
            case BET_TWENTY_FIVE:
                gameState.addBet(25);
                break;
            case BET_FIFTY:
                gameState.addBet(50);
                break;
            case BET_HUNDRED:
                gameState.addBet(100);
                break;
            case TAKE_ALL:
                gameState.withdrawBet(human.getBet());
                break;
            case TAKE_ONE:
                gameState.withdrawBet(1);
                break;
            case TAKE_FIVE:
                gameState.withdrawBet(5);
                break;
            case TAKE_TEN:
                gameState.withdrawBet(10);
                break;
            case TAKE_TWENTY_FIVE:
                gameState.withdrawBet(25);
                break;
            case TAKE_FIFTY:
                gameState.withdrawBet(50);
                break;
            case TAKE_HUNDRED:
                gameState.withdrawBet(100);
                break;
            case RESTART:
                gameState.restart();
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

    public static enum Winner {
        DEALER, HUMAN, PUSH
    }

    private class ResultListenerHandler implements ResultListener {
        @Override
        public void newResult(Result result) {
            final String command = result.getBestFinalResultNoFiller();
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        handleVoiceCommand(VoiceCommand.getVoiceCommand(command));
                    } catch (Exception ignore) {
                        // ignore
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
