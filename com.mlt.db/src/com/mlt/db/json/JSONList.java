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

/**
 * JSONArray wrapper to conform with a more strict typing.
 * @author Miquel Sas
 */
public class JSONList {

	/**
	 * Internal JSONArray.
	 */
	JSONArray jsonArray;

	/**
	 * Construct an empty JSONList.
	 */
	public JSONList() {
		jsonArray = new JSONArray();
	}
	/**
	 * Copy constructor.
	 * @param list The list to copy values.
	 */
	public JSONList(JSONList list) {
		jsonArray = new JSONArray(list.jsonArray);
	}
	/**
	 * Constructor used by {@link JSONDocument}
	 * @param array The internal JSONArray.
	 */
	JSONList(JSONArray array) {
		this.jsonArray = array;
	}

	/**
	 * @param value A boolean value.
	 */
	public void addBoolean(boolean value) {
		jsonArray.put(value);
	}
	/**
	 * @param index The element index.
	 * @param value A boolean value.
	 */
	public void addBoolean(int index, boolean value) {
		jsonArray.put(index, value);
	}

	/**
	 * @param value A decimal value.
	 */
	public void addDecimal(BigDecimal value) {
		jsonArray.put(value);
	}
	/**
	 * @param index The element index.
	 * @param value A decimal value.
	 */
	public void addDecimal(int index, BigDecimal value) {
		jsonArray.put(index, value);
	}
	/**
	 * @param value A double value.
	 */
	public void addDouble(Double value) {
		jsonArray.put(value);
	}
	/**
	 * @param index The element index.
	 * @param value A double value.
	 */
	public void addDouble(int index, Double value) {
		jsonArray.put(index, value);
	}
	/**
	 * @param value An integer value.
	 */
	public void addInteger(Integer value) {
		jsonArray.put(value);
	}
	/**
	 * @param index The element index.
	 * @param value An integer value.
	 */
	public void addInteger(int index, Integer value) {
		jsonArray.put(index, value);
	}
	/**
	 * @param value A long value.
	 */
	public void addLong(Long value) {
		jsonArray.put(value);
	}
	/**
	 * @param index The element index.
	 * @param value A long value.
	 */
	public void addLong(int index, Long value) {
		jsonArray.put(index, value);
	}

	/**
	 * @param value A date value.
	 */
	public void addDate(LocalDate value) {
		jsonArray.put(value);
	}
	/**
	 * @param index The element index.
	 * @param value A date value.
	 */
	public void addDate(int index, LocalDate value) {
		jsonArray.put(index, value);
	}
	/**
	 * @param value A time value.
	 */
	public void addTime(LocalTime value) {
		jsonArray.put(value);
	}
	/**
	 * @param index The element index.
	 * @param value A time value.
	 */
	public void addTime(int index, LocalTime value) {
		jsonArray.put(index, value);
	}
	/**
	 * @param value A timestamp value.
	 */
	public void addTimestamp(LocalDateTime value) {
		jsonArray.put(value);
	}
	/**
	 * @param index The element index.
	 * @param value A timestamp value.
	 */
	public void addTimestamp(int index, LocalDateTime value) {
		jsonArray.put(index, value);
	}

	/**
	 * @param value A string value.
	 */
	public void addString(String value) {
		jsonArray.put(value);
	}
	/**
	 * @param index The element index.
	 * @param value A string value.
	 */
	public void addString(int index, String value) {
		jsonArray.put(index, value);
	}

	/**
	 * @param value A binary (byte[]) value.
	 */
	public void addBinary(byte[] value) {
		jsonArray.put(value);
	}
	/**
	 * @param index The element index.
	 * @param value A binary (byte[]) value.
	 */
	public void addBinary(int index, byte[] value) {
		jsonArray.put(index, value);
	}

	/**
	 * @param value A JSONDocument value.
	 */
	public void addDocument(JSONDocument value) {
		jsonArray.put(value.jsonObject);
	}
	/**
	 * @param index The element index.
	 * @param value A JSONDocument value.
	 */
	public void addDocument(int index, JSONDocument value) {
		jsonArray.put(index, value.jsonObject);
	}
	/**
	 * @param value A JSONList value.
	 */
	public void addList(JSONList value) {
		jsonArray.put(value.jsonArray);
	}
	/**
	 * @param index The element index.
	 * @param value A JSONList value.
	 */
	public void addList(int index, JSONList value) {
		jsonArray.put(index, value.jsonArray);
	}

	/**
	 * @param index The element index.
	 * @return A boolean value.
	 * @throws JSONException If the field is not a boolean.
	 */
	public Boolean getBoolean(int index) throws JSONException {
		return jsonArray.getBoolean(index);
	}

	/**
	 * @param index The element index.
	 * @return A decimal value.
	 * @throws JSONException If the field is not a double.
	 */
	public BigDecimal getDecimal(int index) throws JSONException {
		return BigDecimal.valueOf(getDouble(index));
	}
	/**
	 * @param index The element index.
	 * @param decimals The number of decimal places.
	 * @return A decimal value.
	 * @throws JSONException If the field is not a double.
	 */
	public BigDecimal getDecimal(int index, int decimals) throws JSONException {
		return getDecimal(index).setScale(decimals, RoundingMode.HALF_UP);
	}
	/**
	 * @param index The element index.
	 * @return A double value.
	 * @throws JSONException If the field is not a double.
	 */
	public Double getDouble(int index) throws JSONException {
		return jsonArray.getDouble(index);
	}
	/**
	 * @param index The element index.
	 * @return An integer value.
	 * @throws JSONException If the field is not an integer.
	 */
	public Integer getInteger(int index) throws JSONException {
		return jsonArray.getInt(index);
	}
	/**
	 * @param index The element index.
	 * @return A long value.
	 * @throws JSONException If the field is not a long.
	 */
	public Long getLong(int index) throws JSONException {
		return jsonArray.getLong(index);
	}

	/**
	 * @param index The element index.
	 * @return The local date.
	 * @throws JSONException If the field is not a date.
	 */
	public LocalDate getDate(int index) throws JSONException {
		try {
			return LocalDate.parse(jsonArray.getString(index));
		} catch (DateTimeException exc) {
			throw new JSONException(exc);
		}
	}
	/**
	 * @param index The element index.
	 * @return The local time.
	 * @throws JSONException If the field is not a time.
	 */
	public LocalTime getTime(int index) throws JSONException {
		try {
			return LocalTime.parse(jsonArray.getString(index));
		} catch (DateTimeException exc) {
			throw new JSONException(exc);
		}
	}
	/**
	 * @param index The element index.
	 * @return The local timestamp.
	 * @throws JSONException If the field is not a timestamp.
	 */
	public LocalDateTime getTimestamp(int index) throws JSONException {
		try {
			return LocalDateTime.parse(jsonArray.getString(index));
		} catch (DateTimeException exc) {
			throw new JSONException(exc);
		}
	}

	/**
	 * @param index The element index.
	 * @return The string.
	 * @throws JSONException If the field is not a string.
	 */
	public String getString(int index) throws JSONException {
		return jsonArray.getString(index);
	}

	/**
	 * @param index The element index.
	 * @return The byte array.
	 * @throws JSONException If the field is not a byte array.
	 */
	public byte[] getBinary(int index) throws JSONException {
		return (byte[]) jsonArray.get(index);
	}

	/**
	 * @param index The element index.
	 * @return The document.
	 * @throws JSONException If the field is not a document.
	 */
	public JSONDocument getDocument(int index) throws JSONException {
		JSONObject o = (JSONObject) jsonArray.get(index);
		return new JSONDocument(o);
	}
	/**
	 * @param index The element index.
	 * @return The list.
	 * @throws JSONException If the field is not a list.
	 */
	public JSONList getList(int index) {
		JSONArray a = (JSONArray) jsonArray.get(index);
		return new JSONList(a);
	}

	/**
	 * @param index The index to remove.
	 */
	public void remove(int index) {
		jsonArray.remove(index);
	}

	/**
	 * @return A string representation.
	 */
	@Override
	public String toString() {
		return jsonArray.toString();
	}
}
