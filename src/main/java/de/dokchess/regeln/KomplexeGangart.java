package de.dokchess.regeln;

import java.util.List;

import de.dokchess.allgemein.Feld;
import de.dokchess.allgemein.Figur;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;

/**
 * Abstrakte Oberklasse fuer alle komplexen Gangarten im Schach, gibt eine Implementierung fuer {@link #fuegeZugkandidatenHinzu(Feld, Stellung, List)} vor.
 */
public abstract class KomplexeGangart extends Gangart {

	@Override
	void fuegeZugkandidatenHinzu(Feld von, Stellung stellung, List<Zug> liste) {
		Figur eigeneFigur = stellung.getFigur(von);

        List<Feld> erreichbareFelder = ermittleErreichbareFelder(stellung, von);
        for (Feld nach : erreichbareFelder) {
            if (stellung.getFigur(nach) == null) {
                Zug m = new Zug(eigeneFigur, von, nach);
                liste.add(m);
            } else {
                Zug m = new Zug(eigeneFigur, von, nach,
                        true);
                liste.add(m);
            }
        }
	}
	
	protected abstract List<Feld> ermittleErreichbareFelder(Stellung stellung,
            Feld startfeld);

}
