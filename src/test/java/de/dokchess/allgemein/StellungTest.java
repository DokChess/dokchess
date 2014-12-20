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
package de.dokchess.allgemein;

import static de.dokchess.allgemein.Felder.e2;
import static de.dokchess.allgemein.Felder.e3;
import static de.dokchess.allgemein.Felder.e4;
import junit.framework.Assert;

import org.junit.Test;

public class StellungTest {

	private static final Figur WEISSER_BAUER = new Figur(FigurenArt.BAUER,
			Farbe.WEISS);


	@Test
	public void bauerEinsVor() {
		Stellung stellung = new Stellung();
		
		Zug zug = new Zug(WEISSER_BAUER, e2, e3);
		
		Stellung neueStellung = stellung.fuehreZugAus(zug);
		
		Assert.assertNull(neueStellung.getFigur(e2));
		Assert.assertNull(neueStellung.getEnPassantFeld());
		Assert.assertEquals(WEISSER_BAUER, neueStellung.getFigur(e3));
		
		Assert.assertEquals(Farbe.SCHWARZ, neueStellung.getAmZug());	
	}
	
	@Test
	public void bauerZweiVor() {
		Stellung stellung = new Stellung();
		
		Zug zug = new Zug(WEISSER_BAUER, e2, e4);
		
		Stellung neueStellung = stellung.fuehreZugAus(zug);
		
		Assert.assertNull(neueStellung.getFigur(e2));
		Assert.assertEquals(e3, neueStellung.getEnPassantFeld());
		Assert.assertEquals(WEISSER_BAUER, neueStellung.getFigur(e4));
		
		Assert.assertEquals(Farbe.SCHWARZ, neueStellung.getAmZug());
	}
}
