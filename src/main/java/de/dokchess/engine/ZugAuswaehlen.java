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
package de.dokchess.engine;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;

/**
 * Uebernimmt die Rolle Handler im Chain of Responsibility Muster.
 *  
 * @author StefanZ
 */
abstract class ZugAuswaehlen {
	
	private ZugAuswaehlen nachfolger;
	
	public ZugAuswaehlen(ZugAuswaehlen nachfolger) {
		this.nachfolger = nachfolger;
	}

	public Zug waehleZug(Stellung stellung) {
		if (nachfolger == null) {
			return null;
		} else {
			return nachfolger.waehleZug(stellung);
		}
	}
}
