/*
 * Copyright (c) 2010-2015 Stefan Zoerner
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

package de.dokchess.engine.suche;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.engine.bewertung.ReineMaterialBewertung;
import de.dokchess.regeln.DefaultSpielregeln;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static de.dokchess.allgemein.Felder.*;

/**
 * Tests fuer verschiedene taktische Elemente im Schach.
 *
 * @author StefanZ
 */
public class MinimaxAlgorithmusTaktikTest {

    private MinimaxAlgorithmus algorithmus;

    @Before
    public void setup() {
        algorithmus = new MinimaxAlgorithmus();
        algorithmus.setBewertung(new ReineMaterialBewertung());
        algorithmus.setSpielregeln(new DefaultSpielregeln());
    }

    @Test
    /**
     * Beispiel, wo der weisse Koenig durch einen Laeufer aufgespiesst wird.
     * Er gewinnt so die Dame.
     *
     * Quelle des Beispiels: Wikipedia.
     * http://de.wikipedia.org/wiki/Spieß_(Schach)
     */
    public void laeuferSpiess() {
        algorithmus.setTiefe(4);

        Stellung stellung = new Stellung(
                "8/3qkb2/8/8/4KB2/5Q2/8/8 b - - 0 1");
        Zug z = algorithmus.ermittleBestenZug(stellung);

        Assert.assertEquals(f7, z.getVon());
        Assert.assertEquals(d5, z.getNach());
    }

    @Test
    /**
     * Weiss gewinnt durch eine Gabel mit einem Bauern einen Turm.
     *
     * Quelle des Beispiels: Wikipedia.
     * http://de.wikipedia.org/wiki/Gabel_(Schach)
     */
    public void bauernGabel() {
        algorithmus.setTiefe(4);

        Stellung stellung = new Stellung(
                "8/5k2/2r1r3/8/3P4/6P1/5PK1/8 w - - 0 1");
        Zug z = algorithmus.ermittleBestenZug(stellung);

        Assert.assertEquals(d4, z.getVon());
        Assert.assertEquals(d5, z.getNach());

    }
}
