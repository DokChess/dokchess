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

/**
 * Figurenarten als Aufzaehlung.
 *
 * @author StefanZ
 */
public enum FigurenArt {

    KOENIG('K'), DAME('Q'), TURM('R'), LAEUFER('B'), SPRINGER('N'), BAUER('P');

    private char buchstabe;

    private FigurenArt(char buchstabe) {
        this.buchstabe = buchstabe;
    }

    /**
     * Liegert die passende Figurenart zu einem Buchstaben, wie er in englischen
     * Schachnotationen Verwendung findet, zurueck. Kann klein oder gross sein.
     *
     * @param c Buchstabe, z.B. 'q' fuer Queen (Dame).
     * @return die Figurenart.
     * @throws java.lang.IllegalArgumentException kein zulaessiger Buchstabe.
     */
    public static FigurenArt ausBuchstabe(final char c) {
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
                throw new IllegalArgumentException("keine zulaessige Figurenart");
        }
    }

    /**
     * Liefert den Buchstaben der Figurenart, wie er in englischen Notationen
     * Verwendung findet, zurueck. Zum Beispiel Q fuer Queen (Dame).
     *
     * @return Buchstabe zur Figurenart
     */
    public char getBuchstabe() {
        return buchstabe;
    }
}
