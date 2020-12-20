/*
 * Copyright (c) 2020. Miquel Sas
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A list of fields efficiently accessible by index or alias.
 *
 * @author Miquel Sas
 */
public class FieldList {

	/** Map of field indexes by alias. */
	private HashMap<String, Integer> map = new HashMap<>();
	/** List of fields. */
	private List<Field> fields = new ArrayList<>();
	/** The list of persistent fields. */
	private List<Field> persistentFields;
	/** The list of primary key fields. */
	private List<Field> primaryKeyFields;
	/** Primary order. */
	private Order primaryOrder;

	/** @param field The field to add. */
	public void addField(Field field) {
		fields.add(field);
		reset();
	}

	/** Reset the map, primary and persistent fields. */
	private void reset() {
		map.clear();
		if (primaryKeyFields != null) {
			primaryKeyFields.clear();
		}
		primaryKeyFields = null;
		if (persistentFields != null) {
			persistentFields.clear();
		}
		persistentFields = null;
		primaryOrder = null;
	}

	/** @return The number of fields. */
	public int size() { return fields.size(); }

	/** @return The map of fields. */
	private HashMap<String, Integer> getMap() {
		if (map.isEmpty()) {
			for (int index = 0; index < fields.size(); index++) {
				map.put(fields.get(index).getAlias(), index);
			}
		}
		return map;
	}

	/**
	 * @param alias The field alias.
	 * @return A boolean.
	 */
	public boolean containsField(String alias) { return (getFieldIndex(alias) >= 0); }

	/**
	 * @param index The index of the field.
	 * @return The field
	 */
	public Field getField(int index) {	return fields.get(index); }

	/**
	 * @return The field or null if not found.
	 * @param alias The field alias.
	 */
	public Field getField(String alias) {
		int index = getFieldIndex(alias);
		return (index == -1 ? null : FieldList.this.getField(index));
	}

	/**
	 * @return The field index or -1 if not found.
	 * @param alias The field alias.
	 */
	public int getFieldIndex(String alias) {
		Integer index = getMap().get(alias);
		if (index != null) {
			return index;
		}
		return -1;
	}

	/** @return The list of persistent fields. */
	public List<Field> getPersistentFields() {
		if (persistentFields == null) {
			persistentFields = new ArrayList<>();
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				if (field.isPersistent()) {
					persistentFields.add(field);
				}
			}
		}
		return persistentFields;
	}

	/** @return The list of primary key fields. */
	public List<Field> getPrimaryKeyFields() {
		if (primaryKeyFields == null) {
			primaryKeyFields = new ArrayList<>();
			for (Field field : fields) {
				if (field.isPrimaryKey()) {
					primaryKeyFields.add(field);
				}
			}
		}
		return primaryKeyFields;
	}

	/** @return The primary order. */
	public Order getPrimaryOrder() {
		if (primaryOrder == null) {
			primaryOrder = new Order();
			List<Field> pkFields = getPrimaryKeyFields();
			for (Field field : pkFields) {
				primaryOrder.add(field, true);
			}
		}
		return primaryOrder;
	}
}
