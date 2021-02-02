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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Properties backed by a hash map that seamlessly save/restore themselves to/from a JSON object.
 * Note that in the getters no class validation is done, leaving the validation to a class cast
 * exception.
 *
 * @author Miquel Sas
 */
public class Properties {

	private Map<String, Object> map = new HashMap<>();

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
		valid |= value instanceof JSONArray;
		valid |= value instanceof JSONObject;
		if (!valid) throw new IllegalArgumentException("Invalid value type");

		map.put(key, value);
	}

	/**
	 * @param key The string key.
	 * @return A {@link Boolean} value.
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
	 * @param key The string key.
	 * @return A {@link JSONArray} value.
	 * @throws ClassCastException If the underlying value is not a {@link JSONArray}.
	 */
	public JSONArray getJSONArray(String key) {
		return (JSONArray) map.get(key);
	}
	/**
	 * @param key The string key.
	 * @return A {@link JSONObject} value.
	 * @throws ClassCastException If the underlying value is not a {@link JSONObject}.
	 */
	public JSONObject getJSONObject(String key) {
		return (JSONObject) map.get(key);
	}

	/**
	 * Put a key/value pair where the value is a boolean.
	 *
	 * @param key   String key.
	 * @param value A double.
	 */
	public void putBoolean(String key, Boolean value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a big decimal.
	 *
	 * @param key   String key.
	 * @param value A big decimal.
	 */
	public void putBigDecimal(String key, BigDecimal value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a big integer.
	 *
	 * @param key   String key.
	 * @param value A big integer.
	 */
	public void putBigInteger(String key, BigInteger value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a double.
	 *
	 * @param key   String key.
	 * @param value A double.
	 */
	public void putDouble(String key, Double value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is an integer.
	 *
	 * @param key   String key.
	 * @param value An integer.
	 */
	public void putInteger(String key, Integer value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a long.
	 *
	 * @param key   String key.
	 * @param value A long.
	 */
	public void putLong(String key, Long value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a local date.
	 *
	 * @param key   String key.
	 * @param value A local date.
	 */
	public void putLocalDate(String key, LocalDate value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a local date-time.
	 *
	 * @param key   String key.
	 * @param value A local date-time.
	 */
	public void putLocalDateTime(String key, LocalDateTime value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a local time.
	 *
	 * @param key   String key.
	 * @param value A local time.
	 */
	public void putLocalTime(String key, LocalTime value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a string.
	 *
	 * @param key   String key.
	 * @param value A string.
	 */
	public void putString(String key, String value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a JSON array.
	 *
	 * @param key   String key.
	 * @param value A JSON array.
	 */
	public void putJSONArray(String key, JSONArray value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a JSON object.
	 *
	 * @param key   String key.
	 * @param value A JSON object.
	 */
	public void putJSONObject(String key, JSONObject value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a double vector.
	 *
	 * @param key   String key.
	 * @param value A double vector.
	 */
	public void putDoubleVector(String key, double[] value) {
		put(key, value);
	}
	/**
	 * Put a key/value pair where the value is a double matrix.
	 *
	 * @param key   String key.
	 * @param value A double matrix.
	 */
	public void putDoubleMatrix(String key, double[][] value) {
		put(key, value);
	}

	private Integer getInteger(JSONObject property, String key) {
		Integer value = null;
		try {
			value = property.getInt(key);
		} catch (JSONException ignore) {}
		return value;
	}
	private String getString(JSONObject property, String key) {
		String value = null;
		try {
			value = property.getString(key);
		} catch (JSONException ignore) {}
		return value;
	}
	private Object getObject(JSONObject property) {
		Object object = null;
		try {
			object = property.get("value");
		} catch (JSONException ignore) {}
		return object;
	}
	private LocalDate parseLocalDate(String s) {
		LocalDate value = null;
		try {
			value = LocalDate.parse(s);
		} catch (DateTimeParseException ignore) {}
		return value;
	}
	private LocalDateTime parseLocalDateTime(String s) {
		LocalDateTime value = null;
		try {
			value = LocalDateTime.parse(s);
		} catch (DateTimeParseException ignore) {}
		return value;
	}
	private LocalTime parseLocalTime(String s) {
		LocalTime value = null;
		try {
			value = LocalTime.parse(s);
		} catch (DateTimeParseException ignore) {}
		return value;
	}

	/**
	 * @param src The source JSONObject from which the properties will be read. Only entries that
	 *            conform with the format used to save this properties will be considered.
	 */
	public void fromJSON(JSONObject src) {

		List<String> types = new ArrayList<>();
		types.add("Boolean");
		types.add("BigDecimal");
		types.add("BigInteger");
		types.add("Double");
		types.add("Integer");
		types.add("Long");
		types.add("LocalDate");
		types.add("LocalDateTime");
		types.add("LocalTime");
		types.add("String");
		types.add("JSONArray");
		types.add("JSONObject");
		types.add("double[]");
		types.add("double[][]");

		for (String key : src.keySet()) {
			Object entry = src.get(key);
			if (!(entry instanceof JSONObject)) continue;
			JSONObject property = (JSONObject) entry;

			String type = getString(property, "type");
			if (type == null) continue;
			if (!types.contains(type)) continue;

			Object object = getObject(property);
			if (object == null) continue;

			if (type.equals("Boolean")) {
				if (!(object instanceof Boolean)) continue;
				Boolean value = (Boolean) object;
				putBoolean(key, value);
				continue;
			}
			if (type.equals("BigDecimal")) {
				if (!(object instanceof BigDecimal)) continue;
				Integer scale = getInteger(property, "scale");
				if (scale == null) continue;
				BigDecimal value = ((BigDecimal) object).setScale(scale, RoundingMode.HALF_UP);
				putBigDecimal(key, value);
				continue;
			}
			if (type.equals("BigInteger")) {
				if (!(object instanceof BigInteger)) continue;
				BigInteger value = (BigInteger) object;
				putBigInteger(key, value);
				continue;
			}
			if (type.equals("Double")) {
				if (!(object instanceof Double)) continue;
				Double value = (Double) object;
				putDouble(key, value);
				continue;
			}
			if (type.equals("Integer")) {
				if (!(object instanceof Integer)) continue;
				Integer value = (Integer) object;
				putInteger(key, value);
				continue;
			}
			if (type.equals("Long")) {
				if (!(object instanceof Long)) continue;
				Long value = (Long) object;
				putLong(key, value);
				continue;
			}
			if (type.equals("LocalDate")) {
				if (!(object instanceof String)) continue;
				LocalDate value = parseLocalDate((String) object);
				if (value == null) continue;
				putLocalDate(key, value);
				continue;
			}
			if (type.equals("LocalDateTime")) {
				if (!(object instanceof String)) continue;
				LocalDateTime value = parseLocalDateTime((String) object);
				if (value == null) continue;
				putLocalDateTime(key, value);
				continue;
			}
			if (type.equals("LocalTime")) {
				if (!(object instanceof String)) continue;
				LocalTime value = parseLocalTime((String) object);
				if (value == null) continue;
				putLocalTime(key, value);
				continue;
			}
			if (type.equals("String")) {
				if (!(object instanceof String)) continue;
				String value = (String) object;
				if (value == null) continue;
				if (value == null) continue;
				putString(key, value);
				continue;
			}
			if (type.equals("JSONArray")) {
				if (!(object instanceof JSONArray)) continue;
				JSONArray value = (JSONArray) object;
				putJSONArray(key, value);
				continue;
			}
			if (type.equals("JSONObject")) {
				if (!(object instanceof JSONObject)) continue;
				JSONObject value = (JSONObject) object;
				putJSONObject(key, value);
				continue;
			}
			if (type.equals("double[]")) {
				if (!(object instanceof double[])) continue;
				double[] value = (double[]) object;
				putDoubleVector(key, value);
				continue;
			}
			if (type.equals("double[][]")) {
				if (!(object instanceof double[][])) continue;
				double[][] value = (double[][]) object;
				putDoubleMatrix(key, value);
				continue;
			}
		}
	}
	/**
	 * @return The properties as a JSONObject.
	 */
	public JSONObject toJSON() {
		JSONObject properties = new JSONObject();
		for (String key : map.keySet()) {
			Object object = map.get(key);
			if (object instanceof Boolean) {
				Boolean value = (Boolean) object;
				JSONObject property = new JSONObject();
				property.put("type", "Boolean");
				property.put("value", value);
				properties.put(key, property);
				continue;
			}
			if (object instanceof BigDecimal) {
				BigDecimal value = (BigDecimal) object;
				JSONObject property = new JSONObject();
				property.put("type", "BigDecimal");
				property.put("scale", value.scale());
				property.put("value", value);
				properties.put(key, property);
				continue;
			}
			if (object instanceof BigInteger) {
				BigInteger value = (BigInteger) object;
				JSONObject property = new JSONObject();
				property.put("type", "BigInteger");
				property.put("value", value);
				properties.put(key, property);
				continue;
			}
			if (object instanceof Double) {
				Double value = (Double) object;
				JSONObject property = new JSONObject();
				property.put("type", "Double");
				property.put("value", value);
				properties.put(key, property);
				continue;
			}
			if (object instanceof Integer) {
				Integer value = (Integer) object;
				JSONObject property = new JSONObject();
				property.put("type", "Integer");
				property.put("value", value);
				properties.put(key, property);
				continue;
			}
			if (object instanceof Long) {
				Long value = (Long) object;
				JSONObject property = new JSONObject();
				property.put("type", "Long");
				property.put("value", value);
				properties.put(key, property);
				continue;
			}
			if (object instanceof LocalDate) {
				LocalDate value = (LocalDate) object;
				JSONObject property = new JSONObject();
				property.put("type", "LocalDate");
				property.put("value", value.toString());
				properties.put(key, property);
				continue;
			}
			if (object instanceof LocalDateTime) {
				LocalDateTime value = (LocalDateTime) object;
				JSONObject property = new JSONObject();
				property.put("type", "LocalDateTime");
				property.put("value", value.toString());
				properties.put(key, property);
				continue;
			}
			if (object instanceof LocalTime) {
				LocalTime value = (LocalTime) object;
				JSONObject property = new JSONObject();
				property.put("type", "LocalTime");
				property.put("value", value.toString());
				properties.put(key, property);
				continue;
			}
			if (object instanceof String) {
				String value = (String) object;
				JSONObject property = new JSONObject();
				property.put("type", "String");
				property.put("value", value.toString());
				properties.put(key, property);
				continue;
			}
			if (object instanceof double[]) {
				double[] value = (double[]) object;
				JSONObject property = new JSONObject();
				property.put("type", "double[]");
				property.put("value", value);
				properties.put(key, property);
				continue;
			}
			if (object instanceof double[][]) {
				double[][] value = (double[][]) object;
				JSONObject property = new JSONObject();
				property.put("type", "double[][]");
				property.put("value", value);
				properties.put(key, property);
				continue;
			}
			if (object instanceof JSONArray) {
				JSONArray jsa = (JSONArray) object;
				JSONObject property = new JSONObject();
				property.put("type", "JSONArray");
				property.put("value", jsa);
				properties.put(key, property);
				continue;
			}
			if (object instanceof JSONObject) {
				JSONObject jso = (JSONObject) object;
				JSONObject property = new JSONObject();
				property.put("type", "JSONObject");
				property.put("value", jso);
				properties.put(key, property);
				continue;
			}
			throw new IllegalStateException("Invalid value type for key : " + key);
		}
		return properties;
	}
}
