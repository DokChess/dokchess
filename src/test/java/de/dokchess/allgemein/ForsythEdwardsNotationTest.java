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

package de.dokchess.allgemein;

import org.junit.Assert;
import org.junit.Test;

import static de.dokchess.allgemein.Farbe.SCHWARZ;
import static de.dokchess.allgemein.Farbe.WEISS;
import static de.dokchess.allgemein.Felder.*;
import static de.dokchess.allgemein.FigurenArt.BAUER;
import static de.dokchess.allgemein.FigurenArt.DAME;

public class ForsythEdwardsNotationTest {

    /**
     * Umwandlung der Anfangsstellung in eine FEN-Zeichenkette
     */
    @Test
    public void anfangsstellungToString() {

        Stellung s = new Stellung();

        String fen = ForsythEdwardsNotation.toString(s);
        Assert.assertEquals(
                "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", fen);
    }

    /**
     * Stellung mit en passant in Zeichenkette.
     */
    @Test
    public void enPassantToString() {

        Stellung s = new Stellung();
        Zug z = new Zug(new Figur(BAUER, Farbe.WEISS), e2, e4);
        s = s.fuehreZugAus(z);

        String fen = ForsythEdwardsNotation.toString(s);
        Assert.assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", fen);
    }

    /**
     * Umwandlung einer FEN-Zeichenkette in ein Stellungsobjekt
     */
    @Test
    public void schaefermattFenInStellung() {

        Stellung pos = new Stellung();
        String fen = "r1bqkb1r/pppp1ppp/2n2n2/4p2Q/2B1P3/8/PPPP1PPP/RNB1K1NR w KQkq - 0 1";
        ForsythEdwardsNotation.fromString(pos, fen);

        Assert.assertEquals(Farbe.WEISS, pos.getAmZug());
        Assert.assertEquals(new Figur(DAME, WEISS), pos.getFigur(h5));
        Assert.assertEquals(new Figur(BAUER, SCHWARZ), pos.getFigur(e5));
    }
}
