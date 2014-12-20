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
package de.dokchess.regeln;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class ZuegeAusAnfangsstellungTest {

    private Spielregeln spielregeln;

    @Before
    public void setup() {
        spielregeln = new SpielregelnImpl();
    }

    @Test
    /**
     * Aus der Angangsstellung sind 20 Zuege moeglich.
     */
    public void anfangsstellung() {
        Stellung anfangsstellung = new Stellung();
        Collection<Zug> zuege = spielregeln.ermittleGueltigeZuege(anfangsstellung);
        System.out.println(zuege);
        Assert.assertEquals(20, zuege.size());
    }
}
