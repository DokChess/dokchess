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
 * Das Modul ermittelt zu einer Stellung den unter bestimmten Bedingungen
 * optimalen Zug.
 * Theoretisch gibt es den generell optimalen Zug im Schach.
 * Die hohe Anzahl der m&ouml;glichen Z&uuml;ge und die damit verbundene
 * schier unglaubliche Anzahl zu bewertender Spielsituationen macht es in der
 * Praxis aber unm&ouml;glich, ihn zu bestimmen.
 * G&auml;ngige Algorithmen begn&uuml;gen sich daher damit, den "Spielbaum"
 * nur bis zu einer bestimmten Tiefe zu explorieren.
 */
package de.dokchess.engine.suche;
