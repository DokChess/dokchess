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
package de.dokchess.eroeffnung.polyglot;

class PolyglotTools {

	private static final String FILES = "abcdefgh";
	private static final String ROWS = "12345678";

	/**
	 * Converts a chess board field given by file and row to a String
	 * representation.
	 * 
	 * @param file
	 *            the file number, 0..7, meaning a..h
	 * @param row
	 *            the row number, 0..7
	 * 
	 * @return field, e.g. "e5"
	 */
	static String fileAndRowToString(int file, int row) {
		StringBuilder sb = new StringBuilder(2);
		sb.append(FILES.charAt(file));
		sb.append(ROWS.charAt(row));
		return sb.toString();
	}

	static int twoBytesToInt(byte[] source) {
		int result = 0;

		byte byteA = source[1];
		byte byteB = source[0];

		for (int i = 0; i < 8; ++i) {
			if ((byteA & (1 << i)) > 0) {
				result = result | (1 << i);
			}
		}
		for (int i = 0; i < 8; ++i) {
			if ((byteB & (1 << i)) > 0) {
				result = result | (1 << (8 + i));
			}
		}

		return result;
	}

	static byte[] longToByteArray(long source) {
		byte[] bytesKey = new byte[8];
		String sBytes = Long.toBinaryString(source);
		while (sBytes.length() < 64) {
			sBytes = "0" + sBytes;
		}
		for (int i = 0; i < 8; i++) {
			String s = sBytes.substring(i * 8, (i * 8) + 8);
			for (int j = 0; j < 8; ++j) {
				if (s.charAt(j) == '1') {
					bytesKey[i] = (byte) (bytesKey[i] | (1 << (7 - j)));
				}
			}
		}
		return bytesKey;
	}
}
