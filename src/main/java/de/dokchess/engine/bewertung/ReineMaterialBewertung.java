/*
 * Copyright (c) 2010-2021 Stefan Zoerner
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
package de.dokchess.engine.bewertung;

import de.dokchess.allgemein.Farbe;
import de.dokchess.allgemein.Figur;
import de.dokchess.allgemein.Stellung;

/**
 * Bewertung ausschliesslich anhand des Figurenwertes.
 *
 * Jede Figurenart enth&auml;lt einen Wert (Bauer 1, Springer 3, ..., Dame 9),
 * die Figuren auf dem Brett werden entsprechend aufsummiert. Eigene Figuren
 * z&auml;hlen positiv, gegnerische negativ. Entsprechend ist bei ausgeglichenem
 * Material das Ergebnis 0, verliert man z.B. eine Dame, sinkt der Wert um 9. Es
 * spielt also keine Rolle, wo die Figur steht.
 *
 * @author StefanZ
 */
public class ReineMaterialBewertung implements Bewertung {

    @Override
    public int bewerteStellung(Stellung stellung, Farbe ausSicht) {
        int summe = 0;

        for (int reihe = 0; reihe < 8; ++reihe) {
            for (int linie = 0; linie < 8; ++linie) {
                Figur figur = stellung.getFigur(reihe, linie);
                if (figur != null) {
                    double wert = figurenWert(figur);
                    if (figur.getFarbe() == ausSicht) {
                        summe += wert;
                    } else {
                        summe -= wert;
                    }
                }
            }
        }

        return summe;
    }

    /**
     * Materialwert der Figur.
     */
    protected int figurenWert(final Figur f) {
        switch (f.getArt()) {
            case BAUER:
                return 1;
            case SPRINGER:
            case LAEUFER:
                return 3;
            case TURM:
                return 5;
            case DAME:
                return 9;
            default:
                return 0;
        }
    }
}
