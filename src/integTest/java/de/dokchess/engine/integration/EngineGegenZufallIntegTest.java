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
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import java.util.*;

/**
 * Die Engine spielt in der Standardkonfiguration eine Partie als weiss
 * gegen einen Computergegner, der mehr oder weniger zufaellig spielt.
 *
 * Konkret nimmt er den erst besten gueltigen Zug aus den Spielregeln, wobei er
 * aber schlagende Zuege und Bauernzuege bevorzugt, um das Spiel voran zu bringen.
 */
public class EngineGegenZufallIntegTest implements rx.Observer<Zug> {

    private Zug besterZug = null;

    private Spielregeln spielregeln = null;

    private Engine dokChess = null;

    private Stellung brett;

    @Before
    public void setup() {
        spielregeln = new DefaultSpielregeln();
        dokChess = new DefaultEngine(spielregeln);
        brett = new Stellung();
        dokChess.figurenAufbauen(brett);
    }


    @Test
    public void partieSpielen() {
        
        while (! (spielregeln.aufMattPruefen(brett) || spielregeln.aufPattPruefen(brett))) {
            // Engine zieht (weiss)
            Observable<Zug> subjekt = dokChess.ermittleDeinenZug();
            subjekt.subscribe(this);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Assert.assertTrue(brett.getAmZug() == Farbe.SCHWARZ);
        Assert.assertTrue(spielregeln.aufMattPruefen(brett));
    }

    @Override
    public void onCompleted() {
        System.out.println("onCompleted. ");
        
        // Zug von weiss (mir selber) ausfuehren
        //
        dokChess.ziehen(besterZug);
        brett = this.brett.fuehreZugAus(besterZug);

        // Zufall (schwarz) zieht, falls moeglich
        Collection<Zug> zuege = spielregeln.ermittleGueltigeZuege(brett);
        List<Zug> zugListe = new ArrayList<>(zuege);

        if(zugListe.size() > 0) {
            Collections.sort(zugListe, new Sortierung());
            Zug z2 = zugListe.get(0);
            dokChess.ziehen(z2);
            brett = brett.fuehreZugAus(z2);

            Observable<Zug> subjekt = dokChess.ermittleDeinenZug();
            subjekt.subscribe(this);
        }
    }

    @Override
    public void onError(Throwable e) {
        Assert.fail(e.getMessage());
    }

    @Override
    public void onNext(Zug zug) {
        System.out.println("onNext: " + zug);

        besterZug = zug;
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
