/*
 * Copyright (c) 2021. Miquel Sas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.mlt.common.lang;

import java.util.Comparator;

/**
 * Comparators.
 * @author Miquel Sas
 */
public class Comparators {
	/**
	 * Comparator of binary arrays.
	 */
	public static class Binary implements Comparator<byte[]> {
		/**
		 * Return a negative integer, zero, or a positive integer as the first argument is less
		 * than, equal to, or greater than the second.
		 * @param b1 the first object to be compared.
		 * @param b2 the second object to be compared.
		 * @return a negative integer, zero, or a positive integer as the first argument is less
		 * than, equal to, or greater than the second.
		 */
		@Override
		public int compare(byte[] b1, byte[] b2) {
			if (b1 == null && b2 == null) return 0;
			if (b1 == null && b2 != null) return 1;
			if (b1 != null && b2 == null) return -1;
			int size = Math.max(b1.length, b2.length);
			for (int i = 0; i < size; i++) {
				if (i >= b1.length) return 1;
				if (i >= b2.length) return -1;
				int compare = Byte.compare(b1[i], b2[i]);
				if (compare != 0) return compare;
			}
			return 0;
		}
	}

	/**
	 * Compare binaries.
	 * @param b1 First binary.
	 * @param b2 Second binary.
	 * @return a negative integer, zero, or a positive integer as the first argument is less than,
	 * equal to, or greater than the second.
	 */
	public static int compare(byte[] b1, byte[] b2) {
		Comparator<byte[]> cmp = new Binary();
		return cmp.compare(b1, b2);
	}
}
