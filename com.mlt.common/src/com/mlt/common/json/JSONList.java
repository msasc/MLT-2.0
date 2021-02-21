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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A list (array) of values.
 * @author Miquel Sas
 */
public class JSONList {

	/**
	 * Internal list.
	 */
	private List<Entry> list = new ArrayList<>();

	/**
	 * Add a boolean value.
	 * @param value The value.
	 */
	public void addBoolean(Boolean value) {
		list.add(new Entry(Type.BOOLEAN, value));
	}
	/**
	 * Add a boolean value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addBoolean(int index, Boolean value) {
		list.add(index, new Entry(Type.BOOLEAN, value));
	}

	/**
	 * Add a decimal value.
	 * @param value The value.
	 */
	public void addDecimal(BigDecimal value) {
		list.add(new Entry(Type.DECIMAL, value));
	}
	/**
	 * Add a decimal value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addDecimal(int index, BigDecimal value) {
		list.add(index, new Entry(Type.DECIMAL, value));
	}
	/**
	 * Add a double value.
	 * @param value The value.
	 */
	public void addDouble(Double value) {
		list.add(new Entry(Type.DOUBLE, value));
	}
	/**
	 * Add a double value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addDouble(int index, Double value) {
		list.add(index, new Entry(Type.DOUBLE, value));
	}
	/**
	 * Add an integer value.
	 * @param value The value.
	 */
	public void addInteger(Integer value) {
		list.add(new Entry(Type.INTEGER, value));
	}
	/**
	 * Add an integer value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addInteger(int index, Integer value) {
		list.add(index, new Entry(Type.INTEGER, value));
	}
	/**
	 * Add a long value.
	 * @param value The value.
	 */
	public void addLong(Long value) {
		list.add(new Entry(Type.LONG, value));
	}
	/**
	 * Add a long value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addLong(int index, Long value) {
		list.add(index, new Entry(Type.LONG, value));
	}

	/**
	 * Add a date value.
	 * @param value The value.
	 */
	public void addDate(LocalDate value) {
		list.add(new Entry(Type.DATE, value));
	}
	/**
	 * Add a date value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addDate(int index, LocalDate value) {
		list.add(index, new Entry(Type.DATE, value));
	}
	/**
	 * Add a time value.
	 * @param value The value.
	 */
	public void addTime(LocalTime value) {
		list.add(new Entry(Type.TIME, value));
	}
	/**
	 * Add a time value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addTime(int index, LocalTime value) {
		list.add(index, new Entry(Type.TIME, value));
	}
	/**
	 * Add a timestamp value.
	 * @param value The value.
	 */
	public void addTimestamp(LocalDateTime value) {
		list.add(new Entry(Type.TIMESTAMP, value));
	}
	/**
	 * Add a timestamp value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addTimestamp(int index, LocalDateTime value) {
		list.add(index, new Entry(Type.TIMESTAMP, value));
	}

	/**
	 * Add a string value.
	 * @param value The value.
	 */
	public void addString(String value) {
		list.add(new Entry(Type.STRING, value));
	}
	/**
	 * Add a string value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addString(int index, String value) {
		list.add(index, new Entry(Type.STRING, value));
	}

	/**
	 * Add a binary value.
	 * @param value The value.
	 */
	public void addBinary(byte[] value) {
		list.add(new Entry(Type.BINARY, value));
	}
	/**
	 * Add a binary value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addBinary(int index, byte[] value) {
		list.add(index, new Entry(Type.BINARY, value));
	}

	/**
	 * Add a document value.
	 * @param value The value.
	 */
	public void addDocument(JSONDoc value) {
		list.add(new Entry(Type.DOCUMENT, value));
	}
	/**
	 * Add a document value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addDocument(int index, JSONDoc value) {
		list.add(index, new Entry(Type.DOCUMENT, value));
	}
	/**
	 * Add a list value.
	 * @param value The value.
	 */
	public void addList(JSONList value) {
		list.add(new Entry(Type.LIST, value));
	}
	/**
	 * Add a list value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void addList(int index, JSONList value) {
		list.add(index, new Entry(Type.LIST, value));
	}

	/**
	 * Returns a boolean value.
	 * @param index The index.
	 * @return A boolean value.
	 */
	public Boolean getBoolean(int index) {
		Entry entry = list.get(index);
		if (entry.type != Type.BOOLEAN) throw new IllegalStateException("Invalid entry type");
		return (Boolean) entry.value;
	}

	/**
	 * Returns a decimal value.
	 * @param index The index.
	 * @return A decimal value.
	 */
	public BigDecimal getDecimal(int index) {
		Entry entry = list.get(index);
		if (!entry.type.isNumber()) throw new IllegalStateException("Invalid entry type");
		if (entry.value == null) return null;
		if (entry.type == Type.DECIMAL) return (BigDecimal) entry.value;
		if (entry.type == Type.DOUBLE) return BigDecimal.valueOf((Double) entry.value);
		if (entry.type == Type.INTEGER) return BigDecimal.valueOf((Integer) entry.value);
		if (entry.type == Type.LONG) return BigDecimal.valueOf((Long) entry.value);
		return null;
	}
	/**
	 * Returns a double value.
	 * @param index The index.
	 * @return A double value.
	 */
	public Double getDouble(int index) {
		Entry entry = list.get(index);
		if (!entry.type.isNumber()) throw new IllegalStateException("Invalid entry type");
		if (entry.value == null) return null;
		if (entry.type == Type.DECIMAL) return ((BigDecimal) entry.value).doubleValue();
		if (entry.type == Type.DOUBLE) return ((Double) entry.value).doubleValue();
		if (entry.type == Type.INTEGER) return ((Integer) entry.value).doubleValue();
		if (entry.type == Type.LONG) return ((Long) entry.value).doubleValue();
		return null;
	}
	/**
	 * Returns an integer value.
	 * @param index The index.
	 * @return An integer value.
	 */
	public Integer getInteger(int index) {
		Entry entry = list.get(index);
		if (!entry.type.isNumber()) throw new IllegalStateException("Invalid entry type");
		if (entry.value == null) return null;
		if (entry.type == Type.DECIMAL) return ((BigDecimal) entry.value).intValue();
		if (entry.type == Type.DOUBLE) return ((Double) entry.value).intValue();
		if (entry.type == Type.INTEGER) return ((Integer) entry.value).intValue();
		if (entry.type == Type.LONG) return ((Long) entry.value).intValue();
		return null;
	}
	/**
	 * Returns a long value.
	 * @param index The index.
	 * @return A long value.
	 */
	public Long getLong(int index) {
		Entry entry = list.get(index);
		if (!entry.type.isNumber()) throw new IllegalStateException("Invalid entry type");
		if (entry.value == null) return null;
		if (entry.type == Type.DECIMAL) return ((BigDecimal) entry.value).longValue();
		if (entry.type == Type.DOUBLE) return ((Double) entry.value).longValue();
		if (entry.type == Type.INTEGER) return ((Integer) entry.value).longValue();
		if (entry.type == Type.LONG) return ((Long) entry.value).longValue();
		return null;
	}

	/**
	 * Returns a date value.
	 * @param index The index.
	 * @return A date value.
	 */
	public LocalDate getDate(int index) {
		Entry entry = list.get(index);
		if (entry.type != Type.DATE) throw new IllegalStateException("Invalid entry type");
		return (LocalDate) entry.value;
	}
	/**
	 * Returns a time value.
	 * @param index The index.
	 * @return A time value.
	 */
	public LocalTime getTime(int index) {
		Entry entry = list.get(index);
		if (entry.type != Type.TIME) throw new IllegalStateException("Invalid entry type");
		return (LocalTime) entry.value;
	}
	/**
	 * Returns a timestamp value.
	 * @param index The index.
	 * @return A timestamp value.
	 */
	public LocalDateTime getTimstamp(int index) {
		Entry entry = list.get(index);
		if (entry.type != Type.TIMESTAMP) throw new IllegalStateException("Invalid entry type");
		return (LocalDateTime) entry.value;
	}

	/**
	 * Returns a string value.
	 * @param index The index.
	 * @return A string value.
	 */
	public String getString(int index) {
		Entry entry = list.get(index);
		if (entry.type != Type.STRING) throw new IllegalStateException("Invalid entry type");
		return (String) entry.value;
	}

	/**
	 * Returns a binary value.
	 * @param index The index.
	 * @return A binary value.
	 */
	public byte[] getBinary(int index) {
		Entry entry = list.get(index);
		if (entry.type != Type.BINARY) throw new IllegalStateException("Invalid entry type");
		return (byte[]) entry.value;
	}

	/**
	 * Returns a document value.
	 * @param index The index.
	 * @return A document value.
	 */
	public JSONDoc getDocument(int index) {
		Entry entry = list.get(index);
		if (entry.type != Type.DOCUMENT) throw new IllegalStateException("Invalid entry type");
		return (JSONDoc) entry.value;
	}
	/**
	 * Returns a list value.
	 * @param index The index.
	 * @return A list value.
	 */
	public JSONList getList(int index) {
		Entry entry = list.get(index);
		if (entry.type != Type.LIST) throw new IllegalStateException("Invalid entry type");
		return (JSONList) entry.value;
	}

	/**
	 * Remove the entry at index.
	 * @param index The index.
	 */
	public void remove(int index) {
		list.remove(index);
	}

	/**
	 * Set a boolean value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setBoolean(int index, Boolean value) {
		list.set(index, new Entry(Type.BOOLEAN, value));
	}

	/**
	 * Set a decimal value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setDecimal(int index, BigDecimal value) {
		list.set(index, new Entry(Type.DECIMAL, value));
	}
	/**
	 * Set a double value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setDouble(int index, Double value) {
		list.set(index, new Entry(Type.DOUBLE, value));
	}
	/**
	 * Set an integer value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setInteger(int index, Integer value) {
		list.set(index, new Entry(Type.INTEGER, value));
	}
	/**
	 * Set a long value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setLong(int index, Long value) {
		list.set(index, new Entry(Type.LONG, value));
	}

	/**
	 * Set a date value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setDate(int index, LocalDate value) {
		list.set(index, new Entry(Type.DATE, value));
	}
	/**
	 * Set a time value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setTime(int index, LocalTime value) {
		list.set(index, new Entry(Type.TIME, value));
	}
	/**
	 * Set a timestamp value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setTimestamp(int index, LocalDateTime value) {
		list.set(index, new Entry(Type.TIMESTAMP, value));
	}

	/**
	 * Set a string value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setString(int index, String value) {
		list.set(index, new Entry(Type.STRING, value));
	}

	/**
	 * Set a binary value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setBinary(int index, byte[] value) {
		list.set(index, new Entry(Type.BINARY, value));
	}

	/**
	 * Set a document value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setDocument(int index, JSONDoc value) {
		list.set(index, new Entry(Type.DOCUMENT, value));
	}
	/**
	 * Set a list value.
	 * @param index The index.
	 * @param value The value.
	 */
	public void setList(int index, JSONList value) {
		list.set(index, new Entry(Type.LIST, value));
	}

	/**
	 * Returns a boolean indicating whether the list is empty.
	 * @return A boolean indicating whether the list is empty.
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	/**
	 * Returns the size or number of elements.
	 * @return The size or number of elements.
	 */
	public int size() {
		return list.size();
	}
	/**
	 * Returns an iterator on the entris of this list.
	 * @return The iterator.
	 */
	public Iterator<Entry> entries() {
		return list.iterator();
	}
}
