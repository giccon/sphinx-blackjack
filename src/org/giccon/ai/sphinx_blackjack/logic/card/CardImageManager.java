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

import org.giccon.ai.sphinx_blackjack.exception.SystemFaultException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Paul Minasian
 */
public class CardImageManager {

    private static final String IMAGE_PATH = ""
            + "org/giccon/ai/sphinx_blackjack/assets/images/cards.png";

    private static final CardImageManager INSTANCE = new CardImageManager();

    private Map<Rank, Map<Suit, Image>> cardImages;
    private Image backOfCardImage;

    private int cardWidth;
    private int cardHeight;

    private CardImageManager() {
        loadCardImages();
    }

    private void loadCardImages() {
        URL cardsUrl = ClassLoader.getSystemResource(IMAGE_PATH);
        BufferedImage bi;
        try {
            bi = ImageIO.read(cardsUrl);
        } catch (IOException e) {
            throw new SystemFaultException("Cannot load "
                    + "the cards image file", e);
        }

        cardImages = new HashMap<Rank, Map<Suit, Image>>();

        int x, y = 0;
        cardWidth = bi.getWidth() / 13;
        cardHeight = bi.getHeight() / 5;
        for (Suit suit : Suit.values()) {
            x = 0;
            for (Rank rank : Rank.values()) {
                BufferedImage cardImage = bi.getSubimage(x++ * cardWidth, y
                        * cardHeight, cardWidth, cardHeight);

                Map<Suit, Image> images = cardImages.get(rank);
                if (images == null) {
                    images = new HashMap<Suit, Image>();
                }
                images.put(suit, cardImage);
                cardImages.put(rank, images);
            }
            y++;
        }

        backOfCardImage = bi.getSubimage(2 * cardWidth, 4 * cardHeight,
                cardWidth, cardHeight);
    }

    public static CardImageManager getInstance() {
        return INSTANCE;
    }

    public Image getCardImage(Rank rank, Suit suit) {
        return cardImages.get(rank).get(suit);
    }

    public Image getBackOfCardImage() {
        return backOfCardImage;
    }

    public int getCardWidth() {
        return cardWidth;
    }

    public int getCardHeight() {
        return cardHeight;
    }
}
