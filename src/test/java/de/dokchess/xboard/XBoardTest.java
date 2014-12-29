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

import de.dokchess.engine.DefaultEngine;
import de.dokchess.regeln.DefaultSpielregeln;
import de.dokchess.regeln.Spielregeln;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Minimaler Test fuer XBoard. Die Engine wird sofort verlassen,
 * nachdem das xboard-Protocol steht.
 */
public class XBoardTest {

    @Test
    public void einfachsteVerwendung() {

        XBoard xBoard = new XBoard();

        Reader eingabe = new StringReader("xboard\nprotover 2\nnew\nquit\n");
        Writer ausgabe = new StringWriter();

        xBoard.setEingabe(eingabe);
        xBoard.setAusgabe(ausgabe);

        Spielregeln spielregeln = new DefaultSpielregeln();
        xBoard.setEngine(new DefaultEngine(spielregeln));
        xBoard.setSpielregeln(spielregeln);

        xBoard.spielen();
    }
}
