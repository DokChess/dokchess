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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.regeln.Spielregeln;
import de.dokchess.regeln.SpielregelnImpl;

@RunWith(Parameterized.class)
public class SpielregelnTestSuite {

	private Spielregeln regeln;

	private Datensatz datenSatz;

	private Collection<Zug> ermittelteZuege;

	public SpielregelnTestSuite(Datensatz satz) {
		this.datenSatz = satz;
	}

	@Before
	public void setup() {
		this.regeln = new SpielregelnImpl();

		String fen = datenSatz.getFen();
		Stellung stellung = new Stellung(fen);

		this.ermittelteZuege = regeln.ermittleGueltigeZuege(stellung);
	}

	/**
	 * Stimmt die Anzahl der von den Spielregeln ermittelten Zuege mit der
	 * Anzahl erwarteten Zuege ueberein?
	 */
	@Test
	public void anzahlZuegeKorrekt() {
		Assert.assertEquals(datenSatz.toString(),
				datenSatz.getAnzahlErwarteteZuege(), ermittelteZuege.size());
	}

	/**
	 * Sind alle von den Spielregeln ermittelten Zuege laut Datensatz erwartet?
	 */
	@Test
	public void alleErmitteltenKorrekt() {
		List<String> erwartete = datenSatz.getGueltigeZuege();
		List<String> ermittelte = zuegeInZeichenkettenUmwandeln(ermittelteZuege);

		for (String zug : ermittelte) {
			String meldung = String.format("%s: Zug %s falsch", datenSatz, zug);
			Assert.assertTrue(meldung, erwartete.contains(zug));
		}
	}

	/**
	 * Sind alle laut Datensatz erwartetetn Zuege ermittelt worden?  	
	 */
	@Test
	public void alleGefordertenErmittelt() {
		List<String> erwartete = datenSatz.getGueltigeZuege();
		List<String> ermittelte = zuegeInZeichenkettenUmwandeln(ermittelteZuege);

		for (String zug : erwartete) {
			String meldung = String.format("%s: %s fehlt", datenSatz, zug);
			Assert.assertTrue(meldung, ermittelte.contains(zug));
		}
	}

	@Parameters
	public static Collection<Object[]> saetzeLesen() {

		Collection<Object[]> params = new ArrayList<Object[]>();

		String datei = "de/dokchess/regeln/suite/testsuite_r.txt";

		try {
			InputStream in = SpielregelnTestSuite.class.getClassLoader()
					.getResourceAsStream(datei);
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader buf = new BufferedReader(reader);

			String zeile = null;
			while ((zeile = buf.readLine()) != null) {

				if (!(zeile.equals("") || zeile.startsWith("#"))) {
					Datensatz satz = new Datensatz(zeile);
					Object[] arr = new Object[] { satz };
					params.add(arr);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return params;
	}

	public List<String> zuegeInZeichenkettenUmwandeln(Collection<Zug> zuege) {
		List<String> ergebnis = new ArrayList<String>();
		for (Zug zug : zuege) {
			String szug = zug.getVon().toString() + zug.getNach().toString();
			if (zug.istUmwandlung()) {
				szug = szug
						+ Character.toUpperCase(zug.getNeueFigurenart()
								.getBuchstabe());
			}
			ergebnis.add(szug);
		}
		return ergebnis;
	}
}
