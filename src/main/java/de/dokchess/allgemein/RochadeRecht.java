/*
 * Copyright (c) 2010-2021 Stefan Zoerner
 *
 * This file is part of DokChess.
 *
 * DokChess is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DokChess is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DokChess.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.dokchess.allgemein;

/**
 * Die verschiedenen Rochaderechte als Aufzaehlung.
 */
public enum RochadeRecht {
    WEISS_KURZ('K'), WEISS_LANG('Q'), SCHWARZ_KURZ('k'), SCHWARZ_LANG('q');

    private char buchstabe;

    private RochadeRecht(char buchstabe) {
        this.buchstabe = buchstabe;
    }

    /**
     * Liefert zur Kurznotion aus FEN das passende Rocahderecht.
     *
     * @param c Buchstabe, 'K', 'Q', 'k' oder 'q'
     * @return das passende Rochaderecht, oder null
     */
    public static RochadeRecht ausBuchstabe(char c) {
        switch (c) {
            case 'K':
                return WEISS_KURZ;
            case 'Q':
                return WEISS_LANG;
            case 'k':
                return SCHWARZ_KURZ;
            case 'q':
                return SCHWARZ_LANG;
        }
        return null;
    }

    /**
     * Liefert das Rochaderecht in Kurznotation, also ein Buchstabe, f&uuml;r FEN.
     * Beispiel: 'K' f&uuml;r weisse, kurze Rochade. 'q' f&uuml;r schwarze, lange Rochade.
     *
     * @return Rochaderecht als Buchstabe.
     */
    public char alsBuchstabe() {
        return this.buchstabe;
    }
}
