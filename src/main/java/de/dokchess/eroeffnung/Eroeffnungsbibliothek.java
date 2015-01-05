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

package de.dokchess.eroeffnung;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;

public interface Eroeffnungsbibliothek {

    /**
     * Liefert zur angegebenen Stellung einen aus der Bibliothek bekannten Zug,
     * oder null.
     *
     * @param stellung zu betrachtende Stellung
     * @return passender Zug, oder null, falls keiner bekannt
     */
    Zug liefereZug(Stellung stellung);
}
