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

package com.mlt.common.collections;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Properties backed by a hash map that seamlessly save/restore themselves to/from a JSON object.
 * Note that in the getters no class validation is done, leaving the validation to a class cast
 * exception.
 *
 * @author Miquel Sas
 */
public class Properties {

	/**
	 * Internal map.
	 */
	private Map<String, Object> map = new HashMap<>();

	/**
	 * @param key   The string key.
	 * @param value The value to store.
	 * @throws IllegalArgumentException If the type is not valid.
	 */
	private void put(String key, Object value) {

		if (key == null) throw new NullPointerException();
		if (value == null) throw new NullPointerException();

		boolean valid = false;
		valid |= value instanceof Boolean;
		valid |= value instanceof BigDecimal;
		valid |= value instanceof BigInteger;
		valid |= value instanceof Double;
		valid |= value instanceof Integer;
		valid |= value instanceof Long;
		valid |= value instanceof double[];
		valid |= value instanceof double[][];
		valid |= value instanceof LocalDate;
		valid |= value instanceof LocalDateTime;
		valid |= value instanceof LocalTime;
		valid |= value instanceof String;
		if (!valid) throw new IllegalArgumentException("Invalid value type");

		map.put(key, value);
	}

	/**
	 * @param key The string key.
	 * @return A Boolean value.
	 * @throws ClassCastException If the underlying value is not a {@link Boolean}.
	 */
	public Boolean getBoolean(String key) {
		return (Boolean) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return A {@link BigDecimal} value.
	 * @throws ClassCastException If the underlying value is not a {@link BigDecimal}.
	 */
	public BigDecimal getBigDecimal(String key) {
		return (BigDecimal) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return A {@link BigInteger} value.
	 * @throws ClassCastException If the underlying value is not a {@link BigInteger}.
	 */
	public BigInteger getBigInteger(String key) {
		return (BigInteger) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return A {@link Double} value.
	 * @throws ClassCastException If the underlying value is not a {@link Double}.
	 */
	public Double getDouble(String key) {
		return (Double) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return An {@link Integer} value.
	 * @throws ClassCastException If the underlying value is not a {@link Integer}.
	 */
	public Integer getInteger(String key) {
		return (Integer) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return A {@link Long} value.
	 * @throws ClassCastException If the underlying value is not a {@link Long}.
	 */
	public Long getLong(String key) {
		return (Long) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return A double[] vector value.
	 * @throws ClassCastException If the underlying value is not a double[].
	 */
	public double[] getDoubleVector(String key) {
		return (double[]) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return A double[][] matrix value.
	 * @throws ClassCastException If the underlying value is not a double[][].
	 */
	public double[][] getDoubleMatrix(String key) {
		return (double[][]) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return A {@link LocalDate} value.
	 * @throws ClassCastException If the underlying value is not a {@link LocalDate}.
	 */
	public LocalDate getLocalDate(String key) {
		return (LocalDate) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return A {@link LocalDateTime} value.
	 * @throws ClassCastException If the underlying value is not a {@link LocalDateTime}.
	 */
	public LocalDateTime getLocalDateTime(String key) {
		return (LocalDateTime) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return A {@link LocalTime} value.
	 * @throws ClassCastException If the underlying value is not a {@link LocalTime}.
	 */
	public LocalTime getLocalTime(String key) {
		return (LocalTime) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return A {@link String} value.
	 * @throws ClassCastException If the underlying value is not a {@link String}.
	 */
	public String getString(String key) {
		return (String) map.get(key);
	}

	/**
	 * @param key   String key.
	 * @param value A double.
	 */
	public void putBoolean(String key, Boolean value) {
		put(key, value);
	}
	/**
	 * @param key   String key.
	 * @param value A big decimal.
	 */
	public void putBigDecimal(String key, BigDecimal value) {
		put(key, value);
	}
	/**
	 * @param key   String key.
	 * @param value A big integer.
	 */
	public void putBigInteger(String key, BigInteger value) {
		put(key, value);
	}
	/**
	 * @param key   String key.
	 * @param value A double.
	 */
	public void putDouble(String key, Double value) {
		put(key, value);
	}
	/**
	 * @param key   String key.
	 * @param value An integer.
	 */
	public void putInteger(String key, Integer value) {
		put(key, value);
	}
	/**
	 * @param key   String key.
	 * @param value A long.
	 */
	public void putLong(String key, Long value) {
		put(key, value);
	}
	/**
	 * @param key   String key.
	 * @param value A local date.
	 */
	public void putLocalDate(String key, LocalDate value) {
		put(key, value);
	}
	/**
	 * @param key   String key.
	 * @param value A local date-time.
	 */
	public void putLocalDateTime(String key, LocalDateTime value) {
		put(key, value);
	}
	/**
	 * @param key   String key.
	 * @param value A local time.
	 */
	public void putLocalTime(String key, LocalTime value) {
		put(key, value);
	}
	/**
	 * @param key   String key.
	 * @param value A string.
	 */
	public void putString(String key, String value) {
		put(key, value);
	}
	/**
	 * @param key   String key.
	 * @param value A double vector.
	 */
	public void putDoubleVector(String key, double[] value) {
		put(key, value);
	}
	/**
	 * @param key   String key.
	 * @param value A double matrix.
	 */
	public void putDoubleMatrix(String key, double[][] value) {
		put(key, value);
	}

	/**
	 * @param s The source string.
	 * @return The {@link LocalDate}
	 */
	private LocalDate parseLocalDate(String s) {
		LocalDate value = null;
		try {
			value = LocalDate.parse(s);
		} catch (DateTimeParseException ignore) {}
		return value;
	}
	/**
	 * @param s The source string.
	 * @return The {@link LocalDateTime}
	 */
	private LocalDateTime parseLocalDateTime(String s) {
		LocalDateTime value = null;
		try {
			value = LocalDateTime.parse(s);
		} catch (DateTimeParseException ignore) {}
		return value;
	}
	/**
	 * @param s The source string.
	 * @return The {@link LocalTime}
	 */
	private LocalTime parseLocalTime(String s) {
		LocalTime value = null;
		try {
			value = LocalTime.parse(s);
		} catch (DateTimeParseException ignore) {}
		return value;
	}

}
