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

import com.mlt.common.json.JSONDocument;
import com.mlt.common.json.JSONList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Data types.
 *
 * @author Miquel Sas
 */
public enum Type {
	/**
	 * Boolean, can be backed to the underlying database either as a boolean (true/false) or as a
	 * string (Y/N or Yes/No). Additionally, the field definition specifies how it is displayed.
	 */
	BOOLEAN(Boolean.class, "BOOL"),

	/**
	 * Decimal, a number of any length with a pre-defined number of decimal places.
	 */
	DECIMAL(BigDecimal.class, "NUM"),
	/**
	 * Double, floating point 64 bit number.
	 */
	DOUBLE(Double.class, "NUM"),
	/**
	 * Integer, 32 bit integer number.
	 */
	INTEGER(Integer.class, "NUM"),
	/**
	 * Long, 64 bit integer number.
	 */
	LONG(Long.class, "NUM"),

	/**
	 * Date, with a normalized 'YYYY-MM-DD' format.
	 */
	DATE(LocalDate.class, "DATE"),
	/**
	 * Time, with a normalized 'hh:mm:ss.nnnnnnnnn' format. The field definition can specify
	 * whether nanos are included and to what level of detail.
	 */
	TIME(LocalTime.class, "DATE"),
	/**
	 * Date-time, with the normalyzed format 'YYY-MM-DD hh:mm:ss.nnnnnnnnn'.
	 */
	DATETIME(LocalDateTime.class, "DATE"),

	/**
	 * String, whether it will be backed in the database by a simple VARCHAR or a CLOB will depend
	 * on the database, the supported maximum length for varying chars and the required length of
	 * the field.
	 */
	STRING(String.class, "STR"),

	/**
	 * Binary, as with the string class, whether it will be backed in the database by a simple
	 * VARBINARY or a BLOB will depend on the database, the supporting maximum length for varying
	 * binaries and the required length of the field.
	 */
	BINARY(byte[].class, "BIN"),

	/**
	 * Document, backed in the database by a CLOB or a document, depending on whether it is a
	 * relational or document database.
	 */
	DOCUMENT(JSONDocument.class, "OBJ"),
	/**
	 * List, backed in the database by a CLOB or a list, depending on whether it is a relational or
	 * document database.
	 */
	LIST(JSONList.class, "OBJ");

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isBoolean() {
		return this == Type.BOOLEAN;
	}

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isDecimal() {
		return this == Type.DECIMAL;
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isDouble() {
		return this == Type.DOUBLE;
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isInteger() {
		return this == Type.DECIMAL;
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isLong() {
		return this == Type.DECIMAL;
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isNumber() {
		return isDecimal() || isDouble() || isInteger() || isLong();
	}

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isDate() {
		return this == Type.DATE;
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isTime() {
		return this == Type.TIME;
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isDateTime() {
		return this == Type.DATETIME;
	}

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isString() {
		return this == Type.STRING;
	}

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isBinary() {
		return this == Type.BINARY;
	}

	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isDocument() {
		return this == Type.DOCUMENT;
	}
	/**
	 * @return A boolean to confirm the type.
	 */
	public boolean isList() {
		return this == Type.LIST;
	}

	/**
	 * @param javaClass The java class.
	 * @param superType The string supertype.
	 */
	private Type(Class<?> javaClass, String superType) {
		this.javaClass = javaClass;
		this.superType = superType;
	}
	/**
	 * Java rlated class.
	 */
	private Class<?> javaClass;
	/**
	 * Supertype as as string.
	 */
	private String superType;
}
