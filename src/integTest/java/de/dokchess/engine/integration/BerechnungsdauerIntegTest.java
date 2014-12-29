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

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.engine.DefaultEngine;
import de.dokchess.engine.Engine;
import de.dokchess.regeln.DefaultSpielregeln;
import de.dokchess.regeln.Spielregeln;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.Observer;

/**
 * Created by stefanz on 29.12.14.
 */
public class BerechnungsdauerIntegTest implements Observer<Zug> {

    private Zug besterZug = null;

    private Spielregeln spielregeln = null;

    private Engine dokChess = null;

    private Stellung brett;

    private boolean completed = false;

    @Before
    public void setup() {
        spielregeln = new DefaultSpielregeln();
        dokChess = new DefaultEngine(spielregeln);
        brett = new Stellung("1b2krr1/1q6/8/8/2Q4R/8/1B6/R3K3 w - - 0 1");
        dokChess.figurenAufbauen(brett);
    }

    @Test(timeout = 10000)
    public void zugBerechnen() {

        Observable<Zug> subject = dokChess.ermittleDeinenZug();
        subject.subscribe(this);

        while (!isCompleted()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onCompleted() {
        setCompleted();
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(Zug zug) {
    }

    public synchronized void setCompleted() {
        this.completed = true;
    }

    public synchronized boolean isCompleted() {
        return this.completed;
    }
}
