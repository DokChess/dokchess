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

import de.dokchess.allgemein.Figur;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static de.dokchess.allgemein.Farbe.SCHWARZ;
import static de.dokchess.allgemein.Farbe.WEISS;
import static de.dokchess.allgemein.Felder.e1;
import static de.dokchess.allgemein.Felder.e8;
import static de.dokchess.allgemein.FigurenArt.KOENIG;

public class RochadeZuegeTest {

    private static final Figur WEISSER_KOENIG = new Figur(KOENIG, WEISS);
    private static final Figur SCHWARZER_KOENIG = new Figur(KOENIG, SCHWARZ);

    @Test
    public void weisseRochadenErlaubt() {
        Stellung stellung = new Stellung("r3k2r/ppp2ppp/8/3p4/3P4/8/PPP2PPP/R3K2R w KQkq - 0 1");

        RochadeZuege rochadeZuege = new RochadeZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        rochadeZuege.fuegeZugkandidatenHinzu(e1, stellung, zuege);
        Assert.assertEquals(2, zuege.size());
        for (Zug zug : zuege) {
            Assert.assertTrue(zug.getFigur().equals(WEISSER_KOENIG));
            Assert.assertEquals(e1, zug.getVon());
        }
    }

    @Test
    public void weisseRochadenNichtErlaubt() {
        Stellung stellung = new Stellung("r3k2r/ppp2ppp/8/3p4/3P4/8/PPP2PPP/R3K2R w kq - 0 1");

        RochadeZuege rochadeZuege = new RochadeZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        rochadeZuege.fuegeZugkandidatenHinzu(e1, stellung, zuege);
        Assert.assertEquals(0, zuege.size());
    }

    @Test
    public void schwarzeRochadenErlaubt() {
        Stellung stellung = new Stellung("r3k2r/ppp2ppp/8/3p4/3P4/8/PPP2PPP/R3K2R b KQkq - 0 1");

        RochadeZuege rochadeZuege = new RochadeZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        rochadeZuege.fuegeZugkandidatenHinzu(e8, stellung, zuege);
        Assert.assertEquals(2, zuege.size());
        for (Zug zug : zuege) {
            Assert.assertTrue(zug.getFigur().equals(SCHWARZER_KOENIG));
            Assert.assertEquals(e8, zug.getVon());
        }
    }

    @Test
    public void schwarzeRochadenNichtErlaubt() {
        Stellung stellung = new Stellung("r3k2r/ppp2ppp/8/3p4/3P4/8/PPP2PPP/R3K2R b KQ - 0 1");

        RochadeZuege rochadeZuege = new RochadeZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        rochadeZuege.fuegeZugkandidatenHinzu(e8, stellung, zuege);
        Assert.assertEquals(0, zuege.size());
    }

    @Test
    public void felderMuesserFreiSeinBeiWeiss() {
        Stellung stellung = new Stellung("rn2kb1r/ppp2ppp/8/3p4/3P4/8/PPP2PPP/RN2KB1R w KQkq - 0 1");
        RochadeZuege rochadeZuege = new RochadeZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        rochadeZuege.fuegeZugkandidatenHinzu(e1, stellung, zuege);
        Assert.assertEquals(0, zuege.size());
    }

    @Test
    public void felderMuesserFreiSeinBeiSchwarz() {
        Stellung stellung = new Stellung("rn2kb1r/ppp2ppp/8/3p4/3P4/8/PPP2PPP/RN2KB1R b KQkq - 0 1");
        RochadeZuege rochadeZuege = new RochadeZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        rochadeZuege.fuegeZugkandidatenHinzu(e8, stellung, zuege);
        Assert.assertEquals(0, zuege.size());
    }

    @Test
    public void koenigDarfNichtImSchachStehenBeiWeiss() {
        Stellung stellung = new Stellung("r3k2r/ppp2ppp/8/1B1pq3/3P4/8/PPP2PPP/R3K2R w KQkq - 0 1");
        RochadeZuege rochadeZuege = new RochadeZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        rochadeZuege.fuegeZugkandidatenHinzu(e1, stellung, zuege);
        Assert.assertEquals(0, zuege.size());
    }

    @Test
    public void koenigDarfNichtImSchachStehenBeiSchwarz() {
        Stellung stellung = new Stellung("r3k2r/ppp2ppp/8/1B1pq3/3P4/8/PPP2PPP/R3K2R b KQkq - 0 1");
        RochadeZuege rochadeZuege = new RochadeZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        rochadeZuege.fuegeZugkandidatenHinzu(e8, stellung, zuege);
        Assert.assertEquals(0, zuege.size());
    }

    @Test
    public void koenigDarfNichtUeberAngriffendeFelderRochierenBeiWeiss() {
        Stellung stellung = new Stellung("r3k2r/ppp2ppp/8/1b6/1B1Q1b2/8/PPP2PPP/R3K2R w KQkq - 0 1");
        RochadeZuege rochadeZuege = new RochadeZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        rochadeZuege.fuegeZugkandidatenHinzu(e1, stellung, zuege);
        Assert.assertEquals(0, zuege.size());
    }

    @Test
    public void koenigDarfNichtUeberAngriffendeFelderRochierenBeiSchwarz() {
        Stellung stellung = new Stellung("r3k2r/ppp2ppp/8/1b6/1B1Q1b2/8/PPP2PPP/R3K2R b KQkq - 0 1");
        RochadeZuege rochadeZuege = new RochadeZuege();
        List<Zug> zuege = new ArrayList<Zug>();

        rochadeZuege.fuegeZugkandidatenHinzu(e8, stellung, zuege);
        Assert.assertEquals(0, zuege.size());
    }

}
