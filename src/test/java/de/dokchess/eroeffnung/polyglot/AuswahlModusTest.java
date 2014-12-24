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
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class AuswahlModusTest {

    PolyglotOpeningBook buch = null;

    @Before
    public void buchLaden() {
        InputStream is = getClass().getClassLoader().getResourceAsStream(
                "de/dokchess/eroeffnung/polyglot/demoBook.bin");
        buch = new PolyglotOpeningBook(is);
    }


    @Test
    public void haeufigster() {
        buch.setAuswahlModus(AuswahlModus.HAEUFIGSTER);

        Stellung stellung = new Stellung();
        List<BookEntry> eintraege = buch.findEntriesByFen(stellung.toString());

        // Zug mit hoechstem Gewicht in der Liste ermitteln
        int maxGewicht = Integer.MIN_VALUE;
        BookEntry zug1 = null;
        for (BookEntry e : eintraege) {
            if (e.getWeightAsInt() > maxGewicht) {
                zug1 = e;
                maxGewicht = e.getWeightAsInt();
            }
        }

        // Pruefen, dass der geliefert wird.
        Zug zug2 = buch.liefereZug(stellung);
        Assert.assertEquals(zug1.getMoveFrom(), zug2.getVon().toString());
        Assert.assertEquals(zug1.getMoveTo(), zug2.getNach().toString());
    }

    @Test
    public void erster() {
        buch.setAuswahlModus(AuswahlModus.ERSTER);

        Stellung stellung = new Stellung();
        List<BookEntry> eintraege = buch.findEntriesByFen(stellung.toString());
        BookEntry zug1 = eintraege.get(0);

        Zug zug2 = buch.liefereZug(stellung);
        Assert.assertEquals(zug1.getMoveFrom(), zug2.getVon().toString());
        Assert.assertEquals(zug1.getMoveTo(), zug2.getNach().toString());
    }

    @Test
    public void zufaellig() {
        buch.setAuswahlModus(AuswahlModus.PER_ZUFALL);

        Stellung stellung = new Stellung();
        List<BookEntry> eintraege = buch.findEntriesByFen(stellung.toString());
        BookEntry zug = eintraege.get(0);

        Assert.assertNotNull(zug);
    }
}
