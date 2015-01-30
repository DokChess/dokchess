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
package de.dokchess.xboard;

import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import de.dokchess.engine.Engine;
import de.dokchess.regeln.Spielregeln;
import rx.Observable;
import rx.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;

/**
 * Implementierung des XBoard-Protokolls. Enthaelt nur die wichtigsten Befehle.
 *
 * @author StefanZ
 */
public class XBoard implements Observer<Zug> {

    private Reader eingabe;

    private BufferedReader bufEingabe;

    private Writer ausgabe;

    private Spielregeln spielregeln;

    private Engine engine;

    private Zug besterZug;

    private Stellung stellung = new Stellung();

    private ZugParser parser = new ZugParser();

    /**
     * Setzt die Protokoll-Eingabe per Dependency Injection. Typischerweise ist
     * das die Standardeingabe (stdin); z.B. f&uuml;r automatische Tests kann
     * eine andere Quelle verwendet werden.
     *
     * @param reader
     */
    public void setEingabe(Reader reader) {
        this.eingabe = reader;
        this.bufEingabe = new BufferedReader(eingabe);
    }

    /**
     * Setzt die Protokoll-Ausgabe. Typischerweise ist das die Standardausgabe
     * (stdout), f&uuml;r automatische Tests kann eine andere Senke verwendet
     * werden.
     *
     * @param writer die Ausgabe
     */
    public void setAusgabe(Writer writer) {
        this.ausgabe = writer;
    }

    /**
     * Setzt eine Implementierung der Spielregeln. Optional, ohne Spielregeln
     * werden die eingehenden Zuege nicht geprueft.
     *
     * @param spielregeln die Spielregeln
     */
    public void setSpielregeln(Spielregeln spielregeln) {
        this.spielregeln = spielregeln;
    }

    /**
     * Setzt eine Implementierung der Engine. Erforderlich zum Spielen.
     *
     * @param engine die Engine
     */
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    /**
     * Startet die eigentliche Kommunikation (Eingabe/Verarbeitung/Ausgabe) in
     * einer Endlosschleife, bis zum Beenden-Kommando.
     */
    public void spielen() {




        boolean running = true;
        while (running) {

            String eingelesen = lesen();

            if (eingelesen == null || eingelesen.equals("quit")) {
                running = false;
                continue;
            }

            if (eingelesen.equals("xboard")) {
                schreiben("");
                continue;
            }

            if (eingelesen.equals("protover 2")) {
                schreiben("feature done=1");
                continue;
            }

            if (eingelesen.equals("new")) {
                stellung = new Stellung();
                engine.figurenAufbauen(stellung);
                continue;
            }

            if (eingelesen.equals("go")) {
                Observable<Zug> s = engine.ermittleDeinenZug();
                s.subscribe(this);
                continue;
            }

            Zug zug = parser.vonXboard(eingelesen, stellung);
            if (zug != null) {

                if (spielregeln != null) {
                    Collection<Zug> gueltigeZuege = spielregeln
                            .liefereGueltigeZuege(stellung);
                    if (!gueltigeZuege.contains(zug)) {
                        schreiben("Illegal move: " + eingelesen);
                        continue;
                    }
                }

                engine.ziehen(zug);
                stellung = stellung.fuehreZugAus(zug);

                Observable<Zug> s = engine.ermittleDeinenZug();
                s.subscribe(this);
                continue;
            }

            schreiben("telluser xboard-Befehl unbekannt oder nicht implementiert: " + eingelesen);
        }

        engine.schliessen();
    }

    String lesen() {
        String zeile = null;
        try {
            zeile = bufEingabe.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return zeile;
    }

    void schreiben(String zeile) {
        try {
            synchronized (ausgabe) {
                ausgabe.write(zeile + "\n");
                ausgabe.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCompleted() {
        schreiben(parser.nachXboard(this.besterZug));
        this.engine.ziehen(this.besterZug);
        this.stellung = this.stellung.fuehreZugAus(this.besterZug);
    }

    @Override
    public void onError(Throwable e) {
        schreiben("tellusererror " + e.getMessage());
    }

    @Override
    public void onNext(Zug zug) {
        schreiben("# neuen besten Zug gefunden: "+ zug);
        this.besterZug = zug;
    }
}
