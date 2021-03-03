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
package de.dokchess.regeln;

import de.dokchess.allgemein.Feld;
import de.dokchess.allgemein.Figur;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;

import java.util.List;

/**
 * Abstrakte Oberklasse fuer alle gangarten im Schach.
 */
abstract class Gangart {

    /**
     * Ermittelt Zugkandidaten von einem Feld aus, und fuegt sie der uebergebenen Liste hinzu. Die Methode ist fuer
     * die unterschiedlichen Gangarten der Figuren entsprechend zu implementieren.
     *
     * @param von      Feld, auf dem die Figur steht
     * @param stellung zu untersuchende Stellung
     * @param liste    Zielliste fuer Zugkandidaten
     */
    abstract void fuegeZugkandidatenHinzu(Feld von, Stellung stellung,
                                          List<Zug> liste);

    /**
     * Marschiert von einem Feld aus eine Richtung entlang und fuegt alle Felder einer Liste hinzu,
     * die so erreichbar sind. Wird fuer Turm und aenliches verwendet.
     *
     * @param stellung zu untersuchende Stellung
     * @param von      Startfeld, von dem losmarschiert wird.
     * @param dx       Richtung dx
     * @param dy       Richtung dy
     * @param felder   Zielliste fuer die erreichbaren Felder
     */
    protected final void fuegeFelderInRichtungHinzuFallsErreichbar(final Stellung stellung,
                                                                   final Feld von, final int dx, final int dy, final List<Feld> felder) {

        Figur figurDieZieht = stellung.getFigur(von);
        boolean weiter = true;
        int reihe = von.getReihe();
        int linie = von.getLinie();

        while (weiter) {

            linie += dx;
            reihe += dy;

            if (aufDemBrett(reihe, linie)) {
                Figur zuSchlagendeFigur = stellung.getFigur(reihe, linie);
                if (zuSchlagendeFigur == null) {
                    felder.add(new Feld(reihe, linie));
                } else {
                    if (zuSchlagendeFigur.getFarbe() != figurDieZieht
                            .getFarbe()) {
                        // Schlagen
                        felder.add(new Feld(reihe, linie));
                    }
                    weiter = false;
                }
            } else {
                weiter = false;
            }
        }
    }

    /**
     * Guckt von einem Feld aus in Richtung eines anderes Feldes. Wenn es erreichbar ist, wird es einer
     * Zielliste hinzugefuegt. Dabei wird beruecksichtigt, ob das Zielffeld frei ist, oder von einer gegnerischen
     * Figur besetzt ist, die geschlagen werden kann.
     *
     * @param stellung  zu untersuchende Stellung
     * @param startfeld Startfeld, von dem losmarschiert wird.
     * @param dx        Richtung dx
     * @param dy        Richtung dy
     * @param felder    Zielliste fuer die erreichbaren Felder
     */
    protected final void fuegeFeldHinzuFallsErreichbar(final Stellung stellung,
                                                       final Feld startfeld, final int dx, final int dy, final List<Feld> felder) {

        int reihe = startfeld.getReihe() + dx;
        int linie = startfeld.getLinie() + dy;

        if (aufDemBrett(reihe, linie)) {
            Figur figurDieZieht = stellung.getFigur(startfeld);
            Figur zuSchlagendeFigur = stellung.getFigur(reihe, linie);
            if (zuSchlagendeFigur == null) {
                // Ziehe auf freies Feld
                felder.add(new Feld(reihe, linie));
            } else {
                if (zuSchlagendeFigur.getFarbe() != figurDieZieht.getFarbe()) {
                    // schlagen
                    felder.add(new Feld(reihe, linie));
                }
            }
        }
    }

    /**
     * prueft, ob Kordinaten noch auf dem Brett sind.
     *
     * @param reihe Reihe
     * @param linie Linie
     * @return true, falls noch auf dem Brett.
     */
    private boolean aufDemBrett(final int reihe, final int linie) {
        return linie >= 0 && linie < 8 && reihe >= 0 && reihe < 8;
    }
}
