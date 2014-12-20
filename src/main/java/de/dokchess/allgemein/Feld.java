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

package de.dokchess.allgemein;

/**
 * Ein Feld auf dem Schachbrett. Die Klasse ist unveraenderlich (immutable).
 *
 * @author StefanZ
 */
public final class Feld {

    private final int linie;
    private final int reihe;

    /**
     * Erzeugt ein Feld anhand der Koordinaten.
     *
     * @param reihe Reihe des Feldes, 0-7
     * @param linie Linie des Feldes, 0-7, 0 ist a
     */
    public Feld(int reihe, int linie) {
        this.reihe = reihe;
        this.linie = linie;
    }

    /**
     * Erzeugt ein Feld anhand des Namens.
     *
     * @param name Name des Feldes, z.B. "e4"
     */
    public Feld(String name) {

        char linie = name.charAt(0); // a - h
        char reihe = name.charAt(1); // 1 - 8

        if (Character.isUpperCase(linie)) {
            linie = Character.toLowerCase(linie);
        }

        this.reihe = (7 - (reihe - '1'));
        this.linie = linie - 'a';
    }

    /**
     * @return Nummer der Linie des Feldes (0-7), 0 entspricht der Linie a
     */
    public int getLinie() {
        return this.linie;
    }

    /**
     * @return Nummer der Reihe des Feldes (0-7)
     */
    public int getReihe() {
        return this.reihe;
    }

    @Override
    public int hashCode() {
        return reihe * 8 + linie;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Feld other = (Feld) obj;
        if (reihe != other.reihe) {
            return false;
        }
        if (linie != other.linie) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(2);

        String linien = "abcdefgh";
        String reihen = "87654321";

        sb.append(linien.charAt(this.linie));
        sb.append(reihen.charAt(this.reihe));

        return sb.toString();
    }
}
