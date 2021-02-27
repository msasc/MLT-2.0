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

import com.mlt.common.json.JSONDoc;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * A document that conforms to a schema or not.
 * <p>
 * When a document conforms to a schema, values are handled by an array and an unique reference to
 * the schema and field map is hold by all documents in a collection.
 * <p>
 * When a document does not conform to a schema, values are handles by a map, and a different map is
 * handled by each document in a collection.
 * @author Miquel Sas
 */
public class Document {

	/**
	 * The schema that describes the document structure.
	 */
	private Schema schema;
	/**
	 * Map or array of values.
	 */
	private Object values;

	/**
	 * Constructor of a document that does not conform to a schema. The value will be handles by a
	 * different map for each document.
	 */
	public Document() {
		this.schema = null;
		this.values = new HashMap<String, Value>();
	}
	/**
	 * Constructor of a document that conforms to a schema.
	 * @param schema The schema that describes the structure.
	 */
	public Document(Schema schema) {
		this.schema = schema;
		this.values = new Value[schema.fields().size()];
	}
	/**
	 * Constructor of a document that conforms to a schema, assigning a list of values that must
	 * match the schema structure.
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
	public Schema getSchema() {
		return schema;
	}

	/**
	 * @param key The field key, name or alias.
	 * @return The corresponding value.
	 */
	public Value getValue(String key) {
		if (schema != null) {
			int index = schema.fields().getIndex(key);
			if (index < 0) throw new IllegalArgumentException("Invalid field key: " + key);
			return ((Value[]) values)[index];
		} else {
			return ((HashMap<String, Value>) values).get(key);
		}
	}
	/**
	 * Set the value for the given key. No validation is made on the value.
	 * @param key   The key.
	 * @param value The value.
	 */
	public void setValue(String key, Value value) {
		setValue(key, value, false);
	}
	/**
	 * Set the value for the given key.
	 * @param key      The key.
	 * @param value    The value.
	 * @param validate A boolean to force value validation agains the field in a document that
	 *                 conforms with a schema.
	 */
	public void setValue(String key, Value value, boolean validate) {
		if (schema != null) {
			if (validate) schema.fields().getField(key).validate(value);
			int index = schema.fields().getIndex(key);
			((Value[]) values)[index] = value;
		} else {
			((HashMap<String, Value>) values).put(key, value);
		}
	}

	/**
	 * Returns an unmodifiable collection with the list of keys.
	 * @return A collection with the list of keys.
	 */
	public Collection<String> keys() {
		if (schema != null) {
			return schema.fields().keys();
		} else {
			return Collections.unmodifiableCollection(((HashMap<String, Value>) values).keySet());
		}
	}

	/**
	 * Returns a JSON representation of this document.
	 * @return A JSON representation of this document.
	 */
	public JSONDoc toJSONDoc() {
		JSONDoc doc = new JSONDoc();
		Collection<String> keys = keys();
		for (String key : keys) {
			Value.setValue(doc, key, getValue(key));
		}
		return doc;
	}
}
