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

public class FeldTest {

    @Test
    public void konstruktionMitString() {

        Feld a8 = new Feld("a8");
        Assert.assertEquals(0, a8.getReihe());
        Assert.assertEquals(0, a8.getLinie());
        Assert.assertEquals("a8", a8.toString());

        Feld h8 = new Feld("h8");
        Assert.assertEquals(0, h8.getReihe());
        Assert.assertEquals(7, h8.getLinie());
        Assert.assertEquals("h8", h8.toString());

        Feld a1 = new Feld("a1");
        Assert.assertEquals(7, a1.getReihe());
        Assert.assertEquals(0, a1.getLinie());
        Assert.assertEquals("a1", a1.toString());

        Feld h1 = new Feld("h1");
        Assert.assertEquals(7, h1.getReihe());
        Assert.assertEquals(7, h1.getLinie());
        Assert.assertEquals("h1", h1.toString());
    }

    @Test
    public void konstruktionMitKoordinaten() {

        Feld a8 = new Feld(0, 0);
        Assert.assertEquals("a8", a8.toString());

        Feld h8 = new Feld(0, 7);
        Assert.assertEquals("h8", h8.toString());

        Feld a1 = new Feld(7, 0);
        Assert.assertEquals("a1", a1.toString());

        Feld h1 = new Feld(7, 7);
        Assert.assertEquals("h1", h1.toString());
    }

    @Test
    public void vergleicheMitEquals() {
        Feld a1 = new Feld("a1");
        Feld h1 = new Feld("h1");
        Feld anderesH1 = new Feld("h1");

        Assert.assertFalse(a1.equals(h1));
        Assert.assertFalse(a1.equals(null));
        Assert.assertFalse(a1.equals("Hallo"));

        Assert.assertTrue(a1.equals(a1));
        Assert.assertTrue(h1.equals(anderesH1));
    }

    @Test
    public void hashCodesSindGleich() {
        Feld h1 = new Feld("h1");
        Feld anderesH1 = new Feld("h1");
        Assert.assertTrue(h1.hashCode() == anderesH1.hashCode());
    }


}
