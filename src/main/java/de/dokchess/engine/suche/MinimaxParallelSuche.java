/*
 * Copyright (c) 2010-2015 Stefan Zoerner
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

package de.dokchess.engine.suche;

import de.dokchess.allgemein.Farbe;
import de.dokchess.allgemein.Stellung;
import de.dokchess.allgemein.Zug;
import rx.Observer;
import rx.subjects.ReplaySubject;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by stefanz on 02.01.15.
 */
public class MinimaxParallelSuche extends MinimaxAlgorithmus implements Suche {

    private ExecutorService executorService;

    private ReplaySubject<BewerteterZug> aktuelleSuchErgebnisse;

    public MinimaxParallelSuche() {
        int cores = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(cores);
    }

    @Override
    public void suchen(Stellung stellung, Observer<Zug> subject) {
        Collection<Zug> zuege = spielregeln.liefereGueltigeZuege(stellung);
        if (zuege.size() > 0) {
            ReplaySubject<BewerteterZug> suchErgebnisse = ReplaySubject.create();
            aktuelleSuchErgebnisse = suchErgebnisse;

            ErgebnisMelden melder = new ErgebnisMelden(subject, zuege.size());
            suchErgebnisse.subscribe(melder);

            for (Zug zug : zuege) {
                EinzelnenZugUntersuchen zugUntersuchen = new EinzelnenZugUntersuchen(stellung, zug, suchErgebnisse);
                suchErgebnisse.subscribe(zugUntersuchen);
                executorService.execute(zugUntersuchen);
            }
        } else {
            subject.onCompleted();
        }
    }

    public void abbrechen() {
        if (aktuelleSuchErgebnisse != null) {
            aktuelleSuchErgebnisse.onCompleted();
            aktuelleSuchErgebnisse = null;
        }
    }

    class EinzelnenZugUntersuchen implements Runnable, Observer<BewerteterZug> {

        private Stellung stellung;

        private Zug zug;

        private ReplaySubject<BewerteterZug> suchErgebnisse;

        private boolean berechnungBeendet = false;

        EinzelnenZugUntersuchen(Stellung stellung, Zug zug, ReplaySubject<BewerteterZug> suchErgebnisse) {
            this.stellung = stellung;
            this.zug = zug;
            this.suchErgebnisse = suchErgebnisse;
        }

        @Override
        public void run() {
            if (!berechnungBeendet) {
                Farbe spielerFarbe = stellung.getAmZug();
                Stellung nachZug = stellung.fuehreZugAus(zug);
                int wert = bewerteStellungRekursiv(nachZug, spielerFarbe);
                suchErgebnisse.onNext(new BewerteterZug(zug, wert));
            }
        }

        @Override
        public void onCompleted() {
            this.berechnungBeendet = true;
        }

        @Override
        public void onError(Throwable e) {
            this.berechnungBeendet = true;
        }

        @Override
        public void onNext(BewerteterZug bewerteterZug) {
        }
    }

    class ErgebnisMelden implements Observer<BewerteterZug> {

        Observer<Zug> subject;

        int anzahlKandidaten;

        int anzahlBisher;

        BewerteterZug bester = null;

        public ErgebnisMelden(Observer<Zug> subject, int anzahlKandidaten) {
            this.subject = subject;
            this.anzahlKandidaten = anzahlKandidaten;
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public synchronized void onNext(BewerteterZug bewerteterZug) {

            if (bester == null || bester.getWert() < bewerteterZug.getWert()) {
                bester = bewerteterZug;
                subject.onNext(bewerteterZug.getZug());
            }

            anzahlBisher += 1;
            if (anzahlBisher == anzahlKandidaten) {
                subject.onCompleted();
            }
        }
    }
}
