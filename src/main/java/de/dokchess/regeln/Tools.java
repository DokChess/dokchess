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
package de.dokchess.regeln;

import de.dokchess.allgemein.*;

import java.util.EnumSet;
import java.util.Set;

final class Tools {

    private static final Set<FigurenArt> DAME_LAEUFER = EnumSet.of(FigurenArt.DAME,
            FigurenArt.LAEUFER);
    private static final Set<FigurenArt> DAME_TURM = EnumSet.of(FigurenArt.DAME,
            FigurenArt.TURM);

    /**
     * Toolklasse, Konstruktor nicht sichtbar.
     */
    private Tools() {
    }

    /**
     * Prueft ob das angegebene Feld von einer Figur in der angegebenen Farbe
     * angegriffen wird. Dabei ist unerheblich, ob das zu pruefende Feld besetzt
     * ist. Die Methode kann daher sowohl fuer eine Schach/Matt-Pruefung,als
     * auch auf gueltige Rochade eingesetzt werden.
     *
     * @param stellung Stellung, der die ggf. angreifenden Figuren entnommen werden
     * @param feld     zu pruefendes Feld
     * @param farbe    Farbe des potentiellen Angreifers
     * @return true, falls das Feld von einer Figur passender Farbe angegriffen
     * wird.
     */
    public static boolean istFeldAngegriffen(Stellung stellung, Feld feld,
                                             Farbe farbe) {

        // Schraeg (Dame/Laeufer)
        boolean dameLauferAngriff = istFeldAngegriffenInRichtung(stellung,
                feld, farbe, 1, 1, DAME_LAEUFER)
                || istFeldAngegriffenInRichtung(stellung, feld, farbe, -1, -1,
                DAME_LAEUFER)
                || istFeldAngegriffenInRichtung(stellung, feld, farbe, 1, -1,
                DAME_LAEUFER)
                || istFeldAngegriffenInRichtung(stellung, feld, farbe, -1, 1,
                DAME_LAEUFER);
        if (dameLauferAngriff) {
            return true;
        }

        // Gerade (Dame/Turm)
        boolean dameTurmAngriff = istFeldAngegriffenInRichtung(stellung, feld,
                farbe, 1, 0, DAME_TURM)
                || istFeldAngegriffenInRichtung(stellung, feld, farbe, 0, 1,
                DAME_TURM)
                || istFeldAngegriffenInRichtung(stellung, feld, farbe, -1, 0,
                DAME_TURM)
                || istFeldAngegriffenInRichtung(stellung, feld, farbe, 0, -1,
                DAME_TURM);
        if (dameTurmAngriff) {
            return true;
        }

        // Springer
        boolean springerAngriff = istFeldAngegriffenVonFeld(stellung, feld,
                farbe, 1, 2, FigurenArt.SPRINGER)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, 1, -2,
                FigurenArt.SPRINGER)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, -1, 2,
                FigurenArt.SPRINGER)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, -1, -2,
                FigurenArt.SPRINGER)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, 2, 1,
                FigurenArt.SPRINGER)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, 2, -1,
                FigurenArt.SPRINGER)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, -2, 1,
                FigurenArt.SPRINGER)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, -2, -1,
                FigurenArt.SPRINGER);
        if (springerAngriff) {
            return true;
        }

        // Bauer
        int delta = farbe == Farbe.WEISS ? +1 : -1;
        boolean bauernAngriff = istFeldAngegriffenVonFeld(stellung, feld,
                farbe, 1, delta, FigurenArt.BAUER)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, -1, delta,
                FigurenArt.BAUER);
        if (bauernAngriff) {
            return true;
        }

        // Koenig
        boolean koenigsAngriff = istFeldAngegriffenVonFeld(stellung, feld,
                farbe, 0, 1, FigurenArt.KOENIG)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, 0, -1,
                FigurenArt.KOENIG)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, 1, 0,
                FigurenArt.KOENIG)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, -1, 0,
                FigurenArt.KOENIG)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, 1, 1,
                FigurenArt.KOENIG)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, 1, -1,
                FigurenArt.KOENIG)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, -1, 1,
                FigurenArt.KOENIG)
                || istFeldAngegriffenVonFeld(stellung, feld, farbe, -1, -1,
                FigurenArt.KOENIG);

        if (koenigsAngriff) {
            return true;
        }

        return false;
    }

    private static boolean istFeldAngegriffenInRichtung(Stellung stellung,
                                                        Feld feld, Farbe farbe, int dx, int dy, Set<FigurenArt> figurenArten) {

        int reihe = feld.getReihe();
        int linie = feld.getLinie();
        boolean weiter = true;

        while (weiter) {

            linie += dx;
            reihe += dy;

            if (linie >= 0 && linie < 8 && reihe >= 0 && reihe < 8) {
                Figur figur = stellung.getFigur(reihe, linie);
                if (figur != null) {
                    weiter = false;
                    if (figur.getFarbe() == farbe) {
                        if (figurenArten.contains(figur.getArt())) {
                            return true;
                        }
                    }
                }
            } else {
                weiter = false;
            }
        }
        return false;
    }

    private static boolean istFeldAngegriffenVonFeld(Stellung stellung,
                                                     Feld feld, Farbe farbe, int dx, int dy, FigurenArt figurenArt) {

        int linie = feld.getLinie() + dx;
        int reihe = feld.getReihe() + dy;

        if (linie >= 0 && linie < 8 && reihe >= 0 && reihe < 8) {
            Figur figur = stellung.getFigur(reihe, linie);
            if (figur != null) {
                if (figur.getFarbe() == farbe) {
                    if (figur.getArt() == figurenArt) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
