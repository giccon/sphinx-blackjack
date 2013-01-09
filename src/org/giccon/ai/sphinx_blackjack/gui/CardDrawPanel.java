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
package org.giccon.ai.sphinx_blackjack.gui;

import org.giccon.ai.sphinx_blackjack.Player;
import org.giccon.ai.sphinx_blackjack.rendering.Renderer;

import javax.swing.*;
import java.awt.*;

/**
 * @author Paul Minasian
 */
public class CardDrawPanel extends JPanel {

    private static final long serialVersionUID = -4534795109399774313L;
    private Renderer renderer;

    public CardDrawPanel(Player dealer, Player human) {
        setPreferredSize(new Dimension(800, 600));

        renderer = new Renderer(this, dealer, human);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        renderer.render(g);
    }
}
