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

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.engine.bewertung.ReineMaterialBewertung;
import de.dokchess.regeln.DefaultSpielregeln;
import org.junit.Assert;
import org.junit.Test;

import static de.dokchess.allgemein.Felder.e7;
import static de.dokchess.allgemein.Felder.f5;

public class GabelTest {

    @Test
    /**
     * Garri Kasparow: Schachtaktik, Seite 14, Beispiel 1
     */
    public void springerGabel() {

        MinimaxAlgorithmus algorithmus = new MinimaxAlgorithmus();
        algorithmus.setBewertung(new ReineMaterialBewertung());
        algorithmus.setSpielregeln(new DefaultSpielregeln());
        algorithmus.setTiefe(2);

        Stellung stellung = new Stellung(
                "2q3k1/5ppp/8/5N2/8/8/PPP5/1KR5 w - - 0 1");
        Zug z = algorithmus.ermittleZug(stellung);

        Assert.assertEquals(z.getVon(), f5);
        Assert.assertEquals(z.getNach(), e7);

    }
}
