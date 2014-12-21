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

import de.dokchess.allgemein.Feld;
import de.dokchess.allgemein.Figur;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.eroeffnung.Eroeffnungsbibliothek;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PolyglotOpeningBook implements Eroeffnungsbibliothek {

    private AuswahlModus auswahlModus;

    private List<BookEntry> eintraege = new ArrayList<BookEntry>();

    public PolyglotOpeningBook(File datei) {
        this.auswahlModus = AuswahlModus.ERSTER;
        readData(datei);
    }

    public PolyglotOpeningBook(InputStream is) {
        this.auswahlModus = AuswahlModus.ERSTER;
        readData(is);
    }


    public void setAuswahlModus(AuswahlModus auswahlModus) {
        this.auswahlModus = auswahlModus;
    }

    @Override
    public Zug liefereZug(Stellung stellung) {

        String fen = stellung.toString();
        List<BookEntry> treffer = findEntriesByFen(fen);

        if (treffer != null && treffer.size() > 0) {
            BookEntry eintrag = treffer.get(0);

            Feld von = new Feld(eintrag.getMoveFrom());
            Feld nach = new Feld(eintrag.getMoveTo());
            Figur figur = stellung.getFigur(von);
            boolean schlagen = stellung.getFigur(nach) != null;

            Zug zug = new Zug(figur, von, nach, schlagen);

            return zug;
        } else {
            return null;
        }
    }

    void readData(File datei) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(datei);
            readData(fis);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    void readData(InputStream is) {
        try {
            BufferedInputStream bis = new BufferedInputStream(is);

            while (bis.available() > 0) {
                byte[] entry = new byte[16];
                bis.read(entry);
                BookEntry bookEntry = new BookEntry(entry);
                eintraege.add(bookEntry);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    List<BookEntry> getEntries() {
        return eintraege;
    }

    List<BookEntry> findEntriesByFen(String fen) {
        long key = FenTools.calculateKeyFromFen(fen);
        return findEntriesByKey(key);
    }

    List<BookEntry> findEntriesByKey(long key) {
        byte[] bytesKey = PolyglotTools.longToByteArray(key);
        return findEntriesByKey(bytesKey);
    }

    List<BookEntry> findEntriesByKey(byte[] key) {
        List<BookEntry> result = new ArrayList<BookEntry>();

        for (BookEntry bookEntry : eintraege) {
            boolean equal = true;
            byte[] bookEntryKey = bookEntry.getKey();
            for (int i = 0; i < 8 && equal; ++i) {
                if (bookEntryKey[i] != key[i]) {
                    equal = false;
                }
            }
            if (equal) {
                result.add(bookEntry);
            }
        }
        return result;
    }

}
