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

/**
 * Tests fuer Matt im Minimax-Algorithmus
 */
public class MinimaxAlgorithmusMattTest {

    private MinimaxAlgorithmus algorithmus;

    @Before
    public void setup() {
        algorithmus = new MinimaxAlgorithmus();
        algorithmus.setBewertung(new ReineMaterialBewertung());
        algorithmus.setSpielregeln(new DefaultSpielregeln());
    }

    /**
     * Matt in einem Zug (Schaefermatt). Siegzug fuer weiss: Dame h5xf7
     */
    @Test
    public void schaeferMatt() {

        algorithmus.setTiefe(2);

        Stellung stellung = new Stellung(
                "r1bqkb1r/pppp1ppp/2n2n2/4p2Q/2B1P3/8/PPPP1PPP/RNB1K1NR w KQkq - 0 1");
        Zug z = algorithmus.ermittleBestenZug(stellung);

        Assert.assertEquals("Q h5xf7", z.toString());
    }
}
