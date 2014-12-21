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

package de.dokchess.engine.zugauswahl;

import de.dokchess.allgemein.Farbe;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.engine.bewertung.Bewertung;
import de.dokchess.regeln.Spielregeln;

import java.util.Collection;

public class MinimaxAlgorithmus implements ZugAuswahl {

    private Spielregeln spielregeln;

    private Bewertung bewertung;

    private int tiefe;

    @Override
    public Zug ermittleZug(Stellung stellung) {

        Farbe spielerFarbe = stellung.getAmZug();
        Collection<Zug> zuege = spielregeln.ermittleGueltigeZuege(stellung);

        int besterWert = -Integer.MAX_VALUE;
        Zug besterZug = null;

        for (Zug zug : zuege) {
            Stellung neueStellung = stellung.fuehreZugAus(zug);

            int wert = ermittleZugRekursiv(neueStellung, 1, spielerFarbe);

            if (wert > besterWert) {
                besterWert = wert;
                besterZug = zug;
            }
        }

        return besterZug;
    }

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
     * Setzt die maximale Suchtiefe in Halbzuegen, das heist bei 4 zieht jeder
     * Spieler zweimal.
     *
     * @param tiefe Suchtiefe in Halbzuegen
     */
    public void setTiefe(int tiefe) {
        this.tiefe = tiefe;
    }

    protected int ermittleZugRekursiv(Stellung stellung, int aktuelleTiefe,
                                      Farbe spielerFarbe) {

        if (aktuelleTiefe == tiefe) {
            return bewertung.bewerteStellung(stellung, spielerFarbe);
        } else {
            Collection<Zug> zuege = spielregeln.ermittleGueltigeZuege(stellung);
            if (zuege.isEmpty()) {

                // PATT
                if (!spielregeln
                        .aufSchachPruefen(stellung, stellung.getAmZug())) {
                    return 0;
                }

                // MATT
                if (stellung.getAmZug() == spielerFarbe) {
                    return -(100000 - aktuelleTiefe);
                } else {
                    return 100000 - aktuelleTiefe;
                }

            } else {
                if (aktuelleTiefe % 2 == 0) {
                    // Max
                    int max = -Integer.MAX_VALUE;
                    for (Zug zug : zuege) {
                        Stellung neueStellung = stellung.fuehreZugAus(zug);
                        int wert = ermittleZugRekursiv(neueStellung,
                                aktuelleTiefe + 1, spielerFarbe);
                        if (wert > max) {
                            max = wert;
                        }
                    }
                    return max;
                } else {
                    // Min
                    int min = Integer.MAX_VALUE;
                    for (Zug zug : zuege) {
                        Stellung neueStellung = stellung.fuehreZugAus(zug);
                        int wert = ermittleZugRekursiv(neueStellung,
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
