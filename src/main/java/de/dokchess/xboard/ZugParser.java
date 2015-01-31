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

package de.dokchess.xboard;

import de.dokchess.allgemein.*;

/**
 * Wandelt Zug-Objekte in das Format von XBoard um, und umgekehrt.
 *
 * @author StefanZ
 */
public class ZugParser {

    /**
     * Wandelt einen als Zeichenkette eingegebenen Zug in ein Zug-Objekt um.
     * Die Methode reichert das Zugobjekt mit einigen Informationen an (zum Beispiel, ob eine
     * Figur geschlagen wird), dazu wird die aktuelle Stellung benotigt.
     *
     * @param eingabe  eingelesener Zug
     * @param stellung Stellung, fuer Kontext-Informationen
     * @return Zug-Objekt, oder null, falls kein gueltiger Zug.
     */
    public Zug vonXboard(final String eingabe, final Stellung stellung) {

        Zug zug = null;

        if (eingabe.matches("[a-h][1-8][a-h][1-8][qrnb]?")) {

            Feld from = new Feld(eingabe.substring(0, 2));
            Feld to = new Feld(eingabe.substring(2, 4));
            boolean schlagen = false;
            FigurenArt neueFigurenArt = null;
            Figur figur = stellung.getFigur(from);

            if (stellung.getFigur(to) != null) {
                schlagen = true;
            }

            if (figur != null && figur.getArt() == FigurenArt.BAUER
                    && (to.getReihe() == 0 || to.getReihe() == 7)) {
                char neueFigur = eingabe.charAt(4);
                neueFigurenArt = FigurenArt.ausBuchstabe(neueFigur);
            }

            zug = new Zug(figur, from, to, schlagen, neueFigurenArt);
        }

        return zug;
    }

    /**
     * Wandelt ein Zug-Objekt in das Format fuer das XBoard-Protokoll zurueck.
     *
     * @param zug der umzuwandelnde Zug.
     * @return die entsprechende Zeichenkette.
     */
    public String nachXboard(final Zug zug) {

        StringBuilder sb = new StringBuilder();
        sb.append("move ");

        sb.append(zug.getVon());
        sb.append(zug.getNach());

        if (zug.istUmwandlung()) {
            char c = zug.getNeueFigurenart().getBuchstabe();
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
}
