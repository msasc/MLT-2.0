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
 * A value for the supported types.
 *
 * @author Miquel Sas
 */
public class Value implements Comparable<Object> {

	/** The value type. */
	private Types type = null;
	/** The value itself. */
	private Object value = null;
	/** Modified flag. */
	private boolean modified = false;
	/** Decimals. */
	private int decimals = -1;

	/**
	 * @param value A boolean.
	 */
	public Value(Boolean value) {
		this.value = value;
		this.type = Types.BOOLEAN;
	}

	/**
	 * @param value A big decimal that can not be null to ensure the number of decimal places, read
	 *              from the scale.
	 */
	public Value(BigDecimal value) {
		if (value == null) throw new NullPointerException("Null big decimal");
		this.type = Types.DECIMAL;
		this.decimals = value.scale();
		this.value = value;
	}

	/**
	 * @param value    A big decimal that can be null.
	 * @param decimals The scale or number of decimal places.
	 */
	public Value(BigDecimal value, int decimals) {
		if (value != null) this.value = value.setScale(decimals, RoundingMode.HALF_UP);
		this.type = Types.DECIMAL;
		this.decimals = decimals;
	}

	/**
	 * @param value A double.
	 */
	public Value(Double value) {
		this.value = value;
		this.type = Types.DOUBLE;
	}

	/**
	 * @param value An integer.
	 */
	public Value(Integer value) {
		this.value = value;
		this.type = Types.INTEGER;
	}

	/**
	 * @param value A long.
	 */
	public Value(Long value) {
		this.value = value;
		this.type = Types.LONG;
	}

	/**
	 * @param value A string.
	 */
	public Value(String value) {
		this.value = value;
		this.type = Types.STRING;
	}

	/**
	 * @param value A date.
	 */
	public Value(LocalDate value) {
		this.value = value;
		this.type = Types.DATE;
	}

	/**
	 * @param value A timestamp.
	 */
	public Value(LocalDateTime value) {
		this.value = value;
		this.type = Types.DATETIME;
	}

	/**
	 * @param value A time.
	 */
	public Value(LocalTime value) {
		this.value = value;
		this.type = Types.TIME;
	}

	/**
	 * @param value A byte array.
	 */
	public Value(byte[] value) {
		this.value = value;
		this.type = Types.BYTEARRAY;
	}

	/**
	 * @param value A value that can not be null.
	 */
	public Value(Value value) {
		if (value == null) throw new NullPointerException();
		this.value = value.value;
		this.type = value.type;
		this.decimals = value.decimals;
		this.modified = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Object o) {
		return 0;
	}

	/**
	 * @return A boolean.
	 */
	public Boolean getBoolean() {
		if (isBoolean()) {
			if (isNull()) return false;
			return ((Boolean) value);
		}
		throw new UnsupportedOperationException("Value " + value + " is not a boolean");
	}

	/**
	 * @return A BigDecimal.
	 */
	public BigDecimal getBigDecimal() {
		if (isDecimal()) return (BigDecimal) value;
		if (isNumber()) {
			if (isDouble()) return BigDecimal.valueOf(getDouble());
			return BigDecimal.valueOf(getLong());
		}
		throw new UnsupportedOperationException("Value " + value + " is not a number");
	}

	/**
	 * @return A double.
	 */
	public Double getDouble() {
		if (isNumber()) {
			if (isNull()) return Double.valueOf(0);
			return ((Number) value).doubleValue();
		}
		throw new UnsupportedOperationException("Value " + value + " is not a number");
	}
	/**
	 * @return An integer.
	 */
	public Integer getInteger() {
		if (isNumber()) {
			if (isNull()) return Integer.valueOf(0);
			return ((Number) value).intValue();
		}
		throw new UnsupportedOperationException("Value " + value + " is not a number");
	}
	/**
	 * @return A long.
	 */
	public Long getLong() {
		if (isNumber()) {
			if (isNull()) return Long.valueOf(0);
			return ((Number) value).longValue();
		}
		throw new UnsupportedOperationException("Value " + value + " is not a number");
	}
	/**
	 * @return The number.
	 */
	public Number getNumber() {
		if (isNumber()) return (Number) value;
		throw new UnsupportedOperationException("Value " + value + " is not a number");
	}
	/**
	 * @return A String.
	 */
	public String getString() {
		if (isString()) return (String) value;
		throw new UnsupportedOperationException("Value " + value + " is not a string");
	}
	/**
	 * @return A byte array.
	 */
	public byte[] getByteArray() {
		if (isByteArray()) return (byte[]) value;
		throw new UnsupportedOperationException("Value " + value + " is not a byte array");
	}
	/**
	 * @return A Date.
	 */
	public LocalDate getDate() {
		if (isNull()) return null;
		if (isDate()) return (LocalDate) value;
		if (isDateTime()) return getDateTime().toLocalDate();
		throw new UnsupportedOperationException("Value " + value + " is not a date");
	}
	/**
	 * @return A Timestamp.
	 */
	public LocalDateTime getDateTime() {
		if (isNull()) return null;
		if (isDateTime()) return (LocalDateTime) value;
		throw new UnsupportedOperationException("Value " + value + " is not a date-time");
	}
	/**
	 * @return A Time.
	 */
	public LocalTime getTime() {
		if (isNull()) return null;
		if (isTime()) return (LocalTime) value;
		if (isDateTime()) return getDateTime().toLocalTime();
		throw new UnsupportedOperationException("Value " + value + " is not a time");
	}
	public boolean isBlank() { return isEmpty() || (isString() && getString().trim().length() == 0); }
	public boolean isEmpty() {
		if (isNull()) return true;
		if (isString() && getString().length() == 0) return true;
		return isNumber() && getDouble() == 0;
	}
	public boolean isModified() { return modified; }
	public boolean isNull() { return (value == null); }

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

	/**
	 * @param value A boolean, can be null.
	 */
	public void setBoolean(Boolean value) {
		if (!isBoolean()) throw new IllegalArgumentException("Value is not a boolean");
		this.value = value;
		this.modified = true;
	}

	/**
	 * @param value A big decimal, can be null.
	 */
	public void setBigDecimal(BigDecimal value) {
		setNumber(value);
	}

	/**
	 * @param value A double, can be null.
	 */
	public void setDouble(Double value) {
		setNumber(value);
	}

	/**
	 * @param value An integer, can be null.
	 */
	public void setInteger(Integer value) {
		setNumber(value);
	}

	/**
	 * @param value A long, can be null.
	 */
	public void setLong(Long value) {
		setNumber(value);
	}

	/**
	 * @param value The number.
	 */
	public void setNumber(Number value) {
		if (!isNumber()) throw new IllegalArgumentException("Value is not a number");
		/* No matter the numeric type, null can be assigned. */
		if (value == null) {
			this.value = null;
		} else if (isDecimal()) {
			if (value instanceof BigDecimal) {
				/* Preserve scale? */
				BigDecimal b = (BigDecimal) value;
				if (b.scale() != decimals) {
					throw new IllegalArgumentException("Invalid value scale");
				}
				this.value = value;
			}
		} else if (isDouble()) {
			this.value = value.doubleValue();
		} else if (isInteger()) {
			this.value = value.intValue();
		} else {
			this.value = value.longValue();
		}
		this.modified = true;
	}

	/**
	 * @param value A date, can be null and if not, can be set as the date part of date-time.
	 */
	public void setDate(LocalDate value) {
		if (!isDate() && !isDateTime()) {
			throw new IllegalArgumentException("Value is not a date or date-time");
		}
		/* No matter date or date-time, null can be assigned. */
		if (value == null) {
			this.value = null;
		} else if (isDate()) {
			this.value = value;
		} else {
			this.value = LocalDateTime.of(value, getDateTime().toLocalTime());
		}
		this.modified = true;
	}

	/**
	 * @param value A date-time.
	 */
	public void setDateTime(LocalDateTime value) {
		if (!isDateTime()) throw new IllegalArgumentException("Value is not a date-time");
		this.value = value;
		this.modified = true;
	}

	/**
	 * @param value A time, can be null and if not, can be set as the time part of date-time.
	 */
	public void setTime(LocalTime value) {
		if (!isTime() && !isDateTime()) {
			throw new IllegalArgumentException("Value is not a time or date-time");
		}
		/* No matter time or date-time, null can be assigned. */
		if (value == null) {
			this.value = null;
		} else if (isTime()) {
			this.value = value;
		} else {
			this.value = LocalDateTime.of(getDateTime().toLocalDate(), value);
		}
		this.modified = true;
	}

	/**
	 * @param value A string.
	 */
	public void setString(String value) {
		if (!isString()) throw new IllegalArgumentException("Value is not a string");
		this.value = value;
		this.modified = true;
	}

	/**
	 * @param value A byte array.
	 */
	public void setByteArray(byte[] value) {
		if (!isByteArray()) throw new IllegalArgumentException("Value is not a byte array");
		this.value = value;
		this.modified = true;
	}

	/**
	 * Force the modified flag.
	 * @param modified A boolean.
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}
}
