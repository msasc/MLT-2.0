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
import java.util.Objects;

/**
 * Mutable value of a field.
 *
 * @author Miquel Sas
 */
public class Value {

	/**
	 * Internal value.
	 */
	private Object value;
	/**
	 * Type.
	 */
	private Type type;
	/**
	 * Number of decimal places, if applicable, necessary to retain them when the value is set to
	 * null.
	 */
	private Integer decimals;
	/**
	 * Modified flag.
	 */
	private boolean modified;

	/**
	 * @param value A Boolean value.
	 */
	public Value(Boolean value) {
		initialize(value, Type.BOOLEAN, null);
	}

	/**
	 * @param value A BigDecimal value. If null, then the number of decimal places is set to zero.
	 */
	public Value(BigDecimal value) {
		initialize(value, Type.BOOLEAN, value == null ? 0 : value.scale());
	}
	/**
	 * @param value A Double value.
	 */
	public Value(Double value) {
		initialize(value, Type.DOUBLE, null);
	}
	/**
	 * @param value An Integer value.
	 */
	public Value(Integer value) {
		initialize(value, Type.INTEGER, null);
	}
	/**
	 * @param value A Long value.
	 */
	public Value(Long value) {
		initialize(value, Type.LONG, null);
	}

	/**
	 * @param value A LocalDate value.
	 */
	public Value(LocalDate value) {
		initialize(value, Type.DATE, null);
	}
	/**
	 * @param value A LocalTime value.
	 */
	public Value(LocalTime value) {
		initialize(value, Type.TIME, null);
	}
	/**
	 * @param value A LocalDateTime value.
	 */
	public Value(LocalDateTime value) {
		initialize(value, Type.DATETIME, null);
	}

	/**
	 * @param value A String value.
	 */
	public Value(String value) {
		initialize(value, Type.STRING, null);
	}

	/**
	 * @param value A byte[] value.
	 */
	public Value(byte[] value) {
		initialize(value, Type.BINARY, null);
	}

	/**
	 * @param value A JSONDocument value.
	 */
	public Value(JSONDocument value) {
		initialize(value, Type.DOCUMENT, null);
	}
	/**
	 * @param value A JSONList value.
	 */
	public Value(JSONList value) {
		initialize(value, Type.LIST, null);
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

	/**
	 * @return A boolean indicating whether the value is null.
	 */
	public boolean isNull() {
		return value == null;
	}

	/**
	 * @return A Boolean if the type is boolean, otherwise throws an IllegalStateException.
	 */
	public Boolean getBoolean() {
		if (!isBoolean()) throw new IllegalStateException();
		return (Boolean) value;
	}

	/**
	 * @return A BigDecimal if the type is numeric, otherwise throws an IllegalStateException. The
	 * returned BigDecimal retains the number of decimal places.
	 */
	public BigDecimal getDecimal() {
		if (!isNumber()) {
			throw new IllegalStateException();
		}
		if (isNull()) {
			return (BigDecimal) null;
		}
		if (!isDecimal()) {
			return BigDecimal.valueOf(((Number) value).doubleValue()).setScale(decimals, RoundingMode.HALF_UP);
		}
		return (BigDecimal) value;
	}
	/**
	 * @return A Double if the type is numeric, otherwise throws an IllegalStateException.
	 */
	public Double getDouble() {
		if (!isNumber()) {
			throw new IllegalStateException();
		}
		if (isNull()) {
			return (Double) null;
		}
		return ((Number) value).doubleValue();
	}
	/**
	 * @return A Integer if the type is numeric, otherwise throws an IllegalStateException.
	 */
	public Integer getInteger() {
		if (!isNumber()) {
			throw new IllegalStateException();
		}
		if (isNull()) {
			return (Integer) null;
		}
		return ((Number) value).intValue();
	}
	/**
	 * @return A Long if the type is numeric, otherwise throws an IllegalStateException.
	 */
	public Long getLong() {
		if (!isNumber()) {
			throw new IllegalStateException();
		}
		if (isNull()) {
			return (Long) null;
		}
		return ((Number) value).longValue();
	}

	/**
	 * @return A LocalDate if the type is a date, otherwise throws an IllegalStateException.
	 */
	public LocalDate getDate() {
		if (!isDate()) {
			throw new IllegalStateException();
		}
		return (LocalDate) value;
	}
	/**
	 * @return A LocalTime if the type is a time, otherwise throws an IllegalStateException.
	 */
	public LocalTime getTime() {
		if (!isTime()) {
			throw new IllegalStateException();
		}
		return (LocalTime) value;
	}
	/**
	 * @return A LocalDateTime if the type is a date-time, otherwise throws an IllegalStateException.
	 */
	public LocalDateTime getDateTime() {
		if (!isDateTime()) {
			throw new IllegalStateException();
		}
		return (LocalDateTime) value;
	}

	/**
	 * @return A String if the type is a string, otherwise throws an IllegalStateException.
	 */
	public String getString() {
		if (!isString()) {
			throw new IllegalStateException();
		}
		return (String) value;
	}

	/**
	 * @return A byte[] if the type is a binary, otherwise throws an IllegalStateException.
	 */
	public byte[] getBinary() {
		if (!isBinary()) {
			throw new IllegalStateException();
		}
		return (byte[]) value;
	}

	/**
	 * @return A JSONDocument if the type is a document, otherwise throws an IllegalStateException.
	 */
	public JSONDocument getDocument() {
		if (!isDocument()) {
			throw new IllegalStateException();
		}
		return (JSONDocument) value;
	}
	/**
	 * @return A JSONList if the type is a list, otherwise throws an IllegalStateException.
	 */
	public JSONList getList() {
		if (!isList()) {
			throw new IllegalStateException();
		}
		return (JSONList) value;
	}

	/**
	 * @return The type.
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @return The number of decimal places or null.
	 */
	public Integer getDecimals() {
		return decimals;
	}
	/**
	 * @return A boolean indicating whether the value has been modified.
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * @param value A Boolean value.
	 */
	public void setBoolean(Boolean value) {
		setValue(value, Type.BOOLEAN);
	}

	/**
	 * @param value A BigDecimal value.
	 */
	public void setDecimal(BigDecimal value) {
		setNumber(value);
	}
	/**
	 * @param value A Double value.
	 */
	public void setDouble(Double value) {
		setNumber(value);
	}
	/**
	 * @param value An Integer value.
	 */
	public void setInteger(Integer value) {
		setNumber(value);
	}
	/**
	 * @param value A Long value.
	 */
	public void setLong(Long value) {
		setNumber(value);
	}

	/**
	 * @param value A LocalDate value.
	 */
	public void setDate(LocalDate value) {
		setValue(value, Type.DATE);
	}
	/**
	 * @param value A LocalTime value.
	 */
	public void setTime(LocalTime value) {
		setValue(value, Type.TIME);
	}
	/**
	 * @param value A LocalDateTime value.
	 */
	public void setDateTime(LocalDateTime value) {
		setValue(value, Type.DATETIME);
	}

	/**
	 * @param value A String value.
	 */
	public void setString(String value) {
		setValue(value, Type.STRING);
	}

	/**
	 * @param value A byte[] value.
	 */
	public void setBinary(byte[] value) {
		setValue(value, Type.BINARY);
	}

	/**
	 * @param value A JSONDocument value.
	 */
	public void setDocument(JSONDocument value) {
		setValue(value, Type.DOCUMENT);
	}
	/**
	 * @param value A JSONList value.
	 */
	public void setList(JSONList value) {
		setValue(value, Type.LIST);
	}

	/**
	 * Set the value to NULL.
	 */
	public void setNull() {
		this.value = null;
		this.modified = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Value v = (Value) o;
		return Objects.equals(value, v.value) && type == v.type;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(value, type);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (value == null) return "null";
		return value.toString();
	}
	/**
	 * Set any value except numeric values.
	 *
	 * @param value The value.
	 * @param type  The type.
	 */
	private void setValue(Object value, Type type) {
		if (this.type != type) throw new IllegalStateException();
		this.value = value;
		this.modified = true;
	}
	/**
	 * Set a number applying the proper transformation depending on the numeric type.
	 *
	 * @param value The number value.
	 */
	private void setNumber(Number value) {
		if (value == null) {
			this.value = null;
		} else if (isDecimal()) {
			this.value = BigDecimal.valueOf(value.doubleValue()).setScale(decimals, RoundingMode.HALF_UP);
		} else if (isDouble()) {
			this.value = value.doubleValue();
		} else if (isInteger()) {
			this.value = value.intValue();
		} else if (isLong()) {
			this.value = value.longValue();
		} else {
			throw new IllegalStateException();
		}
		this.modified = true;
	}
	/**
	 * @param value    The value.
	 * @param type     The type.
	 * @param decimals The number of decimal places if applicable.
	 */
	private void initialize(Object value, Type type, Integer decimals) {
		this.value = value;
		this.type = type;
		this.decimals = decimals;
		this.modified = false;
	}
}
