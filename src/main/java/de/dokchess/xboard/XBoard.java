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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.engine.Engine;
import de.dokchess.regeln.Spielregeln;

/**
 * Implementierung des XBoard-Protokolls. Enthaelt nur die wichtigsten Befehle.
 * 
 * @author StefanZ
 */
public class XBoard {

	private Reader eingabe;

	private BufferedReader bufEingabe;

	private Writer ausgabe;

	private Spielregeln spielregeln;

	private Engine engine;

	/**
	 * Setzt die Protokoll-Eingabe per Dependency Injection. Typischerweise ist
	 * das die Standardeingabe (stdin); z.B. f&uuml;r automatische Tests kann
	 * eine andere Quelle verwendet werden.
	 * 
	 * @param reader
	 */
	public void setEingabe(Reader reader) {
		this.eingabe = reader;
		this.bufEingabe = new BufferedReader(eingabe);
	}

	/**
	 * Setzt die Protokoll-Ausgabe. Typischerweise ist das die Standardausgabe
	 * (stdout), f&uuml;r automatische Tests kann eine andere Senke verwendet
	 * werden.
	 * 
	 * @param writer
	 *            die Ausgabe
	 */
	public void setAusgabe(Writer writer) {
		this.ausgabe = writer;
	}

	/**
	 * Setzt eine Implementierung der Spielregeln. Optional, ohne Spielregeln
	 * werden die eingehenden Zuege nicht geprueft.
	 * 
	 * @param spielregeln
	 *            die Spielregeln
	 */
	public void setSpielregeln(Spielregeln spielregeln) {
		this.spielregeln = spielregeln;
	}

	/**
	 * Setzt eine Implementierung der Engine. Erforderlich zum Spielen.
	 * 
	 * @param engine
	 *            die Engine
	 */
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	/**
	 * Startet die eigentliche Kommunikation (Eingabe/Verarbeitung/Ausgabe) in
	 * einer Endlosschleife, bis zum Beenden-Kommando.
	 */
	public void spielen() {

		ZugParser parser = new ZugParser();
		Stellung stellung = new Stellung();

		boolean running = true;
		while (running) {

			String eingelesen = lesen();

			if (eingelesen.equals("quit")) {
				running = false;
			}

			if (eingelesen.equals("xboard")) {
				schreiben("");
			}

			if (eingelesen.equals("protover 2")) {
				schreiben("feature done=1");
			}

			if (eingelesen.equals("new")) {
				stellung = new Stellung();
				engine.figurenAufbauen(stellung);
			}

			if (eingelesen.equals("go")) {
				Zug engineZug = engine.ermittleDeinenZug();
				stellung = stellung.fuehreZugAus(engineZug);
				engine.ziehen(engineZug);
				schreiben(parser.nachXboard(engineZug));
			}

			Zug zug = parser.vonXboard(eingelesen, stellung);
			if (zug != null) {

				if (spielregeln != null) {
					Collection<Zug> gueltigeZuege = spielregeln
							.ermittleGueltigeZuege(stellung);
					if (!gueltigeZuege.contains(zug)) {
						schreiben("tellall Ungueltiger Zug: " + zug);
						continue;
					}
				}

				engine.ziehen(zug);
				stellung = stellung.fuehreZugAus(zug);

				Zug engineZug = engine.ermittleDeinenZug();
				stellung = stellung.fuehreZugAus(engineZug);
				engine.ziehen(engineZug);
				schreiben(parser.nachXboard(engineZug));
			}

		}
	}

	String lesen() {
		String zeile = null;
		try {
			zeile = bufEingabe.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return zeile;
	}

	void schreiben(String zeile) {
		try {
			ausgabe.write(zeile + "\n");
			ausgabe.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
