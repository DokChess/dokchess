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

package de.dokchess.xboard;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.engine.Engine;
import rx.Observable;
import rx.subjects.ReplaySubject;

/**
 * Einfache Implementierung des Engine-Interfaces fuer Testzwecke.
 */
public class MockEngine implements Engine {

    private Zug zug;

    public MockEngine() {
    }

    /**
     * Erzeugt eine Engine, die beim Ermitteln genau diesen Zug liefert.
     *
     * @param zug der zu spielende Zug.
     */
    public MockEngine(Zug zug) {
        this.zug = zug;
    }


    @Override
    public void figurenAufbauen(Stellung stellung) {
    }

    @Override
    public Observable<Zug> ermittleDeinenZug() {

        ReplaySubject<Zug> subject = ReplaySubject.create();
        if (zug != null) {
            subject.onNext(zug);
        }
        subject.onCompleted();

        return subject;
    }

    @Override
    public void ziehen(Zug zug) {
    }

    @Override
    public void beenden() {
    }
}
