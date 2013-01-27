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
 * along with Sphinx Blackjack. If not, see <http://www.gnu.org/licenses/>.
 */

package org.giccon.ai.sphinx_blackjack.rendering;

import org.giccon.ai.sphinx_blackjack.logic.GameManager;
import org.giccon.ai.sphinx_blackjack.logic.Player;
import org.giccon.ai.sphinx_blackjack.logic.gamestate.GameStateChanged;
import org.giccon.ai.sphinx_blackjack.speech.VoiceCommand;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Author: Paul Minasian
 */
public class Renderer implements Observer {
    private final JPanel canvas;
    private final RenderState gameIdleState;
    private final RenderState humanPlayingState;
    private final RenderState dealerPlayingState;
    private final RenderState gameRoundEndState;
    private final RenderState gameOverState;
    private RenderState renderState;

    public Renderer(JPanel canvas, Player dealer, Player human) {
        this.canvas = canvas;
        gameIdleState = new GameIdleState(canvas, dealer, human);
        humanPlayingState = new HumanPlayingState(canvas, dealer, human);
        dealerPlayingState = new DealerPlayingState(canvas, dealer, human);
        gameRoundEndState = new GameRoundEndState(canvas, dealer, human);
        gameOverState = new GameOverState(canvas, dealer, human);
        renderState = gameIdleState;

        GameManager.getInstance().addObserver(this);
    }

    public void render(Graphics g) {
        renderState.render(g);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GameManager) {
            if (arg == null) {
                canvas.repaint();
            } else if (arg instanceof GameStateChanged) {
                setRenderState((GameStateChanged) arg);
                canvas.repaint();
            } else if (arg instanceof VoiceCommand) {
                RenderState.setVoiceCommand((VoiceCommand) arg);
                canvas.repaint();
            }
        }
    }

    private void setRenderState(GameStateChanged gameStateChanged) {
        switch (gameStateChanged) {
            case GAME_IDLE_STATE:
                renderState = gameIdleState;
                break;
            case HUMAN_PLAYING_STATE:
                renderState = humanPlayingState;
                break;
            case DEALER_PLAYING_STATE:
                renderState = dealerPlayingState;
                break;
            case GAME_ROUND_END_STATE:
                renderState = gameRoundEndState;
                break;
            case GAME_OVER_STATE:
                renderState = gameOverState;
                break;
        }
    }
}
