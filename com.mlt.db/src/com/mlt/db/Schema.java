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

import com.mlt.db.json.JSONDocument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Describes the structure of a collection of documents.
 * @author Miquel Sas
 */
public class Schema {

	/**
	 * Name of the schema.
	 */
	private String name;
	/**
	 * An optional description.
	 */
	private String description;
	/**
	 * Map of fields.
	 */
	private Map<String, Field> fields;
	/**
	 * Map of value indexes within the corresponding document.
	 */
	private Map<String, Integer> valueIndexes;
	/**
	 * List of indexes.
	 */
	private List<Index> indexes;
	/**
	 * Default and unique constructor.
	 */
	public Schema() {
		fields = new LinkedHashMap<>();
		valueIndexes = new LinkedHashMap<>();
		indexes = new ArrayList<>();
	}
	/**
	 * Constructor assigning name and description.
	 * @param name        The schema name.
	 * @param description The description.
	 */
	public Schema(String name, String description) {
		this();
		this.name = name;
		this.description = description;
	}

	/**
	 * Returns the schema name.
	 * @return The schema name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set the name of the schema.
	 * @param name The name of the schema.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Return the description.
	 * @return The description of the schema.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set the description of the schema.
	 * @param description The description.
	 */
	public void setDescription(String description) {
		this.description = description;
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
	public int getValueIndex(String key) {
		Integer valueIndex = valueIndexes.get(key);
		return valueIndex == null ? -1 : valueIndex;
	}

	/**
	 * @param field The field.
	 */
	public void putField(Field field) {
		String key = field.getAlias();
		int valueIndex = fields.size();
		fields.put(key, field);
		valueIndexes.put(key, valueIndex);
	}
	/**
	 * @return A collection with the schema fields.
	 */
	public Collection<Field> fields() {
		return Collections.unmodifiableCollection(fields.values());
	}

	/**
	 * @return The size or number of entries in the schema.
	 */
	public int fieldCount() {
		return fields.size();
	}

	/**
	 * Adds an index to the list of indexes.
	 * @param index The index to add.
	 */
	public void addIndex(Index index) {
		indexes.add(index);
	}
	/**
	 * Returns the index at the argument position.
	 * @param pos The index position.
	 * @return The index at the argument position.
	 */
	public Index getIndex(int pos) {
		return indexes.get(pos);
	}
	/**
	 * Returns the number of indexes.
	 * @return The number of indexes.
	 */
	public int indexCount() {
		return indexes.size();
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
		Schema s = (Schema) obj;
		return fields.equals(s.fields);
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

	/**
	 * Returns a JSON representation of this field.
	 * @return A JSON representation of this field.
	 */
	public JSONDocument toJSON() {
		JSONDocument doc = new JSONDocument();
		if (getName() != null) {
			doc.setString("name", getName());
		}
		if (getDescription() != null) {
			doc.setString("description", getDescription());
		}
		if (!fields.isEmpty()) {
			JSONDocument fields_doc = new JSONDocument();
			for (Field entry : fields.values()) {
				fields_doc.setDocument(entry.getAlias(), entry.toJSON());
			}
			doc.setDocument("fields", fields_doc);
		}
		if (!indexes.isEmpty()) {

		}
		return doc;
	}

}
