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

package de.dokchess.engine.integration;

import de.dokchess.allgemein.Farbe;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.engine.DefaultEngine;
import de.dokchess.engine.Engine;
import de.dokchess.regeln.Spielregeln;
import de.dokchess.regeln.SpielregelnImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Die Engine spielt in der Standardkonfiguration eine Partie als weiss
 * gegen einen Computergegner, der mehr oder weniger zufaellig spielt.
 *
 * Konkret nimmt er den erst besten gueltigen Zug aus den Spielregeln, wobei er
 * aber schlagende Zuege und Bauernzuege bevorzugt, um das Spiel voran zu bringen.
 */
public class EngineGegenZufallIntegTest {

    @Test
    public void partieSpielen() {

        Stellung brett = new Stellung();

        Spielregeln regel = new SpielregelnImpl();
        Engine dokChess = new DefaultEngine(regel);

        dokChess.figurenAufbauen(brett);

        while (true) {

            // Engine zieht (weiss)
            Zug z1 = dokChess.ermittleDeinenZug();
            dokChess.ziehen(z1);
            brett = brett.fuehreZugAus(z1);

            if (regel.aufMattPruefen(brett) || regel.aufPattPruefen(brett)) {
                break;
            }

            // Zufall zieht (schwarz)
            Collection<Zug> zuege = regel.ermittleGueltigeZuege(brett);
            List<Zug> zugListe = new ArrayList<>(zuege);
            Collections.sort(zugListe, new Sortierung());
            Zug z2 = zugListe.get(0);
            dokChess.ziehen(z2);
            brett = brett.fuehreZugAus(z2);

            if (regel.aufMattPruefen(brett) || regel.aufPattPruefen(brett)) {
                Assert.fail("Zufall hat gewonnen");
            }
        }

        Assert.assertTrue(brett.getAmZug() == Farbe.SCHWARZ);
        Assert.assertTrue(regel.aufMattPruefen(brett));
    }

    /** Bevorzugte Zuege des anderen, mehr oder weniger zufaelligen Computergegners.
     */
    class Sortierung implements Comparator<Zug> {

        @Override
        public int compare(Zug z1, Zug z2) {
            return zugWert(z2) - zugWert(z1);
        }

        int zugWert(Zug z) {
            int wert = 0;

            if (z.istSchlagen()) {
                wert += 1000;
            }

            if (z.istRochade()) {
                wert += 100;
            }

            if (z.istBauernZug()) {
                wert += 10;
            }

            return wert;
        }
    }
}
