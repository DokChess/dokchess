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
package de.dokchess.xboard;

import de.dokchess.allgemein.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZugParserTest {

    private static final Figur WEISSER_KOENIG = new Figur(FigurenArt.KOENIG,
            Farbe.WEISS);
    private static final Figur WEISSER_BAUER = new Figur(FigurenArt.BAUER,
            Farbe.WEISS);

    @Test
    public void einfacheZuegeVonXBoard() {

        Stellung stellung = new Stellung();
        ZugParser parser = new ZugParser();

        // Koenigsbauer 1 vor
        {
            String eingabe = "e2e3";
            Zug zug = parser.vonXboard(eingabe, stellung);
            assertNotNull(zug);
            assertEquals(Felder.e2, zug.getVon());
            assertEquals(Felder.e3, zug.getNach());
            assertEquals(WEISSER_BAUER, zug.getFigur());
            assertTrue(zug.istBauernZug());
            assertFalse(zug.istBauernZugZweiVor());
        }

        // Koenigsbauer 2 vor
        {
            String eingabe = "e2e4";
            Zug zug = parser.vonXboard(eingabe, stellung);
            assertNotNull(zug);
            assertEquals(Felder.e2, zug.getVon());
            assertEquals(Felder.e4, zug.getNach());
            assertEquals(WEISSER_BAUER, zug.getFigur());
            assertTrue(zug.istBauernZug());
            assertTrue(zug.istBauernZugZweiVor());
        }
    }

    @Test
    public void umwandlungInDameVonXBoard() {

        Stellung stellung = new Stellung("8/2k3P1/8/8/8/8/4K3/8 w - - 0 1");
        ZugParser parser = new ZugParser();

        String eingabe = "g7g8q";
        Zug zug = parser.vonXboard(eingabe, stellung);

        assertNotNull(zug);
        assertEquals(Felder.g7, zug.getVon());
        assertEquals(Felder.g8, zug.getNach());
        assertEquals(WEISSER_BAUER, zug.getFigur());
        assertTrue(zug.istBauernZug());
        assertTrue(zug.istUmwandlung());
        assertEquals(FigurenArt.DAME, zug.getNeueFigurenart());
    }

    @Test
    public void rochadeNachXBoard() {
        ZugParser parser = new ZugParser();
        Zug rochadeKurz = new Zug(WEISSER_KOENIG, Felder.e1, Felder.g1);

        String ausgabe = parser.nachXboard(rochadeKurz);
        assertEquals("move e1g1", ausgabe);
    }

    @Test
    public void umwandlungInDameNachXBoard() {

        ZugParser parser = new ZugParser();
        Zug umwandlung = new Zug(WEISSER_BAUER, Felder.h7, Felder.h8,
                FigurenArt.DAME);

        String ausgabe = parser.nachXboard(umwandlung);
        assertEquals("move h7h8q", ausgabe);
    }

}
