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

package com.mlt.db;

import java.util.Objects;

/**
 * A field key in an order or index. A field key packs a field and an ascending flag.
 * @author Miquel Sas
 */
public class FieldKey implements Comparable<Object> {

	/**
	 * The field.
	 */
	private Field field;
	/**
	 * The ascending flag.
	 */
	private boolean asc = true;

	/**
	 * Constructor.
	 * @param field The field.
	 * @param asc   Ascending boolean flag.
	 */
	public FieldKey(Field field, boolean asc) {
		this.field = field;
		this.asc = asc;
	}

	/**
	 * Get the field.
	 * @return The field.
	 */
	public Field getField() {
		return field;
	}
	/**
	 * Check the ascending flag.
	 * @return A boolean that indicates whether the key is ascending or descending.
	 */
	public boolean isAsc() {
		return asc;
	}

	/**
	 * Compares this object with the specified object for order. Returns a negative integer, zero,
	 * or a positive integer as this object is less than, equal to, or greater than the specified
	 * object.
	 * @param obj the object to be compared.
	 * @return negative integer, zero, or a positive integer as this object is less than, equal to,
	 * or greater than the specified object.
	 */
	@Override
	public int compareTo(Object obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		if (!(obj instanceof FieldKey)) {
			throw new UnsupportedOperationException("Not comparable type: " + obj.getClass());
		}
		FieldKey key = (FieldKey) obj;
		if (key == null) throw new NullPointerException();
		int compare = field.compareTo(key.field);
		if (compare != 0) return compare * (asc ? 1 : -1);
		return 0;
	}
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param obj the reference object with which to compare.
	 * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		FieldKey key = (FieldKey) obj;
		if (!Objects.equals(this.field, key.field)) return false;
		return this.asc == key.asc;
	}
	/**
	 * Returns a hash code.
	 * @return a hash code.
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash ^= field.hashCode();
		hash ^= Boolean.valueOf(asc).hashCode();
		return hash;
	}
}
