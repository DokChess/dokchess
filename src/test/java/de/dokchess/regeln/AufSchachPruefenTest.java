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

import de.dokchess.allgemein.Farbe;
import de.dokchess.allgemein.Stellung;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AufSchachPruefenTest {

	private Spielregeln spielregeln;

	@Before
	public void setup() {
		spielregeln = new DefaultSpielregeln();
	}

	@Test
	public void weisserBauerGibtSchach() {

		Stellung stellung1 = new Stellung("8/5k2/6P1/8/8/8/8/2K5 w - - 0 1");
		Assert.assertTrue("im Schach",
				spielregeln.aufSchachPruefen(stellung1, Farbe.SCHWARZ));

		Stellung stellung2 = new Stellung("8/5k2/4P3/8/8/8/8/2K5 w - - 0 1");
		Assert.assertTrue("im Schach",
				spielregeln.aufSchachPruefen(stellung2, Farbe.SCHWARZ));
	}

	@Test
	public void schwarzerBauerGibtSchach() {

		Stellung stellung1 = new Stellung("4k3/8/8/8/8/2p5/3K4/8 b - - 0 1");
		Assert.assertTrue("im Schach",
				spielregeln.aufSchachPruefen(stellung1, Farbe.WEISS));

		Stellung stellung2 = new Stellung("4k3/8/8/8/8/4p3/3K4/8 b - - 0 1");
		Assert.assertTrue("im Schach",
				spielregeln.aufSchachPruefen(stellung2, Farbe.WEISS));
	}

	@Test
	public void weisseDameGibtSchach() {

		// Gerade
		Stellung stellung1 = new Stellung("8/5k2/8/8/8/8/8/2K2Q2 w - - 0 1");
		Assert.assertTrue("im Schach",
				spielregeln.aufSchachPruefen(stellung1, Farbe.SCHWARZ));

		// Schraeg
		Stellung stellung2 = new Stellung("8/5k2/8/8/8/8/Q7/2K5 w - - 0 1");
		Assert.assertTrue("im Schach",
				spielregeln.aufSchachPruefen(stellung2, Farbe.SCHWARZ));

		// Schraeg, aber Figur im Weg
		Stellung stellung3 = new Stellung("8/5k2/8/8/2R5/8/Q7/2K5 w - - 0 1");
		Assert.assertFalse("nicht im Schach",
				spielregeln.aufSchachPruefen(stellung3, Farbe.SCHWARZ));
	}

	@Test
	public void weisserLaeuferGibtSchach() {

		// Schraeg 1
		Stellung stellung1 = new Stellung("8/5k2/6B1/8/8/8/8/2K5 w - - 0 1");
		Assert.assertTrue("im Schach",
				spielregeln.aufSchachPruefen(stellung1, Farbe.SCHWARZ));

		// Schraeg 2
		Stellung stellung2 = new Stellung("8/5k2/8/8/8/8/B7/2K5 w - - 0 1");
		Assert.assertTrue("im Schach",
				spielregeln.aufSchachPruefen(stellung2, Farbe.SCHWARZ));

		// Schraeg, aber Figur im Weg
		Stellung stellung3 = new Stellung("8/5k2/8/8/2R5/8/B7/2K5 w - - 0 1");
		Assert.assertFalse("nicht im Schach",
				spielregeln.aufSchachPruefen(stellung3, Farbe.SCHWARZ));
	}

}
