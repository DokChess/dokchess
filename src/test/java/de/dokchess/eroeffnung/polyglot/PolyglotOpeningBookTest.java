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

package de.dokchess.eroeffnung.polyglot;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

public class PolyglotOpeningBookTest {

    /**
     * Fehlende Datei erzeugt Exception.
     */
    @Test(expected = RuntimeException.class)
    public void dateiNichtGefunden() {
        File eingabe = new File("dateiGibtEsNicht.bin");
        new PolyglotOpeningBook(eingabe);
    }

    /**
     * Die Testdaten des Files enthalten lediglich eine Eroeffnung: Die
     * italienische Partie. 1. e2-e4 e7-e5 2. S g1-f3 S b8-c6 3. L f1-c4 L f8-c5
     */
    @Test
    public void italienischePartie() {

        InputStream is = getClass().getClassLoader().getResourceAsStream(
                "de/dokchess/eroeffnung/polyglot/italienischePartie.bin");

        PolyglotOpeningBook buch = new PolyglotOpeningBook(is);

        // 1. e2-e4
        Stellung stellung = new Stellung();
        Zug zug = buch.liefereZug(stellung);
        Assert.assertEquals("e2", zug.getVon().toString());
        Assert.assertEquals("e4", zug.getNach().toString());

        // 1. ... e7-e5
        stellung = stellung.fuehreZugAus(zug);
        zug = buch.liefereZug(stellung);
        Assert.assertEquals("e7", zug.getVon().toString());
        Assert.assertEquals("e5", zug.getNach().toString());

        // 2. S g1-f3
        stellung = stellung.fuehreZugAus(zug);
        zug = buch.liefereZug(stellung);
        Assert.assertEquals("g1", zug.getVon().toString());
        Assert.assertEquals("f3", zug.getNach().toString());

    }

}
