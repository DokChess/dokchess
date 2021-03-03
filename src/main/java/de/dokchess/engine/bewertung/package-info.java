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

/**
 * Bewertet eine Stellung aus Sicht eines Spielers.
 *
 * Das Modul bewertet eine Stellung aus Sicht eines Spielers. Ergebnis ist eine Zahl,
 * wobei 0 eine ausgeglichene Situation beschreibt, eine positive Zahl einen Vorteil für den
 * Spieler, eine negative einen Nachteil. Je h&ouml;her der Betrag, desto gr&uml;sser der Vor- bzw. Nachteil.
 * Das Modul erm&ouml;glicht es so, Stellungen miteinander zu vergleichen.
 */
package de.dokchess.engine.bewertung;
