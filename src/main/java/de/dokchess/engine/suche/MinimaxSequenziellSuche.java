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

import de.dokchess.allgemein.Farbe;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import rx.subjects.Subject;

import java.util.Collection;

/**
 * Created by stefanz on 02.01.15.
 */
public class MinimaxSequenziellSuche extends BasisMinimaxAlgorithmus implements Suche {

    @Override
    public void suchen(Stellung stellung, Subject<Zug, Zug> subject) {

        Farbe spielerFarbe = stellung.getAmZug();
        Collection<Zug> zuege = spielregeln.ermittleGueltigeZuege(stellung);

        int besterWert = MINIMALE_BEWERTUNG;
        Zug besterZug = null;

        for (Zug zug : zuege) {
            Stellung neueStellung = stellung.fuehreZugAus(zug);

            int wert = bewerteStellungRekursiv(neueStellung, spielerFarbe);

            if (wert > besterWert) {
                besterWert = wert;
                besterZug = zug;

                subject.onNext(besterZug);
            }
        }
        subject.onCompleted();
    }

    public void abbrechen() {
    }
}
