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

import com.mlt.common.json.JSONDoc;
import com.mlt.common.json.JSONList;
import com.mlt.common.lang.Comparators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Mutable value of a field.
 * @author Miquel Sas
 */
public class Value implements Comparable<Object> {
	/**
	 * Add the value to the JSON list.
	 * @param list  The destination list.
	 * @param value The value to add.
	 */
	public static void addValue(JSONList list, Value value) {
		switch (value.getType()) {
		case BOOLEAN:
			list.addBoolean(value.getBoolean());
			break;
		case DECIMAL:
			list.addDecimal(value.getDecimal());
			break;
		case DOUBLE:
			list.addDouble(value.getDouble());
			break;
		case INTEGER:
			list.addInteger(value.getInteger());
			break;
		case LONG:
			list.addLong(value.getLong());
			break;
		case DATE:
			list.addDate(value.getDate());
			break;
		case TIME:
			list.addTime(value.getTime());
			break;
		case TIMESTAMP:
			list.addTimestamp(value.getTimestamp());
			break;
		case STRING:
			list.addString(value.getString());
			break;
		case BINARY:
			list.addBinary(value.getBinary());
			break;
		case DOCUMENT:
			list.addDocument(value.getDocument().toJSONDoc());
			break;
		case LIST:
			list.addList(value.getList().toJSONList());
			break;
		}
	}
	/**
	 * Set a value to a documen t.
	 * @param doc   The JSON document.
	 * @param key   The key.
	 * @param value The value to set.
	 */
	public static void setValue(JSONDoc doc, String key, Value value) {
		switch (value.getType()) {
		case BOOLEAN:
			doc.setBoolean(key, value.getBoolean());
			break;
		case DECIMAL:
			doc.setDecimal(key, value.getDecimal());
			break;
		case DOUBLE:
			doc.setDouble(key, value.getDouble());
			break;
		case INTEGER:
			doc.setInteger(key, value.getInteger());
			break;
		case LONG:
			doc.setLong(key, value.getLong());
			break;
		case DATE:
			doc.setDate(key, value.getDate());
			break;
		case TIME:
			doc.setTime(key, value.getTime());
			break;
		case TIMESTAMP:
			doc.setTimestamp(key, value.getTimestamp());
			break;
		case STRING:
			doc.setString(key, value.getString());
			break;
		case BINARY:
			doc.setBinary(key, value.getBinary());
			break;
		case DOCUMENT:
			doc.setDocument(key, value.getDocument().toJSONDoc());
			break;
		case LIST:
			doc.setList(key, value.getList().toJSONList());
			break;
		}
	}

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
	 * Constructor of a boolean value.
	 * @param value A Boolean value.
	 */
	public Value(Boolean value) {
		initialize(value, Type.BOOLEAN, null);
	}

	/**
	 * Constructor of a decimal value.
	 * @param value A BigDecimal value. If null, then the number of decimal places is set to zero.
	 */
	public Value(BigDecimal value) {
		initialize(value, Type.DECIMAL, value == null ? 0 : value.scale());
	}
	/**
	 * Constructor of a double value.
	 * @param value A Double value.
	 */
	public Value(Double value) {
		initialize(value, Type.DOUBLE, null);
	}
	/**
	 * Constructor of an integer value.
	 * @param value An Integer value.
	 */
	public Value(Integer value) {
		initialize(value, Type.INTEGER, null);
	}
	/**
	 * Constructor of a long value.
	 * @param value A Long value.
	 */
	public Value(Long value) {
		initialize(value, Type.LONG, null);
	}

	/**
	 * Constructor of a date value.
	 * @param value A LocalDate value.
	 */
	public Value(LocalDate value) {
		initialize(value, Type.DATE, null);
	}
	/**
	 * Constructor of a time value.
	 * @param value A LocalTime value.
	 */
	public Value(LocalTime value) {
		initialize(value, Type.TIME, null);
	}
	/**
	 * Constructor of a timestamp value.
	 * @param value A LocalDateTime value.
	 */
	public Value(LocalDateTime value) {
		initialize(value, Type.TIMESTAMP, null);
	}

	/**
	 * Constructor of a string value.
	 * @param value A String value.
	 */
	public Value(String value) {
		initialize(value, Type.STRING, null);
	}

	/**
	 * Constructor of a binary value.
	 * @param value A byte[] value.
	 */
	public Value(byte[] value) {
		initialize(value, Type.BINARY, null);
	}

	/**
	 * Constructor of a document value.
	 * @param value A Document value.
	 */
	public Value(Document value) {
		initialize(value, Type.DOCUMENT, null);
	}
	/**
	 * Constructor of a value list  value.
	 * @param value A ValueList value.
	 */
	public Value(ValueList value) {
		initialize(value, Type.LIST, null);
	}

	/**
	 * Check whether this value is a boolean.
	 * @return A boolean to confirm the type.
	 */
	public boolean isBoolean() {
		return type.isBoolean();
	}

	/**
	 * Check whether this value is a decimal.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDecimal() {
		return type.isDecimal();
	}
	/**
	 * Check whether this value is a double.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDouble() {
		return type.isDouble();
	}
	/**
	 * Check whether this value is an integer.
	 * @return A boolean to confirm the type.
	 */
	public boolean isInteger() {
		return type.isInteger();
	}
	/**
	 * Check whether this value is a long.
	 * @return A boolean to confirm the type.
	 */
	public boolean isLong() {
		return type.isLong();
	}
	/**
	 * Check whether this value is a number.
	 * @return A boolean to confirm the type.
	 */
	public boolean isNumber() {
		return type.isNumber();
	}

	/**
	 * Check whether this value is a date.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDate() {
		return type.isDate();
	}
	/**
	 * Check whether this value is a time.
	 * @return A boolean to confirm the type.
	 */
	public boolean isTime() {
		return type.isTime();
	}
	/**
	 * Check whether this value is a timestamp.
	 * @return A boolean to confirm the type.
	 */
	public boolean isTimestamp() {
		return type.isTimestamp();
	}

	/**
	 * Check whether this value is a string.
	 * @return A boolean to confirm the type.
	 */
	public boolean isString() {
		return type.isString();
	}

	/**
	 * Check whether this value is a binary.
	 * @return A boolean to confirm the type.
	 */
	public boolean isBinary() {
		return type.isBinary();
	}

	/**
	 * Check whether this value is a document.
	 * @return A boolean to confirm the type.
	 */
	public boolean isDocument() {
		return type.isDocument();
	}
	/**
	 * Check whether this value is a value list.
	 * @return A boolean to confirm the type.
	 */
	public boolean isList() {
		return type.isList();
	}

	/**
	 * Check whether this value is null.
	 * @return A boolean indicating whether the value is null.
	 */
	public boolean isNull() {
		return value == null;
	}

	/**
	 * Return a Boolean if the type is boolean, otherwise throws an IllegalStateException.
	 * @return A Boolean if the type is boolean, otherwise throws an IllegalStateException.
	 */
	public Boolean getBoolean() {
		if (!isBoolean()) throw new IllegalStateException();
		return (Boolean) value;
	}

	/**
	 * Return a BigDecimal if the type is numeric, otherwise throws an IllegalStateException.
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
			return BigDecimal.valueOf(((Number) value).doubleValue());
		}
		return (BigDecimal) value;
	}
	/**
	 * Return a Double if the type is numeric, otherwise throws an IllegalStateException.
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
	 * Return an Integer if the type is numeric, otherwise throws an IllegalStateException.
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
	 * Return a Long if the type is numeric, otherwise throws an IllegalStateException.
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
	 * Return a Number if the type is numeric, otherwise throws an IllegalStateException.
	 * @return A Number if the type is numeric, otherwise throws an IllegalStateException.
	 */
	public Number getNumber() {
		if (!isNumber()) {
			throw new IllegalStateException();
		}
		return (Number) value;
	}

	/**
	 * Return a LocalDate if the type is a date, otherwise throws an IllegalStateException.
	 * @return A LocalDate if the type is a date, otherwise throws an IllegalStateException.
	 */
	public LocalDate getDate() {
		if (!isDate()) {
			throw new IllegalStateException();
		}
		return (LocalDate) value;
	}
	/**
	 * Return a LocalTime if the type is a time, otherwise throws an IllegalStateException.
	 * @return A LocalTime if the type is a time, otherwise throws an IllegalStateException.
	 */
	public LocalTime getTime() {
		if (!isTime()) {
			throw new IllegalStateException();
		}
		return (LocalTime) value;
	}
	/**
	 * Return a LocalDateTime if the type is a date-time, otherwise throws an IllegalStateException.
	 * @return A LocalDateTime if the type is a date-time, otherwise throws an IllegalStateException.
	 */
	public LocalDateTime getTimestamp() {
		if (!isTimestamp()) {
			throw new IllegalStateException();
		}
		return (LocalDateTime) value;
	}

	/**
	 * Return a String if the type is a string, otherwise throws an IllegalStateException.
	 * @return A String if the type is a string, otherwise throws an IllegalStateException.
	 */
	public String getString() {
		if (!isString()) {
			throw new IllegalStateException();
		}
		return (String) value;
	}

	/**
	 * Return a byte[] if the type is a binary, otherwise throws an IllegalStateException.
	 * @return A byte[] if the type is a binary, otherwise throws an IllegalStateException.
	 */
	public byte[] getBinary() {
		if (!isBinary()) {
			throw new IllegalStateException();
		}
		return (byte[]) value;
	}

	/**
	 * Return a Document if the type is a document, otherwise throws an IllegalStateException.
	 * @return A Document if the type is a document, otherwise throws an IllegalStateException.
	 */
	public Document getDocument() {
		if (!isDocument()) {
			throw new IllegalStateException();
		}
		return (Document) value;
	}
	/**
	 * Return a ValueList if the type is a list, otherwise throws an IllegalStateException.
	 * @return A ValueList if the type is a list, otherwise throws an IllegalStateException.
	 */
	public ValueList getList() {
		if (!isList()) {
			throw new IllegalStateException();
		}
		return (ValueList) value;
	}

	/**
	 * Return the type.
	 * @return The type.
	 */
	public Type getType() {
		return type;
	}
	/**
	 * Return the number of decimal places or null.
	 * @return The number of decimal places or null.
	 */
	public Integer getDecimals() {
		return decimals;
	}
	/**
	 * Return a boolean indicating whether the value has been modified.
	 * @return A boolean indicating whether the value has been modified.
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * Set the Boolean value if applicable.
	 * @param value A Boolean value.
	 */
	public void setBoolean(Boolean value) {
		setValue(value, Type.BOOLEAN);
	}

	/**
	 * Set the BigDecimal value if applicable.
	 * @param value A BigDecimal value.
	 */
	public void setDecimal(BigDecimal value) {
		setNumber(value);
	}
	/**
	 * Set the Double value if applicable.
	 * @param value A Double value.
	 */
	public void setDouble(Double value) {
		setNumber(value);
	}
	/**
	 * Set the Integer value if applicable.
	 * @param value An Integer value.
	 */
	public void setInteger(Integer value) {
		setNumber(value);
	}
	/**
	 * Set the Long value if applicable.
	 * @param value A Long value.
	 */
	public void setLong(Long value) {
		setNumber(value);
	}

	/**
	 * Set the LocalDate value if applicable.
	 * @param value A LocalDate value.
	 */
	public void setDate(LocalDate value) {
		setValue(value, Type.DATE);
	}
	/**
	 * Set the LocalTime value if applicable.
	 * @param value A LocalTime value.
	 */
	public void setTime(LocalTime value) {
		setValue(value, Type.TIME);
	}
	/**
	 * Set the LocalDateTime value if applicable.
	 * @param value A LocalDateTime value.
	 */
	public void setTimestamp(LocalDateTime value) {
		setValue(value, Type.TIMESTAMP);
	}

	/**
	 * Set the String value if applicable.
	 * @param value A String value.
	 */
	public void setString(String value) {
		setValue(value, Type.STRING);
	}

	/**
	 * Set the byte[] value if applicable.
	 * @param value A byte[] value.
	 */
	public void setBinary(byte[] value) {
		setValue(value, Type.BINARY);
	}

	/**
	 * Set the Document value if applicable.
	 * @param value A Document value.
	 */
	public void setDocument(Document value) {
		setValue(value, Type.DOCUMENT);
	}
	/**
	 * Set the ValueList value if applicable.
	 * @param value A ValueList value.
	 */
	public void setList(ValueList value) {
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
		if (!(obj instanceof Value)) {
			throw new UnsupportedOperationException("Not comparable type: " + obj.getClass());
		}
		Value v = (Value) obj;

		/* Null types. */
		if (isNull() && v.isNull()) return 0;
		if (isNull() && !v.isNull()) return -1;
		if (!isNull() && v.isNull()) return 1;

		boolean comparable = true;
		if (isBoolean() && !v.isBoolean()) comparable = false;
		if (isNumber() && !v.isNumber()) comparable = false;
		if (isString() && !v.isString()) comparable = false;
		if (isDate() && !v.isDate()) comparable = false;
		if (isTime() && !v.isTime()) comparable = false;
		if (isTimestamp() && !v.isTimestamp()) comparable = false;
		if (isBinary() && !v.isBinary()) comparable = false;
		if (isDocument() && !v.isDocument()) comparable = false;
		if (isList() && !v.isList()) comparable = false;
		if (!comparable) {
			throw new UnsupportedOperationException("Not comparable type: " + obj.getClass().getName());
		}

		if (isBoolean()) return Boolean.compare(getBoolean(), v.getBoolean());
		if (isNumber()) return getDecimal().compareTo(v.getDecimal());
		if (isString()) return getString().compareTo(v.getString());
		if (isDate()) return getDate().compareTo(v.getDate());
		if (isTime()) return getTime().compareTo(v.getTime());
		if (isTimestamp()) return getTimestamp().compareTo(v.getTimestamp());
		if (isBinary()) return Comparators.compare(getBinary(), v.getBinary());
		if (isList()) return getList().compareTo(v.getList());

		return 0;
	}
	/**
	 * Check whether this object is equal to the argument object.
	 * @param obj The object to compare for equality.
	 * @return A boolen indicating whether this object can be considered equal to the argument object.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Value v = (Value) obj;
		if (isNumber() && v.isNumber()) {
			return getNumber().doubleValue() == v.getNumber().doubleValue();
		}
		return Objects.equals(this.value, v.value);
	}
	/**
	 * Return a suitable hash code.
	 * @return A suitable hash code.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(value, type);
	}
	/**
	 * Return a string representing this value content.
	 * @return A string representing this value content.
	 */
	@Override
	public String toString() {
		if (value == null) return "null";
		return value.toString();
	}

	/**
	 * Set any value except numeric values.
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
	 * Initialize the members.
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
