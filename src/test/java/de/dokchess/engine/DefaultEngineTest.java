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

package de.dokchess.engine;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.eroeffnung.polyglot.PolyglotOpeningBook;
import de.dokchess.regeln.DefaultSpielregeln;
import de.dokchess.regeln.Spielregeln;
import org.junit.Assert;
import org.junit.Test;
import io.reactivex.rxjava3.core.Observable;

import java.io.IOException;
import java.io.InputStream;

/**
 * Einfache Tests, eher Richtung Smoke-Test ...
 */
public class DefaultEngineTest {

    @Test
    public void ohneEroeffnungsbiblithek() {

        Spielregeln regeln = new DefaultSpielregeln();
        DefaultEngine engine = new DefaultEngine(regeln);

        Stellung anfang = new Stellung();
        engine.figurenAufbauen(anfang);

        Observable<Zug> observable = engine.ermittleDeinenZug();
        Assert.assertNotNull(observable);
    }

    @Test
    public void mitEroeffnungsbiblithek() throws IOException {

        InputStream is = getClass().getClassLoader().getResourceAsStream(
                "de/dokchess/eroeffnung/polyglot/italienischePartie.bin");
        PolyglotOpeningBook buch = new PolyglotOpeningBook(is);
        is.close();

        Spielregeln regeln = new DefaultSpielregeln();
        DefaultEngine engine = new DefaultEngine(regeln, buch);

        Stellung anfang = new Stellung();
        engine.figurenAufbauen(anfang);

        Observable<Zug> observable = engine.ermittleDeinenZug();
        Assert.assertNotNull(observable);
    }


}
