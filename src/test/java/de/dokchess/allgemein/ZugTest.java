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

import static de.dokchess.allgemein.Farbe.WEISS;
import static de.dokchess.allgemein.Felder.e2;
import static de.dokchess.allgemein.Felder.e4;
import static de.dokchess.allgemein.FigurenArt.BAUER;

public class ZugTest {

    private static final Figur WEISSER_BAUER = new Figur(BAUER,
            WEISS);

    @Test
    public void testEqualsHashcode() {

        Zug z1 = new Zug(WEISSER_BAUER, e2, e4);
        Zug z2 = new Zug(WEISSER_BAUER, e2, e4);

        Assert.assertEquals(z1, z2);
        Assert.assertEquals(z1.hashCode(), z2.hashCode());
    }

    @Test
    public void testToString() {

        Zug z1 = new Zug(WEISSER_BAUER, e2, e4);
        String s = z1.toString();

        Assert.assertEquals("e2-e4", s);
    }
}