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
import de.dokchess.regeln.DefaultSpielregeln;
import de.dokchess.regeln.Spielregeln;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Die Engine spielt in der Standardkonfiguration eine Partie als weiss
 * gegen einen Computergegner, der mehr oder weniger zufaellig spielt.
 *
 * Konkret nimmt er den erst besten gueltigen Zug aus den Spielregeln, wobei er
 * aber schlagende Zuege und Bauernzuege bevorzugt, um das Spiel voran zu bringen.
 */
public class EngineGegenZufallIntegTest {

    Stellung brett = null;
    Engine dokChess = null;
    Spielregeln spielregeln = null;

    @Test
    public void spieleEinePartie() throws InterruptedException {

        spielregeln = new DefaultSpielregeln();
        dokChess = new DefaultEngine(spielregeln);
        brett = new Stellung();
        dokChess.figurenAufbauen(brett);

        Observable<Zug> weissZieht = dokChess.ermittleDeinenZug();
        weissZieht.subscribe(new ZugHinRueck());

        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (spielZuEnde()) {
                    executor.shutdown();
                }
            }
        }, 10, 5, TimeUnit.SECONDS);

        executor.awaitTermination(5, TimeUnit.MINUTES);

        // Schwarz sollte am Ende am Zug und Matt sein
        Assert.assertTrue(brett.getAmZug() == Farbe.SCHWARZ);
        Assert.assertTrue(spielregeln.aufMattPruefen(brett));
    }

    synchronized boolean spielZuEnde() {
        return spielregeln.aufMattPruefen(brett) || spielregeln.aufPattPruefen(brett);
    }

    synchronized void ziehen(Zug zug) {
        brett = brett.fuehreZugAus(zug);
        dokChess.ziehen(zug);
    }

    class ZugHinRueck implements rx.Observer<Zug> {

        Zug besterZug = null;

        @Override
        public void onNext(Zug zug) {
            besterZug = zug;
        }

        @Override
        public void onCompleted() {

            ziehen(besterZug);

            if (!spielZuEnde()) {

                Zug zug = ermittleSchwarzenZug();
                ziehen(zug);

                if (!spielZuEnde()) {
                    besterZug = null;
                    Observable<Zug> subjekt = dokChess.ermittleDeinenZug();
                    subjekt.subscribe(this);
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            Assert.fail(e.getMessage());
        }

        Zug ermittleSchwarzenZug() {
            Assert.assertTrue(brett.getAmZug() == Farbe.SCHWARZ);
            Collection<Zug> zuege = spielregeln.ermittleGueltigeZuege(brett);
            Assert.assertFalse(zuege.isEmpty());

            SortedSet<Zug> besteZuege = new TreeSet<>(new Sortierung());
            besteZuege.addAll(zuege);

            return besteZuege.first();
        }


        /**
         * Bevorzugte Zuege des anderen, mehr oder weniger zufaelligen Computergegners.
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
}
