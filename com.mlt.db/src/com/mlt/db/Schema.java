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
import com.mlt.common.json.JSONList;

import java.util.ArrayList;
import java.util.List;

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
	 * The field map associated to this schema.
	 */
	private FieldMap fieldMap;
	/**
	 * List of indexes.
	 */
	private List<Index> indexes;

	/**
	 * Constructor assigning name and description.
	 * @param name        The schema name.
	 * @param description The description.
	 */
	public Schema(String name, String description) {
		if (name == null) throw new NullPointerException();
		this.name = name;
		this.description = description;
		this.fieldMap = new FieldMap();
		this.indexes = new ArrayList<>();
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
	 * Returns the map of fields.
	 * @return The map of fields.
	 */
	public FieldMap fields() {
		return fieldMap;
	}
	/**
	 * Returns the list of indexes.
	 * @return The list of indexes.
	 */
	public List<Index> indexes() {
		return indexes;
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
		return fieldMap.equals(s.fieldMap);
	}
	/**
	 * Returns a suitable hash code.
	 * @return A suitable hash code.
	 */
	@Override
	public int hashCode() {
		return fieldMap.hashCode();
	}

	/**
	 * Returns a JSON representation of this schema.
	 * @return A JSON representation of this schema.
	 */
	public JSONDoc toJSONDoc() {
		JSONDoc doc = new JSONDoc();
		if (getName() != null) {
			doc.setString("name", getName());
		}
		if (getDescription() != null) {
			doc.setString("description", getDescription());
		}
		if (!fieldMap.isEmpty()) {
			JSONDoc fields_doc = new JSONDoc();
			for (Field entry : fieldMap.fields()) {
				fields_doc.setDocument(entry.getAlias(), entry.toJSONDocument());
			}
			doc.setDocument("fields", fields_doc);
		}
		if (!indexes.isEmpty()) {
			JSONList index_list = new JSONList();
			for (Index index : indexes) {
				index_list.addDocument(index.toJSONDoc());
			}
			doc.setList("indexes", index_list);
		}
		return doc;
	}
}
