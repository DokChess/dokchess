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
import de.dokchess.engine.bewertung.Bewertung;
import de.dokchess.regeln.Spielregeln;

import java.util.Collection;

public class MinimaxAlgorithmus {

    protected Spielregeln spielregeln;

    protected Bewertung bewertung;

    private int tiefe;

    /**
     * Setzt die Bewertungsfunktion, anhand derer die Stellungen bei Erreichen
     * der maximalen Suchtiefe bewertet werden.
     */
    public void setBewertung(Bewertung bewertung) {
        this.bewertung = bewertung;
    }

    /**
     * Setzt eine Implementierung der Spielregeln, anhand erlaubte moegliche
     * Zuege und auch Matt und Patt erkannt werden.
     */
    public void setSpielregeln(Spielregeln spielregeln) {
        this.spielregeln = spielregeln;
    }

    /**
     * Setzt die maximale Suchtiefe in Halbzuegen, das heisst bei 4 zieht jeder
     * Spieler zweimal.
     *
     * @param tiefe Suchtiefe in Halbzuegen
     */
    public void setTiefe(int tiefe) {
        this.tiefe = tiefe;
    }

    public Zug ermittleBestenZug(Stellung stellung) {

        Farbe spielerFarbe = stellung.getAmZug();
        Collection<Zug> zuege = spielregeln.liefereGueltigeZuege(stellung);

        int besterWert = Bewertung.MINIMALE_BEWERTUNG;
        Zug besterZug = null;

        for (Zug zug : zuege) {
            Stellung neueStellung = stellung.fuehreZugAus(zug);

            int wert = bewerteStellungRekursiv(neueStellung, spielerFarbe);

            if (wert > besterWert) {
                besterWert = wert;
                besterZug = zug;
            }
        }

        return besterZug;
    }


    protected int bewerteStellungRekursiv(Stellung stellung, Farbe spielerFarbe) {
        return bewerteStellungRekursiv(stellung, 1, spielerFarbe);
    }


    protected int bewerteStellungRekursiv(Stellung stellung, int aktuelleTiefe,
                                          Farbe spielerFarbe) {

        if (aktuelleTiefe == tiefe) {
            return bewertung.bewerteStellung(stellung, spielerFarbe);
        } else {
            Collection<Zug> zuege = spielregeln.liefereGueltigeZuege(stellung);
            if (zuege.isEmpty()) {

                // PATT
                if (!spielregeln
                        .aufSchachPruefen(stellung, stellung.getAmZug())) {
                    return Bewertung.PATT_BEWERTUNG;
                }

                // MATT
                // Tiefe mit einrechnen, um fruehes Matt zu bevorzugen
                if (stellung.getAmZug() == spielerFarbe) {
                    return -(Bewertung.MATT_BEWERTUNG - aktuelleTiefe);
                } else {
                    return Bewertung.MATT_BEWERTUNG - aktuelleTiefe;
                }

            } else {
                if (aktuelleTiefe % 2 == 0) {
                    // Max
                    int max = Bewertung.MINIMALE_BEWERTUNG;
                    for (Zug zug : zuege) {
                        Stellung neueStellung = stellung.fuehreZugAus(zug);
                        int wert = bewerteStellungRekursiv(neueStellung,
                                aktuelleTiefe + 1, spielerFarbe);
                        if (wert > max) {
                            max = wert;
                        }
                    }
                    return max;
                } else {
                    // Min
                    int min = Bewertung.MAXIMALE_BEWERTUNG;
                    for (Zug zug : zuege) {
                        Stellung neueStellung = stellung.fuehreZugAus(zug);
                        int wert = bewerteStellungRekursiv(neueStellung,
                                aktuelleTiefe + 1, spielerFarbe);
                        if (wert < min) {
                            min = wert;
                        }
                    }
                    return min;
                }
            }
        }
    }


}
