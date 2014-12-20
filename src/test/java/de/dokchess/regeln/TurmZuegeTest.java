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
package de.dokchess.regeln;

import static de.dokchess.allgemein.Felder.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.dokchess.allgemein.Farbe;
import de.dokchess.allgemein.Feld;
import de.dokchess.allgemein.Figur;
import de.dokchess.allgemein.FigurenArt;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;

public class TurmZuegeTest {

	private static final Figur TURM_WEISS = new Figur(FigurenArt.TURM,
			Farbe.WEISS);

	@Test
	public void einzelnerTurmInDerEcke() {

		{
			Stellung weisserTurmA1 = new Stellung("8/8/8/8/8/8/8/R7 w - - 0 1");

			TurmZuege turmZuege = new TurmZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			turmZuege.fuegeZugkandidatenHinzu(A1, weisserTurmA1, zuege);

			Assert.assertEquals(14, zuege.size());
			for (Zug zug : zuege) {
				Assert.assertEquals(A1, zug.getVon());
			}
			Feld[] ziele = { B1, C1, D1, E1, F1, G1, H1, A2, A3, A4, A5, A6,
					A7, A8 };
			for (Feld ziel : ziele) {
				Zug zuTesten = new Zug(TURM_WEISS, A1, ziel);
				Assert.assertTrue(zuege.contains(zuTesten));
			}
		}

		{
			Stellung weisserTurmH8 = new Stellung("7R/8/8/8/8/8/8/8 w - - 0 1");

			TurmZuege turmZuege = new TurmZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			turmZuege.fuegeZugkandidatenHinzu(H8, weisserTurmH8, zuege);
			Assert.assertEquals(14, zuege.size());

			for (Zug zug : zuege) {
				Assert.assertEquals(H8, zug.getVon());
			}
			Feld[] ziele = { H1, H2, H3, H4, H5, H6, H6, A8, B8, C8, D8, E8,
					F8, G8 };
			for (Feld ziel : ziele) {
				Zug zuTesten = new Zug(TURM_WEISS, H8, ziel);
				Assert.assertTrue(zuege.contains(zuTesten));
			}
		}
	}

	@Test
	public void einzelnerTurmInDerMitte() {

		{
			Stellung weisserTurmAufD5 = new Stellung(
					"8/8/8/3R4/8/8/8/8 w - - 0 1");

			TurmZuege turmZuege = new TurmZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			turmZuege.fuegeZugkandidatenHinzu(D5, weisserTurmAufD5, zuege);

			Assert.assertEquals(14, zuege.size());

			for (Zug zug : zuege) {
				Assert.assertEquals(D5, zug.getVon());
			}
			Feld[] ziele = { A5, B5, C5, E5, F5, G5, H5, D1, D2, D3, D4, D6,
					D7, D8 };
			for (Feld ziel : ziele) {
				Zug toTest = new Zug(TURM_WEISS, D5, ziel);
				Assert.assertTrue(toTest.toString(), zuege.contains(toTest));
			}
		}
	}

	@Test
	public void einzelnerTurmSchlaegt() {

		Stellung weisserTurmSchlaegtZweiFiguren = new Stellung(
				"8/3q4/8/8/8/Q2R2bb/8/3N4 w - - 0 1");

		TurmZuege turmZuege = new TurmZuege();
		List<Zug> zuege = new ArrayList<Zug>();

		turmZuege.fuegeZugkandidatenHinzu(D3, weisserTurmSchlaegtZweiFiguren,
				zuege);

		Assert.assertEquals(10, zuege.size());
		for (Zug zug : zuege) {
			Assert.assertEquals(D3, zug.getVon());
		}

		Zug schwarzeDameSchlagen = new Zug(TURM_WEISS, D3, D7, true);
		Zug schwarzenLaeuferSchlagen = new Zug(TURM_WEISS, D3, G3, true);

		Assert.assertTrue(schwarzeDameSchlagen.toString(),
				zuege.contains(schwarzeDameSchlagen));
		Assert.assertTrue(schwarzenLaeuferSchlagen.toString(),
				zuege.contains(schwarzenLaeuferSchlagen));

	}

}
