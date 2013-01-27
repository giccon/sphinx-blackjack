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
    BET_ALL("bet all"),
    BET_ONE("bet one"),
    BET_FIVE("bet five"),
    BET_TEN("bet ten"),
    BET_TWENTY_FIVE("bet twenty five"),
    BET_FIFTY("bet fifty"),
    BET_HUNDRED("bet hundred"),
    TAKE_ALL("take all"),
    TAKE_ONE("take one"),
    TAKE_FIVE("take five"),
    TAKE_TEN("take ten"),
    TAKE_TWENTY_FIVE("take twenty five"),
    TAKE_FIFTY("take fifty"),
    TAKE_HUNDRED("take hundred"),
    DEAL("go deal"),
    HIT("go hit"),
    STAND("go stand"),
    DOUBLE("go double"),
    SPLIT("go split"),
    SURRENDER("go surrender"),
    RESTART("restart"),
    HELP("go help"),
    QUIT("go quit"),
    UNRECOGNIZED("unrecognized");
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

    public String getCommand() {
        return command;
    }
}
