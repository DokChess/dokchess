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

			turmZuege.fuegeZugkandidatenHinzu(a1, weisserTurmA1, zuege);

			Assert.assertEquals(14, zuege.size());
			for (Zug zug : zuege) {
				Assert.assertEquals(a1, zug.getVon());
			}
			Feld[] ziele = {b1, c1, d1, e1, f1, g1, h1, a2, a3, a4, a5, a6,
					a7, a8};
			for (Feld ziel : ziele) {
				Zug zuTesten = new Zug(TURM_WEISS, a1, ziel);
				Assert.assertTrue(zuege.contains(zuTesten));
			}
		}

		{
			Stellung weisserTurmH8 = new Stellung("7R/8/8/8/8/8/8/8 w - - 0 1");

			TurmZuege turmZuege = new TurmZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			turmZuege.fuegeZugkandidatenHinzu(h8, weisserTurmH8, zuege);
			Assert.assertEquals(14, zuege.size());

			for (Zug zug : zuege) {
				Assert.assertEquals(h8, zug.getVon());
			}
			Feld[] ziele = {h1, h2, h3, h4, h5, h6, h6, a8, b8, c8, d8, e8,
					f8, g8};
			for (Feld ziel : ziele) {
				Zug zuTesten = new Zug(TURM_WEISS, h8, ziel);
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

			turmZuege.fuegeZugkandidatenHinzu(d5, weisserTurmAufD5, zuege);

			Assert.assertEquals(14, zuege.size());

			for (Zug zug : zuege) {
				Assert.assertEquals(d5, zug.getVon());
			}
			Feld[] ziele = {a5, b5, c5, e5, f5, g5, h5, d1, d2, d3, d4, d6,
					d7, d8};
			for (Feld ziel : ziele) {
				Zug toTest = new Zug(TURM_WEISS, d5, ziel);
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

		turmZuege.fuegeZugkandidatenHinzu(d3, weisserTurmSchlaegtZweiFiguren,
				zuege);

		Assert.assertEquals(10, zuege.size());
		for (Zug zug : zuege) {
			Assert.assertEquals(d3, zug.getVon());
		}

		Zug schwarzeDameSchlagen = new Zug(TURM_WEISS, d3, d7, true);
		Zug schwarzenLaeuferSchlagen = new Zug(TURM_WEISS, d3, g3, true);

		Assert.assertTrue(schwarzeDameSchlagen.toString(),
				zuege.contains(schwarzeDameSchlagen));
		Assert.assertTrue(schwarzenLaeuferSchlagen.toString(),
				zuege.contains(schwarzenLaeuferSchlagen));

	}

}
