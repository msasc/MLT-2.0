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

import com.mlt.db.Type;

/**
 * Comparison operators used within filter criterias.
 *
 * @author Miquel Sas
 */
public enum Comparison {

	/** Equal to. */
	EQ("EQ", 1),
	/** Greater than. */
	GT("GT", 1),
	/** Greater than or equal to. */
	GE("GE", 1),
	/** Less than. */
	LT("LT", 1),
	/** Less than or equal to. */
	LE("LE", 1),
	/** Not equal to. */
	NE("NE", 1),

	/** Starts with. */
	STARTS_WITH("STARTS WITH", 1, Type.STRING),
	/** Contains. */
	CONTAINS("CONTAINS", 1, Type.STRING),
	/** Ends with. */
	ENDS_WITH("ENDS WITH", 1, Type.STRING),

	/** Contained in the list. */
	IN("IN", -1, new Type[]{
		Type.DECIMAL, Type.DOUBLE, Type.INTEGER, Type.LONG,
		Type.DATE, Type.TIME, Type.TIMESTAMP,
		Type.STRING
	}),
	/** Not contained in the list. */
	NOT_IN("NOT IN", -1, new Type[]{
		Type.DECIMAL, Type.DOUBLE, Type.INTEGER, Type.LONG,
		Type.DATE, Type.TIME, Type.TIMESTAMP,
		Type.STRING
	}),

	/** Is null. */
	IS_NULL("IS NULL", 0),
	/** Is not null. */
	IS_NOT_NULL("IS NOT NULL", 0);

	/** String identifier. */
	private String id;
	/** Size or number of required values, -1 for one or more. */
	private int size;
	/** Accepted types, null for all types. */
	private Type[] types;
	/**
	 * @param id    String identifier.
	 * @param size  Size or number of required values.
	 * @param types Accepted supertypes.
	 */
	Comparison(String id, int size, Type... types) {
		this.id = id;
		this.size = size;
		this.types = types;
	}

	/** @return The string identifier. */
	public String getId() { return id; }
	/** @return The size or number of accepted values. */
	public int size() { return size; }
	/** @return The list of accepted supertypes. */
	public Type[] getTypes() { return types.length == 0 ? null : types; }

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
