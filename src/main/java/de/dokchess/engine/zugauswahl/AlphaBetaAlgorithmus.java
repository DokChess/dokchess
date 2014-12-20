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
package de.dokchess.engine.zugauswahl;

import java.util.Collection;

import de.dokchess.allgemein.Farbe;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.engine.bewertung.Bewertung;
import de.dokchess.regeln.Spielregeln;

public class AlphaBetaAlgorithmus implements ZugAuswahl {

	private Spielregeln spielregeln;

	private Bewertung bewertung;

	private int tiefe;

	@Override
	public Zug ermittleZug(Stellung stellung) {

		Farbe spielerFarbe = stellung.getAmZug();
		Collection<Zug> zuege = spielregeln.ermittleGueltigeZuege(stellung);
//		Collections.sort(zuege, new ZugComparator());

		int besterWert = -Integer.MAX_VALUE;
		int alpha = besterWert;
		int beta = -alpha;
		Zug besterZug = null;

		for (Zug zug : zuege) {
			Stellung neueStellung = stellung.fuehreZugAus(zug);

			int wert = ermittleZugRekursiv(neueStellung, 1, alpha, beta, spielerFarbe);
			
			if (wert > besterWert) {
				besterWert = wert;
				alpha = wert;
				besterZug = zug;
			}
		}

		return besterZug;
	}

	protected int ermittleZugRekursiv(Stellung stellung, int aktuelleTiefe, int alpha, int beta,
			Farbe spielerFarbe) {
		
		if (aktuelleTiefe == tiefe) {
			return bewertung.bewerteStellung(stellung, spielerFarbe);
		} else {
			Collection<Zug> zuege = spielregeln.ermittleGueltigeZuege(stellung);
//			Collections.sort(zuege, new ZugComparator());
			
			if (zuege.isEmpty()) {
				
				    // PATT
				    if (!spielregeln.aufSchachPruefen(stellung, stellung.getAmZug())) {
				    	return 0;
				    }
				
					// MATT
					if (stellung.getAmZug() == spielerFarbe) {
						return -(100000 - aktuelleTiefe);
					} else {
						return 100000 - aktuelleTiefe;
					}

				
			} else {
				if (aktuelleTiefe % 2 == 0) {
					
					int ergebnis = alpha;
					boolean abbrechen = false; 
					
					// Max
					for (Zug zug : zuege) {
						Stellung neueStellung = stellung.fuehreZugAus(zug);
						
						int wert = ermittleZugRekursiv(neueStellung,
								aktuelleTiefe + 1, alpha, beta, spielerFarbe);
						
						if (wert >= beta) {
							ergebnis = beta;
							abbrechen = true;
						}
						else if (wert > alpha) {
							alpha = wert;
							ergebnis = alpha;
						}
						if (abbrechen) {
							break;
						}
					}
					return ergebnis;
				} else {
					// Min
					int ergebnis = beta;
					boolean abbrechen = false;
					for (Zug zug : zuege) {
						Stellung neueStellung = stellung.fuehreZugAus(zug);
						int wert = ermittleZugRekursiv(neueStellung,
								aktuelleTiefe + 1, alpha, beta, spielerFarbe);
						
						if (wert <= alpha) {
							ergebnis = alpha;
							abbrechen = true;
						}
						else if (wert < beta) {
							beta = wert;
							ergebnis = beta;
						}
						if (abbrechen) {
							break;
						}
					}
					return ergebnis;
				}
			}
		}
	}

	public void setBewertung(Bewertung bewertung) {
		this.bewertung = bewertung;
	}

	public void setSpielregeln(Spielregeln spielregeln) {
		this.spielregeln = spielregeln;
	}
	
	public void setTiefe(int tiefe) {
		this.tiefe = tiefe;
	}
}
