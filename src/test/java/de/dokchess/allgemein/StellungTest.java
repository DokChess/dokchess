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

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static de.dokchess.allgemein.Farbe.SCHWARZ;
import static de.dokchess.allgemein.Farbe.WEISS;
import static de.dokchess.allgemein.Felder.*;
import static de.dokchess.allgemein.FigurenArt.BAUER;
import static de.dokchess.allgemein.FigurenArt.DAME;


public class StellungTest {

    private static final Figur WEISSER_BAUER = new Figur(BAUER,
            WEISS);
    private static final Figur WEISSE_DAME = new Figur(DAME,
            WEISS);

    @Test
    public void bauerEinsVor() {
        Stellung stellung = new Stellung();

        Zug zug = new Zug(WEISSER_BAUER, e2, e3);

        Stellung neueStellung = stellung.fuehreZugAus(zug);

        Assert.assertNull(neueStellung.getFigur(e2));
        Assert.assertNull(neueStellung.getEnPassantFeld());
        Assert.assertEquals(WEISSER_BAUER, neueStellung.getFigur(e3));

        Assert.assertEquals(SCHWARZ, neueStellung.getAmZug());
    }

    @Test
    public void bauerZweiVor() {
        Stellung stellung = new Stellung();

        Zug zug = new Zug(WEISSER_BAUER, e2, e4);

        Stellung neueStellung = stellung.fuehreZugAus(zug);

        Assert.assertNull(neueStellung.getFigur(e2));
        Assert.assertEquals(e3, neueStellung.getEnPassantFeld());
        Assert.assertEquals(WEISSER_BAUER, neueStellung.getFigur(e4));

        Assert.assertEquals(SCHWARZ, neueStellung.getAmZug());
    }

    /**
     * Umwandlung eines weissen Bauern in eine Dame
     */
    @Test
    public void bauerUmwandlungInDame() {
        Stellung stellung = new Stellung("4k3/1P6/4K3/8/8/8/8/8 w - - 0 1");

        Zug zug = new Zug(WEISSER_BAUER, b7, b8, FigurenArt.DAME);
        Stellung neueStellung = stellung.fuehreZugAus(zug);

        Assert.assertNull(neueStellung.getFigur(b7));
        Assert.assertEquals(WEISSE_DAME, neueStellung.getFigur(b8));
    }

    @Test
    public void sucheFelderMitWeissenFiguren() {
        Stellung stellung = new Stellung();

        Set<Feld> felder = stellung.felderMitFarbe(WEISS);
        Assert.assertEquals(16, felder.size());
        Assert.assertTrue("Weisse Figur a1", felder.contains(Felder.a1));
    }

    @Test
    public void sucheFelderMitWeissenBauern() {
        Stellung stellung = new Stellung();

        List<Feld> felder = stellung.findeFelderMit(WEISSER_BAUER);
        Assert.assertEquals(8, felder.size());
        Assert.assertTrue("Weisser Bauer e2", felder.contains(Felder.e2));
    }

    @Test
    public void sucheFelderMitKoenig() {
        Stellung stellung = new Stellung();

        Feld feldSchwarz = stellung.findeFeldMitKoenig(SCHWARZ);
        Assert.assertEquals(Felder.e8, feldSchwarz);

        Feld feldWeiss = stellung.findeFeldMitKoenig(WEISS);
        Assert.assertEquals(Felder.e1, feldWeiss);
    }
}
