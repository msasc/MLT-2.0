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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
	 * Constructor assingning only the name and the type. Applicable to all types, those that accept
	 * a length are treated as no length limit, and for a decimal the scale is set to zero.
	 *
	 * @param name The name.
	 * @param type The type.
	 */
	public Field(String name, Type type) {
		this(name, type, null, null);
	}
	/**
	 * Constructor assingning the name, the type and the length. Applicable to DECIMAL, STRING and
	 * BINARY types.
	 *
	 * @param name   The name.
	 * @param type   The type.
	 * @param length The length.
	 */
	public Field(String name, Type type, Integer length) {
		this(name, type, length, null);
	}
	/**
	 * Constructor assingning the name, the type, length and decimals. Applicable to DECIMAL, STRING
	 * and
	 * BINARY types.
	 *
	 * @param name     The name.
	 * @param type     The type.
	 * @param length   The length.
	 * @param decimals The number of decimal places.
	 */
	public Field(String name, Type type, Integer length, Integer decimals) {

		/*
		 * Name and type can not be null.
		 */
		if (name == null) {
			throw new NullPointerException("Name can not be null");
		}
		if (type == null) {
			throw new NullPointerException("Type can not be null");
		}
		if (length != null && length <= 0) {
			throw new IllegalArgumentException("Invalid length " + length + " do not accept length");
		}

		/*
		 * Only types DECIMAL, STRING and BINARY accept lengh.
		 */
		if (type != Type.DECIMAL && type != Type.STRING && type != Type.BINARY && length != null) {
			throw new IllegalArgumentException("Type " + type + " do not accept length");
		}

		/*
		 * Only type DECIMAL accepts decimals.
		 */
		if (type != Type.DECIMAL && decimals != null) {
			throw new IllegalArgumentException("Type " + type + " do not accept decimals");
		}

		/*
		 * If type is DECIMAL and decimals is null, set decimals to zero.
		 */
		if (type == Type.DECIMAL && decimals == null) {
			decimals = 0;
		}

		/*
		 * Assign.
		 */
		this.name = name;
		this.type = type;
		this.length = length;
		this.decimals = decimals;
	}

	/**
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return The type.
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @return The length if applicable.
	 */
	public Integer getLength() {
		return length;
	}
	/**
	 * @return The lengthnumber of decimal places if applicable.
	 */
	public Integer getDecimals() {
		return decimals;
	}

	public Value getDefaultValue() {
		switch (type) {
		case BOOLEAN:
			return new Value(false);
		case DECIMAL:
			return new Value(BigDecimal.valueOf(0).setScale(decimals, RoundingMode.HALF_UP));
		case DOUBLE:
			return new Value(Double.valueOf(0));
		case INTEGER:
			return new Value(Integer.valueOf(0));
		case LONG:
			return new Value(Long.valueOf(0));
		case DATE:
			return new Value(LocalDate.now());
		case TIME:
			return new Value(LocalTime.now());
		case DATETIME:
			return new Value(LocalDateTime.now());
		case STRING:
			return new Value("");
		case BINARY:
			return new Value((byte[]) null);
		case DOCUMENT:
			return new Value(new JSONDocument());
		case LIST:
			return new Value(new JSONList());
		}
		throw new IllegalStateException();
	}

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
