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
package de.dokchess.regeln.suite;

import java.util.ArrayList;
import java.util.List;

/**
 * Repraesentiert eine Datensatz (also eine Zeile) in der Testsuite.
 * 
 * @author StefanZ
 */
public class Datensatz {

	private String name;

	private String fen;

	private int anzahlErwarteteZuege;

	private List<String> gueltigeZuege;

	public Datensatz(String satz) {
		String[] token = satz.split(";");

		name = token[0].trim();
		fen = token[1].trim();
		anzahlErwarteteZuege = Integer.parseInt(token[2]);
		gueltigeZuege = new ArrayList<String>();
		if (token.length > 3) {
			String[] erwarteteZuege = token[3].split(",");
			for (String string : erwarteteZuege) {
				gueltigeZuege.add(string.trim());
			}
		}
	}

	/**
	 * Name des Datensatzes.
	 * 
	 * @return der Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Stellung in FEN-Notation.
	 * 
	 * @return Stellung als FEN
	 */
	public String getFen() {
		return fen;
	}

	/**
	 * Liste der laut Datensatz erwarteten Zuege in der Notation "e2e4".
	 * 
	 * @return gueltige Zuege laut Datensatz
	 */
	public List<String> getGueltigeZuege() {
		return gueltigeZuege;
	}

	/**
	 * Anzahl erwarteter Zuege laut Datensatz.
	 * 
	 * @return Anzahl erwarteter Zuege
	 */
	public int getAnzahlErwarteteZuege() {
		return anzahlErwarteteZuege;
	}

	@Override
	public String toString() {
		return String.format("%s \"%s\"", name, fen);
	}
}
