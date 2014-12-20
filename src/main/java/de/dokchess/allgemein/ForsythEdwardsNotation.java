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

import java.util.EnumSet;


/**
 * Die Forsyth-Edwards-Notation (FEN) ist eine Kurznotation, mit der jede
 * beliebige Brettstellung im Schach als eine Zeichenkette niedergeschrieben
 * werden kann.
 *
 * @author StefanZ
 */
class ForsythEdwardsNotation {

    private Felder() {
    }

    public static void fromString(Stellung pos, String fen) {

        String[] gruppen = fen.split(" ");

        int reihe = 0;
        int linie = 0;

        String figuren = gruppen[0];

        for (int i = 0; i < figuren.length(); ++i) {

            char figur = figuren.charAt(i);

            if (Character.isDigit(figur)) {
                int n = Integer.parseInt("" + figur);
                for (int j = 0; j < n; ++j) {
                    pos.setFigur(reihe, linie, null);
                    linie++;
                }
            } else if (figur == '/') {
                reihe++;
                linie = 0;
            } else {
                FigurenArt pieceType = FigurenArt.ausBuchstabe(figur);
                Farbe side = Character.isUpperCase(figur) ? Farbe.WEISS
                        : Farbe.SCHWARZ;
                Figur p = new Figur(pieceType, side);
                pos.setFigur(reihe, linie, p);
                linie++;
            }
        }

        String amZug = gruppen[1];
        char side = amZug.charAt(0);
        switch (side) {
            case 'w':
                pos.setAmZug(Farbe.WEISS);
                break;
            case 'b':
                pos.setAmZug(Farbe.SCHWARZ);
                break;
        }

        String rochadeRecht = gruppen[2];
        if (rochadeRecht.equals("-")) {
            pos.setRochadeRechte(EnumSet.noneOf(RochadeRecht.class));
        } else {
            EnumSet<RochadeRecht> rechte = EnumSet.noneOf(RochadeRecht.class);
            for (int i = 0; i < rochadeRecht.length(); ++i) {
                char c = rochadeRecht.charAt(i);
                rechte.add(RochadeRecht.ausBuchstabe(c));
            }
            pos.setRochadeRechte(rechte);
        }

        String enPassant = gruppen[3];
        if (enPassant.equals("-")) {
            pos.setEnPassantFeld(null);
        } else {
            Feld enPassantFeld = new Feld(enPassant);
            pos.setEnPassantFeld(enPassantFeld);
        }
    }

    public static String toString(Stellung position) {

        StringBuffer sb = new StringBuffer();

        for (int reihe = 0; reihe < 8; ++reihe) {
            int leer = 0;
            for (int linie = 0; linie < 8; ++linie) {

                Figur piece = position.getFigur(reihe, linie);
                if (piece == null) {
                    leer++;
                } else {
                    if (leer > 0) {
                        sb.append(leer);
                        leer = 0;
                    }
                    sb.append(piece.alsBuchstabe());
                }
            }
            if (leer > 0) {
                sb.append(leer);
            }
            if (reihe < 7) {
                sb.append("/");
            }
        }

        sb.append(" ");

        switch (position.getAmZug()) {
            case WEISS:
                sb.append('w');
                break;
            case SCHWARZ:
                sb.append('b');
                break;
        }

        sb.append(" ");

        if (position.getRochadeRechte().size() == 0) {
            sb.append("-");
        } else {
            for (RochadeRecht r : RochadeRecht.values()) {
                if (position.getRochadeRechte().contains(r)) {
                    sb.append(r.alsBuchstabe());
                }
            }
        }

        sb.append(" ");
        if (position.getEnPassantFeld() == null) {
            sb.append("-");
        } else {
            sb.append(position.getEnPassantFeld());
        }

        // TODO: Richtig implementieren

        sb.append(" 0 1");

        return sb.toString();
    }
}
