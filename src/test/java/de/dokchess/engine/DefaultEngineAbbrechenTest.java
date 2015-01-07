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

package de.dokchess.engine;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.regeln.DefaultSpielregeln;
import de.dokchess.regeln.Spielregeln;
import org.junit.Test;

/**
 * Test startet eine Berechnung. Zieht dann aber nach 500ms.
 */
public class DefaultEngineAbbrechenTest {

    @Test
    public void ziehenNach500ms() throws InterruptedException {

        Spielregeln regeln = new DefaultSpielregeln();
        DefaultEngine engine = new DefaultEngine(regeln);

        Stellung anfang = new Stellung();
        engine.figurenAufbauen(anfang);

        engine.ermittleDeinenZug();
        Thread.sleep(500);

        Zug z = regeln.liefereGueltigeZuege(anfang).iterator().next();
        engine.ziehen(z);

        Thread.sleep(1000);
    }
}
