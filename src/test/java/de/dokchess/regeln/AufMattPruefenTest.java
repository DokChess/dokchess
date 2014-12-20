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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.dokchess.allgemein.Stellung;

public class AufMattPruefenTest {

	private Spielregeln spielregeln;

	@Before
	public void setup() {
		spielregeln = new SpielregelnImpl();
	}
	
	/**
	 * Die Anfangsstellung ist kein Matt.
	 */
	@Test
	public void anfangsstellung() {
		Stellung anfangsstellung = new Stellung();
		Assert.assertFalse("Anfangsstellung",
				spielregeln.aufMattPruefen(anfangsstellung));
	}


	/**
	 * Schaefermatt ist matt.
	 */
	@Test
	public void schaeferMatt() {
		Stellung stellung = new Stellung("r1bqkb1r/pppp1Qpp/2n2n2/4p3/2B1P3/8/PPPP1PPP/RNB1K1NR b KQkq - 0 1");
		Assert.assertTrue("Schaefermatt",
				spielregeln.aufMattPruefen(stellung));
	}

}
