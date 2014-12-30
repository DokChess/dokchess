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
import de.dokchess.engine.Engine;
import de.dokchess.regeln.DefaultSpielregeln;
import de.dokchess.regeln.Spielregeln;
import org.junit.Assert;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class XBoardTest {

    /**
     * Smoke-Test fuer XBoard. Die Engine wird sofort verlassen,
     * nachdem das xboard-Protocol steht.
     */
    @Test
    public void sofortVerlassen() {

        String eingegeben = "xboard\nprotover 2\nquit\n";

        XBoard xBoard = new XBoard();
        xBoard.setAusgabe(new StringWriter());
        xBoard.setEngine(new MockEngine());
        xBoard.setSpielregeln(null);

        Reader eingabe = new StringReader(eingegeben);
        xBoard.setEingabe(eingabe);

        xBoard.spielen();
    }

    /**
     * Eingabe eines unbekannten Befehls.
     */
    @Test
    public void unsinnEingeben() {
        XBoard xBoard = new XBoard();

        xBoard.setEngine(new MockEngine());
        xBoard.setSpielregeln(null);

        String s = "Quak\nquit\n";
        Reader eingabe = new StringReader(s);
        xBoard.setEingabe(eingabe);

        Writer ausgabe = new StringWriter();
        xBoard.setAusgabe(ausgabe);

        xBoard.spielen();

        Assert.assertTrue(ausgabe.toString().contains("Quak"));
    }

    /**
     * Weiss versucht den Turm auf a1 nach vorn zu ziehen.
     */
    @Test
    public void ungueltigerZug() {

        XBoard xBoard = new XBoard();

        Reader eingabe = new StringReader("xboard\nprotover 2\nnew\na1a2\nquit\n");
        Writer ausgabe = new StringWriter();

        xBoard.setEingabe(eingabe);
        xBoard.setAusgabe(ausgabe);

        Spielregeln spielregeln = new DefaultSpielregeln();
        xBoard.setSpielregeln(spielregeln);
        xBoard.setEngine(new MockEngine());

        xBoard.spielen();
        Assert.assertTrue(ausgabe.toString().contains("Ungueltiger Zug"));
    }

    /**
     * Weiss zieht e2-e4, Engine antwortet mit e7-e5.
     */
    @Test
    public void gueltigerZug() {

        Figur weisserBauer = new Figur(FigurenArt.BAUER, Farbe.WEISS);
        Zug e2e4 = new Zug(weisserBauer, Felder.e2, Felder.e4);
        Zug e7e5 = new Zug(weisserBauer, Felder.e7, Felder.e5);

        XBoard xBoard = new XBoard();

        Reader eingabe = new StringReader("xboard\nprotover 2\nnew\ne2e4\nquit\n");
        Writer ausgabe = new StringWriter();

        xBoard.setEingabe(eingabe);
        xBoard.setAusgabe(ausgabe);

        Spielregeln spielregeln = new DefaultSpielregeln();
        xBoard.setSpielregeln(spielregeln);

        Engine engine = new MockEngine(e7e5);
        engine.figurenAufbauen(new Stellung());
        engine.ziehen(e2e4);

        xBoard.setEngine(engine);

        xBoard.spielen();
        Assert.assertTrue(ausgabe.toString().contains("move e7e5"));
    }
}
