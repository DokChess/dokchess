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

import de.dokchess.allgemein.Farbe;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;

import java.util.Collection;

/**
 * Spielregeln fuer Schach.
 */
public interface Spielregeln {

    /**
     * Liefert die Menge aller g&uuml;ltigen Z&uuml;ge f&uuml;r den aktuellen
     * Spieler. Der Spieler am Zug wird aus der Stellung ermittelt. Im Falle
     * eines Matt oder Patt wird eine leere Collection zur&uuml;ckgeliefert, das
     * Ergebnis ist also nie null.
     *
     * @param stellung zu betrachtende Spielsituation
     * @return Menge, der Z&uuml;ge, gegebenenfalls leer
     */
    Collection<Zug> liefereGueltigeZuege(Stellung stellung);

    /**
     * Liefert die Grundaufstellung zu Beginn einer Partie zur&uuml;ck.
     *
     * @return Grundaufstellung
     */
    Stellung liefereGrundaufstellung();

    /**
     * Pr&uuml;ft, ob der K&ouml;nig der angegebenen Farbe angegriffen ist,
     * also im Schach steht.
     *
     * @param stellung zu betrachtende Spielsituation
     * @param farbe Farbe des Koenigs, der untersucht werden soll
     * @return true im Falle eines Schachs
     */
    boolean aufSchachPruefen(Stellung stellung, Farbe farbe);

    /**
     * Pr&uuml;ft, ob die &uuml;bergebene Stellung ein Matt ist, also
     * der aktuellen Spieler am Zug im Schach steht und kein Zug ihn aus diesem
     * Angriff f&uuml;hrt. Eine solche Spielsituation ist f&uuml;r den Spiel am
     * Zug verloren ("Schach Matt").
     *
     * @param stellung zu betrachtende Spielsituation
     * @return true im Falle eines Schach Matt
     */
    boolean aufMattPruefen(Stellung stellung);

    /**
     * Pr&uuml;ft, ob die &uuml;bergebene Stellung ein Patt ist, also
     * der aktuelle Spieler keinen g&uuml;ltigen Zug hat, aber nicht im Schach
     * steht. Eine solche Spielsituation wird Remis gewertet.
     *
     * @param stellung zu betrachtende Spielsituation
     * @return true im Falle eines Schach Matt
     */
    boolean aufPattPruefen(Stellung stellung);
}
