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

package de.dokchess.eroeffnung.polyglot;

import org.junit.Assert;
import org.junit.Test;

public class PolyglotToolsTest {

    @Test
    public void felderInZeichenketten() {

        String a1 = PolyglotTools.fileAndRowToString(0, 0);
        Assert.assertEquals("a1", a1);

        String h1 = PolyglotTools.fileAndRowToString(7, 0);
        Assert.assertEquals("h1", h1);

        String a8 = PolyglotTools.fileAndRowToString(0, 7);
        Assert.assertEquals("a8", a8);

        String h8 = PolyglotTools.fileAndRowToString(7, 7);
        Assert.assertEquals("h8", h8);
    }

    @Test
    public void longInBytesUmwandeln() {

        long longValue = 0x463b96181691fc9cl;
        byte[] byteArray = PolyglotTools.longToByteArray(longValue);

        Assert.assertEquals(70, byteArray[0]);
        Assert.assertEquals(59, byteArray[1]);
        Assert.assertEquals(-106, byteArray[2]);
        Assert.assertEquals(24, byteArray[3]);
        Assert.assertEquals(22, byteArray[4]);
        Assert.assertEquals(-111, byteArray[5]);
        Assert.assertEquals(-4, byteArray[6]);
        Assert.assertEquals(-100, byteArray[7]);
    }

    @Test
    public void zweiBytesInIntUmwandeln() {

        byte[] leer = new byte[2];
        Assert.assertEquals(0, PolyglotTools.twoBytesToInt(leer));

        byte[] halbvoll = new byte[2];
        halbvoll[0] = 0;
        halbvoll[1] = 127;
        Assert.assertEquals(127, PolyglotTools.twoBytesToInt(halbvoll));

        byte[] ganzvoll = new byte[2];
        ganzvoll[0] = 127;
        ganzvoll[1] = 127;
        Assert.assertEquals(32639, PolyglotTools.twoBytesToInt(ganzvoll));
    }
}
