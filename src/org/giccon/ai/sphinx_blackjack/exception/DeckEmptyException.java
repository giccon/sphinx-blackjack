package org.giccon.ai.sphinx_blackjack.exception;

/**
 * Author: Paul Minasian
 */
public class DeckEmptyException extends RuntimeException {

    public DeckEmptyException(String message) {
        super(message);
    }
}
