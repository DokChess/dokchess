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

            damenZuege.fuegeZugkandidatenHinzu(A1, weisseDameA1, zuege);

            Assert.assertEquals(21, zuege.size());
            for (Zug zug : zuege) {
                Assert.assertEquals(A1, zug.getVon());
            }
            Feld[] ziele = {B1, C1, D1, E1, F1, G1, H1, A2, A3, A4, A5, A6,
                    A7, A8, B2, C3, D4, E5, F6, G7, H8};
            for (Feld ziel : ziele) {
                Zug zuTesten = new Zug(DAME_WEISS, A1, ziel);
                Assert.assertTrue(zuege.contains(zuTesten));
            }
        }

        {
            Stellung weisseDameH8 = new Stellung("7Q/8/8/8/8/8/8/8 w - - 0 1");

            DamenZuege damenZuege = new DamenZuege();
            List<Zug> zuege = new ArrayList<Zug>();

            damenZuege.fuegeZugkandidatenHinzu(H8, weisseDameH8, zuege);
            Assert.assertEquals(21, zuege.size());
            for (Zug zug : zuege) {
                Assert.assertEquals(H8, zug.getVon());
            }
            Feld[] ziele = {H1, H2, H3, H4, H5, H6, H7, A8, B8, C8, D8, E8,
                    F8, G8, A1, B2, C3, D4, E5, F6, G7};
            for (Feld ziel : ziele) {
                Zug zuTesten = new Zug(DAME_WEISS, H8, ziel);
                Assert.assertTrue(zuege.contains(zuTesten));
            }
        }
    }

    @Test
    public void dameImZentrum() {

        Stellung weisseDameD5 = new Stellung("8/8/8/3Q4/8/8/8/8 w - - 0 1");

        DamenZuege damenZuege = new DamenZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        damenZuege.fuegeZugkandidatenHinzu(D5, weisseDameD5, zuege);

        Assert.assertEquals(27, zuege.size());
        for (Zug zug : zuege) {
            Assert.assertEquals(D5, zug.getVon());
        }
        Feld[] ziele = {A5, B5, C5, E5, F5, G5, H5, D1, D2, D3, D4, D6, D7,
                D8, A2, B3, C4, E5, F7, G8};
        for (Feld ziel : ziele) {
            Zug zuTesten = new Zug(DAME_WEISS, D5, ziel);
            Assert.assertTrue(zuTesten.toString(), zuege.contains(zuTesten));
        }
    }

    @Test
    public void dameSchlaegt() {

        Stellung weisseDameSchlaegtZweiFiguren = new Stellung(
                "8/8/n2N4/8/8/3Q1pb1/8/1B6 w - - 0 1");

        DamenZuege damenZuege = new DamenZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        damenZuege.fuegeZugkandidatenHinzu(D3, weisseDameSchlaegtZweiFiguren,
                zuege);

        Assert.assertEquals(19, zuege.size());
        for (Zug zug : zuege) {
            Assert.assertEquals(D3, zug.getVon());
        }

        Zug schlaegtSchwarzenSpringer = new Zug(DAME_WEISS, D3, A6, true);
        Zug schlaegtSchwarzenBauern = new Zug(DAME_WEISS, D3, F3, true);

        Assert.assertTrue(schlaegtSchwarzenSpringer.toString(),
                zuege.contains(schlaegtSchwarzenSpringer));
        Assert.assertTrue(schlaegtSchwarzenBauern.toString(),
                zuege.contains(schlaegtSchwarzenBauern));
    }
}
