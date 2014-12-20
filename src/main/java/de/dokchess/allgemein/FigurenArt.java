/*
 * Copyright (c) 2010-2014 Stefan Zoerner
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

public enum FigurenArt {

    KOENIG('K'), DAME('Q'), TURM('R'), LAEUFER('B'), SPRINGER('N'), BAUER('P');

    private FigurenArt(char buchstabe) {
        this.buchstabe = buchstabe;
    }

    private char buchstabe;

    public char getBuchstabe() {
        return buchstabe;
    }

    public static FigurenArt ausBuchstabe(char c) {
        switch (c) {
            case 'k':
            case 'K':
                return KOENIG;
            case 'q':
            case 'Q':
                return DAME;
            case 'r':
            case 'R':
                return TURM;
            case 'b':
            case 'B':
                return LAEUFER;
            case 'n':
            case 'N':
                return SPRINGER;
            case 'p':
            case 'P':
                return BAUER;
            default:
                return null;
        }
    }
}
