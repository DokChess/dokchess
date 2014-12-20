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
import static de.dokchess.allgemein.FigurenArt.*;
import static de.dokchess.allgemein.FigurenArt.DAME;
import static de.dokchess.allgemein.FigurenArt.KOENIG;


public class StellungTest {

    private static final Figur WEISSER_BAUER = new Figur(BAUER,
            WEISS);
    private static final Figur WEISSE_DAME = new Figur(DAME,
            WEISS);

    @Test
    public void bauerEinsVor() {
        Stellung stellung = new Stellung();

        Zug zug = new Zug(WEISSER_BAUER, E2, E3);

        Stellung neueStellung = stellung.fuehreZugAus(zug);

        Assert.assertNull(neueStellung.getFigur(E2));
        Assert.assertNull(neueStellung.getEnPassantFeld());
        Assert.assertEquals(WEISSER_BAUER, neueStellung.getFigur(E3));

        Assert.assertEquals(SCHWARZ, neueStellung.getAmZug());
    }

    @Test
    public void bauerZweiVor() {
        Stellung stellung = new Stellung();

        Zug zug = new Zug(WEISSER_BAUER, E2, E4);

        Stellung neueStellung = stellung.fuehreZugAus(zug);

        Assert.assertNull(neueStellung.getFigur(E2));
        Assert.assertEquals(E3, neueStellung.getEnPassantFeld());
        Assert.assertEquals(WEISSER_BAUER, neueStellung.getFigur(E4));

        Assert.assertEquals(SCHWARZ, neueStellung.getAmZug());
    }

    /**
     * Umwandlung eines weissen Bauern in eine Dame
     */
    @Test
    public void bauerUmwandlungInDame() {
        Stellung stellung = new Stellung("4k3/1P6/4K3/8/8/8/8/8 w - - 0 1");

        Zug zug = new Zug(WEISSER_BAUER, B7, B8, FigurenArt.DAME);
        Stellung neueStellung = stellung.fuehreZugAus(zug);

        Assert.assertNull(neueStellung.getFigur(B7));
        Assert.assertEquals(WEISSE_DAME, neueStellung.getFigur(B8));
    }

    /**
     * Weiss macht eine kurze Rochade.
     */
    @Test
    public void kurzeRochadeWeiss() {
        Stellung stellung = new Stellung("rnbqkbnr/ppp3pp/3ppp2/8/8/4PN2/PPPPBPPP/RNBQK2R w KQkq - 0 1");

        Zug zug = new Zug(new Figur(KOENIG, WEISS), E1, G1);
        Assert.assertTrue(zug.istRochadeKurz());

        Stellung neueStellung = stellung.fuehreZugAus(zug);

        // Koenig und Turm haben sich bewegt
        Assert.assertNull(neueStellung.getFigur(E1));
        Assert.assertNull(neueStellung.getFigur(H1));

        Assert.assertEquals(new Figur(KOENIG, WEISS), neueStellung.getFigur(G1));
        Assert.assertEquals(new Figur(TURM, WEISS), neueStellung.getFigur(F1));

        Set<RochadeRecht> rochadeRechte = neueStellung.getRochadeRechte();
        Assert.assertTrue(rochadeRechte.contains(RochadeRecht.SCHWARZ_LANG));
        Assert.assertTrue(rochadeRechte.contains(RochadeRecht.SCHWARZ_KURZ));
        Assert.assertFalse(rochadeRechte.contains(RochadeRecht.WEISS_LANG));
        Assert.assertFalse(rochadeRechte.contains(RochadeRecht.WEISS_KURZ));
    }

    @Test
    public void sucheFelderMitWeissenFiguren() {
        Stellung stellung = new Stellung();

        Set<Feld> felder = stellung.felderMitFarbe(WEISS);
        Assert.assertEquals(16, felder.size());
        Assert.assertTrue("Weisse Figur a1", felder.contains(A1));
    }

    @Test
    public void sucheFelderMitWeissenBauern() {
        Stellung stellung = new Stellung();

        List<Feld> felder = stellung.findeFelderMit(WEISSER_BAUER);
        Assert.assertEquals(8, felder.size());
        Assert.assertTrue("Weisser Bauer e2", felder.contains(E2));
    }

    @Test
    public void sucheFelderMitKoenig() {
        Stellung stellung = new Stellung();

        Feld feldSchwarz = stellung.findeFeldMitKoenig(SCHWARZ);
        Assert.assertEquals(E8, feldSchwarz);

        Feld feldWeiss = stellung.findeFeldMitKoenig(WEISS);
        Assert.assertEquals(E1, feldWeiss);
    }
}
