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

import de.dokchess.allgemein.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static de.dokchess.allgemein.Farbe.WEISS;
import static de.dokchess.allgemein.FigurenArt.*;

class BauernZuege extends Gangart {

    private static final Set<FigurenArt> UMWANDLUNG_OPTIONEN;

    static {
        UMWANDLUNG_OPTIONEN = EnumSet.noneOf(FigurenArt.class);
        UMWANDLUNG_OPTIONEN.add(DAME);
        UMWANDLUNG_OPTIONEN.add(TURM);
        UMWANDLUNG_OPTIONEN.add(LAEUFER);
        UMWANDLUNG_OPTIONEN.add(SPRINGER);
    }

    public void fuegeZugkandidatenHinzu(Feld von, Stellung stellung,
                                        List<Zug> liste) {

        Figur eigenerBauer = stellung.getFigur(von);
        int delta1 = stellung.getAmZug() == WEISS ? -1 : +1;
        int delta2 = stellung.getAmZug() == WEISS ? -2 : +2;

        {
            // Ein Feld vor
            //
            Feld nach = new Feld(von.getReihe() + delta1, von.getLinie());
            if (stellung.getFigur(nach) == null) {
                if (nach.getReihe() != 0 && nach.getReihe() != 7) {
                    // Keine Umwandlung
                    Zug z = new Zug(eigenerBauer, von, nach);
                    liste.add(z);
                } else {
                    // Umwandlung
                    for (FigurenArt neueFigurenart : UMWANDLUNG_OPTIONEN) {
                        Zug z = new Zug(eigenerBauer, von, nach, neueFigurenart);
                        liste.add(z);
                    }
                }
            }
        }
        {
            // Zwei Felder vor
            //

            int startReihe = stellung.getAmZug() == WEISS ? 6 : 1;

            if (von.getReihe() == startReihe) {
                if (stellung.getFigur(startReihe + delta1, von.getLinie()) == null) {
                    if (stellung.getFigur(startReihe + delta2, von.getLinie()) == null) {
                        Feld nach = new Feld(startReihe + delta2,
                                von.getLinie());
                        Zug z = new Zug(eigenerBauer, von, nach);
                        liste.add(z);
                    }
                }
            }
        }
        {

            // Schlagen
            //
            List<Feld> squares = new ArrayList<Feld>();
            fuegeFeldHinzuFallsErreichbar(stellung, von, delta1, +1, squares);
            fuegeFeldHinzuFallsErreichbar(stellung, von, delta1, -1, squares);
            for (Feld nach : squares) {
                if (nach.getReihe() != 0 && nach.getReihe() != 7) {
                    if (stellung.getFigur(nach) != null) {
                        // Schlagen, keine Umwandlung
                        Zug m = new Zug(eigenerBauer, von, nach, true);
                        liste.add(m);
                    } else if (nach.equals(stellung.getEnPassantFeld())) {
                        // Schlagen, en passent
                        Zug m = new Zug(eigenerBauer, von, nach, true);
                        liste.add(m);
                    }

                } else {
                    if (stellung.getFigur(nach) != null) {
                        // Wandlung
                        for (FigurenArt neueFigurenart : UMWANDLUNG_OPTIONEN) {
                            Zug z = new Zug(eigenerBauer, von, nach, true,
                                    neueFigurenart);
                            liste.add(z);
                        }
                    }
                }
            }
        }
    }
}
