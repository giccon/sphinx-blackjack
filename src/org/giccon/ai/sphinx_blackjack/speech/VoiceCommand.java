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

package org.giccon.ai.sphinx_blackjack.speech;

/**
 * Author: Paul Minasian
 */
public enum VoiceCommand {
    CORRECT("correct"),
    WRONG("wrong"),
    PLUS_ONE("plus one"),
    PLUS_FIVE("plus five"),
    PLUS_TEN("plus ten"),
    PLUS_TWENTY_FIVE("plus twenty five"),
    PLUS_FIFTY("plus fifty"),
    PLUS_HUNDRED("plus hundred"),
    MINUS_ONE("minus one"),
    MINUS_FIVE("minus five"),
    MINUS_TEN("minus ten"),
    MINUS_TWENTY_FIVE("minus twenty five"),
    MINUS_FIFTY("minus fifty"),
    MINUS_HUNDRED("minus hundred"),
    DEAL("deal"),
    HIT("hit"),
    STAND("stand"),
    DOUBLE("double"),
    SPLIT("split"),
    RESTART("restart"),
    HELP("help"),
    QUIT("quit"),
    UNRECOGNIZED("");
    private String command;

    private VoiceCommand(String command) {
        this.command = command;
    }

    public static VoiceCommand getVoiceCommand(String textCommand) {
        for (VoiceCommand c : VoiceCommand.values()) {
            if (c.command.equals(textCommand)) {
                return c;
            }
        }

        return UNRECOGNIZED;
    }
}
