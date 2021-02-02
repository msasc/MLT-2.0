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

/**
 * Metadata definition of a field.
 *
 * @author Miquel Sas
 */
public class Field {

	/**
	 * The name or key used to access the field within a document or row.
	 */
	private String name;
	/**
	 * The type.
	 */
	private Type type;
	/**
	 * The length if applicable or null.
	 */
	private Integer length;
	/**
	 * The number of decimal places if applicable or null.
	 */
	private Integer decimals;

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isBoolean() {
		return type.isBoolean();
	}

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isDecimal() {
		return type.isDecimal();
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isDouble() {
		return type.isDouble();
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isInteger() {
		return type.isInteger();
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isLong() {
		return type.isLong();
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isNumber() {
		return type.isNumber();
	}

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isDate() {
		return type.isDate();
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isTime() {
		return type.isTime();
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isDateTime() {
		return type.isDateTime();
	}

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isString() {
		return type.isString();
	}

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isBinary() {
		return type.isBinary();
	}

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isDocument() {
		return type.isDocument();
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isList() {
		return type.isList();
	}
}
