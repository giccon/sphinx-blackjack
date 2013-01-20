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

/**
 * @author Paul Minasian
 */
public class Human extends Player {
    private final static int CASH_AMOUNT = 500;
    private int cash;
    private int bet;

    public Human() {
        initCash();
    }

    public void initCash() {
        this.cash = CASH_AMOUNT;
    }

    public int getCash() {
        return cash;
    }

    public int getBet() {
        return bet;
    }

    public void addBet(int cashAmount) {
        if (cashAmount >= cash) {
            bet += cash;
            cash = 0;
        } else {
            bet += cashAmount;
            cash -= cashAmount;
        }

    }

    public void withdrawBet(int cashAmount) {
        if (cashAmount >= bet) {
            cash += bet;
            bet = 0;
        } else {
            bet -= cashAmount;
            cash += cashAmount;
        }

    }
}
