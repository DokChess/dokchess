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
 * Farben der Spieler als Aufzaehlung.
 *
 * @author StefanZ
 */
public enum Farbe {

    /**
     * Spielerfarbe schwarz.
     */
    SCHWARZ,

    /**
     * Spielerfarbe weiss.
     */
    WEISS;

    /**
     * Liefert zu einer Farbe die andere Farbe.
     *
     * @return die andere Farbe.
     */
    public Farbe andereFarbe() {
        switch (this) {
            case WEISS:
                return SCHWARZ;
            case SCHWARZ:
                return WEISS;
            default:
                throw new IllegalStateException("Ungueltige Farbe");
        }
    }
}
