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
import rx.Observable;

/**
 * Zentrale Schnittstelle des Engine-Subsystems. Ermittlung des n&auml;chsten
 * Zuges, ausgehend von einer Spielsituation. Diese Situation kann von aussen
 * vorgegeben werden. Die Engine ist zustandsbehaftet und spielt stets eine
 * Partie zur gleichen Zeit.
 *
 * @author StefanZ
 */
public interface Engine {

    /**
     * Setzt den Zustand der Engine auf die angegebene Spielsituation.
     * Falls aktuell eine Zugermittlung l&auml;uft, wird diese abgebrochen.
     *
     * @param stellung die neue Stellung
     */
    void figurenAufbauen(Stellung stellung);

    /**
     * Liefert den aus Sicht der Engine optimalen Zug f&uuml;r den aktuellen Spieler,
     * ohne ihn auszuf&uuml;hren.
     * Als Ergebnis wird ein Observable zur&uuml;ckgeliefert,
     * d.h. die Methode blockiert nicht, die Engine rechnet ggf. im Hintergrund.
     * Neue beste Z&uuml;ge werden &uuml;ber onNext() gemeldet, das Ende der Berechnung mit onComplete.
     *
     * @return Observable, ueber das der beste Zug aus Sicht der Engine &uuml;bermittelt wird, sowie Zwischenergebnisse.
     */
    Observable<Zug> ermittleDeinenZug();

    /**
     * F&uuml;hrt den angegebenen Zug aus, d.h. &auml;ndert den Zustand der
     * Engine. Falls aktuell eine Zugermittlung l&auml;uft, wird diese abgebrochen.
     *
     * @param zug der auszuf&uuml;rende Zug.
     */
    void ziehen(Zug zug);

    /**
     * Schliesst die Engine. Die Methode erlaubt es der Engine, Ressourcen frei zu geben.
     * Im Anschluss sind keine Zugermittlungen mehr m&ouml;glich.
     */
    void schliessen();
}
