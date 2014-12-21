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
package de.dokchess.engine.zugauswahl;

import de.dokchess.allgemein.Felder;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.engine.bewertung.ReineMaterialBewertung;
import de.dokchess.regeln.SpielregelnImpl;
import org.junit.Assert;
import org.junit.Test;

public class MinimaxMattsucheTest {

    /**
     * Matt in einem Zug (Schaefermatt). Siegzug fuer weiss: Dame h5xf7
     */
    @Test
    public void schaeferMatt() {

        MinimaxAlgorithmus algorithmus = new MinimaxAlgorithmus();
        algorithmus.setBewertung(new ReineMaterialBewertung());
        algorithmus.setSpielregeln(new SpielregelnImpl());
        algorithmus.setTiefe(2);

        Stellung stellung = new Stellung(
                "r1bqkb1r/pppp1ppp/2n2n2/4p2Q/2B1P3/8/PPPP1PPP/RNB1K1NR w KQkq - 0 1");
        Zug z = algorithmus.ermittleZug(stellung);

        Assert.assertEquals(Felder.h5, z.getVon());
        Assert.assertEquals(Felder.f7, z.getNach());
    }
}
