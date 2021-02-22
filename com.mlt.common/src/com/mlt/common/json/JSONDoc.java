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

import com.mlt.common.lang.Strings;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A JSON object is an ordered collection of name/value pairs, externally represented as a string
 * formatted following the ECMA-404 specification.
 * @author Miquel Sas
 */
public class JSONDoc {

	/**
	 * Map ordered by insertion.
	 */
	private Map<String, Entry> map = new LinkedHashMap<>();

	/**
	 * Append the argument document.
	 * @param doc The document to append.
	 */
	public void append(JSONDoc doc) {
		map.putAll(doc.map);
	}

	/**
	 * Return the boolean value or null if not exists or is not a boolean.
	 * @param key The key.
	 * @return The boolean value.
	 */
	public Boolean getBoolean(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (entry.type != Type.BOOLEAN) throw new IllegalStateException("Invalid entry type");
		return (Boolean) entry.value;
	}

	/**
	 * Return the decimal value or null if not exists or is not a number.
	 * @param key The key.
	 * @return The decimal value.
	 */
	public BigDecimal getDecimal(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (!entry.type.isNumber()) throw new IllegalStateException("Invalid entry type");
		if (entry.value == null) return null;
		if (entry.type == Type.DECIMAL) return (BigDecimal) entry.value;
		if (entry.type == Type.DOUBLE) return BigDecimal.valueOf((Double) entry.value);
		if (entry.type == Type.INTEGER) return BigDecimal.valueOf((Integer) entry.value);
		if (entry.type == Type.LONG) return BigDecimal.valueOf((Long) entry.value);
		return null;
	}
	/**
	 * Return the double value or null if not exists or is not a number.
	 * @param key The key.
	 * @return The double value.
	 */
	public Double getDouble(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (!entry.type.isNumber()) throw new IllegalStateException("Invalid entry type");
		if (entry.value == null) return null;
		if (entry.type == Type.DECIMAL) return ((BigDecimal) entry.value).doubleValue();
		if (entry.type == Type.DOUBLE) return ((Double) entry.value).doubleValue();
		if (entry.type == Type.INTEGER) return ((Integer) entry.value).doubleValue();
		if (entry.type == Type.LONG) return ((Long) entry.value).doubleValue();
		return null;
	}
	/**
	 * Return the integer value or null if not exists or is not a number.
	 * @param key The key.
	 * @return The integer value.
	 */
	public Integer getInteger(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (!entry.type.isNumber()) throw new IllegalStateException("Invalid entry type");
		if (entry.value == null) return null;
		if (entry.type == Type.DECIMAL) return ((BigDecimal) entry.value).intValue();
		if (entry.type == Type.DOUBLE) return ((Double) entry.value).intValue();
		if (entry.type == Type.INTEGER) return ((Integer) entry.value).intValue();
		if (entry.type == Type.LONG) return ((Long) entry.value).intValue();
		return null;
	}
	/**
	 * Return the long value or null if not exists or is not a number.
	 * @param key The key.
	 * @return The long value.
	 */
	public Long getLong(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (!entry.type.isNumber()) throw new IllegalStateException("Invalid entry type");
		if (entry.value == null) return null;
		if (entry.type == Type.DECIMAL) return ((BigDecimal) entry.value).longValue();
		if (entry.type == Type.DOUBLE) return ((Double) entry.value).longValue();
		if (entry.type == Type.INTEGER) return ((Integer) entry.value).longValue();
		if (entry.type == Type.LONG) return ((Long) entry.value).longValue();
		return null;
	}

	/**
	 * Return the date value or null if not exists or is not a boolean.
	 * @param key The key.
	 * @return The date value.
	 */
	public LocalDate getDate(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (entry.type != Type.DATE) throw new IllegalStateException("Invalid entry type");
		return (LocalDate) entry.value;
	}
	/**
	 * Return the time value or null if not exists or is not a boolean.
	 * @param key The key.
	 * @return The time value.
	 */
	public LocalTime getTime(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (entry.type != Type.TIME) throw new IllegalStateException("Invalid entry type");
		return (LocalTime) entry.value;
	}
	/**
	 * Return the timestamp value or null if not exists or is not a boolean.
	 * @param key The key.
	 * @return The timestamp value.
	 */
	public LocalDateTime getTimestamp(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (entry.type != Type.TIMESTAMP) throw new IllegalStateException("Invalid entry type");
		return (LocalDateTime) entry.value;
	}

	/**
	 * Return the string value or null if not exists or is not a boolean.
	 * @param key The key.
	 * @return The string value.
	 */
	public String getString(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (entry.type != Type.STRING) throw new IllegalStateException("Invalid entry type");
		return (String) entry.value;
	}

	/**
	 * Return the binary value or null if not exists or is not a boolean.
	 * @param key The key.
	 * @return The binary value.
	 */
	public byte[] getBinary(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (entry.type != Type.BINARY) throw new IllegalStateException("Invalid entry type");
		return (byte[]) entry.value;
	}

	/**
	 * Return the document value or null if not exists or is not a boolean.
	 * @param key The key.
	 * @return The document value.
	 */
	public JSONDoc getDocument(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (entry.type != Type.DOCUMENT) throw new IllegalStateException("Invalid entry type");
		return (JSONDoc) entry.value;
	}
	/**
	 * Return the list value or null if not exists or is not a boolean.
	 * @param key The key.
	 * @return The list value.
	 */
	public JSONList getList(String key) {
		Entry entry = get(key);
		if (entry == null) return null;
		if (entry.type != Type.LIST) throw new IllegalStateException("Invalid entry type");
		return (JSONList) entry.value;
	}

	/**
	 * Set a boolean value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setBoolean(String key, Boolean value) {
		set(key, Type.BOOLEAN, value);
	}

	/**
	 * Set a decimal value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setDecimal(String key, BigDecimal value) {
		set(key, Type.DECIMAL, value);
	}
	/**
	 * Set a double value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setDouble(String key, Double value) {
		set(key, Type.DOUBLE, value);
	}
	/**
	 * Set an integer value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setInteger(String key, Integer value) {
		set(key, Type.INTEGER, value);
	}
	/**
	 * Set a long value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setLong(String key, Long value) {
		set(key, Type.LONG, value);
	}

	/**
	 * Set a date value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setDate(String key, LocalDate value) {
		set(key, Type.DATE, value);
	}
	/**
	 * Set a time value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setTime(String key, LocalTime value) {
		set(key, Type.TIME, value);
	}
	/**
	 * Set a timestamp value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setTimestamp(String key, LocalDateTime value) {
		set(key, Type.TIMESTAMP, value);
	}

	/**
	 * Set a string value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setString(String key, String value) {
		set(key, Type.STRING, value);
	}

	/**
	 * Set a binary value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setBinary(String key, byte[] value) {
		set(key, Type.BINARY, value);
	}

	/**
	 * Set an object or document value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setDocument(String key, JSONDoc value) {
		set(key, Type.DOCUMENT, value);
	}
	/**
	 * Set an list value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setList(String key, JSONList value) {
		set(key, Type.LIST, value);
	}

	/**
	 * Clear the document.
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * Returns the collection of keys.
	 * @return The collection of keys.
	 */
	public Set<String> keys() {
		return map.keySet();
	}

	public String toString() {
		try {
			StringWriter w = new StringWriter();
			write(w);
			return w.toString();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
		return null;
	}

	/**
	 * Write this JSONDocument.
	 * @param w The writer.
	 * @throws IOException If any IO error occurs.
	 */
	public void write(Writer w) throws IOException {
		w.write("{");
		Iterator<String> i = keys().iterator();
		while (i.hasNext()) {
			String key = i.next();
			w.write("\"" + key + "\":");
			Entry entry = map.get(key);
			writeEntry(w, entry);
			if (i.hasNext()) w.write(",");
		}
		w.write("}");
	}
	/**
	 * Write an antry value.
	 * @param w     The writer.
	 * @param entry The entry.
	 * @throws IOException If an IO error occurs.
	 */
	private void writeEntry(Writer w, Entry entry) throws IOException {
		if (entry.value == null) {
			w.write("null");
		} else {
			switch (entry.type) {
			case BOOLEAN:
			case DECIMAL:
			case DOUBLE:
			case INTEGER:
			case LONG:
				w.write(entry.value.toString());
				break;
			case DATE:
			case TIME:
			case TIMESTAMP:
			case STRING:
				w.write("\"" + entry.value.toString() + "\"");
				break;
			case BINARY:
				w.write("{");
				w.write("\"bin\":\"");
				byte[] bytes = (byte[]) entry.value;
				for (byte b : bytes) {
					w.write(Strings.leftPad(Integer.toHexString(b), 2, "0"));
				}
				w.write("\"}");
				break;
			case DOCUMENT:
				JSONDoc doc = (JSONDoc) entry.value;
				doc.write(w);
				break;
			case LIST:
				JSONList list = (JSONList) entry.value;
				w.write("[");
				Iterator<Entry> i = list.entries();
				while (i.hasNext()) {
					writeEntry(w, i.next());
					if (i.hasNext()) w.write(",");
				}
				w.write("]");
				break;
			}
		}
	}

	/**
	 * Return the entry with the given key.
	 * @param key The key.
	 * @return The entry.
	 */
	private Entry get(String key) {
		validateKey(key);
		return map.get(key);
	}
	/**
	 * Set the value.
	 * @param key   The key or field key.
	 * @param type  The type of the value.
	 * @param value The value itself.
	 */
	private void set(String key, Type type, Object value) {
		validateKey(key);
		put(key, new Entry(type, value));
	}
	/**
	 * Put the entry into the map. Also used by the JSONParser.
	 * @param key   The key.
	 * @param entry The entry.
	 */
	void put(String key, Entry entry) {
		map.put(key, entry);
	}

	/**
	 * Validates that the argument is not null and does not contain any forbidden escape character.
	 * @param key The key to validate.
	 */
	private void validateKey(String key) {
		if (key == null) throw new NullPointerException();
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			switch (c) {
			case '\"':
			case '\\':
			case '/':
			case '\b':
			case '\f':
			case '\n':
			case '\r':
			case '\t':
				throw new IllegalArgumentException("Key contains escape character " + Strings.toUnicode(c));
			}
		}
	}
}
