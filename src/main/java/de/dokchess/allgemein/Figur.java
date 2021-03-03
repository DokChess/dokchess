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

package de.dokchess.allgemein;

/**
 * Figur im Schachspiel. Die Klasse ist unveraenderlich (immutable).
 *
 * @author StefanZ
 */
public final class Figur {

    /**
     * Farbe der Figut, etwa WEISS
     */
    private final Farbe farbe;

    /**
     * Art der Figur, etwa BAUER
     */
    private final FigurenArt art;

    /**
     * Erzeugt eine unveraenderliche Figur.
     *
     * @param a Art der Figur, etwa BAUER
     * @param f Farbe der Figut, etwa WEISS
     */
    public Figur(final FigurenArt a, final Farbe f) {
        this.art = a;
        this.farbe = f;
    }

    /**
     * Die Art der Figur. Z.B. BAUER.
     *
     * @return die Art.
     */
    public FigurenArt getArt() {
        return art;
    }

    /**
     * Die Farbe der Figur. Z.B. WEISS.
     *
     * @return die Farbe.
     */
    public Farbe getFarbe() {
        return farbe;
    }

    /**
     * Darstellung der Figur gemaess FEN. Ein kleiner Buchstabe steht fuer eine
     * schwarze Figur, ein grosser fuer eine weisse. 'K' ist zum Beispiel ein
     * weisser Koenig, 'q' eine schwarze Dame.
     *
     * @return Darstellung der Figur als Buchstabe
     */
    public char alsBuchstabe() {
        char buchstabe = art.getBuchstabe();
        if (farbe == Farbe.SCHWARZ) {
            buchstabe = Character.toLowerCase(buchstabe);
        }
        return buchstabe;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(farbe);
        if (art == FigurenArt.DAME) {
            sb.append("E ");
        } else {
            sb.append("ER ");
        }
        sb.append(art);

        return sb.toString();
    }

    /**
     * Testet ob die Figur von der gewuenschten Art ist.
     *
     * @param a die zu testenden Figurenart.
     * @return true, falls dem so ist
     */
    public boolean ist(final FigurenArt a) {
        return this.art == a;
    }

    /**
     * Testet ob die Figur von der gewuenschten Farbe ist.
     *
     * @param f die zu testenden Farbe.
     * @return true, falls dem so ist
     */
    public boolean ist(final Farbe f) {
        return this.farbe == f;
    }

    @Override
    public int hashCode() {
        return (farbe.ordinal() * 10) + art.ordinal();
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
        Figur andereFigur = (Figur) obj;
        if (art != andereFigur.art) {
            return false;
        }
        if (farbe != andereFigur.farbe) {
            return false;
        }
        return true;
    }
}
