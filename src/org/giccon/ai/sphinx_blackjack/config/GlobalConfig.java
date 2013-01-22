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

package org.giccon.ai.sphinx_blackjack.config;

/**
 * Author: Paul Minasian
 */
public final class GlobalConfig {
    private static final int DEALER_STAND_SCORE = 17;
    private static final double BLACKJACK_PAYOUT = 1.5;
    private static final int PLAYER_CASH_AMOUNT = 500;
    private static final int BLACKJACK_WIN_SCORE = 21;

    public static int getDealerStandScore() {
        return DEALER_STAND_SCORE;
    }

    public static double getBlackjackPayout() {
        return BLACKJACK_PAYOUT;
    }

    public static int getPlayerCashAmount() {
        return PLAYER_CASH_AMOUNT;
    }

    public static int getBlackjackWinScore() {
        return BLACKJACK_WIN_SCORE;
    }
}
