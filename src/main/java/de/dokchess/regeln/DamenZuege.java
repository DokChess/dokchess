/*
 * Copyright (c) 2010-2021 Stefan Zoerner
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

import java.util.ArrayList;
import java.util.List;

import de.dokchess.allgemein.Feld;
import de.dokchess.allgemein.Stellung;

class DamenZuege extends KomplexeGangart {

    protected List<Feld> ermittleErreichbareFelder(Stellung stellung, Feld feld) {

        List<Feld> felder = new ArrayList<Feld>();

        // Gerade
        fuegeFelderInRichtungHinzuFallsErreichbar(stellung, feld, 0, 1, felder);
        fuegeFelderInRichtungHinzuFallsErreichbar(stellung, feld, 1, 0, felder);
        fuegeFelderInRichtungHinzuFallsErreichbar(stellung, feld, 0, -1, felder);
        fuegeFelderInRichtungHinzuFallsErreichbar(stellung, feld, -1, 0, felder);

        // Schraeg
        fuegeFelderInRichtungHinzuFallsErreichbar(stellung, feld, 1, 1, felder);
        fuegeFelderInRichtungHinzuFallsErreichbar(stellung, feld, 1, -1, felder);
        fuegeFelderInRichtungHinzuFallsErreichbar(stellung, feld, -1, 1, felder);
        fuegeFelderInRichtungHinzuFallsErreichbar(stellung, feld, -1, -1,
                felder);

        return felder;
    }

}
