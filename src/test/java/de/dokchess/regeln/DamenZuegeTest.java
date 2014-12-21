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
package de.dokchess.regeln;

import de.dokchess.allgemein.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static de.dokchess.allgemein.Felder.*;

public class DamenZuegeTest {

    private static final Figur DAME_WEISS = new Figur(FigurenArt.DAME,
            Farbe.WEISS);

    @Test
    public void dameInDerEcke() {

        {
            Stellung weisseDameA1 = new Stellung("8/8/8/8/8/8/8/Q7 w - - 0 1");

            DamenZuege damenZuege = new DamenZuege();
            List<Zug> zuege = new ArrayList<Zug>();

            damenZuege.fuegeZugkandidatenHinzu(a1, weisseDameA1, zuege);

            Assert.assertEquals(21, zuege.size());
            for (Zug zug : zuege) {
                Assert.assertEquals(a1, zug.getVon());
            }
            Feld[] ziele = {b1, c1, d1, e1, f1, g1, h1, a2, a3, a4, a5, a6,
                    a7, a8, b2, c3, d4, e5, f6, g7, h8};
            for (Feld ziel : ziele) {
                Zug zuTesten = new Zug(DAME_WEISS, a1, ziel);
                Assert.assertTrue(zuege.contains(zuTesten));
            }
        }

        {
            Stellung weisseDameH8 = new Stellung("7Q/8/8/8/8/8/8/8 w - - 0 1");

            DamenZuege damenZuege = new DamenZuege();
            List<Zug> zuege = new ArrayList<Zug>();

            damenZuege.fuegeZugkandidatenHinzu(h8, weisseDameH8, zuege);
            Assert.assertEquals(21, zuege.size());
            for (Zug zug : zuege) {
                Assert.assertEquals(h8, zug.getVon());
            }
            Feld[] ziele = {h1, h2, h3, h4, h5, h6, h7, a8, b8, c8, d8, e8,
                    f8, g8, a1, b2, c3, d4, e5, f6, g7};
            for (Feld ziel : ziele) {
                Zug zuTesten = new Zug(DAME_WEISS, h8, ziel);
                Assert.assertTrue(zuege.contains(zuTesten));
            }
        }
    }

    @Test
    public void dameImZentrum() {

        Stellung weisseDameD5 = new Stellung("8/8/8/3Q4/8/8/8/8 w - - 0 1");

        DamenZuege damenZuege = new DamenZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        damenZuege.fuegeZugkandidatenHinzu(d5, weisseDameD5, zuege);

        Assert.assertEquals(27, zuege.size());
        for (Zug zug : zuege) {
            Assert.assertEquals(d5, zug.getVon());
        }
        Feld[] ziele = {a5, b5, c5, e5, f5, g5, h5, d1, d2, d3, d4, d6, d7,
                d8, a2, b3, c4, e5, f7, g8};
        for (Feld ziel : ziele) {
            Zug zuTesten = new Zug(DAME_WEISS, d5, ziel);
            Assert.assertTrue(zuTesten.toString(), zuege.contains(zuTesten));
        }
    }

    @Test
    public void dameSchlaegt() {

        Stellung weisseDameSchlaegtZweiFiguren = new Stellung(
                "8/8/n2N4/8/8/3Q1pb1/8/1B6 w - - 0 1");

        DamenZuege damenZuege = new DamenZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        damenZuege.fuegeZugkandidatenHinzu(d3, weisseDameSchlaegtZweiFiguren,
                zuege);

        Assert.assertEquals(19, zuege.size());
        for (Zug zug : zuege) {
            Assert.assertEquals(d3, zug.getVon());
        }

        Zug schlaegtSchwarzenSpringer = new Zug(DAME_WEISS, d3, a6, true);
        Zug schlaegtSchwarzenBauern = new Zug(DAME_WEISS, d3, f3, true);

        Assert.assertTrue(schlaegtSchwarzenSpringer.toString(),
                zuege.contains(schlaegtSchwarzenSpringer));
        Assert.assertTrue(schlaegtSchwarzenBauern.toString(),
                zuege.contains(schlaegtSchwarzenBauern));
    }
}
