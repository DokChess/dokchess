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
package de.dokchess.engine;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.eroeffnung.Eroeffnungsbibliothek;

class AusBibliothek extends ZugAuswaehlen {

    private Eroeffnungsbibliothek bibliothek;

    public AusBibliothek(Eroeffnungsbibliothek bibliothek,
                         ZugAuswaehlen nachfolger) {
        super(nachfolger);
        this.bibliothek = bibliothek;
    }

    @Override
    public Zug waehleZug(Stellung stellung) {
        Zug zug = bibliothek.liefereZug(stellung);
        return (zug != null) ? zug : super.waehleZug(stellung);
    }
}
