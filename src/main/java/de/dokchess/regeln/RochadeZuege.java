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

import java.util.List;

import static de.dokchess.allgemein.Farbe.SCHWARZ;
import static de.dokchess.allgemein.Farbe.WEISS;
import static de.dokchess.allgemein.Felder.*;
import static de.dokchess.allgemein.FigurenArt.KOENIG;

public class RochadeZuege extends Gangart {

    private static final Figur WEISSER_KOENIG = new Figur(KOENIG, WEISS);
    private static final Figur SCHWARZER_KOENIG = new Figur(KOENIG, SCHWARZ);

    @Override
    public void fuegeZugkandidatenHinzu(Feld von, Stellung stellung,
                                        List<Zug> liste) {

        switch (stellung.getAmZug()) {
            case WEISS:
                if (stellung.getRochadeRechte().contains(RochadeRecht.WEISS_KURZ)) {
                    if (alleFelderFrei(stellung, F1, G1)
                            && keinesDerFelderAngegriffen(stellung, SCHWARZ, E1,
                            F1, G1)) {
                        Zug rochadeKurz = new Zug(WEISSER_KOENIG, E1, G1);
                        liste.add(rochadeKurz);
                    }
                }
                if (stellung.getRochadeRechte().contains(RochadeRecht.WEISS_LANG)) {
                    if (alleFelderFrei(stellung, B1, C1, D1)
                            && keinesDerFelderAngegriffen(stellung, SCHWARZ, E1,
                            D1, C1)) {
                        Zug rochadeLang = new Zug(WEISSER_KOENIG, E1, C1);
                        liste.add(rochadeLang);
                    }
                }
                break;

            case SCHWARZ:
                if (stellung.getRochadeRechte().contains(RochadeRecht.SCHWARZ_KURZ)) {
                    if (alleFelderFrei(stellung, F8, G8)
                            && keinesDerFelderAngegriffen(stellung, WEISS, E8, F8,
                            G8)) {
                        Zug rochadeKurz = new Zug(SCHWARZER_KOENIG, E8, G8);
                        liste.add(rochadeKurz);
                    }
                }
                if (stellung.getRochadeRechte().contains(RochadeRecht.SCHWARZ_LANG)) {
                    if (alleFelderFrei(stellung, B8, C8, D8)
                            && keinesDerFelderAngegriffen(stellung, WEISS, E8, D8,
                            C8)) {
                        Zug rochadeLang = new Zug(SCHWARZER_KOENIG, E8, C8);
                        liste.add(rochadeLang);
                    }
                }
                break;
        }
    }

    protected boolean keinesDerFelderAngegriffen(Stellung stellung,
                                                 Farbe farbe, Feld... felder) {
        for (Feld feld : felder) {
            if (Tools.istFeldAngegriffen(stellung, feld, farbe)) {
                return false;
            }
        }
        return true;
    }

    protected boolean alleFelderFrei(Stellung stellung, Feld... felder) {
        for (Feld feld : felder) {
            if (stellung.getFigur(feld) != null) {
                return false;
            }
        }
        return true;
    }
}
