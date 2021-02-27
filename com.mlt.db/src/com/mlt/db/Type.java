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
 * Data types.
 * @author Miquel Sas
 */
public enum Type {
	/**
	 * Boolean, can be backed to the underlying database either as a boolean (true/false) or as a
	 * string (Y/N or Yes/No). Additionally, the field definition specifies how it is displayed.
	 */
	BOOLEAN,

	/**
	 * Decimal, a number of any length with a pre-defined number of decimal places.
	 */
	DECIMAL,
	/**
	 * Double, floating point 64 bit number.
	 */
	DOUBLE,
	/**
	 * Integer, 32 bit integer number.
	 */
	INTEGER,
	/**
	 * Long, 64 bit integer number.
	 */
	LONG,

	/**
	 * Date, with a normalized 'YYYY-MM-DD' format.
	 */
	DATE,
	/**
	 * Time, with a normalized 'hh:mm:ss.nnnnnnnnn' format. The field definition can specify
	 * whether nanos are included and to what level of detail.
	 */
	TIME,
	/**
	 * Date-time, with the normalyzed format 'YYYY-MM-DDThh:mm:ss.nnnnnnnnn'.
	 */
	TIMESTAMP,

	/**
	 * String, whether it will be backed in the database by a simple VARCHAR or a CLOB will depend
	 * on the database, the supported maximum length for varying chars and the required length of
	 * the field.
	 */
	STRING,

	/**
	 * Binary, as with the string class, whether it will be backed in the database by a simple
	 * VARBINARY or a BLOB will depend on the database, the supporting maximum length for varying
	 * binaries and the required length of the field.
	 */
	BINARY,

	/**
	 * Document, backed in the database by a CLOB or a document, depending on whether it is a
	 * relational or document database.
	 */
	DOCUMENT,
	/**
	 * List, backed in the database by a CLOB or a list, depending on whether it is a relational or
	 * document database.
	 */
	LIST;

	/**
	 * Return a boolean indicating whether this type is one of the list.
	 * @param types The reference list of types.
	 * @return A boolean indicating whether this type is one of the list.
	 */
	public boolean in(Type... types) {
		if (types == null) throw new NullPointerException();
		for (Type type : types) {
			if (this == type) return true;
		}
		return false;
	}

	/**
	 * Return a boolean indicating whether the type is BOOLEAN.
	 * @return A boolean to confirm the type.
	 */
	public boolean isBoolean() {
		return this == Type.BOOLEAN;
	}

	/**
	 * Return a boolean indicating whether the type is DECIMAL.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDecimal() {
		return this == Type.DECIMAL;
	}
	/**
	 * Return a boolean indicating whether the type is DOUBLE.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDouble() {
		return this == Type.DOUBLE;
	}
	/**
	 * Return a boolean indicating whether the type is INTEGER.
	 * @return A boolean to confirm the type.
	 */
	public boolean isInteger() {
		return this == Type.INTEGER;
	}
	/**
	 * Return a boolean indicating whether the type is LONG.
	 * @return A boolean to confirm the type.
	 */
	public boolean isLong() {
		return this == Type.LONG;
	}
	/**
	 * Return a boolean indicating whether the type is a number type.
	 * @return A boolean to confirm the type.
	 */
	public boolean isNumber() {
		return isDecimal() || isDouble() || isInteger() || isLong();
	}

	/**
	 * Return a boolean indicating whether the type is DATE.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDate() {
		return this == Type.DATE;
	}
	/**
	 * Return a boolean indicating whether the type is TIME.
	 * @return A boolean to confirm the type.
	 */
	public boolean isTime() {
		return this == Type.TIME;
	}
	/**
	 * Return a boolean indicating whether the type is TIMESTAMP.
	 * @return A boolean to confirm the type.
	 */
	public boolean isTimestamp() {
		return this == Type.TIMESTAMP;
	}

	/**
	 * Return a boolean indicating whether the type is STRING.
	 * @return A boolean to confirm the type.
	 */
	public boolean isString() {
		return this == Type.STRING;
	}

	/**
	 * Return a boolean indicating whether the type is BINARY.
	 * @return A boolean to confirm the type.
	 */
	public boolean isBinary() {
		return this == Type.BINARY;
	}

	/**
	 * Return a boolean indicating whether the type is DOCUMENT.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDocument() {
		return this == Type.DOCUMENT;
	}
	/**
	 * Return a boolean indicating whether the type is LIST.
	 * @return A boolean to confirm the type.
	 */
	public boolean isList() {
		return this == Type.LIST;
	}
}
