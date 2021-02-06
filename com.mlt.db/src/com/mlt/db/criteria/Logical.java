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

package com.mlt.db.criteria;

/**
 * Logical operators used within filter criterias.
 *
 * @author Miquel Sas
 */
public enum Logical {

	AND("AND"),
	OR("OR"),

	AND_NOT("AND NOT"),
	OR_NOT("OR NOT");

	/** String identifier. */
	private String id;
	/**
	 * @param id The string identifier.
	 */
	Logical(String id) {
		this.id = id;
	}

	/**
	 * @return The string identifier.
	 */
	public String getId() { return id; }

	/** {@inheritDoc} */
	@Override
	public String toString() { return id; }

	/**
	 * @param id The comparison string identifier.
	 * @return The comparison opeerator.
	 */
	public static Logical get(String id) {
		if (id == null) throw new NullPointerException();
		id = id.toUpperCase();
		for (Logical logical : values()) {
			if (logical.id.equals(id)) return logical;
		}
		return null;
	}
}
