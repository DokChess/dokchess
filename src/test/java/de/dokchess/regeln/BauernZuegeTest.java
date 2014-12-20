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
import de.dokchess.allgemein.Figur;
import de.dokchess.allgemein.FigurenArt;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;

public class BauernZuegeTest {

	private static final Figur BAUER_WEISS = new Figur(FigurenArt.BAUER,
			Farbe.WEISS);

	private static final Figur BAUER_SCHWARZ = new Figur(FigurenArt.BAUER,
			Farbe.SCHWARZ);

	@Test
	public void einzelnerWeisserBauerAmStart() {

		Stellung einzelnerWeisserBauerAmStart = new Stellung(
				"8/8/8/8/8/8/1P6/8 w - - 0 1");

		BauernZuege bauernzuege = new BauernZuege();
		List<Zug> zuege = new ArrayList<Zug>();

		bauernzuege.fuegeZugkandidatenHinzu(B2, einzelnerWeisserBauerAmStart,
				zuege);

		Assert.assertEquals(2, zuege.size());
		for (Zug zug : zuege) {
			Assert.assertEquals(B2, zug.getVon());
		}

		Assert.assertEquals(2, zuege.size());
		Zug einFeld = new Zug(BAUER_WEISS, B2, B3);
		Zug zweiFelder = new Zug(BAUER_WEISS, B2, B4);

		Assert.assertTrue(einFeld.toString(), zuege.contains(einFeld));
		Assert.assertTrue(zweiFelder.toString(), zuege.contains(zweiFelder));
	}

	@Test
	public void einzelnerWeisserBauerInDerMitte() {

		Stellung einzelnerWeisserBauerInDerMitte = new Stellung(
				"8/8/8/4P3/8/8/8/8 w - - 0 1");

		BauernZuege bauernzuege = new BauernZuege();
		List<Zug> zuege = new ArrayList<Zug>();

		bauernzuege.fuegeZugkandidatenHinzu(E5,
				einzelnerWeisserBauerInDerMitte, zuege);

		Assert.assertEquals(1, zuege.size());
		Zug einFeld = new Zug(BAUER_WEISS, E5, E6);
		Assert.assertTrue(einFeld.toString(), zuege.contains(einFeld));
	}

	@Test
	public void einzelnerWeisserBauerKannSchlagen() {

		Stellung einzelnerWeisserBauerKannSchlagen = new Stellung(
				"8/8/8/2N1n3/3P4/8/8/8 w - - 0 1");

		BauernZuege bauernzuege = new BauernZuege();
		List<Zug> zuege = new ArrayList<Zug>();

		bauernzuege.fuegeZugkandidatenHinzu(D4,
				einzelnerWeisserBauerKannSchlagen, zuege);

		Assert.assertEquals(2, zuege.size());

		Zug einFeld = new Zug(BAUER_WEISS, D4, D5);
		Assert.assertTrue(einFeld.toString(), zuege.contains(einFeld));

		Zug schlagen = new Zug(BAUER_WEISS, D4, E5, true);
		Assert.assertTrue(einFeld.toString(), zuege.contains(schlagen));
	}

	@Test
	public void einzelnerWeisserBauerUmwandlung() {

		Stellung einzelnerWeisserBauerUmwandlung = new Stellung(
				"5n2/4P3/8/8/8/8/8/8 w - - 0 1");

		BauernZuege bauernzuege = new BauernZuege();
		List<Zug> zuege = new ArrayList<Zug>();

		bauernzuege.fuegeZugkandidatenHinzu(E7,
				einzelnerWeisserBauerUmwandlung, zuege);

		Assert.assertEquals(8, zuege.size());
		for (Zug zug : zuege) {
			Assert.assertEquals(E7, zug.getVon());
			Assert.assertTrue(zug.istUmwandlung());
		}
	}

	@Test
	public void keineUmwandlungWennGrundlinieBelegtWeiss() {

		Stellung einzelnerWeisserBauerUmwandlung = new Stellung(
				"4n3/4P3/8/8/8/8/8/8 w - - 0 1");

		BauernZuege bauernzuege = new BauernZuege();
		List<Zug> zuege = new ArrayList<Zug>();

		bauernzuege.fuegeZugkandidatenHinzu(E7,
				einzelnerWeisserBauerUmwandlung, zuege);
		Assert.assertEquals(0, zuege.size());
	}

	@Test
	public void keineUmwandlungWennGrundlinieBelegtSchwarz() {

		Stellung einzelnerWeisserBauerUmwandlung = new Stellung(
				"8/8/8/8/8/8/4p3/4N3 b - - 0 1");

		BauernZuege bauernzuege = new BauernZuege();
		List<Zug> zuege = new ArrayList<Zug>();

		bauernzuege.fuegeZugkandidatenHinzu(E2,
				einzelnerWeisserBauerUmwandlung, zuege);
		Assert.assertEquals(0, zuege.size());
	}

	@Test
	public void einzelnerSchwarzerBauerAmStart() {

		Stellung einzelnerSchwarzerBauerAmStart = new Stellung(
				"8/4p3/8/8/8/8/8/8 b - - 0 1");

		BauernZuege bauernzuege = new BauernZuege();
		List<Zug> zuege = new ArrayList<Zug>();

		bauernzuege.fuegeZugkandidatenHinzu(E7, einzelnerSchwarzerBauerAmStart,
				zuege);

		Assert.assertEquals(2, zuege.size());
		for (Zug zug : zuege) {
			Assert.assertEquals(E7, zug.getVon());
		}

		Zug einFeld = new Zug(BAUER_SCHWARZ, E7, E6);
		Zug zweiFelder = new Zug(BAUER_SCHWARZ, E7, E5);

		Assert.assertTrue(einFeld.toString(), zuege.contains(einFeld));
		Assert.assertTrue(zweiFelder.toString(), zuege.contains(zweiFelder));
	}

	@Test
	public void weisserBauerBlockiert() {

		Stellung weisserBauerBlockiert = new Stellung(
				"8/8/8/8/4b3/1b6/1P2P3/8 w - - 0 1");

		BauernZuege bauernzuege = new BauernZuege();
		List<Zug> zuege = new ArrayList<Zug>();

		bauernzuege.fuegeZugkandidatenHinzu(E2, weisserBauerBlockiert, zuege);

		Assert.assertEquals(1, zuege.size());
		for (Zug zug : zuege) {
			Assert.assertEquals(E2, zug.getVon());
		}

		Zug einFeld = new Zug(BAUER_WEISS, E2, E3);

		Assert.assertTrue(einFeld.toString(), zuege.contains(einFeld));
	}

	@Test
	public void schwarzerBauerEnPassant() {

		Stellung schwarzerBauerEnPassant = new Stellung(
				"4k3/8/8/8/4pPp1/8/8/4K3 b - f3 1 1");

		BauernZuege bauernzuege = new BauernZuege();
		List<Zug> zuege = new ArrayList<Zug>();

		bauernzuege.fuegeZugkandidatenHinzu(E4, schwarzerBauerEnPassant, zuege);
		bauernzuege.fuegeZugkandidatenHinzu(G4, schwarzerBauerEnPassant, zuege);

		Assert.assertEquals(4, zuege.size());

		Zug schlag1 = new Zug(BAUER_SCHWARZ, E4, F3, true);
		Zug schlag2 = new Zug(BAUER_SCHWARZ, G4, F3, true);

		Assert.assertTrue(zuege.contains(schlag1));
		Assert.assertTrue(zuege.contains(schlag2));
	}

	@Test
	public void weisserBauerEnPassant() {

		Stellung weisserBauerEnPassant = new Stellung(
				"4k3/8/8/3PpP2/8/8/8/4K3 w - e6 0 1");

		BauernZuege bauernzuege = new BauernZuege();
		List<Zug> zuege = new ArrayList<Zug>();

		bauernzuege.fuegeZugkandidatenHinzu(D5, weisserBauerEnPassant, zuege);
		bauernzuege.fuegeZugkandidatenHinzu(F5, weisserBauerEnPassant, zuege);

		Assert.assertEquals(4, zuege.size());

		Zug schlag1 = new Zug(BAUER_WEISS, D5, E6, true);
		Zug schlag2 = new Zug(BAUER_WEISS, F5, E6, true);

		Assert.assertTrue(zuege.contains(schlag1));
		Assert.assertTrue(zuege.contains(schlag2));
	}

}
