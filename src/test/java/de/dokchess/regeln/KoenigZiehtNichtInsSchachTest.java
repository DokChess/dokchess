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
package de.dokchess.regeln;

import de.dokchess.allgemein.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

import static de.dokchess.allgemein.Felder.*;

public class KoenigZiehtNichtInsSchachTest {

    private static final Figur KOENIG_WEISS = new Figur(FigurenArt.KOENIG,
            Farbe.WEISS);

    @Test
    public void koenigZiehtNichtInsSchachDurchAnderenKoenig() {

        // Schwarzer Koenig in Opposition
        String fen = "8/8/3k4/8/3K4/8/8/8 w - - 0 1";

        Stellung stellung = new Stellung(fen);
        Spielregeln regeln = new DefaultSpielregeln();
        Collection<Zug> zuege = regeln.liefereGueltigeZuege(stellung);

        Zug illegal1 = new Zug(KOENIG_WEISS, d4, c5);
        Zug illegal2 = new Zug(KOENIG_WEISS, d4, d5);
        Zug illegal3 = new Zug(KOENIG_WEISS, d4, e5);

        Assert.assertFalse(zuege.contains(illegal1));
        Assert.assertFalse(zuege.contains(illegal2));
        Assert.assertFalse(zuege.contains(illegal3));

        Zug legal = new Zug(KOENIG_WEISS, d4, d3);
        Assert.assertTrue(zuege.contains(legal));

        Assert.assertEquals(5, zuege.size());

    }

    @Test
    public void koenigZiehtNichtInsSchachDurchLaeufer() {

        // Zwei Schwarze Laeufer
        String fen = "3k4/1b6/7b/8/3K4/8/8/8 w - - 0 1";

        Stellung stellung = new Stellung(fen);
        Spielregeln regeln = new DefaultSpielregeln();
        Collection<Zug> zuege = regeln.liefereGueltigeZuege(stellung);


        Zug illegal1 = new Zug(KOENIG_WEISS, d4, d5);
        Zug illegal2 = new Zug(KOENIG_WEISS, d4, e3);

        Assert.assertFalse(zuege.contains(illegal1));
        Assert.assertFalse(zuege.contains(illegal2));

        Zug legal1 = new Zug(KOENIG_WEISS, d4, c5);
        Zug legal2 = new Zug(KOENIG_WEISS, d4, d3);
        Zug legal3 = new Zug(KOENIG_WEISS, d4, e5);

        Assert.assertTrue(zuege.contains(legal1));
        Assert.assertTrue(zuege.contains(legal2));
        Assert.assertTrue(zuege.contains(legal3));

        Assert.assertEquals(5, zuege.size());

    }

}
