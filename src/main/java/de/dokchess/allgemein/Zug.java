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
 * Beschreibt den Zug eines Spielers. Die Klasse ist unver&auml;nderlich
 * (immutable). Neben dem Start- und dem Zielfeld sind verschiedene weitere
 * Informationen enthalten, die in Analysesituationen n&uuml;tzlich sein k&ouml;nnen.
 *
 * @author StefanZ
 */
public final class Zug {

    private final Figur figur;

    private final Feld von;

    private final Feld nach;

    private final boolean schlagen;

    private final FigurenArt neueFigurenart;

    public Zug(Figur figur, Feld von, Feld nach, boolean schlagen,
               FigurenArt neueFigurenArt) {
        this.figur = figur;
        this.von = von;
        this.nach = nach;
        this.schlagen = schlagen;
        this.neueFigurenart = neueFigurenArt;
    }

    public Zug(Figur figur, Feld von, Feld nach) {
        this(figur, von, nach, false, null);
    }

    public Zug(Figur figur, Feld von, Feld nach, boolean schlagen) {
        this(figur, von, nach, schlagen, null);
    }

    public Zug(Figur figur, Feld von, Feld nach, FigurenArt neueFigurenArt) {
        this(figur, von, nach, false, neueFigurenArt);
    }

    public Feld getVon() {
        return von;
    }

    public Feld getNach() {
        return nach;
    }

    public FigurenArt getNeueFigurenart() {
        return neueFigurenart;
    }

    public Figur getFigur() {
        return figur;
    }

    public boolean istSchlagen() {
        return schlagen;
    }

    /**
     * Liefert zurueck, ob es sich um einen Bauernzug handelt. Dies kann
     * Bewegen, Schlagen, Umwandlung sein.
     *
     * @return true, falls ein Bauer bewegt wird.
     */
    public boolean istBauernZug() {
        return figur.getArt() == FigurenArt.BAUER;
    }

    /**
     * Liefert zurueck, ob mit dem Zug ein Bauer 2 Felder vor bewegt wird.
     *
     * @return true, falls ein Bauer zwei Feldet vor bewegt wird.
     */

    public boolean istBauernZugZweiVor() {
        return figur.getArt() == FigurenArt.BAUER
                && Math.abs(von.getReihe() - nach.getReihe()) == 2;
    }

    public boolean istUmwandlung() {
        return neueFigurenart != null;
    }

    public boolean istRochade() {
        return figur.getArt() == FigurenArt.KOENIG
                && Math.abs(von.getLinie() - nach.getLinie()) == 2;
    }

    public boolean istRochadeKurz() {
        return istRochade() && nach.getLinie() == 6;
    }

    public boolean istRochadeLang() {
        return istRochade() && nach.getLinie() == 2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((figur == null) ? 0 : figur.hashCode());
        result = prime * result + ((nach == null) ? 0 : nach.hashCode());
        result = prime * result
                + ((neueFigurenart == null) ? 0 : neueFigurenart.hashCode());
        result = prime * result + (schlagen ? 1231 : 1237);
        result = prime * result + ((von == null) ? 0 : von.hashCode());
        return result;
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
        Zug other = (Zug) obj;
        if (figur == null) {
            if (other.figur != null) {
                return false;
            }
        } else if (!figur.equals(other.figur)) {
            return false;
        }
        if (nach == null) {
            if (other.nach != null) {
                return false;
            }
        } else if (!nach.equals(other.nach)) {
            return false;
        }
        if (neueFigurenart != other.neueFigurenart) {
            return false;
        }
        if (schlagen != other.schlagen) {
            return false;
        }
        if (von == null) {
            if (other.von != null) {
                return false;
            }
        } else if (!von.equals(other.von)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (this.figur.getArt() != FigurenArt.BAUER) {
            sb.append(figur.getArt().getBuchstabe());
            sb.append(' ');
        }
        sb.append(von);
        if (schlagen) {
            sb.append('x');
        } else {
            sb.append('-');
        }
        sb.append(nach);

        if (neueFigurenart != null) {
            sb.append(' ');
            sb.append(neueFigurenart.getBuchstabe());
        }

        return sb.toString();
    }
}
