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

package com.mlt.db.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;

/**
 * JSONObject wrapper to conform with a more strict typing.
 * @author Miquel Sas
 */
public class JSONDocument {

	/**
	 * Internal JSONObject.
	 */
	JSONObject jsonObject;

	/**
	 * Construct an empty JSONDocument.
	 */
	public JSONDocument() {
		jsonObject = new JSONObject();
	}
	/**
	 * Package private constructor assigning the internal {@link JSONObject}
	 * @param jsonObject The internal JSONObject.
	 */
	JSONDocument(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	/**
	 * Check whether this JSONDocument contains the key.
	 * @param key The key to check.
	 * @return A boolean indicating whether the key is contained.
	 */
	public boolean containsKey(String key) {
		return jsonObject.has(key);
	}
	/**
	 * Returns an unmodifiable collection with this object keys.
	 * @return A collection with the keys.
	 */
	public Collection<String> keys() {
		return Collections.unmodifiableCollection(jsonObject.keySet());
	}

	/**
	 * @param key The key or field name.
	 * @return A boolean value.
	 * @throws JSONException If the field is not a boolean.
	 */
	public Boolean getBoolean(String key) throws JSONException {
		return jsonObject.getBoolean(key);
	}

	/**
	 * @param key The key or field name.
	 * @return A decimal value.
	 * @throws JSONException If the field is not a double.
	 */
	public BigDecimal getDecimal(String key) throws JSONException {
		return BigDecimal.valueOf(getDouble(key));
	}
	/**
	 * @param key      The key or field name.
	 * @param decimals The number of decimal places.
	 * @return A decimal value.
	 * @throws JSONException If the field is not a double.
	 */
	public BigDecimal getDecimal(String key, int decimals) throws JSONException {
		return getDecimal(key).setScale(decimals, RoundingMode.HALF_UP);
	}
	/**
	 * @param key The key or field name.
	 * @return A double value.
	 * @throws JSONException If the field is not a double.
	 */
	public Double getDouble(String key) throws JSONException {
		return jsonObject.getDouble(key);
	}
	/**
	 * @param key The key or field name.
	 * @return An integer value.
	 * @throws JSONException If the field is not an integer.
	 */
	public Integer getInteger(String key) throws JSONException {
		return jsonObject.getInt(key);
	}
	/**
	 * @param key The key or field name.
	 * @return A long value.
	 * @throws JSONException If the field is not a long.
	 */
	public Long getLong(String key) throws JSONException {
		return jsonObject.getLong(key);
	}

	/**
	 * @param key The key or field name.
	 * @return The local date.
	 * @throws JSONException If the field is not a date.
	 */
	public LocalDate getDate(String key) throws JSONException {
		try {
			return LocalDate.parse(jsonObject.getString(key));
		} catch (DateTimeException exc) {
			throw new JSONException(exc);
		}
	}
	/**
	 * @param key The key or field name.
	 * @return The local time.
	 * @throws JSONException If the field is not a time.
	 */
	public LocalTime getTime(String key) throws JSONException {
		try {
			return LocalTime.parse(jsonObject.getString(key));
		} catch (DateTimeException exc) {
			throw new JSONException(exc);
		}
	}
	/**
	 * @param key The key or field name.
	 * @return The local timestamp.
	 * @throws JSONException If the field is not a timestamp.
	 */
	public LocalDateTime getTimestamp(String key) throws JSONException {
		try {
			return LocalDateTime.parse(jsonObject.getString(key));
		} catch (DateTimeException exc) {
			throw new JSONException(exc);
		}
	}

	/**
	 * @param key The key or field name.
	 * @return The string.
	 * @throws JSONException If the field is not a string.
	 */
	public String getString(String key) throws JSONException {
		return jsonObject.getString(key);
	}

	/**
	 * @param key The key or field name.
	 * @return The byte array.
	 * @throws JSONException If the field is not a byte array.
	 */
	public byte[] getBinary(String key) throws JSONException {
		return (byte[]) jsonObject.get(key);
	}

	/**
	 * @param key The key or field name.
	 * @return The document.
	 * @throws JSONException If the field is not a document.
	 */
	public JSONDocument getDocument(String key) throws JSONException {
		JSONObject o = (JSONObject) jsonObject.get(key);
		return new JSONDocument(o);
	}
	/**
	 * @param key The key or field name.
	 * @return The list.
	 * @throws JSONException If the field is not a list.
	 */
	public JSONList getList(String key) {
		JSONArray a = (JSONArray) jsonObject.get(key);
		return new JSONList(a);
	}

	/**
	 * @param key   The key or field name.
	 * @param value A boolean value.
	 */
	public void setBoolean(String key, boolean value) {
		jsonObject.put(key, value);
	}

	/**
	 * @param key   The key or field name.
	 * @param value A decimal value.
	 */
	public void setDecimal(String key, BigDecimal value) {
		jsonObject.put(key, value);
	}
	/**
	 * @param key   The key or field name.
	 * @param value A double value.
	 */
	public void setDouble(String key, Double value) {
		jsonObject.put(key, value);
	}
	/**
	 * @param key   The key or field name.
	 * @param value An integer value.
	 */
	public void setInteger(String key, Integer value) {
		jsonObject.put(key, value);
	}
	/**
	 * @param key   The key or field name.
	 * @param value A long value.
	 */
	public void setLong(String key, Long value) {
		jsonObject.put(key, value);
	}

	/**
	 * @param key   The key or field name.
	 * @param value A date value.
	 */
	public void setDate(String key, LocalDate value) {
		jsonObject.put(key, value);
	}
	/**
	 * @param key   The key or field name.
	 * @param value A time value.
	 */
	public void setTime(String key, LocalTime value) {
		jsonObject.put(key, value);
	}
	/**
	 * @param key   The key or field name.
	 * @param value A timestamp value.
	 */
	public void setTimestamp(String key, LocalDateTime value) {
		jsonObject.put(key, value);
	}

	/**
	 * @param key   The key or field name.
	 * @param value A string value.
	 */
	public void setString(String key, String value) {
		jsonObject.put(key, value);
	}

	/**
	 * @param key   The key or field name.
	 * @param value A binary (byte[]) value.
	 */
	public void setBinary(String key, byte[] value) {
		jsonObject.put(key, value);
	}

	/**
	 * @param key   The key or field name.
	 * @param value A JSONDocument value.
	 */
	public void setDocument(String key, JSONDocument value) {
		jsonObject.put(key, value.jsonObject);
	}
	/**
	 * @param key   The key or field name.
	 * @param value A JSONList value.
	 */
	public void setList(String key, JSONList value) {
		jsonObject.put(key, value.jsonArray);
	}

	/**
	 * @return A string representation.
	 */
	@Override
	public String toString() {
		return jsonObject.toString();
	}

	public String toString(int indentFactor) throws JSONException {
		return jsonObject.toString(indentFactor);
	}
}
