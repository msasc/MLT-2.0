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

/**
 * A list of values of the same type.
 * @author Miquel Sas
 */
public class ValueList extends ArrayList<Value> implements Comparable<Object> {

	/**
	 * The field that defines the type of this value list.
	 */
	private Field field;

	/**
	 * Constructor assigning the required field to know the content type of the list.
	 * @param field The field that defines the type of the list.
	 */
	public ValueList(Field field) {
		if (field == null) throw new NullPointerException();
		this.field = field;
	}

	/**
	 * Add a value to the list. Validates that the value type acceptable for the field definition.
	 * @param value The value to add.
	 * @return
	 */
	public boolean add(Value value) {
		field.validate(value);
		return super.add(value);
	}
	/**
	 * Insert a value into the list. Validates that the value type acceptable for the field
	 * definition.
	 * @param index The index to insert.
	 * @param value The value to insert.
	 */
	public void add(int index, Value value) {
		field.validate(value);
		super.add(index, value);
	}

	/**
	 * Compares this object with the specified object for order. Returns a negative integer, zero,
	 * or a positive integer as this object is less than, equal to, or greater than the specified
	 * object.
	 * @param obj the object to be compared.
	 * @return negative integer, zero, or a positive integer as this object is less than, equal to,
	 * or greater than the specified object.
	 */
	@Override
	public int compareTo(Object obj) {
		if (obj == null) throw new NullPointerException();
		if (!(obj instanceof ValueList)) throw new UnsupportedOperationException("Not comparable type: " + obj.getClass());
		ValueList valueList = (ValueList) obj;
		int size = Math.max(size(), valueList.size());
		for (int i = 0; i < size; i++) {
			if (i >= size()) return -1;
			if (i >= valueList.size()) return 1;
			int compare = get(i).compareTo(valueList.get(i));
			if (compare != 0) return compare;
		}
		return 0;
	}

	/**
	 * Returns a JSON representation of this value list.
	 * @return A JSON representation of this value list.
	 */
	public JSONDoc toJSONDoc() {
		JSONDoc doc = new JSONDoc();
		doc.setList("values", toJSONList());
		return doc;
	}
	/**
	 * Returns a JSONList representation of this value list.
	 * @return A JSONList representation of this value list.
	 */
	public JSONList toJSONList() {
		JSONList list = new JSONList();
		forEach(value -> Value.addValue(list, value));
		return list;
	}
}
