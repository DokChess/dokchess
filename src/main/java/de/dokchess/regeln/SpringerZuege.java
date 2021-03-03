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

class SpringerZuege extends KomplexeGangart {

    protected List<Feld> ermittleErreichbareFelder(Stellung stellung,
                                                   Feld startfeld) {

        List<Feld> felder = new ArrayList<Feld>();

        fuegeFeldHinzuFallsErreichbar(stellung, startfeld, 2, 1, felder);
        fuegeFeldHinzuFallsErreichbar(stellung, startfeld, 2, -1, felder);
        fuegeFeldHinzuFallsErreichbar(stellung, startfeld, -2, 1, felder);
        fuegeFeldHinzuFallsErreichbar(stellung, startfeld, -2, -1, felder);
        fuegeFeldHinzuFallsErreichbar(stellung, startfeld, 1, 2, felder);
        fuegeFeldHinzuFallsErreichbar(stellung, startfeld, -1, 2, felder);
        fuegeFeldHinzuFallsErreichbar(stellung, startfeld, 1, -2, felder);
        fuegeFeldHinzuFallsErreichbar(stellung, startfeld, -1, -2, felder);

        return felder;
    }
}
