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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A map of fields backed by a {@link LinkedHashMap} and using the alias as key. It also maintains a
 * list of corresponding indexes to map the values to an array in documents that conforms to a
 * schema, thus avoiding the need of a map of values per document.
 * @author Miquel Sas
 */
public class FieldMap {

	/**
	 * Map of fields.
	 */
	private Map<String, Field> fields;
	/**
	 * Map of value indexes within the corresponding document.
	 */
	private Map<String, Integer> indexes;

	/**
	 * Default constructor.
	 */
	public FieldMap() {
		fields = new LinkedHashMap<>();
		indexes = new LinkedHashMap<>();
	}
	/**
	 * Copy constructor.
	 * @param fieldMap The source field map.
	 */
	public FieldMap(FieldMap fieldMap) {
		fields = new LinkedHashMap<>(fieldMap.fields);
		indexes = new LinkedHashMap<>(fieldMap.indexes);
	}

	/**
	 * Returns the field or null if none is found.
	 * @param key The key or field name or alias.
	 * @return The field or null if none is found.
	 */
	public Field getField(String key) {
		return fields.get(key);
	}
	/**
	 * Returns the index of the value within the document.
	 * @param key The key or field name or alias.
	 * @return The index of the value within the document.
	 */
	public int getIndex(String key) {
		Integer valueIndex = indexes.get(key);
		return valueIndex == null ? -1 : valueIndex;
	}

	/**
	 * Put or add the field.
	 * @param field The field.
	 */
	public void putField(Field field) {
		String key = field.getAlias();
		int valueIndex = fields.size();
		fields.put(key, field);
		indexes.put(key, valueIndex);
	}

	/**
	 * Remove the given field.
	 * @param field The field entry to remove.
	 */
	public void remove(Field field) {
		remove(field.getAlias());
	}
	/**
	 * Remoove the entry with the given key.
	 * @param key The key to remove.
	 */
	public void remove(String key) {
		fields.remove(key);
		indexes.clear();
		int i = 0;
		for (String k : fields.keySet()) {
			indexes.put(k, i++);
		}
	}

	/**
	 * Returns an unmodifiable collection with the list of keys.
	 * @return A collection with the list of keys.
	 */
	public Collection<String> keys() {
		return Collections.unmodifiableCollection(fields.keySet());
	}
	/**
	 * Returns an unmodifiable collection with the list of fields.
	 * @return A collection with the list of fields.
	 */
	public Collection<Field> fields() {
		return Collections.unmodifiableCollection(fields.values());
	}
	/**
	 * Returns an unmodifiable collection with the list of value indexes.
	 * @return A collection with the list of value indexes.
	 */
	public Collection<Integer> indexes() {
		return Collections.unmodifiableCollection(indexes.values());
	}

	/**
	 * Returns a boolean indicating whether this field map is empty.
	 * @return A boolean indicating whether this field map is empty.
	 */
	public boolean isEmpty() {
		return fields.isEmpty();
	}
	/**
	 * Returns the size or number of fields.
	 * @return The size or number of fields.
	 */
	public int size() {
		return fields.size();
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param obj the reference object with which to compare.
	 * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		FieldMap fieldMap = (FieldMap) obj;
		return fields.equals(fieldMap.fields);
	}
	/**
	 * Returns a suitable hash code.
	 * @return A suitable hash code.
	 */
	@Override
	public int hashCode() {
		int hash = 5;
		for (Field e : fields.values()) hash |= e.hashCode();
		return hash;
	}
}
