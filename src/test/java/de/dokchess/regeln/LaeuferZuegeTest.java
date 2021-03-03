/*
 * Copyright (c) 2010-2021 Stefan Zoerner
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

public class LaeuferZuegeTest {

	private static final Figur LAEUFER_WEISS = new Figur(FigurenArt.LAEUFER,
			Farbe.WEISS);

	@Test
	public void einzelnerLaeuferInDerEcke() {

		{
			Stellung weisserLaeuferA1 = new Stellung("8/8/8/8/8/8/8/B7 w - - 0 1");

			LaeuferZuege laeuferZuege = new LaeuferZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			laeuferZuege.fuegeZugkandidatenHinzu(a1, weisserLaeuferA1, zuege);

			Assert.assertEquals(7, zuege.size());
			for (Zug zug : zuege) {
				Assert.assertEquals(a1, zug.getVon());
			}
			Feld[] ziele = {b2, c3, d4, e5, f6, g7, h8};
			for (Feld ziel : ziele) {
				Zug zuTesten = new Zug(LAEUFER_WEISS, a1, ziel);
				Assert.assertTrue(zuTesten.toString(), zuege.contains(zuTesten));
			}
		}

		{
			Stellung weisserLaeuferH1 = new Stellung("8/8/8/8/8/8/8/7B w - - 0 1");

			LaeuferZuege laeuferZuege = new LaeuferZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			laeuferZuege.fuegeZugkandidatenHinzu(h1, weisserLaeuferH1, zuege);
			Assert.assertEquals(7, zuege.size());
			Feld h1 = new Feld("h1");
			for (Zug zug : zuege) {
				Assert.assertEquals(h1, zug.getVon());
			}
			Feld[] ziele = {a8, b7, c6, d5, e4, f3, g2};
			for (Feld ziel : ziele) {
				Zug zuTesten = new Zug(LAEUFER_WEISS, h1, ziel);
				Assert.assertTrue(zuTesten.toString(), zuege.contains(zuTesten));
			}
		}
	}

	@Test
	public void einzelnerLaeuferInDerMitte() {

		{
			Stellung weisserLaeuferD4 = new Stellung("8/8/8/8/3B4/8/8/8 w - - 0 1");

			LaeuferZuege laeuferZuege = new LaeuferZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			laeuferZuege.fuegeZugkandidatenHinzu(d4, weisserLaeuferD4, zuege);

			Assert.assertEquals(13, zuege.size());
			for (Zug zug : zuege) {
				Assert.assertEquals(d4, zug.getVon());
			}
			Feld[] ziele = {a1, b2, c3, e5, f6, g7, h8, a7, b6, c5, e3, f2, g1};
			for (Feld ziel : ziele) {
				Zug zuTesten = new Zug(LAEUFER_WEISS, d4, ziel);
				Assert.assertTrue(zuTesten.toString(), zuege.contains(zuTesten));
			}
		}
	}

	@Test
	public void einzelnerLaeuferSchlaegt() {

		{
			Stellung weisserLaeuferSchlaegtZweiFiguren = new Stellung("8/6n1/1N6/8/3B1r2/8/5r2/6p1 w - - 0 1");

			LaeuferZuege laeuferZuege = new LaeuferZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			laeuferZuege.fuegeZugkandidatenHinzu(d4,
					weisserLaeuferSchlaegtZweiFiguren, zuege);

			Assert.assertEquals(9, zuege.size());
			for (Zug zug : zuege) {
				Assert.assertEquals(d4, zug.getVon());
			}

			Zug schwarzenTurmSchlagen = new Zug(LAEUFER_WEISS, d4, f2, true);
			Zug schwarzenSpringerSchlagen = new Zug(LAEUFER_WEISS, d4, g7, true);

			Assert.assertTrue(schwarzenTurmSchlagen.toString(),
					zuege.contains(schwarzenTurmSchlagen));
			Assert.assertTrue(schwarzenSpringerSchlagen.toString(),
					zuege.contains(schwarzenSpringerSchlagen));
		}
	}
}