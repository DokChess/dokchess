/* Copyright 2010, 2011, 2012 Stefan Zoerner
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

import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import de.dokchess.engine.DefaultEngine;
import de.dokchess.engine.Engine;
import de.dokchess.eroeffnung.Eroeffnungsbibliothek;
import de.dokchess.eroeffnung.polyglot.PolyglotOpeningBook;
import de.dokchess.regeln.Spielregeln;
import de.dokchess.regeln.SpielregelnImpl;

/**
 * Startet DokChess auf der Kommandozeile. Verdrahtet stdin und stdout fuer das
 * XBoard-Protokoll und setzt die Eroeffnungsbibliothek, falls angegeben.
 * 
 * @author StefanZ
 */
public class Main {

	public static void main(String[] args) {
		
		Eroeffnungsbibliothek bibliothek = null;
		
		if (args.length > 0) {
			String dateiName = args[0];
			File eroeffnungen = new File(dateiName);
			if (! eroeffnungen.canRead()) {
				System.err.printf("Kann Eroeffnungsbibliothek aus [%s] nicht lesen.%n", args[0]);
				System.exit(1);
			} else {
				bibliothek = new PolyglotOpeningBook(eroeffnungen);
			}
		}		
		
		XBoard xBoard = new XBoard();

		InputStreamReader isr = new InputStreamReader(System.in);
		xBoard.setEingabe(isr);

		OutputStreamWriter writer = new OutputStreamWriter(System.out);
		xBoard.setAusgabe(writer);

		Spielregeln spielregeln = new SpielregelnImpl();
		Engine engine = new DefaultEngine(spielregeln, bibliothek);

		xBoard.setEngine(engine);
		xBoard.setSpielregeln(spielregeln);

		xBoard.spielen();
	}

}
