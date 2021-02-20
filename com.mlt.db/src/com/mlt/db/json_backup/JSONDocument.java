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

package com.mlt.db.json_backup;

import com.mlt.db.Value;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
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
	 * Copy constructor.
	 * @param doc The source document.
	 */
	public JSONDocument(JSONDocument doc) {
		this();
		append(doc);
	}
	/**
	 * Package private constructor assigning the internal {@link JSONObject}
	 * @param jsonObject The internal JSONObject.
	 */
	JSONDocument(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	/**
	 * Append the argument document.
	 * @param doc The JSONDocument to append.
	 */
	public void append(JSONDocument doc) {
		for (String key : doc.keys()) {
			Object value = doc.jsonObject.get(key);
			jsonObject.put(key, value);
		}
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
	 * Returns the Boolean value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The Boolean value if exists, null otherwise.
	 */
	public Boolean getBoolean(String key) {
		return getBoolean(key, null);
	}
	/**
	 * Returns the Boolean value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default Boolean value.
	 * @return The Boolean value if exists, the default value otherwise.
	 */
	public Boolean getBoolean(String key, Boolean defValue) {
		try {
			return jsonObject.getBoolean(key);
		} catch (Exception exc) { return defValue; }
	}

	/**
	 * Returns the BigDecimal value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The BigDecimal value if exists, null otherwise.
	 */
	public BigDecimal getDecimal(String key) {
		return getDecimal(key, null);
	}
	/**
	 * Returns the BigDecimal value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default BigDecimal value.
	 * @return The BigDecimal value if exists, the default value otherwise.
	 */
	public BigDecimal getDecimal(String key, BigDecimal defValue) {
		try {
			return jsonObject.getBigDecimal(key);
		} catch (Exception exc) { return defValue; }
	}
	/**
	 * Returns the Double value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The Double value if exists, null otherwise.
	 */
	public Double getDouble(String key) {
		return getDouble(key, null);
	}
	/**
	 * Returns the Double value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default Double value.
	 * @return The Double value if exists, the default value otherwise.
	 */
	public Double getDouble(String key, Double defValue) {
		try {
			return jsonObject.getDouble(key);
		} catch (Exception exc) { return defValue; }
	}
	/**
	 * Returns the Integer value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The Integer value if exists, null otherwise.
	 */
	public Integer getInteger(String key) {
		return getInteger(key, null);
	}
	/**
	 * Returns the Integer value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default Integer value.
	 * @return The Integer value if exists, the default value otherwise.
	 */
	public Integer getInteger(String key, Integer defValue) {
		try {
			return jsonObject.getInt(key);
		} catch (Exception exc) { return defValue; }
	}
	/**
	 * Returns the Long value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The Long value if exists, null otherwise.
	 */
	public Long getLong(String key) {
		return getLong(key, null);
	}
	/**
	 * Returns the Long value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default Long value.
	 * @return The Long value if exists, the default value otherwise.
	 */
	public Long getLong(String key, Long defValue) {
		try {
			return jsonObject.getLong(key);
		} catch (Exception exc) { return defValue; }
	}

	/**
	 * Returns the LocalDate value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The LocalDate value if exists, null otherwise.
	 */
	public LocalDate getDate(String key) {
		return getDate(key, null);
	}
	/**
	 * Returns the LocalDate value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default LocalDate value.
	 * @return The LocalDate value if exists, the default value otherwise.
	 */
	public LocalDate getDate(String key, LocalDate defVal) {
		try {
			return LocalDate.parse(jsonObject.getString(key));
		} catch (Exception exc) { return defVal; }
	}
	/**
	 * Returns the LocalTime value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The LocalTime value if exists, null otherwise.
	 */
	public LocalTime getTime(String key) {
		return getTime(key, null);
	}
	/**
	 * Returns the LocalTime value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default LocalTime value.
	 * @return The LocalTime value if exists, the default value otherwise.
	 */
	public LocalTime getTime(String key, LocalTime defValue) {
		try {
			return LocalTime.parse(jsonObject.getString(key));
		} catch (Exception exc) { return defValue; }
	}
	/**
	 * Returns the LocalDateTime value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The LocalDateTime value if exists, null otherwise.
	 */
	public LocalDateTime getTimestamp(String key) {
		return getTimestamp(key, null);
	}
	/**
	 * Returns the LocalDateTime value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default LocalDateTime value.
	 * @return The LocalDateTime value if exists, the default value otherwise.
	 */
	public LocalDateTime getTimestamp(String key, LocalDateTime defValue) {
		try {
			return LocalDateTime.parse(jsonObject.getString(key));
		} catch (Exception exc) { return defValue; }
	}

	/**
	 * Returns the String value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The String value if exists, null otherwise.
	 */
	public String getString(String key) {
		return getString(key, null);
	}
	/**
	 * Returns the String value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default String value.
	 * @return The String value if exists, the default value otherwise.
	 */
	public String getString(String key, String defValue) {
		try {
			return jsonObject.getString(key);
		} catch (Exception exc) { return defValue; }
	}

	/**
	 * Returns the binary value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The binary value if exists, null otherwise.
	 */
	public byte[] getBinary(String key) {
		return getBinary(key, null);
	}
	/**
	 * Returns the String value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default String value.
	 * @return The String value if exists, the default value otherwise.
	 */
	public byte[] getBinary(String key, byte[] defValue) {
		try {
			return (byte[]) jsonObject.get(key);
		} catch (Exception exc) { return defValue; }
	}

	/**
	 * Returns the JSONDocument value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The JSONDocument value if exists, null otherwise.
	 */
	public JSONDocument getDocument(String key) {
		return getDocument(key, null);
	}
	/**
	 * Returns the JSONDocument value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default JSONDocument value.
	 * @return The JSONDocument value if exists, the default value otherwise.
	 */
	public JSONDocument getDocument(String key, JSONDocument defValue) {
		try {
			JSONObject o = (JSONObject) jsonObject.get(key);
			return new JSONDocument(o);
		} catch (Exception exc) { return defValue; }
	}
	/**
	 * Returns the JSONList value if exists, null otherwise.
	 * @param key The key or field name.
	 * @return The JSONList value if exists, null otherwise.
	 */
	public JSONList getList(String key) {
		return getList(key, null);
	}
	/**
	 * Returns the JSONList value if exists, the default value otherwise.
	 * @param key      The key or field name.
	 * @param defValue The default JSONList value.
	 * @return The JSONList value if exists, the default value otherwise.
	 */
	public JSONList getList(String key, JSONList defValue) {
		try {
			JSONArray a = (JSONArray) jsonObject.get(key);
			return new JSONList(a);
		} catch (Exception exc) { return defValue; }
	}

	/**
	 * Set a boolean value.
	 * @param key   The key or field name.
	 * @param value A boolean value.
	 */
	public void setBoolean(String key, Boolean value) {
		jsonObject.put(key, value);
	}

	/**
	 * Set a decimal value.
	 * @param key   The key or field name.
	 * @param value A decimal value.
	 */
	public void setDecimal(String key, BigDecimal value) {
		jsonObject.put(key, value);
	}
	/**
	 * Set a double value.
	 * @param key   The key or field name.
	 * @param value A double value.
	 */
	public void setDouble(String key, Double value) {
		jsonObject.put(key, value);
	}
	/**
	 * Set an integer value.
	 * @param key   The key or field name.
	 * @param value An integer value.
	 */
	public void setInteger(String key, Integer value) {
		jsonObject.put(key, value);
	}
	/**
	 * Set a long value.
	 * @param key   The key or field name.
	 * @param value A long value.
	 */
	public void setLong(String key, Long value) {
		jsonObject.put(key, value);
	}

	/**
	 * Set a date value.
	 * @param key   The key or field name.
	 * @param value A date value.
	 */
	public void setDate(String key, LocalDate value) {
		jsonObject.put(key, value);
	}
	/**
	 * Set a time value.
	 * @param key   The key or field name.
	 * @param value A time value.
	 */
	public void setTime(String key, LocalTime value) {
		jsonObject.put(key, value);
	}
	/**
	 * Set a timestamp value.
	 * @param key   The key or field name.
	 * @param value A timestamp value.
	 */
	public void setTimestamp(String key, LocalDateTime value) {
		jsonObject.put(key, value);
	}

	/**
	 * Set a string value.
	 * @param key   The key or field name.
	 * @param value A string value.
	 */
	public void setString(String key, String value) {
		jsonObject.put(key, value);
	}

	/**
	 * Set a binary value.
	 * @param key   The key or field name.
	 * @param value A binary (byte[]) value.
	 */
	public void setBinary(String key, byte[] value) {
		jsonObject.put(key, value);
	}

	/**
	 * Set a JSONDocument value.
	 * @param key   The key or field name.
	 * @param value A JSONDocument value.
	 */
	public void setDocument(String key, JSONDocument value) {
		jsonObject.put(key, value.jsonObject);
	}
	/**
	 * Set a JSONList value.
	 * @param key   The key or field name.
	 * @param value A JSONList value.
	 */
	public void setList(String key, JSONList value) {
		jsonObject.put(key, value.jsonArray);
	}

	public void setNull(String key) {
		jsonObject.put(key, JSONObject.NULL);
	}

	public void setValue(String key, Value value) {
		if (value == null || value.isNull()) {
			setNull(key);
			return;
		}
		switch (value.getType()) {
		case BOOLEAN:
			setBoolean(key, value.getBoolean());
			break;

		case DECIMAL:
			setDecimal(key, value.getDecimal());
			break;
		case DOUBLE:
			setDouble(key, value.getDouble());
			break;
		case INTEGER:
			setInteger(key, value.getInteger());
			break;
		case LONG:
			setLong(key, value.getLong());
			break;

		case DATE:
			setDate(key, value.getDate());
			break;
		case TIME:
			setTime(key, value.getTime());
			break;
		case TIMESTAMP:
			setTimestamp(key, value.getTimestamp());
			break;

		case STRING:
			setString(key, value.getString());
			break;

		case BINARY:
			setBinary(key, value.getBinary());
			break;

		case DOCUMENT:
			setDocument(key, value.getDocument().toJSONDocument());
			break;
		case LIST:
			setList(key, value.getList().toJSONList());
			break;
		}
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
