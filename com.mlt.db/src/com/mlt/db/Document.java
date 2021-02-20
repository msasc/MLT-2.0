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

import com.mlt.db.json_backup.JSONDocument;

import java.util.Collection;

/**
 * A Document handles a row in a relational or column database such as PostgreSQL or Hadoop, and a
 * document in a document database such as MongoDB.
 * @author Miquel Sas
 */
public class Document {

	/**
	 * The schema that describes the document structure.
	 */
	private Schema schema;
	/**
	 * Array of values.
	 */
	private Value[] values;

	/**
	 * Constructor assigning the schema.
	 * @param schema The schema that describes the structure.
	 */
	public Document(Schema schema) {
		this.schema = schema;
		this.values = new Value[schema.fields().size()];
	}

	/**
	 * Constructor assigning the schema and a list of values that must match the schema structure.
	 * @param schema The schema that describes the structure.
	 * @param values The list of values that must match the schema structure.
	 */
	public Document(Schema schema, Value[] values) {
		if (schema == null) throw new NullPointerException();
		if (values == null) throw new NullPointerException();
		this.schema = schema;
		this.values = values;
	}

	/**
	 * @return The schema that describes the structure.
	 */
	public Schema getSchema() { return schema; }

	/**
	 * @param key The field key, name or alias.
	 * @return The corresponding value.
	 */
	public Value getValue(String key) {
		int index = schema.fields().getIndex(key);
		if (index < 0) throw new IllegalArgumentException("Invalid field key: " + key);
		return values[index];
	}
	/**
	 * Set the value for the given key.
	 * @param key The key.
	 * @param value The value.
	 */
	public void setValue(String key, Value value) {
		setValue(key, value, true);
	}
	/**
	 * Set the value for the given key.
	 * @param key The key.
	 * @param value The value.
	 * @param validate A boolean to force value validation.
	 */
	public void setValue(String key, Value value, boolean validate) {
		if (validate) schema.fields().getField(key).validate(value);
		int index = schema.fields().getIndex(key);
		values[index] = value;
	}

	/**
	 * Returns an unmodifiable collection with the list of keys.
	 * @return A collection with the list of keys.
	 */
	public Collection<String> keys() {
		return schema.fields().keys();
	}

	/**
	 * Returns a JSON representation of this document.
	 * @return A JSON representation of this document.
	 */
	public JSONDocument toJSONDocument() {
		JSONDocument doc = new JSONDocument();
		Collection<String> keys = keys();
		for (String key : keys) {
			Field field = getSchema().fields().getField(key);
			Value value = getValue(key);
			doc.setValue(key, value);
		}
		return doc;
	}
}
