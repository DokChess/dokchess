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

import java.util.*;

public class DefaultSpielregeln implements Spielregeln {

    private SpringerZuege springerZuege = new SpringerZuege();

    private TurmZuege turmZuege = new TurmZuege();

    private DamenZuege damenZuege = new DamenZuege();

    private LaeuferZuege laeuferZuege = new LaeuferZuege();

    private BauernZuege bauernZuege = new BauernZuege();

    private KoenigsZuege koenigsZuege = new KoenigsZuege();

    private RochadeZuege rochadeZuege = new RochadeZuege();

    @Override
    public Collection<Zug> liefereGueltigeZuege(Stellung stellung) {

        List<Zug> zugKandidaten = new ArrayList<Zug>();
        Farbe amZug = stellung.getAmZug();

        Set<Feld> eigeneFelder = stellung.felderMitFarbe(amZug);
        for (Feld feld : eigeneFelder) {

            Figur meineFigur = stellung.getFigur(feld);
            switch (meineFigur.getArt()) {

                case SPRINGER:
                    springerZuege.fuegeZugkandidatenHinzu(feld, stellung,
                            zugKandidaten);
                    break;

                case DAME:
                    damenZuege.fuegeZugkandidatenHinzu(feld, stellung,
                            zugKandidaten);
                    break;

                case TURM:
                    turmZuege
                            .fuegeZugkandidatenHinzu(feld, stellung, zugKandidaten);
                    break;

                case LAEUFER:
                    laeuferZuege.fuegeZugkandidatenHinzu(feld, stellung,
                            zugKandidaten);
                    break;

                case BAUER:
                    bauernZuege.fuegeZugkandidatenHinzu(feld, stellung,
                            zugKandidaten);
                    break;

                case KOENIG:
                    koenigsZuege.fuegeZugkandidatenHinzu(feld, stellung,
                            zugKandidaten);
                    rochadeZuege.fuegeZugkandidatenHinzu(feld, stellung,
                            zugKandidaten);
            }
        }

        Iterator<Zug> iterator = zugKandidaten.iterator();
        while (iterator.hasNext()) {
            Zug zug = iterator.next();
            Stellung neueStellung = stellung.fuehreZugAus(zug);
            if (aufSchachPruefen(neueStellung, amZug)) {
                iterator.remove();
            }
        }

        return zugKandidaten;
    }

    @Override
    public Stellung liefereGrundaufstellung() {
        return new Stellung();
    }

    @Override
    public boolean aufSchachPruefen(Stellung stellung, Farbe farbe) {
        Feld feld = stellung.findeFeldMitKoenig(farbe);
        return Tools.istFeldAngegriffen(stellung, feld, farbe.andereFarbe());
    }

    @Override
    public boolean aufMattPruefen(Stellung stellung) {
        Farbe amZug = stellung.getAmZug();
        if (aufSchachPruefen(stellung, amZug)) {
            Collection<Zug> zuege = liefereGueltigeZuege(stellung);
            if (zuege.size() == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean aufPattPruefen(Stellung stellung) {
        Collection<Zug> zuege = liefereGueltigeZuege(stellung);
        if (zuege.isEmpty()) {
            Farbe amZug = stellung.getAmZug();
            return !aufSchachPruefen(stellung, amZug);
        }
        return false;
    }
}
