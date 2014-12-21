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

package de.dokchess.engine;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.engine.bewertung.ReineMaterialBewertung;
import de.dokchess.engine.zugauswahl.MinimaxAlgorithmus;
import de.dokchess.eroeffnung.Eroeffnungsbibliothek;
import de.dokchess.regeln.Spielregeln;

/**
 * Default-Implementierung einer Engine.
 * <p/>
 * Ben&ouml;tigt zum Arbeiten eine Implementierung der Spielregeln, die
 * Er&ouml;ffnungsbibliothek ist optional.
 *
 * @author StefanZ
 */
public class DefaultEngine implements Engine {

    private Stellung stellung;

    private ZugAuswaehlen zugAuswahl;

    public DefaultEngine(Spielregeln spielregeln) {
        this(spielregeln, null);
    }

    public DefaultEngine(Spielregeln spielregeln,
                         Eroeffnungsbibliothek eroeffnungsbibliothek) {

        this.stellung = new Stellung();

        MinimaxAlgorithmus minimax = new MinimaxAlgorithmus();
        minimax.setTiefe(4);
        minimax.setSpielregeln(spielregeln);
        minimax.setBewertung(new ReineMaterialBewertung());

        AusSuche ausSuche = new AusSuche(minimax);
        if (eroeffnungsbibliothek != null) {
            this.zugAuswahl = new AusBibliothek(eroeffnungsbibliothek, ausSuche);
        } else {
            this.zugAuswahl = ausSuche;
        }
    }

    @Override
    public void figurenAufbauen(Stellung stellung) {
        this.stellung = stellung;
    }

    @Override
    public Zug ermittleDeinenZug() {
        return zugAuswahl.waehleZug(stellung);
    }

    @Override
    public void ziehen(Zug zug) {
        stellung = stellung.fuehreZugAus(zug);
    }
}
