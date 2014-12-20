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

/**
 * Zentrale Schnittstelle des Engine-Subsystems. Ermittlung des n&auml;chsten
 * Zuges, ausgehend von einer Spielsituation. Diese Situation kann von aussen
 * vorgegeben werden. Die Engine ist zustandsbehaftet und spielt stets eine
 * Partie zur gleichen Zeit
 *
 * @author StefanZ
 */
public interface Engine {

    /**
     * Setzt den Zustand der Engine auf die angegebene Spielsituation.
     *
     * @param stellung die neue Stellung
     */
    void figurenAufbauen(Stellung stellung);

    /**
     * Liefert den aus Sicht der Engine optimalen Zug f&uuml;r den aktuellen Spieler,
     * ohne ihn auszuf&uuml;hren.
     *
     * @return der beste Zug aus Sicht der Engine
     */
    Zug ermittleDeinenZug();

    /**
     * F&uuml;hrt den angegebenen Zug aus, d.h. &auml;ndert den Zustand der
     * Engine.
     *
     * @param zug
     */
    void ziehen(Zug zug);
}
