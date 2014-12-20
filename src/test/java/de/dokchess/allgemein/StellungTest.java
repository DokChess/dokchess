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

import org.junit.Test;

import java.util.List;
import java.util.Set;

import static de.dokchess.allgemein.Farbe.SCHWARZ;
import static de.dokchess.allgemein.Farbe.WEISS;
import static de.dokchess.allgemein.Felder.*;
import static de.dokchess.allgemein.FigurenArt.*;
import static de.dokchess.allgemein.RochadeRecht.*;
import static org.junit.Assert.*;


public class StellungTest {

    private static final Figur WEISSER_BAUER = new Figur(BAUER,
            WEISS);
    private static final Figur WEISSE_DAME = new Figur(DAME,
            WEISS);
    private static final Figur WEISSER_KOENIG = new Figur(KOENIG,
            WEISS);
    private static final Figur WEISSER_TURM = new Figur(TURM,
            WEISS);

    @Test
    public void bauerEinsVor() {
        Stellung stellung = new Stellung();

        Zug zug = new Zug(WEISSER_BAUER, E2, E3);

        Stellung neueStellung = stellung.fuehreZugAus(zug);

        assertNull(neueStellung.getFigur(E2));
        assertNull(neueStellung.getEnPassantFeld());
        assertEquals(WEISSER_BAUER, neueStellung.getFigur(E3));

        assertEquals(SCHWARZ, neueStellung.getAmZug());
    }

    @Test
    public void bauerZweiVor() {
        Stellung stellung = new Stellung();

        Zug zug = new Zug(WEISSER_BAUER, E2, E4);

        Stellung neueStellung = stellung.fuehreZugAus(zug);

        assertNull(neueStellung.getFigur(E2));
        assertEquals(E3, neueStellung.getEnPassantFeld());
        assertEquals(WEISSER_BAUER, neueStellung.getFigur(E4));

        assertEquals(SCHWARZ, neueStellung.getAmZug());
    }

    /**
     * Umwandlung eines weissen Bauern in eine Dame
     */
    @Test
    public void bauerUmwandlungInDame() {
        Stellung stellung = new Stellung("4k3/1P6/4K3/8/8/8/8/8 w - - 0 1");

        Zug zug = new Zug(WEISSER_BAUER, B7, B8, FigurenArt.DAME);
        Stellung neueStellung = stellung.fuehreZugAus(zug);

        assertNull(neueStellung.getFigur(B7));
        assertEquals(WEISSE_DAME, neueStellung.getFigur(B8));
    }

    /**
     * Weiss macht eine kurze Rochade.
     */
    @Test
    public void kurzeRochadeWeiss() {
        Stellung stellung = new Stellung("rnbqkbnr/ppp3pp/3ppp2/8/8/4PN2/PPPPBPPP/RNBQK2R w KQkq - 0 1");

        Zug zug = new Zug(WEISSER_KOENIG, E1, G1);
        assertTrue(zug.istRochadeKurz());

        Stellung neueStellung = stellung.fuehreZugAus(zug);

        // Koenig und Turm haben sich bewegt
        assertNull(neueStellung.getFigur(E1));
        assertNull(neueStellung.getFigur(H1));

        assertEquals(WEISSER_KOENIG, neueStellung.getFigur(G1));
        assertEquals(WEISSER_TURM, neueStellung.getFigur(F1));

        assertTrue(neueStellung.rochadeErlaubt((SCHWARZ_LANG)));
        assertTrue(neueStellung.rochadeErlaubt((SCHWARZ_KURZ)));
        assertFalse(neueStellung.rochadeErlaubt((WEISS_LANG)));
        assertFalse(neueStellung.rochadeErlaubt((WEISS_KURZ)));
    }

    /**
     * Weiss bewegt einen Turm oder den Koenig. Rochaderechte anpassen.
     */
    @Test
    public void rochadeRechteAnpassenWeiss() {
        Stellung stellung = new Stellung("4k3/8/1Q6/8/8/8/8/R3K2R w KQ - 0 1");

        // Turm wird bewegt. Auf der anderen Seite ist dann rochade noch erlaubt
        Zug z1 = new Zug(WEISSER_TURM, A1, B1);
        Stellung neueStellung1 = stellung.fuehreZugAus(z1);
        assertFalse(neueStellung1.rochadeErlaubt(WEISS_LANG));
        assertTrue(neueStellung1.rochadeErlaubt(WEISS_KURZ));

        // Gleicher Test, andere Seite
        Zug z2 = new Zug(WEISSER_TURM, H1, G1);
        Stellung neueStellung2 = stellung.fuehreZugAus(z2);
        assertTrue(neueStellung2.rochadeErlaubt(WEISS_LANG));
        assertFalse(neueStellung2.rochadeErlaubt(WEISS_KURZ));

        // Koenig wird bewegt. Keine Rochade fuer weiss merh moeglich.
        Zug z3 = new Zug(WEISSER_KOENIG, E1, E2);
        Stellung neueStellung3 = stellung.fuehreZugAus(z3);
        assertFalse(neueStellung3.rochadeErlaubt(WEISS_LANG));
        assertFalse(neueStellung3.rochadeErlaubt(WEISS_KURZ));
    }


    @Test
    public void sucheFelderMitWeissenFiguren() {
        Stellung stellung = new Stellung();

        Set<Feld> felder = stellung.felderMitFarbe(WEISS);
        assertEquals(16, felder.size());
        assertTrue("Weisse Figur a1", felder.contains(A1));
    }

    @Test
    public void sucheFelderMitWeissenBauern() {
        Stellung stellung = new Stellung();

        List<Feld> felder = stellung.findeFelderMit(WEISSER_BAUER);
        assertEquals(8, felder.size());
        assertTrue("Weisser Bauer e2", felder.contains(E2));
    }

    @Test
    public void sucheFelderMitKoenig() {
        Stellung stellung = new Stellung();

        Feld feldSchwarz = stellung.findeFeldMitKoenig(SCHWARZ);
        assertEquals(E8, feldSchwarz);

        Feld feldWeiss = stellung.findeFeldMitKoenig(WEISS);
        assertEquals(E1, feldWeiss);
    }
}
