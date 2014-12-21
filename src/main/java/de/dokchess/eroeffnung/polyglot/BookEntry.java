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
package de.dokchess.eroeffnung.polyglot;

/**
 * Ein einzelner Eintrag im Eroeffnungsbuch.
 *
 * @author StefanZ
 */
class BookEntry {

    private byte[] key;
    private byte[] move;
    private byte[] weight;

    BookEntry(byte[] data) {
        this.key = new byte[8];
        System.arraycopy(data, 0, this.key, 0, 8);

        this.move = new byte[2];
        System.arraycopy(data, 8, this.move, 0, 2);

        this.weight = new byte[2];
        System.arraycopy(data, 10, this.weight, 0, 2);
    }

    String getMove() {
        return getMoveFrom() + getMoveTo();
    }

    byte[] getKey() {
        return key;
    }

    byte[] getWeight() {
        return weight;
    }

    int getWeightAsInt() {
        return PolyglotTools.twoBytesToInt(weight);
    }

    String getMoveFrom() {
        int file = getMoveFromFile();
        int row = getMoveFromRow();
        return PolyglotTools.fileAndRowToString(file, row);
    }

    String getMoveTo() {
        int file = getMoveToFile();
        int row = getMoveToRow();
        return PolyglotTools.fileAndRowToString(file, row);
    }

    int getMoveFromFile() {

        // bits 6,7,8
        int a = (move[1] & 64) != 0 ? 1 : 0;
        int b = (move[1] & 128) != 0 ? 1 : 0;
        int c = (move[0] & 1) != 0 ? 1 : 0;

        return 1 * a + 2 * b + 4 * c;
    }

    int getMoveFromRow() {

        // bits 3,4,5
        int a = (move[0] & 2) != 0 ? 1 : 0;
        int b = (move[0] & 4) != 0 ? 1 : 0;
        int c = (move[0] & 8) != 0 ? 1 : 0;

        return 1 * a + 2 * b + 4 * c;
    }

    int getMoveToFile() {

        // bits 0,1,2
        int a = (move[1] & 1) != 0 ? 1 : 0;
        int b = (move[1] & 2) != 0 ? 1 : 0;
        int c = (move[1] & 4) != 0 ? 1 : 0;

        return 1 * a + 2 * b + 4 * c;
    }

    int getMoveToRow() {

        // bits 3,4,5
        int a = (move[1] & 8) != 0 ? 1 : 0;
        int b = (move[1] & 16) != 0 ? 1 : 0;
        int c = (move[1] & 32) != 0 ? 1 : 0;

        return 1 * a + 2 * b + 4 * c;
    }

    @Override
    public String toString() {
        return getMove();
    }
}
