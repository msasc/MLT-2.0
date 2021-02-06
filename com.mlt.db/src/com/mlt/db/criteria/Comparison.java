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
 * Comparison operators used within filter criterias.
 *
 * @author Miquel Sas
 */
public enum Comparison {

	EQ("EQ", 1, "BOOL", "NUM", "DATE", "STR"),
	GT("GT", 1, "NUM", "DATE", "STR"),
	GE("GE", 1, "NUM", "DATE", "STR"),
	LT("LT", 1, "NUM", "DATE", "STR"),
	LE("LE", 1, "NUM", "DATE", "STR"),
	NE("NE", 1, "BOOL", "NUM", "DATE", "STR"),

	STARTS_WITH("STARTS WITH", 1, "STR"),
	CONTAINS("CONTAINS", 1, "STR"),
	ENDS_WITH("ENDS WITH", 1, "STR"),

	IN("IN", -1, "BOOL", "NUM", "DATE", "STR"),
	NOT_IN("NOT IN", -1, "BOOL", "NUM", "DATE", "STR"),

	IS_NULL("IS NULL", 0, "DATE", "STR"),
	IS_NOT_NULL("IS NOT NULL", 0, "DATE", "STR");

	/** String identifier. */
	private String id;
	/** Size or number of required values, -1 for one or more. */
	private int size;
	/** Accepted supertypes. */
	private String[] types;
	/**
	 * @param id    String identifier.
	 * @param size  Size or number of required values.
	 * @param types Accepted supertypes.
	 */
	Comparison(String id, int size, String... types) {
		this.id = id;
		this.size = size;
		this.types = types;
	}

	/** @return The string identifier. */
	public String getId() { return id; }
	/** @return The size or number of accepted values. */
	public int size() { return size; }
	/** @return The list of accepted supertypes. */
	public String[] getTypes() { return types; }

	/**
	 * @return A boolean indicating whether the operator is unary.
	 */
	public boolean isUnary() { return size == 0; }
	/**
	 * @return A boolean indicating whether the operator is unary.
	 */
	public boolean isBinary() { return size != 0; }

	/** {@inheritDoc} */
	@Override
	public String toString() { return id; }

	/**
	 * @param id The comparison string identifier.
	 * @return The comparison opeerator.
	 */
	public static Comparison get(String id) {
		if (id == null) throw new NullPointerException();
		id = id.toUpperCase();
		for (Comparison cmp : values()) {
			if (cmp.id.equals(id)) return cmp;
		}
		return null;
	}
}
