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

package com.mlt.common.json;

import com.mlt.common.logging.Logs;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * JSONObject wrapper to conform with a more strict typing.
 *
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
	 * @param key The key or field name.
	 * @return A Boolean or null if the field name is not a boolean.
	 */
	public Boolean getBoolean(String key) {
		try {
			return jsonObject.getBoolean(key);
		} catch (JSONException exc) {
			Logs.catching(exc);
		}
		return null;
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
	public void setBigDecimal(String key, BigDecimal value) {
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
	 * @param value A date-time value.
	 */
	public void setDateTime(String key, LocalDateTime value) {
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
	public void setByteArray(String key, byte[] value) {
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
}
