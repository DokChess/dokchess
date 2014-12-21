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

public class KoenigsZuegeTest {

	private static final Figur KOENIG_WEISS = new Figur(FigurenArt.KOENIG,
			Farbe.WEISS);

	@Test
	public void koenigInDerEcke() {

		{
			Stellung weisserKoenigA1 = new Stellung("8/8/8/8/8/8/8/K7 w - - 0 1");

			KoenigsZuege koenigsZuege = new KoenigsZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			koenigsZuege.fuegeZugkandidatenHinzu(a1, weisserKoenigA1, zuege);

			Assert.assertEquals(3, zuege.size());
			for (Zug zug : zuege) {
				Assert.assertEquals(a1, zug.getVon());
			}
			Feld[] ziele = {a2, b2, b1};
			for (Feld ziel : ziele) {
				Zug zuTesten = new Zug(KOENIG_WEISS, a1, ziel);
				Assert.assertTrue(zuege.contains(zuTesten));
			}
		}

		{
			Stellung weisserKoenigH8 = new Stellung("7K/8/8/8/8/8/8/8 w - - 0 1");

			KoenigsZuege koenigsZuege = new KoenigsZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			koenigsZuege.fuegeZugkandidatenHinzu(h8, weisserKoenigH8, zuege);
			Assert.assertEquals(3, zuege.size());
			for (Zug zug : zuege) {
				Assert.assertEquals(h8, zug.getVon());
			}
			Feld[] ziele = {g8, g7, h7};
			for (Feld ziel : ziele) {
				Zug zuTesten = new Zug(KOENIG_WEISS, h8, ziel);
				Assert.assertTrue(zuege.contains(zuTesten));
			}
		}
	}

	@Test
	public void koenigImZentrum() {

		{
			Stellung weisserKoenigD5 = new Stellung("8/8/8/3K4/8/8/8/8 w - - 0 1");

			KoenigsZuege koenigsZuege = new KoenigsZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			koenigsZuege.fuegeZugkandidatenHinzu(d5, weisserKoenigD5, zuege);

			Assert.assertEquals(8, zuege.size());
			for (Zug zug : zuege) {
				Assert.assertEquals(d5, zug.getVon());
			}
			Feld[] ziele = {c6, c6, e6, c5, e5, c4, d4, e4};
			for (Feld ziel : ziele) {
				Zug zuTesten = new Zug(KOENIG_WEISS, d5, ziel);
				Assert.assertTrue(zuTesten.toString(), zuege.contains(zuTesten));
			}
		}
	}

	@Test
	public void koenigSchlaegt() {

		{
			Stellung weisserKoenigSchlaegtZweiFiguren = new Stellung("8/8/4n3/2NKr3/3R4/8/8/8 w - - 0 1");

			KoenigsZuege koenigsZuege = new KoenigsZuege();
			List<Zug> zuege = new ArrayList<Zug>();

			koenigsZuege.fuegeZugkandidatenHinzu(d5,
					weisserKoenigSchlaegtZweiFiguren, zuege);

			Assert.assertEquals(6, zuege.size());
			for (Zug zug : zuege) {
				Assert.assertEquals(d5, zug.getVon());
			}

			Zug schlaegtSchwarzenSpringer = new Zug(KOENIG_WEISS, d5, e6, true);
			Zug schlaegtScvhwarzenTurm = new Zug(KOENIG_WEISS, d5, e5, true);

			Assert.assertTrue(schlaegtSchwarzenSpringer.toString(),
					zuege.contains(schlaegtSchwarzenSpringer));
			Assert.assertTrue(schlaegtScvhwarzenTurm.toString(),
					zuege.contains(schlaegtScvhwarzenTurm));
		}
	}

}
