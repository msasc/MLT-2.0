/*
 * Copyright (c) 2020. Miquel Sas
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


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A field or column of a table or view.
 *
 * @author Miquel Sas
 */
public class Field implements Comparable<Object> {

	/** Name. */
	private String name;
	/** Alias, should uniquely identify the field within a list, if not set the name is used. */
	private String alias;
	/** Length if applicable, otherwise -1. */
	private int length;
	/** Decimals if applicable, otherwise -1. */
	private int decimals;
	/** Type. */
	private Types type;

	/** A flag that indicates whether this field can be null. */
	private boolean nullable = true;
	/** A flag that indicates whether this field is a primary key field. */
	private boolean primaryKey = false;
	/** A flag that indicates whether this field is persistent. */
	private boolean persistent = true;

	/**
	 * @param name     The name.
	 * @param alias    The alias or null.
	 * @param type     The type.
	 * @param length   The length, only applicable for types DECIMAL and STRING, although STRING can
	 *                 have unlimited length.
	 * @param decimals The decimal places, only applicable for type DECIMAL.
	 */
	public Field(String name, String alias, Types type, int length, int decimals) {
		/* Name is required. */
		if (name == null) {
			throw new NullPointerException("Name is required.");
		}
		/* Type is required. */
		if (type == null) {
			throw new NullPointerException("Type is required.");
		}
		/* Decimals are only accepted for type DECIMAL. */
		if (type != Types.DECIMAL && decimals >= 0) {
			throw new IllegalArgumentException("Decimal places with non decimal field.");
		}
		/* Length is only applicable for STRING and DECIMAL types. */
		if (type != Types.STRING && type != Types.DECIMAL) {
			if (length >= 0) {
				throw new IllegalArgumentException("Invalid length for a non string or decimal field.");
			}
		}
		/* Length is strictly required for type DECIMAL (STRING can be unlimited). */
		if (type == Types.DECIMAL && length <= 0) {
			throw new IllegalArgumentException("DECIMAL type required length.");
		}
		this.name = name;
		this.alias = alias;
		this.type = type;
		if (length > 0) {
			this.length = length;
		} else {
			this.length = -1;
		}
		if (decimals >= 0) {
			this.decimals = decimals;
		} else {
			this.decimals = -1;
		}
	}

	/**
	 * @param field The source field to copy from.
	 */
	public Field(Field field) {
		if (field == null) throw new NullPointerException();

		this.name = field.name;
		this.alias = field.alias;
		this.length = field.length;
		this.decimals = field.decimals;
		this.type = field.type;

		this.primaryKey = field.primaryKey;
		this.persistent = field.persistent;
	}

	/** @return The name. */
	public String getName() { return name; }

	/** @return The alias if set, otherwise the name. */
	public String getAlias() {
		if (alias == null) { return getName(); }
		return alias;
	}

	/** @return The length if applicable, otherwise -1. */
	public int getLength() { return length; }

	/**
	 * @return The number of decimal places. If it is not a number or it is a double, the decimal
	 * places does not apply and returns -1. If it is an integer or a long, the decimals are 0.
	 */
	public int getDecimals() {
		if (!isNumber() || isDouble()) { return -1; }
		if (isInteger() || isLong()) { return 0; }
		return decimals;
	}

	/** @return The type. */
	public Types getType() { return type; }

	/** @return A suitable default value for the field type. */
	public Value getDefaultValue() {

		if (isBoolean()) { return new Value(false); }

		if (isDecimal()) {
			return new Value(new BigDecimal(0).setScale(getDecimals(), RoundingMode.HALF_UP));
		}
		if (isDouble()) { return new Value((double) 0); }
		if (isInteger()) { return new Value(0); }
		if (isLong()) { return new Value((long) 0); }

		if (isString()) { return new Value("");	}

		if (isDate()) {	return new Value((LocalDate) null);	}
		if (isTime()) {	return new Value((LocalTime) null);	}
		if (isDateTime()) {	return new Value((LocalDateTime) null);	}

		if (isByteArray()) { return new Value((byte[]) null); }

		return null;
	}

	public boolean isBoolean() { return type.isBoolean(); }
	public boolean isDecimal() { return type.isDecimal(); }
	public boolean isDouble() { return type.isDouble(); }
	public boolean isInteger() { return type.isInteger(); }
	public boolean isLong() { return type.isLong(); }
	public boolean isNumber() { return type.isNumber(); }
	public boolean isFloatingPoint() { return type.isFloatingPoint(); }
	public boolean isString() { return type.isString(); }
	public boolean isDate() { return type.isDate(); }
	public boolean isDateTime() { return type.isDateTime(); }
	public boolean isTime() { return type.isTime(); }
	public boolean isByteArray() { return type.isByteArray(); }

	public boolean isNullable() { return nullable; }
	public boolean isPrimaryKey() { return primaryKey; }
	public boolean isPersistent() { return persistent; }

	/** @param name The name of the field. */
	public void setName(String name) { this.name = name; }

	/** @param alias The field alias. */
	public void setAlias(String alias) { this.alias = alias; }

	/** @param length The field length. */
	public void setLength(int length) { this.length = length; }

	/** @param decimals The number of decimal places. */
	public void setDecimals(int decimals) { this.decimals = decimals; }

	/** @param type Type. Types can not be modified once set. */
	public void setType(Types type) {
		if (this.type != null) {
			throw new IllegalStateException("Types can not be modified.");
		}
		this.type = type;
	}

	/** @param nullable A boolean */
	public void setNullable(boolean nullable) {	this.nullable = nullable; }

	/** @param persistent A boolean. */
	public void setPersistent(boolean persistent) {	this.persistent = persistent; }

	/** @param primaryKey A boolean. */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
		if (primaryKey) {
			setNullable(false);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Field)) {
			throw new UnsupportedOperationException("Not comparable type: " + o.getClass().getName());
		}
		Field field = (Field) o;
		if (getAlias().equals(field.getAlias())) {
			if (getType().equals(field.getType())) {
				if (getLength() == field.getLength()) {
					if (getDecimals() == field.getDecimals()) {
						return 0;
					}
				}
			}
		}
		return getAlias().compareTo(field.getAlias());
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Field) { return (compareTo(obj) == 0); }
		return false;
	}

	/** @return The hash code. */
	public int hashCode() {	return getAlias().hashCode(); }
}
