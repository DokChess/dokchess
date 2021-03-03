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

package de.dokchess.engine.bewertung;

import de.dokchess.allgemein.Farbe;
import de.dokchess.allgemein.Stellung;

/**
 * Bewertung einer Stellung aus Sicht eines Spielers.
 *
 * @author StefanZ
 */
public interface Bewertung {

    /**
     * Bestm&ouml;glicher Wert.
     */
    int AM_BESTEN = Integer.MAX_VALUE;

    /**
     * Schlechtestm&ouml;glicher Wert.
     */
    int AM_SCHLECHTESTEN = Integer.MIN_VALUE;

    /**
     * Wert fuer eine ausgeglichene Stellung.
     */
    int AUSGEGLICHEN = 0;

    /**
     * Liefert zur gegebenen Stellung eine Bewertung aus Sicht der angegebenen
     * Spielerfarbe. Je h&ouml;her, desto besser.
     *
     * @param stellung zu bewertende Spielsituation
     * @param ausSicht Spieler, aus desen Sicht bewertet wird
     * @return Bewertung, 0 ist ausgeglichen, je h&ouml;her desto besser fuer
     * den Spieler
     */
    int bewerteStellung(Stellung stellung, Farbe ausSicht);
}
